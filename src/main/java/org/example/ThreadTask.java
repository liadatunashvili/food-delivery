package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadTask extends Thread {

    private static final Logger logger = LogManager.getLogger(ThreadTask.class);
    private final String taskName;

    public ThreadTask(String taskName) {
        super(taskName);
        this.taskName = taskName;
    }

    @Override
    public void run() {
        logger.info("[Thread] " + taskName + " running on thread: " + Thread.currentThread().getName());
    }
}
