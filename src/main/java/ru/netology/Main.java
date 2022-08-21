package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        List<String> cars = new ArrayList<>();

        new Thread(() -> {
            if (cars.isEmpty()) {
                for (int i = 0; i < 10; i++) {
                    cars.add("Toyota");
                    System.out.println("Производитель выпустил авто - " + cars.get(0));
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            } else {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                System.out.println("Покупатель 1 зашел в автосалон.");
                    if (cars.isEmpty()) {
                        try {
                            System.out.println("Новых авто пока нет...");
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } lock.unlock();
                    System.out.println("Покупатель 1 уехал на новом авто " + cars.remove(0));
                }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                System.out.println("Покупатель 2 зашел в автосалон.");
                if (cars.isEmpty()) {
                    try {
                        System.out.println("Новых авто пока нет...");
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } lock.unlock();
                System.out.println("Покупатель 2 уехал на новом авто " + cars.remove(0));
            }
        }).start();
    }
}