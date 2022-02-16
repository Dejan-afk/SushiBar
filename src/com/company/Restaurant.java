package com.company;

import java.util.concurrent.Semaphore;

public class Restaurant{
    private final int SEATINGS = 8;
    //private Semaphore sem;
    private boolean table[];
    private int tableIndex;
    private int numberOfGuests;


    public Restaurant(){
        //sem = new Semaphore(SEATINGS, true); //true for fair waiting
        numberOfGuests = 0;
        table = new boolean[SEATINGS];
        for(int i = 0; i < SEATINGS; i++){
            table[i] = true;
        }
    }

    public synchronized void entry(Guest guest){ //working
        int numberOfIncomingGuests = guest.getNumber();
        printStatus(guest, numberOfIncomingGuests, "möchte essen");
        while(!areSeatingsAvailable(numberOfIncomingGuests)){
            try {
                wait();
            }catch (InterruptedException e) {}
        }
        notify();
        printStatus(guest, numberOfIncomingGuests,"betritt das Restaurant");
        for(int i = 0; i < numberOfIncomingGuests; i++){
            /*try {
                sem.acquire();
            }catch (InterruptedException e) {}*/
            table[numberOfGuests] = false;
            ++numberOfGuests;
        }

        System.out.println("Aktuelle Anzahl Gäste: " + this.numberOfGuests);
    }

    public synchronized void leave(Guest guest){
        int amountGuests = guest.getNumber();
        printStatus(guest, guest.getNumber(), "verlässt das Restaurant");
        for(int i = 0; i < amountGuests; i++){
                //sem.release();
                --numberOfGuests;
                table[numberOfGuests] = true;

        }
        notify();
        System.out.println("Aktuelle Anzahl Gäste: " + this.numberOfGuests );
    }

    public void printStatus(Guest guest, int number, String string){
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

}
