package com.company;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant2{
    private final int SEATINGS = 8;
    private Semaphore sem;
    private ReentrantLock lock;
    private boolean table[];
    private volatile int numberOfGuests;

    public Restaurant2(){
        sem = new Semaphore(SEATINGS, true); //true for fair waiting
        lock = new ReentrantLock(true);
        numberOfGuests = 0;
        table = new boolean[SEATINGS];
        for(int i = 0; i < SEATINGS; i++){
            table[i] = true;
        }
    }

    public void entry(Guest2 guest){
        lock.lock();
        int numberOfIncomingGuests = guest.getNumber();
        printStatus(guest, numberOfIncomingGuests, "möchte essen");
        if(sem.tryAcquire(numberOfIncomingGuests)){
            for(int i = 0; i < numberOfIncomingGuests; i++){
                this.table[this.numberOfGuests] = false;
                ++this.numberOfGuests;
            }
            printStatus(guest, numberOfIncomingGuests,"betritt das Restaurant");
            lock.unlock();
        }else{
            int loopCount = sem.availablePermits();
            int groupDifference = numberOfIncomingGuests - loopCount;
            while(loopCount != 0){
                try{
                        sem.acquire();
                        table[numberOfGuests] = false;
                        ++numberOfGuests;

                }catch(InterruptedException e) {}
                --loopCount;
            }
                printStatus(guest, numberOfIncomingGuests, "warten auf Betreten des Restaurants");
                try {
                    for(int i = 0; i < groupDifference; i++){
                        sem.acquire();
                        table[numberOfGuests] = false;
                        ++numberOfGuests;
                    }
                    System.out.println("Restlichen der Gruppe betreten das Restaurant.");
                }catch (InterruptedException e){}
                finally{
                    lock.unlock();
                }
        }
    }


    public synchronized void leave(Guest2 guest){
        int numberOfLeavingGuests = guest.getNumber();
        printStatus(guest, numberOfLeavingGuests, "verlässt das Restaurant");
        for(int i = 0; i < numberOfLeavingGuests; i++){
            sem.release();
            --numberOfGuests;
            table[numberOfGuests] = true;

        }
    }

    public synchronized void printStatus(Guest2 guest, int number, String string){
        String plural = number > 1 ? "Personen" : "Person";
        System.out.println(guest.getGroupName()+" ("+number+" "+plural+") "+string);
    }

}