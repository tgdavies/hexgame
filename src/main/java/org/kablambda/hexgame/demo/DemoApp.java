package org.kablambda.hexgame.demo;

public class DemoApp {

    public static void main(String[] args) {
        DaggerDemoComponent.builder().build().buildGame().start();
    }
}
