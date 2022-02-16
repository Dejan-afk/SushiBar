package com.company;

import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Guest3 implements Runnable{
    private volatile int guestCount;
    private int ticketNumber;
    Restaurant3 restaurant;
    private String groupName;
    private HashMap<Integer, String> seatingNumber;

    Guest3(Restaurant3 restaurant, String name){
        this.restaurant = restaurant;
        this.guestCount = randomNumberOfGuests();
        this.groupName = name;
        seatingNumber = new HashMap<>();
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

    public synchronized  void setSeatingsFromTable(String numbers){
        seatingNumber.put(ticketNumber,numbers);
    }
    public synchronized int[] getSeatingsFromTable(){

        int pufferNumberLength = seatingNumber.get(ticketNumber).length();
        int array[] = new int[pufferNumberLength];
        if(pufferNumberLength == 1){
            array[0] = Integer.parseInt(seatingNumber.get(ticketNumber));
            return array;
        }

        int secondPufferNumber = Integer.parseInt(seatingNumber.get(ticketNumber));

        for(int i = 0; i < pufferNumberLength; i++){
            array[i] = secondPufferNumber % 10;
            secondPufferNumber /= 10;
        }
        //seatingNumber.clear();
        return array;
    }

    public  void setTicket(int ticketNumber){this.ticketNumber = ticketNumber;}
    public  int getTicket(){return this.ticketNumber;}

    public  int randomNumberOfGuests(){
        return (int)(Math.random()*4)+1;
    }
    public  int randomTimeOfDining(){
        return (int)(Math.random()*1000);
    }
    public   int getGuestCount(){return this.guestCount; }
    public   String getGroupName(){return this.groupName;}
}
