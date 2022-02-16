package com.company;

import static java.lang.Thread.sleep;

public class Guest2 implements Runnable{
    private volatile int number;
    Restaurant2 restaurant;
    private String groupName;

    Guest2(Restaurant2 restaurant, String name){
        this.restaurant = restaurant;
        this.number = randomNumberOfGuests();
        this.groupName = name;
    }

    @Override
    public void run(){
        restaurant.entry(this);
        try {
            sleep(randomTimeOfDining());
        } catch (InterruptedException e) {
            System.out.println("Mistakes were made!");
        }
        restaurant.leave(this);
    }

    public synchronized int randomNumberOfGuests(){
        return (int)(Math.random()*4)+1;
    }
    public synchronized int randomTimeOfDining(){
        return (int)(Math.random()*1000);
    }
    public  synchronized int getNumber(){return this.number; }
    public  synchronized String getGroupName(){return this.groupName;}
}
