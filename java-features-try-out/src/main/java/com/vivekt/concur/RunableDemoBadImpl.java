package com.vivekt.concur;

public class RunableDemoBadImpl {

    public static void main(String[] args) {

        for(int i=0; i<50000; i++){
            Task t = new Task(i);
            t.start();
        }
    }


}

class Task extends Thread {
    int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Running task id:" + id + " Thread :" + Thread.currentThread());
    }
}