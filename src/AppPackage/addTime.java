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

/**
 *
 * @author H E Y J A R
 */
public class addTime {
    String AM;
    String PM;
    String mon;
    String tue;
    String wed;
    String thur;
    String fri;
    String sat;
    String sun;  

    SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat parser2 = new SimpleDateFormat("HH:mm");        
    public String getTotal(String AM, String PM) throws ParseException {
        ;
        this.AM = AM;
        this.PM = PM;

                
                parser.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date AMP = parser.parse(AM);
                Date PMP = parser.parse(PM);    
                long AML = AMP.getTime();
                long PML = PMP.getTime();
                long tot = AML + PML;
                
                    long hours = tot / 3600000;
                    long minutes = (tot % 3600000) / 60000;

                    String total  = String.format("%02d:%02d",hours, minutes);
        
        return total;
    }
    
        public String addHours(String mon, String tue, String wed, String thur, String fri, String sat, String sun) throws ParseException {

        parser.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        
        Date monD = parser2.parse(mon);
        Date tueD = parser2.parse(tue); 
        Date wedD = parser2.parse(wed);
        Date thurD = parser2.parse(thur); 
        Date friD = parser2.parse(fri);
        Date satD = parser2.parse(sat); 
        Date sunD = parser2.parse(sun);
       
        
        
        long monDL = monD.getTime();
        long tueDL = tueD.getTime();
        long wedDL = wedD.getTime();
        long thurDL = thurD.getTime();
        long friDL = friD.getTime();
        long satDL = satD.getTime();
        long sunDL = sunD.getTime();
        
        long total = monDL + tueDL + wedDL + thurDL + friDL + satDL + sunDL;
        
                    long hours = total / 3600000;
                    long minutes = (total % 3600000) / 60000;
                    String weeklyTotal  = String.format("%02d:%02d",hours, minutes);
        return weeklyTotal;
    }
    
}
