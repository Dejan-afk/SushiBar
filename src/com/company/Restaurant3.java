package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant3 {
    private final int SEATINGS = 8;
    private ReentrantLock lock;
    private int tickets[];
    private boolean table[];
    private int ticket;
    private int turn;

    public Restaurant3(){
        lock = new ReentrantLock(true);
        table = new boolean[SEATINGS];
        Arrays.fill(table, Boolean.TRUE);
        tickets = new int[SEATINGS];
        for(int i = 0; i < SEATINGS; i++){
            tickets[i] = i+1;
        }
        ticket = 1;
        turn = 1;
    }

    public synchronized void entry(Guest3 guest){
        int numberOfIncomingGuests = guest.getGuestCount();
        printStatus(guest, numberOfIncomingGuests, "möchte essen");
        guest.setTicket(ticket++);
        while((!areSeatingsAvailable(numberOfIncomingGuests)) ){
            try {
                wait();
            }catch (InterruptedException e){}
        }

        printStatus(guest, numberOfIncomingGuests,"betritt das Restaurant");
        setSeatings(numberOfIncomingGuests, guest);
        turn++;
        printSeatings();
    }


    public synchronized void leave(Guest3 guest){
        int numberOfLeavingGuests = guest.getGuestCount();
        printStatus(guest, numberOfLeavingGuests, "verlässt das Restaurant");
        unsetSeatings(guest);
        turn++;
        notifyAll();
        printSeatings();
    }

    public void printStatus(Guest3 guest, int number, String string){
        String plural = number > 1 ? "Personen" : "Person";
        System.out.println(guest.getGroupName()+" ("+number+" "+plural+") "+string);
    }


    //check if enough seatings are available for a group
    public boolean areSeatingsAvailable(int numberOfGuests){
        int count = 0;
        for(int i = 0; i < SEATINGS; i++){
            if(count == numberOfGuests) break;
            if(table[i]){
                for(int j = i; j < numberOfGuests + i; j++){
                    if(j + numberOfGuests - 1 < SEATINGS){
                        if(table[j]) count ++;
                        if(!(table[j])){
                            count = 0;
                            break;
                        }
                    }else{
                        if(j < SEATINGS){
                            if(table[j]) count++;
                            if(!(table[j])){
                                count = 0;
                                break;
                            }
                        }else{
                            if(table[j - SEATINGS]) count++;
                            if(!(table[j - SEATINGS])){
                                count = 0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return count == numberOfGuests;
    }

    //change seatings to false and set seatings at guest
    public void setSeatings(int numberOfGuests, Guest3 guest){
        ArrayList<Integer> list = new ArrayList<Integer>();
        int count = 0;
        for(int i = 0; i < SEATINGS; i++){
            if(count == numberOfGuests) break;
            if(table[i]){
                for(int j = i; j < numberOfGuests + i; j++){
                    if(j + numberOfGuests - 1 < SEATINGS){
                        if(table[j]) {
                            count ++;
                            list.add(j);
                        }
                        if(!(table[j])){
                            count = 0;
                            list.clear();
                            break;
                        }
                    }else{
                        if(j < SEATINGS){
                            if(table[j]){
                                count++;
                                list.add(j);
                            }
                            if(!(table[j])){
                                count = 0;
                                list.clear();
                                break;
                            }
                        }else{
                            if(table[j - SEATINGS]) {
                                count++;
                                list.add(j - SEATINGS);
                            }
                            if(!(table[j - SEATINGS])){
                                count = 0;
                                list.clear();
                                break;
                            }
                        }
                    }
                }
            }
        }
        String seatingsAsString = "";
        for(int i = 0; i < list.size(); i++){
            table[list.get(i)] = false;
            seatingsAsString+=String.valueOf(list.get(i));
        }
        guest.setSeatingsFromTable(seatingsAsString);
    }


    public void unsetSeatings(Guest3 guest){
        int array[] = guest.getSeatingsFromTable();
        for(int i = 0; i < array.length; i++){
            table[array[i]] = true;
        }
    }

    public void printSeatings(){
        String s = "";
        for(int i = 0; i < SEATINGS; i++){
            if(table[i]){
                s+=" O | ";
            }else{
                s+=" X | ";
            }
        }
        System.out.println(s);
    }
}
