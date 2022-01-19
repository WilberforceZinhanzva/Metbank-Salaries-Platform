package zw.co.metbank.coresalariessystem.util;

import java.util.Random;

public class GlobalMethods {


    public static String generateId(String code){
        Random r = new Random();
        StringBuilder s = new StringBuilder(code);
        for(int i=0; i<6;i++){
            char c = (char)(r.nextInt(26)+'a');
            s.append(c);
        }

        int upperBound = 9999;
        int lowerBound = 1000;
        int number = lowerBound + (int)(Math.random() * ((upperBound-lowerBound)+1));
        s.append(number);


        return s.toString().toUpperCase();
    }
}
