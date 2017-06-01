/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

/**
 *
 * @author H E Y J A R
 */
public class checkTime {
    
    String time;
    String newTime;

    
    public String checkTime(String time) {
        this.time = time;
        
        if (time.equals("null")) {
            newTime = "00:00:00";
        } else {
            newTime = time+":00";
        }

        return newTime;
    }


    

    
}
