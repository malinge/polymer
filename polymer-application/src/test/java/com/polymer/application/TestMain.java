package com.polymer.application;

import java.util.concurrent.TimeUnit;

public class TestMain {
    public static void main(String[] args) {
        long interval = TimeUnit.MILLISECONDS.toMillis(5000);
        System.out.println(interval);
    }
}
