package zw.co.metbank.coresalariessystem.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public static DateTimeFormatter dayMonthYearFormatter(){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static String formatDate(LocalDateTime dateTime){
       DateTimeFormatter f=  DateTimeFormatter.ofPattern("dd MMM yyyy @ HH:mm");
       return dateTime.format(f);
    }
    public static String formatDateOnly(LocalDate date){
        DateTimeFormatter f=  DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(f);
    }


    public static String generateFilename(String requestId){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMMyyyyHHMM"));
        return requestId + timestamp;
    }
}
