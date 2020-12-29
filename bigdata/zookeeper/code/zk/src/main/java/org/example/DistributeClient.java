package org.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Consts.*;

public class DistributeClient {

    private ZooKeeper zkClint = null;

    /**
     * 创建zk客户端连接
     */
    public void getConnect() throws IOException {
        zkClint = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 再次启动监听
                try {
                    getServerList();
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取服务器列表信息
     */
    public void getServerList() throws KeeperException, InterruptedException {
        // 1. 获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zkClint.getChildren(PARENT_NODE, true);

        // 2. 存储服务器列表
        List<String> servers = new ArrayList<>();

        // 3. 遍历所有节点，获取节点中的主机名称信息
        for(String child : children){
            byte[] data = zkClint.getData(PARENT_NODE + "/" + child, false, null);
            servers.add(new String(data));
        }

        // 4. 打印服务器列表信息
        System.out.println(servers);
    }

    /**
     * 业务功能
     */
    public void business() throws InterruptedException {
        System.out.println("client is working...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 1. 获取zk连接
        DistributeClient client = new DistributeClient();
        client.getConnect();

        // 2. 获取servers的子节点信息，从中获取服务器信息列表
        client.getServerList();

        // 3. 业务进程启动
        client.business();
    }
}
