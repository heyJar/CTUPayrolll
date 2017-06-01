/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author H E Y J A R
 */
public class Salary {
    String rate;
    String wHours;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
    DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");
    
    
    
    
    Date date;
    DateTime date2;
    
    public double Salary(String rate, String wHours) {
        this.rate = rate;
        this.wHours = wHours;
        parser.setTimeZone(TimeZone.getTimeZone("UTC"));
        double convertedRate = Double.parseDouble(rate) / 60;
        
        

        

        double salary;
        salary = Integer.parseInt(wHours) * convertedRate;

        return salary;
    }
    
    
}
