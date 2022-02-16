package com.company;

public class Guest extends Thread{
    private int number;
    Restaurant restaurant;
    private String groupName;

    Guest(Restaurant restaurant, String name){
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

    public int randomNumberOfGuests(){
        return (int)(Math.random()*8)+1;
    }
    public int randomTimeOfDining(){
        return (int)(Math.random()*3000);
    }
    public int getNumber(){return this.number; }
    public String getGroupName(){return this.groupName;}
}
