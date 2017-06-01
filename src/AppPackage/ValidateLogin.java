/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ComLab 2 - 20
 */
public class ValidateLogin {
    
    
    public boolean ValLogin(String user,String pass) {
   try{           
       Class.forName("com.mysql.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/edtrps?zeroDateTimeBehavior=convertToNull";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(URL,USER,PASS);
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM administrator WHERE user=? and pass=?");  
            pst.setString(1, user); 
            pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();                        
        if(rs.next())            
            return true;    
        else
            return false;            
    }
    catch(Exception e){
        e.printStackTrace();
        return false;
   }       
}
    
}
