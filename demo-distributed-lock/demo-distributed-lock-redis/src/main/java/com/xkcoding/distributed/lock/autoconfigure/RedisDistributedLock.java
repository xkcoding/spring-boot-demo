package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 基于 RedisTemplate 实现的分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 12:33
 */
public class RedisDistributedLock extends DistributedLock {
    private final StringRedisTemplate redisTemplate;
    /**
     * 锁的唯一标识，格式：主机标识(UUID)+线程编号
     * 防误删
     */
    private final String uniqueId;

    protected RedisDistributedLock(StringRedisTemplate redisTemplate, String uniqueIdPrefix, String lockKey, long lockTime,
                                   TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
        this.redisTemplate = redisTemplate;
        this.uniqueId = uniqueIdPrefix + ":" + Thread.currentThread().getId();
    }

    @Override
    public void lock() {
        // 加锁失败，自旋阻塞
        while (!tryLock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(lockTime, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        String script = "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 " +
            "then " +
            "   redis.call('hincrby', KEYS[1], ARGV[1], 1) " +
            "   redis.call('expire', KEYS[1], ARGV[2]) " +
            "   return 1 " +
            "else " +
            "   return 0 " +
            "end";

        long expire = unit.toSeconds(time);
        Boolean getLock = Optional.ofNullable(
                redisTemplate.execute(
                    new DefaultRedisScript<>(script, Boolean.class),
                    Collections.singletonList(lockKey),
                    uniqueId, String.valueOf(expire)))
            .orElse(Boolean.FALSE);
        // 如果获得锁，开启自动续期
        if (getLock) {
            renewLockTime(time, unit);
        }
        return getLock;
    }

    private void renewLockTime(long time, TimeUnit unit) {
        String script = "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 " +
            "then " +
            "   return redis.call('expire', KEYS[1], ARGV[2]) " +
            "else " +
            "   return 0 " +
            "end";

        long expire = unit.toSeconds(time);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Boolean renewed = Optional.ofNullable(
                    redisTemplate.execute(
                        new DefaultRedisScript<>(script, Boolean.class),
                        Collections.singletonList(lockKey),
                        uniqueId, String.valueOf(expire))
                ).orElse(Boolean.FALSE);
                // 续期成功，代表未被解锁，则需要进行下一次续期
                if (renewed) {
                    renewLockTime(time, unit);
                }
            }
        }, expire * 1000 / 3);
    }

    @Override
    public void unlock() {
        String script = "if redis.call('hexists', KEYS[1], ARGV[1]) == 0 " +
            "then " +
            "   return nil " +
            "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 " +
            "then " +
            "   return redis.call('del', KEYS[1]) " +
            "else " +
            "   redis.call('expire', KEYS[1], ARGV[2]) " +
            "   return 0 " +
            "end";

        // 如果解锁，发现是重入的，需要重新续期
        long expire = timeUnit.toSeconds(lockTime);
        Long flag = this.redisTemplate.execute(
            new DefaultRedisScript<>(script, Long.class),
            Collections.singletonList(lockKey),
            uniqueId, String.valueOf(expire)
        );
        if (flag == null) {
            throw new IllegalMonitorStateException("this lock doesn't belong to you!");
        }
    }

}
