/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author H E Y J A R
 */
public class printPDFFunctions {
    timeDiff tDiff = new timeDiff();
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    String time;
    
    public Date Ptime(String time) {
       this.time = time;
       parser.setTimeZone(TimeZone.getTimeZone("UTC"));
       Date timeNew = null;
        try {
            timeNew = parser.parse(time);
        } catch (ParseException ex) {
            Logger.getLogger(printPDFFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return timeNew;
    }
    
}
