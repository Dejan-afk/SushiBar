package com.company;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        final int GUESTS = 6;

        /*
        FIRST TRY

         */
         /*

	    Restaurant restaurant = new Restaurant();
        Guest guests[] = new Guest[GUESTS];

        for(int i = 0; i < GUESTS; i++){
            guests[i] = new Guest(restaurant, "Gruppe-"+String.valueOf(i));
        }

        for(var guest: guests)
            guest.start();

        for(var guest: guests) {
            try{
                guest.join();
            }catch (InterruptedException e) {}
        }

        */


        /*
        SEMAPHORE UND LOCK

         */

        /*
        Restaurant2 restaurant2 = new Restaurant2();
        Thread guests2[] = new Thread[GUESTS];

        for(int i = 0; i < GUESTS; i++){
            guests2[i] = new Thread(new Guest2(restaurant2, "Gruppe-"+String.valueOf(i)));
        }

        for(var guest: guests2)
            guest.start();

        for(var guest: guests2) {
            try{
                guest.join();
            }catch (InterruptedException e) {}
        }

        */


         /*
        WAIT und NOTIFY

         */

        // /*
        Restaurant3 restaurant3 = new Restaurant3();
        Thread guests3[] = new Thread[GUESTS];

        for(int i = 0; i < GUESTS; i++){
            guests3[i] = new Thread(new Guest3(restaurant3, "Gruppe-"+String.valueOf(i)));
        }

        for(var guest: guests3)
            guest.start();

        for(var guest: guests3) {
            try{
                guest.join();
            }catch (InterruptedException e) {}
        }

        // */

    }
}
