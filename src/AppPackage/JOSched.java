/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author H E Y J A R
 */

public class JOSched {
    dbConnect dbC = new dbConnect();
    PreparedStatement stmt = null;
    String id;
    String year;
public void theQuery(String query) {
    Connection conn = null;
    Statement st = null;
    try {
        conn = dbC.getConnection();
        st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Query Success.");
                        }catch(SQLException se){
                      //Handle errors for JDBC
                      se.printStackTrace();
                   }catch(Exception e){
                      //Handle errors for Class.forName
                      e.printStackTrace();
                   }
                            finally{
                      //finally block used to close resources
                      try{
                         if(stmt!=null)
                            conn.close();
                      }catch(SQLException se){
                      }// do nothing
                      try{
                         if(conn!=null)
                            conn.close();
                      }catch(SQLException se){
                         se.printStackTrace();
                      }//end finally try
                   }//end try
    
}
public void JOSched(String id, String year) {
   this.id = id;
   this.year = year;
   String AMTotal = "04:00:00";
   String AMStart = "8:00:00";
   String AMEnd = "12:00:00";
   String PMTotal = "04:00:00";
   String PMStart = "13:00:00";
   String PMEnd = "17:00";
   String total = "08:00";
   String sem = "First Semester";
        try {           
            String monQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + id + "', 'Monday','" + AMTotal + "','" + AMStart + "','" + AMEnd + "','"+ PMTotal+"'"
                + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";
            theQuery(monQuery);
        } catch(Exception ex) {
    }
  
        try {
            String tueQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + id + "', 'Tuesday','" + AMTotal + "','" + AMStart 
                    + "','" + AMEnd + "','"+ PMTotal + "','" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";
            theQuery(tueQuery);

        } catch(Exception ex) {

    }
        try {
            String wedQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + id + "', 'Wednesday','" + AMTotal + "','" + AMStart + "','" + AMEnd 
                + "','"+ PMTotal+"'"
                + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";
            theQuery(wedQuery);

        } catch(Exception ex) {

    }
        try {
            String thurQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + id + "', 'Thursday','" + AMTotal + "','" + AMStart 
                    + "','" + AMEnd + "','"+ PMTotal+"'"
                    + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";
            theQuery(thurQuery);

        } catch(Exception ex) {

    }
        try {
            String friQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + id + "', 'Friday','" + AMTotal + "','" + AMStart + "','" + AMEnd 
                    + "','"+ PMTotal+"'"
                + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";

            theQuery(friQuery);
       } catch(Exception ex) {

    }
        try {
            String satQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + id + "', 'Saturday','" + AMTotal + "','" + AMStart + "','" 
                    + AMEnd + "','"+ PMTotal+"'"
                    + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";

            theQuery(satQuery);
               } catch(Exception ex) {

    }
        try {
            String sunQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + id + "', 'Sunday','" + AMTotal + "','" + AMStart 
                    + "','" + AMEnd + "','"+ PMTotal+"'"
                    + ",'" + PMStart + "', '"+PMEnd+"','"+ total +":00','" + year+ "','" +sem+ "')";
            theQuery(sunQuery);

    } catch(Exception ex) {

    }
}
}
