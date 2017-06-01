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
 * @author H E Y J A R
 */
public class Released {
    
    dbConnect dbC = new dbConnect();
    Connection conn = null;
    Statement xstmt = null;

    private int relId;
    private int empId;
    private String FullName;
    private String fName;
    private String mName= null;
    private String lName;
    private String fDate;
    private String lDate;
    private String totalHours;
    private String lateUT;
    private String workedHours;
    private String salary;
    private String fileName; 
    private String date;

    public Released(int relId, int empId, String fDate, String lDate, String totalHours, String lateUT, String workedHours, String salary, String fileName, String date) {

        conn = dbC.getConnection();
        String fNamex = null;
        String mNamex = null;
        String lNamex = null;
            ResultSet rs;
                try {
                   String sqlx = "SELECT * FROM employee WHERE empId = '" + empId + "'";
                    xstmt = conn.createStatement();
                    rs = xstmt.executeQuery(sqlx);

                    if(rs.next()) {
                        fNamex = rs.getString("fName");
                        String mNamexx = rs.getString("mName");
                        lNamex = rs.getString("lName");
                        mNamex = mNamexx.substring(0, 1);
                        
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
        this.relId = relId;
        this.empId = empId;
        this.fName = fNamex;
        this.mName = mNamex;
        this.lName = lNamex;
        this.fDate = fDate;
        this.lDate = lDate;
        this.totalHours = totalHours;
        this.lateUT = lateUT;
        this.workedHours = workedHours;
        this.salary = salary;
        this.fileName = fileName;
        this.date = date;
        
        
    }

    public int getRelId() {
        return relId;
    }

    public int getEmpId() {
        return empId;
    }

    public String getFullName() {
        String fullNamex = fName + " " + mName + ". " + lName;
        return fullNamex;
    }

    public String getrDate() {
        String rDate = fDate + " - " + lDate;
        return rDate;
    }



    public String getTotalHours() {
        return totalHours;
    }

    public String getLateUT() {
        return lateUT;
    }

    public String getWorkedHours() {
        return workedHours;
    }

    public String getSalary() {
        return salary;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDate() {
        return date;
    }
    
    
    

}
