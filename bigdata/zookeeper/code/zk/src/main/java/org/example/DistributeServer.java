package org.example;

import org.apache.zookeeper.*;
import static org.example.Consts.*;
import java.io.IOException;

public class DistributeServer {


    private ZooKeeper zkClient = null;


    /**
     * 创建到zk的客户端连接
     */
    public void getConnect() throws IOException {
        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
    }

    /**
     * 注册服务器
     */
    public void registerServer(String hostName) throws KeeperException, InterruptedException {
        String create = zkClient.create(PARENT_NODE+"/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + " is online " + create);
    }

    /**
     * 业务功能
     */
    public void business(String hostName) throws InterruptedException {
        System.out.println(hostName + " is working...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 1. 获取ZK连接
        DistributeServer server = new DistributeServer();
        server.getConnect();

        // 2. 利用ZK连接注册服务器信息
        String hostName = args[0];
        server.registerServer(hostName);

        // 3. 启动业务功能
        server.business(hostName);
    }
}
