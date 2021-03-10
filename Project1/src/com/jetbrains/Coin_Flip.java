package com.jetbrains;
import java.math.*;
import java.util.Random;


public class Coin_Flip {
    public static int flip(int N_candi){
        int N_selected;
        Random rand = new Random();
        int rand_int1 = rand.nextInt(N_candi);
        N_selected = rand_int1;
        return N_selected;
    }

    public static void main(String[] args) {
        // write your code here
        //System.out.println("Hello");
        int Nselect = flip(3);
        System.out.println(Nselect);
    }
}
