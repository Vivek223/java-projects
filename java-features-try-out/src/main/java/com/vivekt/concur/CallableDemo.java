package com.vivekt.concur;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableDemo {

    public static void main(String[] args) {
        int numberOfTasks = 50000;
        int poolSize =  Runtime.getRuntime().availableProcessors() * 4; // or some other reasonable number

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < numberOfTasks; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println(" do nothing.." + poolSize);
            } );
 //           executor.submit(new MyTask(i)); //submit(() -> {
//                System.out.println("Running task id:" + taskId + " Thread :" + Thread.currentThread());
//            });
        }

        executor.shutdown(); // Initiates an orderly shutdown
    }
}

class MyTask implements Runnable {
    int taskId;

    public MyTask(int i) {
        this.taskId = i;
    }

    @Override
    public void run() {
        System.out.println("MyTask Running task id:" + taskId + " Thread :" + Thread.currentThread());
//
    }
}