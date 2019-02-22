package ua.univer.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class Race {
    private static final CountDownLatch Start = new CountDownLatch(8);
    private static final int trackLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(1000);
        }
        while (Start.getCount() > 3)
            Thread.sleep(100);
        Thread.sleep(1000);
        System.out.println("На старт");
        Start.countDown();
        Thread.sleep(1000);
        System.out.println("Внимание");
        Start.countDown();
        Thread.sleep(1000);
        System.out.println("Марш");
        Start.countDown();
    }

    public static class Car implements Runnable {
        private int carNumber;
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        public void run() {
            try {
                System.out.printf(" Автомобиль №%d подъехал к стартовой прямой.\n ", carNumber);
                Start.countDown();
                Start.await();
                Thread.sleep(trackLength / carSpeed);
                System.out.printf(" Автомобиль №%d финишировал! \n", carNumber);

            } catch (InterruptedException e) {
            }
        }

    }
}
