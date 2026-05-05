package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunnableTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(RunnableTask.class);
    private final String taskName;

    public RunnableTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        logger.info("[Runnable] " + taskName + " running on thread: " + Thread.currentThread().getName());
    }
}
