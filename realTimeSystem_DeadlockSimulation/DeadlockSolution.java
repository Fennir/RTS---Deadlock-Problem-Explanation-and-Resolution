/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package realTimeSystem_A3;

/**
 *
 * @author Kiy
 */
// Solution to avoid deadlock with Resource Hierarchy
public class DeadlockSolution {

    static class Resource {
        private final String name;

        public Resource(String name) {
            this.name = name;
        }

        public synchronized void useResource() {
            System.out.println(Thread.currentThread().getName() + " using resource " + name);
        }
    }

    static class SafeThread extends Thread {
        private final Resource resource1;
        private final Resource resource2;

        public SafeThread(String name, Resource resource1, Resource resource2) {
            super(name);
            this.resource1 = resource1;
            this.resource2 = resource2;
        }

        @Override
        public void run() {
            Resource first = resource1.name.compareTo(resource2.name) < 0 ? resource1 : resource2;
            Resource second = first == resource1 ? resource2 : resource1;

            synchronized (first) {
                System.out.println(getName() + " locked " + first.name);
                try {
                    Thread.sleep(100); // Simulate some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + " waiting for " + second.name);
                synchronized (second) {
                    System.out.println(getName() + " locked " + second.name);
                    resource1.useResource();
                    resource2.useResource();
                }
            }
        }
    }

    public static void main(String[] args) {
        Resource res1 = new Resource("Resource1");
        Resource res2 = new Resource("Resource2");

        // Solution to avoid deadlock using Resource Hierarchy
        Thread thread1 = new SafeThread("Thread1", res1, res2);
        Thread thread2 = new SafeThread("Thread2", res2, res1);

        // These threads will not deadlock due to consistent resource acquisition order
        thread1.start();
        thread2.start();
    }
}

