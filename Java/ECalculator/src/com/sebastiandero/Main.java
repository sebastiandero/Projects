package com.sebastiandero;

import java.math.BigDecimal;
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
                System.out.format("Decimals to calculate for e (not more than %d): ", MAX_DEC);
                //Read number of digits to calculate
                numOfDec = in.nextInt();

                if (numOfDec > MAX_DEC)
                {
                    throw new EException("Number too Big");
                }


                BigDecimal e = (new BigDecimal(0)).setScale(numOfDec, BigDecimal.ROUND_HALF_UP);

                boolean precise = false;
                final Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");

                for (int i = 1; !precise; i++)
                {
                    BigDecimal d = BigDecimal.valueOf(
                            1).divide(factorial(i),
                                      MAX_DEC,
                                      RoundingMode.HALF_UP);

                    Matcher matcher = lastIntPattern.matcher(format.format(d));
                    if (matcher.find())
                    {
                        String someNumberStr = matcher.group(1);
                        int lastNumberInt = Integer.parseInt(someNumberStr);
                        if (lastNumberInt - 1 > numOfDec)
                        {
                            precise = true;
                        }
                    }
                    e = e.add(d);

                }
                System.out.println(e.setScale(numOfDec, BigDecimal.ROUND_DOWN));
                run = false;
            } catch (InputMismatchException ignored)
            {
                System.out.println("Please type a valid integer.");
                in.nextLine();
            } catch (EException ignored)
            {
                System.out.format("Please type a integer not more than %d.\n", MAX_DEC);
                in.nextLine();
            }
        }
    }

    private static BigDecimal factorial(int i)
    {
        BigDecimal ret = BigDecimal.valueOf(1);
        for (int m = 2; m<i; m++)
        {
            ret = ret.multiply(BigDecimal.valueOf(m));
        }
        return ret;
    }
}

class EException extends Exception
{
    EException(String s)
    {
        super(s);
    }
}
