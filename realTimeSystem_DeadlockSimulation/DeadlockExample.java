/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package realTimeSystem_A3;

/**
 *
 * @author Kiy
 */
// This program demonstrates deadlock and resolves it with Resource Hierarchy
public class DeadlockExample {

    static class Resource {
        private final String name;

        public Resource(String name) {
            this.name = name;
        }

        public synchronized void useResource() {
            System.out.println(Thread.currentThread().getName() + " using resource " + name);
        }
    }

    static class DeadlockThread extends Thread {
        private final Resource resource1;
        private final Resource resource2;

        public DeadlockThread(String name, Resource resource1, Resource resource2) {
            super(name);
            this.resource1 = resource1;
            this.resource2 = resource2;
        }

        @Override
        public void run() {
            synchronized (resource1) {
                System.out.println(getName() + " locked " + resource1.name);
                try {
                    Thread.sleep(100); // Simulate some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + " waiting for " + resource2.name);
                synchronized (resource2) {
                    System.out.println(getName() + " locked " + resource2.name);
                    resource1.useResource();
                    resource2.useResource();
                }
            }
        }
    }

    public static void main(String[] args) {
        Resource res1 = new Resource("Resource1");
        Resource res2 = new Resource("Resource2");

        // Deadlock scenario
        Thread thread1 = new DeadlockThread("Thread1", res1, res2);
        Thread thread2 = new DeadlockThread("Thread2", res2, res1);

        // These threads will deadlock due to circular dependency
        thread1.start();
        thread2.start();
    }
}

