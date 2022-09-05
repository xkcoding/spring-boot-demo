package com.xkcoding.distributed.lock.autoconfigure;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * <p>
 * zookeeper 操作类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-05 19:50
 */
@Slf4j
public class ZkClient {
    /**
     * 连接地址
     */
    @Getter
    private final String connectServer;

    /**
     * 节点根路径，默认是 /locks
     */
    @Getter
    private final String lockRootPath;

    @Getter
    private ZooKeeper zooKeeper;

    private static final String DEFAULT_ROOT_PATH = "/locks";

    public ZkClient(String connectServer) {
        this.connectServer = connectServer;
        this.lockRootPath = DEFAULT_ROOT_PATH;
    }

    public ZkClient(String connectServer, String lockRootPath) {
        this.connectServer = connectServer;
        this.lockRootPath = lockRootPath;
    }

    public void init() {
        try {
            this.zooKeeper = new ZooKeeper(connectServer, 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState() && Event.EventType.None == watchedEvent.getType()) {
                        log.info("===========> zookeeper connected <===========");
                    }
                }
            });
            // 判断根节点是否存在，不存在则创建
            if (this.zooKeeper.exists(lockRootPath, false) == null) {
                this.zooKeeper.create(lockRootPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            log.error("===========> zookeeper connect error <===========");
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        if (this.zooKeeper != null) {
            try {
                zooKeeper.close();
                log.info("===========> zookeeper disconnected <===========");
            } catch (InterruptedException e) {
                log.error("===========> zookeeper disconnect error <===========");
                throw new RuntimeException(e);
            }
        }
    }
}
