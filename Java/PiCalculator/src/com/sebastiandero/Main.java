package com.sebastiandero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{

    public static void main(String[] args)
    {
        boolean run = true;
        int numOfDec;
        DecimalFormat format = new DecimalFormat("0.###E0");
        Scanner in = new Scanner(System.in);
        //MAX DECIMALS to calculate
        int MAX_DEC = 100;
        while (run)
        {
            try
            {
                System.out.format("Decimals to calculate for pi (not more than %d): ", MAX_DEC);
                //Read number of digits to calculate
                numOfDec = in.nextInt();

                if (numOfDec > MAX_DEC)
                {
                    throw new PiException("Number too Big");
                }


                BigDecimal pi = (new BigDecimal(3)).setScale(numOfDec, BigDecimal.ROUND_HALF_UP);

                boolean precise = false;
                final Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");

                for (int i = 1; !precise; i++)
                {
                    BigDecimal d1 = BigDecimal.valueOf(i * 2);
                    BigDecimal d = BigDecimal.valueOf(
                            4).divide(d1.multiply(d1.add(BigDecimal.ONE))
                                        .multiply(d1.add(BigDecimal.valueOf(2))),
                                      MAX_DEC,
                                      RoundingMode.HALF_UP);
                    Matcher matcher = lastIntPattern.matcher(format.format(d));
                    if (matcher.find()) {
                        String someNumberStr = matcher.group(1);
                        int lastNumberInt = Integer.parseInt(someNumberStr);
                        if (lastNumberInt-1 > numOfDec)
                        {
                            precise = true;
                        }
                    }
                    if (i % 2 == 0)
                    {
                        pi = pi.subtract(d);
                    } else
                    {
                        pi = pi.add(d);
                    }
                }
                System.out.println(pi.setScale(numOfDec, BigDecimal.ROUND_DOWN));
                run = false;
            } catch (InputMismatchException ignored)
            {
                System.out.println("Please type a valid integer.");
                in.nextLine();
            } catch (PiException ignored)
            {
                System.out.format("Please type a integer not more than %d.\n", MAX_DEC);
                in.nextLine();
            }
        }
    }
}

class PiException extends Exception
{

    PiException(String s)
    {
        super(s);
    }
}
