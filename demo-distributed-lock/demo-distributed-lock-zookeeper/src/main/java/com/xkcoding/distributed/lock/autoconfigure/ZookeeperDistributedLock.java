package com.xkcoding.distributed.lock.autoconfigure;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xkcoding.distributed.lock.api.DistributedLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 基于 Zookeeper 实现的分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-05 19:35
 */
public class ZookeeperDistributedLock extends DistributedLock {

    private final ZooKeeper zooKeeper;

    private final String lockRootPath;

    private final String lockNodePath;

    private final ThreadLocal<Integer> LOCK_TIMES = new ThreadLocal<>();

    protected ZookeeperDistributedLock(ZkClient client, String lockKey, long lockTime, TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
        this.lockRootPath = client.getLockRootPath();
        this.zooKeeper = client.getZooKeeper();
        try {
            // 直接创建临时顺序节点
            this.lockNodePath = this.zooKeeper.create(client.getLockRootPath() + "/" + lockKey + "-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lock() {
        // 如果是锁重入，则次数加 1，然后直接返回
        Integer lockTimes = LOCK_TIMES.get();
        if (lockTimes != null && lockTimes > 0L) {
            LOCK_TIMES.set(lockTimes + 1);
            return;
        }

        try {
            // 当前节点是第一个临时节点，如果是，直接获得锁
            String previousLockNodePath = getPreviousNodePath(lockNodePath);
            if (StrUtil.isBlank(previousLockNodePath)) {
                LOCK_TIMES.set(1);
                return;
            } else {
                // 如果不是第一个临时节点，则给它的前一个临时节点添加监听删除事件（即锁释放）
                CountDownLatch wait = new CountDownLatch(1);
                Stat stat = this.zooKeeper.exists(lockRootPath + "/" + previousLockNodePath, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (Event.EventType.NodeDeleted == event.getType()) {
                            // 节点删除，通知获得锁，避免自旋判断影响性能
                            wait.countDown();
                        }
                    }
                });
                // 未被删除，则需要阻塞
                if (stat != null) {
                    wait.await();
                }
                // 获得锁，则重入次数设置为 1
                LOCK_TIMES.set(1);
            }
        } catch (Exception e) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            // 重新尝试获取锁
            lock();
        }

    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("ZookeeperDistributedLock`s tryLock method is unsupported");
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) {
        throw new UnsupportedOperationException("ZookeeperDistributedLock`s tryLock method is unsupported");
    }

    @Override
    public void unlock() {
        // -1 代表不使用乐观锁删除，类似 delete --force
        Integer lockTimes = LOCK_TIMES.get();
        if (lockTimes != null && lockTimes > 0) {
            // 计算剩下的重入次数
            int leftLockTime = lockTimes - 1;
            LOCK_TIMES.set(leftLockTime);
            // 剩下为 0 的时候，代表完全解锁，需要删除临时节点
            if (leftLockTime == 0) {
                try {
                    this.zooKeeper.delete(lockNodePath, -1);
                    LOCK_TIMES.remove();
                } catch (InterruptedException | KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 根据当前节点，获取前一个节点
     *
     * @param currentNodePath 当前节点路径
     * @return 前一个节点
     */
    private String getPreviousNodePath(String currentNodePath) {
        try {
            // 截取当前临时节点编号
            long currentNodeSeq = Long.parseLong(StrUtil.subAfter(currentNodePath, "-", true));
            // 获取锁根路径下的所有临时节点
            List<String> nodes = this.zooKeeper.getChildren(lockRootPath, false);

            if (CollUtil.isEmpty(nodes)) {
                return null;
            }

            // 用于标记遍历所有子节点时，离当前最近的一次编号
            long previousNodeSeq = 0L;
            String previousNodePath = null;
            for (String nodePath : nodes) {
                // 获取每个临时节点编号
                long nodeSeq = Long.parseLong(StrUtil.subAfter(nodePath, "-", true));
                if (nodeSeq < currentNodeSeq && nodeSeq > previousNodeSeq) {
                    previousNodeSeq = nodeSeq;
                    previousNodePath = nodePath;
                }
            }

            return previousNodePath;
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
