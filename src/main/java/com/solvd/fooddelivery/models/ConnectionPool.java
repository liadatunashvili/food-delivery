package com.solvd.fooddelivery.models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static volatile ConnectionPool instance;
    private final BlockingQueue<Connection> pool;

    private ConnectionPool(int size) {
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 1; i <= size; i++) {
            pool.offer(new Connection(i));
        }
        System.out.println("[ConnectionPool] initialized with " + size + " connections");
    }

    public static ConnectionPool getInstance(int size) {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(size);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        Connection conn = pool.take();
        System.out.println("[ConnectionPool] " + Thread.currentThread().getName() + " acquired " + conn);
        return conn;
    }

    public void releaseConnection(Connection connection) {
        pool.offer(connection);
        System.out.println("[ConnectionPool] " + Thread.currentThread().getName() + " released " + connection);
    }

    public int availableConnections() {
        return pool.size();
    }
}
