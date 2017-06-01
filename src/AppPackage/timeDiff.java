/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author H E Y J A R
 */
public class timeDiff {
    String time1;
    String time2;

    public String timeDiff(String time1, String time2) {
            
                this.time1 = time1;
                this.time2 = time2;

                
                DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");
                DateTime dt1 = format.parseDateTime(time1);
                DateTime dt2 = format.parseDateTime(time2);
   
                long hours = Hours.hoursBetween(dt1, dt2).getHours() % 24;
                long minutes = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
//                long seconds = Seconds.secondsBetween(dt1, dt2).getSeconds() % 60;
                String diffResult = String.format("%02d:%02d:00",hours, minutes);
                return diffResult;
        
    }
    
    
    
}
