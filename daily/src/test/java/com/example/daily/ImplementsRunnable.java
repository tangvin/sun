package com.example.daily;

import org.omg.CORBA.IMP_LIMIT;

public class ImplementsRunnable implements Runnable {


    public String returnString() {
        return "String!!!!";
    }

    @Override
    public void run() {
        ImplementsRunnable implementsRunnable = new ImplementsRunnable();
        Thread thread = new Thread(implementsRunnable);
        String s = implementsRunnable.returnString();
        System.out.println(s);
        thread.run();
    }

    public static void main(String[] args) {
        ImplementsRunnable implementsRunnable = new ImplementsRunnable();
        implementsRunnable.run();
    }

}
