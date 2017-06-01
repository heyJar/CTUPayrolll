/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */


public class Employee {
    
    dbConnect dbC = new dbConnect();
    Connection conn = null;
    Statement xstmt = null;
    private int empId;
    private String empFName;
    private String empMName;
    private String empLName;
    private String empJType;
    private String empStat;
    private byte[] empImage;

    public Employee(int empId, String empFName, String empMName, String empLName, String empJType, String empStat, byte[] empImage) {
        
            String typeName = null;
            conn = dbC.getConnection();
            
            ResultSet rs;
                try {
                   String sqlx = "SELECT * FROM empType WHERE id = '" + empJType + "'";
                    xstmt = conn.createStatement();
                    rs = xstmt.executeQuery(sqlx);

                    if(rs.next()) {
                        typeName = rs.getString("typeName");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
        
        
        this.empId = empId;
        this.empFName = empFName;
        this.empMName = empMName;
        this.empLName = empLName;
        this.empJType = typeName;
        this.empStat = empStat;
        this.empImage = empImage;
    }

    public int getEmpId() {
        return empId;
    }
    
    public String getFullName() {
        String empIMName = empMName.substring(0, 1);
        String fullName = empFName + " " + empIMName + ". " + empLName;
        return fullName;
    }

    public String getEmpFName() {
        return empFName;
    }

    public String getEmpMName() {
        return empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public String getEmpJType() {
        return empJType;
    }

    public String getEmpStat() {
        return empStat;
    }

    
    public byte[] getEmpImage() {
        return empImage;
    }
    
 
}
