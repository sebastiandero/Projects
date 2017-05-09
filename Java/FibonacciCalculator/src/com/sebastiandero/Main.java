package com.sebastiandero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        boolean run = true;
        int numOfDig;
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> seq = new ArrayList<>();
        seq.add(0);
        seq.add(1);

        while (run)
        {
            try
            {
                System.out.print("Numbers to calculate in Fibonacci Sequence: ");
                //Read number of digits to calculate
                numOfDig = in.nextInt();

                for (int i=2; i<numOfDig; i++)
                {
                    seq.add(seq.get(i-2)+seq.get(i-1));
                }

                System.out.println(seq);

                run = false;
            } catch (InputMismatchException ignored)
            {
                System.out.println("Please type a valid integer.");
                in.nextLine();
            }
        }
    }
}