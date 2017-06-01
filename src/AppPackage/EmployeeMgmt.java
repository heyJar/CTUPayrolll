
package AppPackage;



import com.github.lgooddatepicker.components.TimePickerSettings;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class EmployeeMgmt extends javax.swing.JFrame {
    
    dbConnect dbC = new dbConnect();
    checkTime cTime = new checkTime();
    addTime aTime = new addTime();
    JOSched JOSched  = new JOSched();
    int yMouse;
    int xMouse;
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement xstmt = null;
    String DefImgPath = "build/classes/Resources/defaultIcon.png";
    String ImgPath = DefImgPath;
    int empIdx;
    String sql = "SELECT * FROM employee";
    String searchText;
    String fullName;
    String empIDB;
    int result;
        DateFormat dNow = new SimpleDateFormat("yyyy");
        String yearNow = dNow.format(new Date());
        String monAMStartTime;
        String monAMEndTime;
        String monPMStartTime;
        String monPMEndTime;
        String monAMTotalTime;
        String monPMTotalTime;
        
        String tueAMStartTime;
        String tueAMEndTime;
        String tuePMStartTime;
        String tuePMEndTime;
        String tueAMTotalTime;
        String tuePMTotalTime;
        
        String wedAMStartTime;
        String wedAMEndTime;
        String wedPMStartTime;
        String wedPMEndTime;
        String wedAMTotalTime;
        String wedPMTotalTime;
        
        String thurAMStartTime;
        String thurAMEndTime;
        String thurPMStartTime;
        String thurPMEndTime;
        String thurAMTotalTime;
        String thurPMTotalTime;
        
        String friAMStartTime;
        String friAMEndTime;
        String friPMStartTime;
        String friPMEndTime;
        String friAMTotalTime;
        String friPMTotalTime;
        
        String satAMStartTime;
        String satAMEndTime;
        String satPMStartTime;
        String satPMEndTime;
        String satAMTotalTime;
        String satPMTotalTime;
        
        String sunAMStartTime;
        String sunAMEndTime;
        String sunPMStartTime;
        String sunPMEndTime;
        String sunAMTotalTime;
        String sunPMTotalTime;
        
        String schoolYearx;
        String yearSem;
        String monTot = null;
        String tueTot = null;
        String wedTot = null;
        String thurTot = null;
        String friTot = null;
        String satTot = null;
        String sunTot = null;
    
    
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
    TimePickerSettings timeSettingsTotal = new TimePickerSettings();
    TimePickerSettings timeSettings = new TimePickerSettings();
    DateTimeFormatter pickerFormatterTotal = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter pickerFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    
    public EmployeeMgmt() {
        initComponents();
        
        setTitle("CTU Employee Information System");
        setIconImage(new ImageIcon(getClass().getResource("/imgPackage/edtrps32x32.png")).getImage());
        UIManager.put("ComboBox.selectionBackground", new Color(24,31,51));
        UIManager.put("ComboBox.selectionForeground", new Color(254,252,252));
        EMRoot.setVisible(false);
        EType.setUI(new WindowsComboBoxUI()); 
        empJTypeList.setUI(new WindowsComboBoxUI()); 
        empImage.setIcon(ResizeImage(ImgPath,null));
        searchBar.setVisible(false);
        searchNow.setVisible(false);
    }
    
    public ArrayList<Employee> getEmployeeList() {
        
            ArrayList<Employee> employeeList = new ArrayList<Employee>();
            conn = dbC.getConnection();
            
            
            ResultSet rs;
        
        try {
            xstmt = conn.createStatement();
            rs = xstmt.executeQuery(sql);
            
            Employee employee;
            
            while(rs.next()) {
                empIdx = rs.getInt("empId");
                String fName = rs.getString("fName");
                String mName = rs.getString("mName");
                String lName = rs.getString("lName");
                String empType = rs.getString("empType");
                String empStat = rs.getString("status");
                byte[] empImage = rs.getBytes("image");

                
                employee = new Employee(empIdx,fName,mName,lName,empType, empStat, empImage);
                employeeList.add(employee);
                empId.setText(empIdx+1+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeList; 
    }
    

    
    public void ViewEmployee() {
        ArrayList<Employee> list = getEmployeeList();
        DefaultTableModel model = (DefaultTableModel)EmployeeViewTable.getModel();
        model.setRowCount(0);
        
        Object[] row = new Object[3];
                for(int i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getEmpId();
                    row[1] = list.get(i).getFullName();
                    row[2] = list.get(i).getEmpJType();
                    
                    model.addRow(row);
                }
    }
    
    
    
    public ImageIcon ResizeImage(String imagePath, byte[] pic){
        ImageIcon myImage = null;
        
        if(imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(empImage.getWidth(), empImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }
    public ImageIcon ResizeImage2(String imagePath, byte[] pic){
        ImageIcon myImage = null;
        
        if(imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(EImg.getWidth(), EImg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }
    
    public boolean checkInputs(){
        if (
            empId.getText() == null ||
            empFName.getText() == null ||
            empLName.getText() == null ||
            empMName.getText() == null ||
            empJTypeList.getSelectedItem().equals("Select")
        ){
        return false;
    } else {
            try{
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }
        public boolean checkInputsUpdate(){
        if (
            EId.getText() == null ||
            EFName.getText() == null ||
            ELName.getText() == null ||
            EMName.getText() == null
        ){
        return false;
    } else {
            try{
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }
private static final DateFormat TWELVE_TF = new SimpleDateFormat("hh:mm a");
  // Replace with kk:mm if you want 1-24 interval
private static final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm");

public static String convertTo12HoursFormat(String twelveHourTime)
        throws ParseException {
    String result;
    if(!twelveHourTime.equals("00:00:00")) {
    result = TWELVE_TF.format(TWENTY_FOUR_TF.parse(twelveHourTime));
    } else {
       result = "00:00";
    }
    return result;
  }

private static final DateFormat TWELVE_TFX = new SimpleDateFormat("hh:mm:ss");
  // Replace with kk:mm if you want 1-24 interval
private static final DateFormat TWENTY_FOUR_TFX = new SimpleDateFormat("HH:mm");

public static String convertTo24HoursFormatNoSec(String twelveHourTime)
        throws ParseException {
    return TWENTY_FOUR_TFX.format(
            TWELVE_TFX.parse(twelveHourTime));
  }

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

    public void ShowItem(int index) {
        EId.setText(getEmployeeList().get(index).getEmpId()+"");
        EFName.setText(getEmployeeList().get(index).getEmpFName());
        EMName.setText(getEmployeeList().get(index).getEmpMName());
        ELName.setText(getEmployeeList().get(index).getEmpLName());
        EType.setSelectedItem(getEmployeeList().get(index).getEmpJType());
        EImg.setIcon(ResizeImage2(null,getEmployeeList().get(index).getEmpImage()));
    }

    
    
    
    
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recheckSchedule = new javax.swing.JDialog();
        RSName = new javax.swing.JLabel();
        RSSchoolYear = new javax.swing.JLabel();
        RSSem = new javax.swing.JLabel();
        RSSunAMStart = new javax.swing.JLabel();
        RSSunAMEnd = new javax.swing.JLabel();
        RSSunAMTotal = new javax.swing.JLabel();
        RSSunPMStart = new javax.swing.JLabel();
        RSSunPMEnd = new javax.swing.JLabel();
        RSSunPMTotal = new javax.swing.JLabel();
        RSSunTotal = new javax.swing.JLabel();
        RSSatTotal = new javax.swing.JLabel();
        RSSatPMTotal = new javax.swing.JLabel();
        RSSatPMEnd = new javax.swing.JLabel();
        RSFriTotal = new javax.swing.JLabel();
        RSThuTotal = new javax.swing.JLabel();
        RSThuPMTotal = new javax.swing.JLabel();
        RSThuPMEnd = new javax.swing.JLabel();
        RSThuPMStart = new javax.swing.JLabel();
        RSThuAMTotal = new javax.swing.JLabel();
        RSThuAMEnd = new javax.swing.JLabel();
        RSThuAMStart = new javax.swing.JLabel();
        RSFriAMStart = new javax.swing.JLabel();
        RSFriAMEnd = new javax.swing.JLabel();
        RSFriAMTotal = new javax.swing.JLabel();
        RSFriPMStart = new javax.swing.JLabel();
        RSFriPMEnd = new javax.swing.JLabel();
        RSFriPMTotal = new javax.swing.JLabel();
        RSSatPMStart = new javax.swing.JLabel();
        RSSatAMTotal = new javax.swing.JLabel();
        RSSatAMEnd = new javax.swing.JLabel();
        RSSatAMStart = new javax.swing.JLabel();
        RSWedAMStart = new javax.swing.JLabel();
        RSWedAMEnd = new javax.swing.JLabel();
        RSWedAMTotal = new javax.swing.JLabel();
        RSWedPMStart = new javax.swing.JLabel();
        RSWedPMEnd = new javax.swing.JLabel();
        RSWedPMTotal = new javax.swing.JLabel();
        RSWedTotal = new javax.swing.JLabel();
        RSTueAMStart = new javax.swing.JLabel();
        RSTueAMEnd = new javax.swing.JLabel();
        RSTueAMTotal = new javax.swing.JLabel();
        RSTuePMStart = new javax.swing.JLabel();
        RSTuePMEnd = new javax.swing.JLabel();
        RSTuePMTotal = new javax.swing.JLabel();
        RSTueTotal = new javax.swing.JLabel();
        RSMonAMStart = new javax.swing.JLabel();
        RSMonAMEnd = new javax.swing.JLabel();
        RSMonAMTotal = new javax.swing.JLabel();
        RSMonPMStart = new javax.swing.JLabel();
        RSMonPMEnd = new javax.swing.JLabel();
        RSMonPMTotal = new javax.swing.JLabel();
        RSMonTotal = new javax.swing.JLabel();
        RScheduleClose = new javax.swing.JLabel();
        cancelRecheck = new javax.swing.JLabel();
        scheduleNowDone = new javax.swing.JLabel();
        RScheduleBG = new javax.swing.JLabel();
        backHome = new javax.swing.JLabel();
        winClose = new javax.swing.JLabel();
        winMin = new javax.swing.JLabel();
        navAttendance = new javax.swing.JLabel();
        winDrag = new javax.swing.JLabel();
        EMRoot = new javax.swing.JPanel();
        EMView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EmployeeViewTable = new javax.swing.JTable();
        EId = new javax.swing.JTextField();
        EFName = new javax.swing.JTextField();
        EMName = new javax.swing.JTextField();
        ELName = new javax.swing.JTextField();
        EType = new javax.swing.JComboBox<>();
        EImg = new javax.swing.JLabel();
        choose = new javax.swing.JLabel();
        take = new javax.swing.JLabel();
        EUpdate = new javax.swing.JLabel();
        EMViewBG = new javax.swing.JLabel();
        EMAdd = new javax.swing.JPanel();
        empId = new javax.swing.JTextField();
        empFName = new javax.swing.JTextField();
        empMName = new javax.swing.JTextField();
        empLName = new javax.swing.JTextField();
        empJTypeList = new javax.swing.JComboBox<>();
        empImage = new javax.swing.JLabel();
        empCImage = new javax.swing.JButton();
        empTImage = new javax.swing.JButton();
        empAddBtn = new javax.swing.JLabel();
        EMAddBG = new javax.swing.JLabel();
        EMSchedule = new javax.swing.JPanel();
        schedName = new javax.swing.JLabel();
        schoolYear = new javax.swing.JComboBox<>();
        semester = new javax.swing.JComboBox<>();
        help = new javax.swing.JLabel();
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        monAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        monAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        monAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        monPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        monPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        monPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        tueAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        tueAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        tueAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        tuePMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        tuePMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        tuePMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        wedAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        wedAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        wedAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        wedPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        wedPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        wedPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        thuAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        thuAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        thuAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        thuPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        thuPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        thuPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        friAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        friAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        friAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        friPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        friPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        friPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        satAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        satAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        satAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        satPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        satPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        satPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        sunAMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        sunAMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        sunAMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        sunPMStart = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettings.setFormatForMenuTimes(pickerFormatter);
        timeSettings.setFormatForDisplayTime(pickerFormatter);
        sunPMEnd = new com.github.lgooddatepicker.components.TimePicker(timeSettings);
        timeSettingsTotal.setFormatForMenuTimes(pickerFormatterTotal);
        timeSettingsTotal.setFormatForDisplayTime(pickerFormatterTotal);
        sunPMTotal = new com.github.lgooddatepicker.components.TimePicker(timeSettingsTotal);
        scheduleNow = new javax.swing.JLabel();
        EMAschedBG = new javax.swing.JLabel();
        EMHome = new javax.swing.JLabel();
        EMViewBtn = new javax.swing.JLabel();
        EMAddBtn = new javax.swing.JLabel();
        EMSchedBtn = new javax.swing.JLabel();
        searchBar = new javax.swing.JTextField();
        searchNow = new javax.swing.JLabel();
        userBG = new javax.swing.JLabel();
        EMUpdateBG = new javax.swing.JLabel();

        recheckSchedule.setUndecorated(true);
        recheckSchedule.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RSName.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSName.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 66, 240, 30));

        RSSchoolYear.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSchoolYear.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSchoolYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 66, 130, 30));

        RSSem.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSem.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSem, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 66, 120, 30));

        RSSunAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, 90, 30));

        RSSunAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 90, 30));

        RSSunAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 220, 90, 30));

        RSSunPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 260, 90, 30));

        RSSunPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, 90, 30));

        RSSunPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 340, 90, 30));

        RSSunTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSunTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSunTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSunTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 380, 90, 30));

        RSSatTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 380, 90, 30));

        RSSatPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 340, 90, 30));

        RSSatPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 300, 90, 30));

        RSFriTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 90, 30));

        RSThuTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 380, 90, 30));

        RSThuPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 90, 30));

        RSThuPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 90, 30));

        RSThuPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 90, 30));

        RSThuAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 90, 30));

        RSThuAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 90, 30));

        RSThuAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSThuAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSThuAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSThuAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 90, 30));

        RSFriAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 90, 30));

        RSFriAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, 90, 30));

        RSFriAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 90, 30));

        RSFriPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, 90, 30));

        RSFriPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 90, 30));

        RSFriPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSFriPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSFriPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSFriPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 90, 30));

        RSSatPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 260, 90, 30));

        RSSatAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 220, 90, 30));

        RSSatAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 180, 90, 30));

        RSSatAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSSatAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSSatAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSSatAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 140, 90, 30));

        RSWedAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 90, 30));

        RSWedAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 90, 30));

        RSWedAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 90, 30));

        RSWedPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 90, 30));

        RSWedPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 303, 90, 30));

        RSWedPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 342, 90, 30));

        RSWedTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSWedTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSWedTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSWedTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 375, 90, 30));

        RSTueAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTueAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTueAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTueAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 140, 90, 30));

        RSTueAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTueAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTueAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTueAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 180, 90, 30));

        RSTueAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTueAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTueAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTueAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 220, 90, 30));

        RSTuePMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTuePMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTuePMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTuePMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 260, 90, 30));

        RSTuePMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTuePMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTuePMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTuePMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 303, 90, 30));

        RSTuePMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTuePMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTuePMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTuePMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 342, 90, 30));

        RSTueTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSTueTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSTueTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSTueTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 375, 90, 30));

        RSMonAMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonAMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonAMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 140, 90, 30));

        RSMonAMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonAMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonAMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 180, 90, 30));

        RSMonAMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonAMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonAMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 220, 90, 30));

        RSMonPMStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonPMStart.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonPMStart.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 263, 90, 30));

        RSMonPMEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonPMEnd.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonPMEnd.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 303, 90, 30));

        RSMonPMTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonPMTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonPMTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 342, 90, 30));

        RSMonTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RSMonTotal.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        RSMonTotal.setForeground(new java.awt.Color(255, 255, 255));
        recheckSchedule.getContentPane().add(RSMonTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 375, 90, 30));

        RScheduleClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RScheduleClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RScheduleCloseMouseClicked(evt);
            }
        });
        recheckSchedule.getContentPane().add(RScheduleClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 10, 40, 30));

        cancelRecheck.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelRecheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelRecheckMouseClicked(evt);
            }
        });
        recheckSchedule.getContentPane().add(cancelRecheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 428, 145, 40));

        scheduleNowDone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        scheduleNowDone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scheduleNowDoneMouseClicked(evt);
            }
        });
        recheckSchedule.getContentPane().add(scheduleNowDone, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 142, 38));

        RScheduleBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/UserSchedulePop.png"))); // NOI18N
        recheckSchedule.getContentPane().add(RScheduleBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 831, 502));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backHomeMouseClicked(evt);
            }
        });
        getContentPane().add(backHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 60, 30));

        winClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        winClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                winCloseMouseClicked(evt);
            }
        });
        getContentPane().add(winClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(998, 10, 22, 20));

        winMin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        winMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                winMinMouseClicked(evt);
            }
        });
        getContentPane().add(winMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(965, 10, 22, 20));

        navAttendance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        navAttendance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navAttendanceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navAttendanceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navAttendanceMouseExited(evt);
            }
        });
        getContentPane().add(navAttendance, new org.netbeans.lib.awtextra.AbsoluteConstraints(892, 48, 125, 25));

        winDrag.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                winDragMouseDragged(evt);
            }
        });
        winDrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                winDragMousePressed(evt);
            }
        });
        getContentPane().add(winDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1025, 85));

        EMRoot.setBackground(new java.awt.Color(31, 37, 61));
        EMRoot.setLayout(new java.awt.CardLayout());

        EMView.setBackground(new java.awt.Color(31, 37, 61));
        EMView.setMaximumSize(new java.awt.Dimension(45, 24));
        EMView.setMinimumSize(new java.awt.Dimension(45, 24));
        EMView.setPreferredSize(new java.awt.Dimension(45, 24));
        EMView.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EmployeeViewTable.setBackground(new java.awt.Color(24, 31, 51));
        EmployeeViewTable.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        EmployeeViewTable.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EmployeeViewTable.setRowHeight(30);
        EmployeeViewTable.setRowSelectionAllowed(false);
        EmployeeViewTable.setShowVerticalLines(false);
        EmployeeViewTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmployeeViewTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(EmployeeViewTable);

        EMView.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 670, 210));

        EId.setEditable(false);
        EId.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        EId.setBorder(null);
        EMView.add(EId, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 309, 150, 35));

        EFName.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        EFName.setBorder(null);
        EMView.add(EFName, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 359, 150, 35));

        EMName.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        EMName.setBorder(null);
        EMView.add(EMName, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 409, 150, 35));

        ELName.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        ELName.setBorder(null);
        EMView.add(ELName, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 309, 150, 35));

        EType.setBackground(new java.awt.Color(230, 231, 232));
        EType.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        EType.setBorder(null);
        EMView.add(EType, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 360, 150, 35));
        EMView.add(EImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 309, 150, 135));

        choose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseMouseClicked(evt);
            }
        });
        EMView.add(choose, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 410, 71, 33));

        take.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EMView.add(take, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 410, 71, 33));

        EUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EUpdateMouseClicked(evt);
            }
        });
        EMView.add(EUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 465, 160, 48));

        EMViewBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/UserView.png"))); // NOI18N
        EMViewBG.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EMView.add(EMViewBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(EMView, "card2");

        EMAdd.setBackground(new java.awt.Color(31, 37, 61));
        EMAdd.setMaximumSize(new java.awt.Dimension(45, 24));
        EMAdd.setMinimumSize(new java.awt.Dimension(45, 24));
        EMAdd.setPreferredSize(new java.awt.Dimension(45, 24));
        EMAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        empId.setEditable(false);
        empId.setBackground(new java.awt.Color(230, 231, 232));
        empId.setBorder(null);
        EMAdd.add(empId, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 119, 215, 25));

        empFName.setBackground(new java.awt.Color(230, 231, 232));
        empFName.setBorder(null);
        EMAdd.add(empFName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 184, 215, 25));

        empMName.setBackground(new java.awt.Color(230, 231, 232));
        empMName.setBorder(null);
        EMAdd.add(empMName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 250, 215, 25));

        empLName.setBackground(new java.awt.Color(230, 231, 232));
        empLName.setBorder(null);
        EMAdd.add(empLName, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 315, 215, 25));

        empJTypeList.setBackground(new java.awt.Color(230, 231, 232));
        empJTypeList.setBorder(null);
        empJTypeList.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        EMAdd.add(empJTypeList, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 377, 227, 33));
        EMAdd.add(empImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 144, 215, 215));

        empCImage.setBackground(new java.awt.Color(236, 93, 119));
        empCImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/chooseImageBtn.png"))); // NOI18N
        empCImage.setBorder(null);
        empCImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        empCImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empCImageActionPerformed(evt);
            }
        });
        EMAdd.add(empCImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 376, 110, 33));

        empTImage.setBackground(new java.awt.Color(236, 93, 119));
        empTImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/takeImageBtn.png"))); // NOI18N
        empTImage.setBorder(null);
        empTImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        empTImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empTImageActionPerformed(evt);
            }
        });
        EMAdd.add(empTImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(598, 376, 110, 33));

        empAddBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        empAddBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empAddBtnMouseClicked(evt);
            }
        });
        EMAdd.add(empAddBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 465, 223, 36));

        EMAddBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/UserAdd.png"))); // NOI18N
        EMAdd.add(EMAddBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(EMAdd, "card2");

        EMSchedule.setBackground(new java.awt.Color(31, 37, 61));
        EMSchedule.setMaximumSize(new java.awt.Dimension(45, 24));
        EMSchedule.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        schedName.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        schedName.setForeground(new java.awt.Color(255, 255, 255));
        EMSchedule.add(schedName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 83, 240, 30));

        schoolYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050" }));
        schoolYear.setToolTipText("");
        EMSchedule.add(schoolYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(452, 84, 140, 30));

        semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Semester", "Second Semeter", "Summer" }));
        EMSchedule.add(semester, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 84, 110, 30));

        help.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        help.setForeground(new java.awt.Color(255, 255, 255));
        help.setText("Help");
        help.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        help.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpMouseClicked(evt);
            }
        });
        EMSchedule.add(help, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, -1, -1));
        EMSchedule.add(monAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 175, -1, -1));
        EMSchedule.add(monAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 217, -1, -1));
        EMSchedule.add(monAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 260, 107, -1));
        EMSchedule.add(monPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 305, -1, -1));
        EMSchedule.add(monPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 351, -1, -1));
        EMSchedule.add(monPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 391, 107, -1));
        EMSchedule.add(tueAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 175, -1, -1));
        EMSchedule.add(tueAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 217, -1, -1));
        EMSchedule.add(tueAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 260, 107, -1));
        EMSchedule.add(tuePMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 305, -1, -1));
        EMSchedule.add(tuePMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 351, -1, -1));
        EMSchedule.add(tuePMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 391, 107, -1));
        EMSchedule.add(wedAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 175, -1, -1));
        EMSchedule.add(wedAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 217, -1, -1));
        EMSchedule.add(wedAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 260, 107, -1));
        EMSchedule.add(wedPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 305, -1, -1));
        EMSchedule.add(wedPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 351, -1, -1));
        EMSchedule.add(wedPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 391, 107, -1));
        EMSchedule.add(thuAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 175, -1, -1));
        EMSchedule.add(thuAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 217, -1, -1));
        EMSchedule.add(thuAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 260, 107, -1));
        EMSchedule.add(thuPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 305, -1, -1));
        EMSchedule.add(thuPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 351, -1, -1));
        EMSchedule.add(thuPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 391, 107, -1));
        EMSchedule.add(friAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 175, -1, -1));
        EMSchedule.add(friAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 217, -1, -1));
        EMSchedule.add(friAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 107, -1));
        EMSchedule.add(friPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 305, -1, -1));
        EMSchedule.add(friPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 351, -1, -1));
        EMSchedule.add(friPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 391, 107, -1));
        EMSchedule.add(satAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 175, -1, -1));
        EMSchedule.add(satAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 217, -1, -1));
        EMSchedule.add(satAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 260, 107, -1));
        EMSchedule.add(satPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 305, -1, -1));
        EMSchedule.add(satPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 351, -1, -1));
        EMSchedule.add(satPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 391, 107, -1));
        EMSchedule.add(sunAMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 175, -1, -1));
        EMSchedule.add(sunAMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 217, -1, -1));
        EMSchedule.add(sunAMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 260, 107, -1));
        EMSchedule.add(sunPMStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 305, -1, -1));
        EMSchedule.add(sunPMEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 351, -1, -1));
        EMSchedule.add(sunPMTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 391, 107, -1));

        scheduleNow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        scheduleNow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scheduleNowMouseClicked(evt);
            }
        });
        EMSchedule.add(scheduleNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 456, 226, 40));

        EMAschedBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/UserSchedule.png"))); // NOI18N
        EMSchedule.add(EMAschedBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(EMSchedule, "card2");

        getContentPane().add(EMRoot, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 828, 530));

        EMHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EMHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EMHomeMouseClicked(evt);
            }
        });
        getContentPane().add(EMHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 140, 110));

        EMViewBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EMViewBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EMViewBtnMouseClicked(evt);
            }
        });
        getContentPane().add(EMViewBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 280, 140, 50));

        EMAddBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EMAddBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EMAddBtnMouseClicked(evt);
            }
        });
        getContentPane().add(EMAddBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 347, 138, 50));

        EMSchedBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EMSchedBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EMSchedBtnMouseClicked(evt);
            }
        });
        getContentPane().add(EMSchedBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 414, 140, 50));

        searchBar.setText("Search ID #.");
        searchBar.setBackground(new java.awt.Color(31, 37, 61));
        searchBar.setBorder(null);
        searchBar.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        searchBar.setForeground(new java.awt.Color(255, 255, 255));
        searchBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBarMouseClicked(evt);
            }
        });
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        searchBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBarKeyPressed(evt);
            }
        });
        getContentPane().add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 98, 250, 28));

        searchNow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchNow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchNowMouseClicked(evt);
            }
        });
        getContentPane().add(searchNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(982, 98, 35, 30));

        userBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/User.png"))); // NOI18N
        getContentPane().add(userBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1026, 682));

        EMUpdateBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/UserUpdate.png"))); // NOI18N
        getContentPane().add(EMUpdateBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void winCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_winCloseMouseClicked

    private void winMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winMinMouseClicked
        this.setExtendedState(AttendanceGUI.ICONIFIED);
    }//GEN-LAST:event_winMinMouseClicked

    private void winDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winDragMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_winDragMouseDragged

    private void winDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_winDragMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_winDragMousePressed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBarActionPerformed

    private void backHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backHomeMouseClicked
        // TODO add your handling code here:
       
        
        new AdminGUI().setVisible(true); 
        this.setVisible(false);
    }//GEN-LAST:event_backHomeMouseClicked

    private void EMViewBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EMViewBtnMouseClicked
        // TODO add your handling code here:
        searchNow.setVisible(true);
        searchBar.setVisible(true);
        sql = "SELECT * FROM employee";
        ViewEmployee();
        schedName.setText("");
        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(EMView);
        EMRoot.repaint();
        EMRoot.revalidate();
        
                EType.removeAllItems();
                        try {
                         Class.forName("com.mysql.jdbc.Driver");
                        conn = dbC.getConnection();
                       Statement stmtx = conn.createStatement();

                        String sqlx = "SELECT * FROM empType";
                        ResultSet rsxx = stmtx.executeQuery(sqlx);

                        while(rsxx.next()) {
                            EType.addItem(rsxx.getString(2));
                        }
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
        
    }//GEN-LAST:event_EMViewBtnMouseClicked

    private void EMAddBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EMAddBtnMouseClicked
        // TODO add your handling code here:
        schedName.setText("");
        searchBar.setVisible(false);
        searchNow.setVisible(false);
        empJTypeList.removeAllItems();
                        try {
                         Class.forName("com.mysql.jdbc.Driver");
                        conn = dbC.getConnection();
                       Statement stmtx = conn.createStatement();

                        String sqlx = "SELECT * FROM empType";
                        ResultSet rsxx = stmtx.executeQuery(sqlx);

                        while(rsxx.next()) {
                            empJTypeList.addItem(rsxx.getString(2));
                        }
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
            


        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(EMAdd);
        EMRoot.repaint();
        EMRoot.revalidate();
    }//GEN-LAST:event_EMAddBtnMouseClicked

    private void EMSchedBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EMSchedBtnMouseClicked
        // TODO add your handling code here:
//        Date totH;
//        monAMTotal = monAMTotal.getTime()
//        
//        monTotal = monAMTotal.getTime() + monPMTotal.getTime();



        schedName.setText("");
        searchNow.setVisible(true);
        searchBar.setVisible(true);
        schoolYear.setSelectedItem(yearNow);
        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(EMSchedule);
        EMRoot.repaint();
        EMRoot.revalidate();
    }//GEN-LAST:event_EMSchedBtnMouseClicked

    private void EMHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EMHomeMouseClicked
        EMRoot.setVisible(false);
        searchBar.setVisible(false);
        searchNow.setVisible(false);
    }//GEN-LAST:event_EMHomeMouseClicked

    private void navAttendanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navAttendanceMouseClicked
        // TODO add your handling code here:
        new AttendanceGUI().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_navAttendanceMouseClicked

    private void navAttendanceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navAttendanceMouseEntered
        // TODO add your handling code here:
        ImageIcon img = new ImageIcon(getClass().getResource("/imgPackage/navAttendanceHover.png"));
        navAttendance.setIcon(img);
        navAttendance.setVisible(true);
    }//GEN-LAST:event_navAttendanceMouseEntered

    private void navAttendanceMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navAttendanceMouseExited
        // TODO add your handling code here:
        //        ImageIcon img = new ImageIcon(getClass().getResource("/imgPackage/navAttendanceNormal.png"));
        navAttendance.setIcon(null);
        //        navAttendance.setVisible(true);
    }//GEN-LAST:event_navAttendanceMouseExited

    private void empTImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empTImageActionPerformed
        // TODO add your handling code here:
//                try{
//			MarvinVideoInterface videoAdapter = new MarvinJavaCVAdapter();
//			videoAdapter.connect(0);
//			MarvinImage image = videoAdapter.getFrame();
//			MarvinImageIO.saveImage(image, "./res/webcam_picture.jpg");
//		} catch(MarvinVideoInterfaceException e){
//			e.printStackTrace();
//		}
//                    try {
//            // TODO add your handling code here:
//            Webcam webcam = Webcam.getDefault();
//            webcam.open();
//            BufferedImage image = webcam.getImage();
//            ImageIO.write(image, "JPG", new File("test.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//TP.TakePic();
//        } catch (IOException ex) {
//            Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    }//GEN-LAST:event_empTImageActionPerformed

    private void empAddBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empAddBtnMouseClicked

        String empIdForm;
        String empFNameForm;
        String empMNameForm;
        String empLNameForm;
        String empJTForm;
        
        if(checkInputs() && ImgPath != null) {
            empIdForm = empId.getText();
            empFNameForm = empFName.getText();
            empMNameForm = empMName.getText();
            empLNameForm = empLName.getText();
            empJTForm = empJTypeList.getSelectedItem()+"";
            String empJTFormx = "";
            
            
            
                         try {
                         Class.forName("com.mysql.jdbc.Driver");
                        conn = dbC.getConnection();
                       Statement stmtx = conn.createStatement();

                        String sqlx = "SELECT * FROM empType WHERE typeName ='" + empJTForm + "'";
                        ResultSet rsxx = stmtx.executeQuery(sqlx);

                        if(rsxx.next()) {
                            empJTFormx = rsxx.getString("id");
                        }
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

                         
            
            try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            stmt = conn.prepareStatement("INSERT INTO employee(empId, fName, mName, lName, empType, image) VALUES (?,?,?,?,?,?)");
            stmt.setString(1,empIdForm); 
            stmt.setString(2,empFNameForm);
            stmt.setString(3,empMNameForm);
            stmt.setString(4,empLNameForm);
            stmt.setString(5,empJTFormx);
            InputStream img = new FileInputStream(new File(ImgPath));
            stmt.setBlob(6,img);
            stmt.executeUpdate();
            
            empId.setText(empIdx+2 +"");
            empFName.setText(null);
            empMName.setText(null);
            empLName.setText(null);
            empJTypeList.setSelectedItem("Select");
            ImgPath = DefImgPath;

            empImage.setIcon(ResizeImage(ImgPath,null));
            
            
            if(empJTForm.equals("Job Order")) {
                JOSched.JOSched(empIdForm,yearNow);
            }
            
            JOptionPane.showMessageDialog(null, "Employee added!");

            
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

            
            
//            JOptionPane.showMessageDialog(null, "Nice");
        } else {
            JOptionPane.showMessageDialog(null, "Some fields are empty!");
        }

    }//GEN-LAST:event_empAddBtnMouseClicked

    private void empCImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empCImageActionPerformed
        // TODO add your handling code here:
         JFileChooser file = new JFileChooser();
//        file.setCurrentDirectory(new File(System.getProperty("user.pictures")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images","jpg","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            empImage.setIcon(ResizeImage(path,null));
            ImgPath = path;
        } else {
 
//            String path = "D:/.. Jariel/Programs/Payroll/CTUPayroll/build/classes/Resources/defaultIcon.png";
            empImage.setIcon(ResizeImage(ImgPath,null));
//            empImage.setText("No image file selected!");
        }
    }//GEN-LAST:event_empCImageActionPerformed

    private void scheduleNowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleNowMouseClicked
        // TODO add your handling code here:
        recheckSchedule.getRootPane().setBorder( BorderFactory.createLineBorder(Color.white) );
        
         monAMStartTime = cTime.checkTime(monAMStart.getTime()+"");
         monAMEndTime = cTime.checkTime(monAMEnd.getTime()+"");
         monPMStartTime = cTime.checkTime(monPMStart.getTime()+"");
         monPMEndTime = cTime.checkTime(monPMEnd.getTime()+"");
         monAMTotalTime = cTime.checkTime(monAMTotal.getTime()+"");
         monPMTotalTime = cTime.checkTime(monPMTotal.getTime()+"");
        
         tueAMStartTime = cTime.checkTime(tueAMStart.getTime()+"");
         tueAMEndTime = cTime.checkTime(tueAMEnd.getTime()+"");
         tuePMStartTime = cTime.checkTime(tuePMStart.getTime()+"");
         tuePMEndTime = cTime.checkTime(tuePMEnd.getTime()+"");
         tueAMTotalTime = cTime.checkTime(tueAMTotal.getTime()+"");
         tuePMTotalTime = cTime.checkTime(tuePMTotal.getTime()+"");
        
         wedAMStartTime = cTime.checkTime(wedAMStart.getTime()+"");
         wedAMEndTime = cTime.checkTime(wedAMEnd.getTime()+"");
         wedPMStartTime = cTime.checkTime(wedPMStart.getTime()+"");
         wedPMEndTime = cTime.checkTime(wedPMEnd.getTime()+"");
         wedAMTotalTime = cTime.checkTime(wedAMTotal.getTime()+"");
         wedPMTotalTime = cTime.checkTime(wedPMTotal.getTime()+"");
        
         thurAMStartTime = cTime.checkTime(thuAMStart.getTime()+"");
         thurAMEndTime = cTime.checkTime(thuAMEnd.getTime()+"");
         thurPMStartTime = cTime.checkTime(thuPMStart.getTime()+"");
         thurPMEndTime = cTime.checkTime(thuPMEnd.getTime()+"");
         thurAMTotalTime = cTime.checkTime(thuAMTotal.getTime()+"");
         thurPMTotalTime = cTime.checkTime(thuPMTotal.getTime()+"");
        
         friAMStartTime = cTime.checkTime(friAMStart.getTime()+"");
         friAMEndTime = cTime.checkTime(friAMEnd.getTime()+"");
         friPMStartTime = cTime.checkTime(friPMStart.getTime()+"");
         friPMEndTime = cTime.checkTime(friPMEnd.getTime()+"");
         friAMTotalTime = cTime.checkTime(friAMTotal.getTime()+"");
         friPMTotalTime = cTime.checkTime(friPMTotal.getTime()+"");
        
        satAMStartTime = cTime.checkTime(satAMStart.getTime()+"");
        satAMEndTime = cTime.checkTime(satAMEnd.getTime()+"");
        satPMStartTime = cTime.checkTime(satPMStart.getTime()+"");
        satPMEndTime = cTime.checkTime(satPMEnd.getTime()+"");
        satAMTotalTime = cTime.checkTime(satAMTotal.getTime()+"");
        satPMTotalTime = cTime.checkTime(satPMTotal.getTime()+"");
        
         sunAMStartTime = cTime.checkTime(sunAMStart.getTime()+"");
         sunAMEndTime = cTime.checkTime(sunAMEnd.getTime()+"");
         sunPMStartTime = cTime.checkTime(sunPMStart.getTime()+"");
         sunPMEndTime = cTime.checkTime(sunPMEnd.getTime()+"");
         sunAMTotalTime = cTime.checkTime(sunAMTotal.getTime()+"");
         sunPMTotalTime = cTime.checkTime(sunPMTotal.getTime()+"");
        
         schoolYearx = schoolYear.getSelectedItem()+"";
         yearSem = semester.getSelectedItem()+"";
        

        try {
            monTot = aTime.getTotal(monAMTotalTime, monPMTotalTime);
            tueTot = aTime.getTotal(tueAMTotalTime, tuePMTotalTime);
            wedTot = aTime.getTotal(wedAMTotalTime, wedPMTotalTime);
            thurTot = aTime.getTotal(thurAMTotalTime, thurPMTotalTime);
            friTot = aTime.getTotal(friAMTotalTime, friPMTotalTime);
            satTot = aTime.getTotal(satAMTotalTime, satPMTotalTime);
            sunTot = aTime.getTotal(sunAMTotalTime, sunPMTotalTime);
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        if(schedName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Name field is empty!");
        } else {
         try {   
        RSName.setText(fullName);
        RSSchoolYear.setText(schoolYearx);
        RSSem.setText(yearSem);

        RSMonAMStart.setText(convertTo12HoursFormat(monAMStartTime));
        RSMonAMEnd.setText(convertTo12HoursFormat(monAMEndTime));
        RSMonPMStart.setText(convertTo12HoursFormat(monPMStartTime));
        RSMonPMEnd.setText(convertTo12HoursFormat(monPMEndTime));
        RSMonAMTotal.setText(convertTo24HoursFormatNoSec(monAMTotalTime));
        RSMonPMTotal.setText(convertTo24HoursFormatNoSec(monPMTotalTime));
        RSMonTotal.setText(monTot);
        
        RSTueAMStart.setText(convertTo12HoursFormat(tueAMStartTime));
        RSTueAMEnd.setText(convertTo12HoursFormat(tueAMEndTime));        
        RSTuePMStart.setText(convertTo12HoursFormat(tuePMStartTime));
        RSTuePMEnd.setText(convertTo12HoursFormat(tuePMEndTime));
        RSTueAMTotal.setText(convertTo24HoursFormatNoSec(tueAMTotalTime));
        RSTuePMTotal.setText(convertTo24HoursFormatNoSec(tuePMTotalTime));
        RSTueTotal.setText(tueTot);
        
        RSWedAMStart.setText(convertTo12HoursFormat(wedAMStartTime));
        RSWedAMEnd.setText(convertTo12HoursFormat(wedAMEndTime));
        RSWedPMStart.setText(convertTo12HoursFormat(wedPMStartTime));
        RSWedPMEnd.setText(convertTo12HoursFormat(wedPMEndTime));
        RSWedAMTotal.setText(convertTo24HoursFormatNoSec(wedAMTotalTime));
        RSWedPMTotal.setText(convertTo24HoursFormatNoSec(wedPMTotalTime));
        RSWedTotal.setText(wedTot);
        
        RSThuAMStart.setText(convertTo12HoursFormat(thurAMStartTime));
        RSThuAMEnd.setText(convertTo12HoursFormat(thurAMEndTime));
        RSThuPMStart.setText(convertTo12HoursFormat(thurPMStartTime));
        RSThuPMEnd.setText(convertTo12HoursFormat(thurPMEndTime));
        RSThuAMTotal.setText(convertTo24HoursFormatNoSec(thurAMTotalTime));
        RSThuPMTotal.setText(convertTo24HoursFormatNoSec(thurPMTotalTime));
        RSThuTotal.setText(thurTot);
        
        RSFriAMStart.setText(convertTo12HoursFormat(friAMStartTime));
        RSFriAMEnd.setText(convertTo12HoursFormat(friAMEndTime));
        RSFriPMStart.setText(convertTo12HoursFormat(friPMStartTime));
        RSFriPMEnd.setText(convertTo12HoursFormat(friPMEndTime));
        RSFriAMTotal.setText(convertTo24HoursFormatNoSec(friAMTotalTime));
        RSFriPMTotal.setText(convertTo24HoursFormatNoSec(friPMTotalTime));
        RSFriTotal.setText(friTot);
        
        RSSatAMStart.setText(convertTo12HoursFormat(satAMStartTime));
        RSSatAMEnd.setText(convertTo12HoursFormat(satAMEndTime));
        RSSatPMStart.setText(convertTo12HoursFormat(satPMStartTime));
        RSSatPMEnd.setText(convertTo12HoursFormat(satPMEndTime));
        RSSatAMTotal.setText(convertTo24HoursFormatNoSec(satAMTotalTime));
        RSSatPMTotal.setText(convertTo24HoursFormatNoSec(satPMTotalTime));
        RSSatTotal.setText(satTot);

        RSSunAMStart.setText(convertTo12HoursFormat(sunAMStartTime));
        RSSunAMEnd.setText(convertTo12HoursFormat(sunAMEndTime));
        RSSunPMStart.setText(convertTo12HoursFormat(sunPMStartTime));
        RSSunPMEnd.setText(convertTo12HoursFormat(sunPMEndTime));
        RSSunAMTotal.setText(convertTo24HoursFormatNoSec(sunAMTotalTime));
        RSSunPMTotal.setText(convertTo24HoursFormatNoSec(sunPMTotalTime));
        RSSunTotal.setText(sunTot);        
        String weeklyTime = aTime.addHours(monTot, tueTot, wedTot, thurTot, friTot, sunTot, satTot);

        
         } catch (ParseException ex) {
        Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
    }
            
            
            
            
        recheckSchedule.setVisible(true);
        recheckSchedule.setSize(833,502);
        //        loginDialog.pack();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - recheckSchedule.getWidth()) / 2;
        final int y = (screenSize.height - recheckSchedule.getHeight()) / 2;
        recheckSchedule.setLocation(x, y);
        recheckSchedule.setVisible(true);
        this.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_scheduleNowMouseClicked

    private void searchNowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchNowMouseClicked
        // TODO add your handling code here:
        
        
        searchText = searchBar.getText();
        schedName.setText("");
        if (searchText.length()== 0) {
            
             JOptionPane.showMessageDialog(null, "Field can't be empty!");
             sql = "SELECT * FROM employee";
             ViewEmployee();
        } else if (searchText.equals("Search ID #.")) {
        JOptionPane.showMessageDialog(null, "Please input valid keyword!");
        sql = "SELECT * FROM employee";
        ViewEmployee();
    } 
        
        else {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            xstmt = conn.createStatement();
            sql = "SELECT * FROM employee WHERE empId = '" + searchText +"'";
            ViewEmployee();
            
            
            ResultSet rs = xstmt.executeQuery(sql);
        if (rs.next()){
           empIDB = rs.getString("empId");
           String fName = rs.getString("fName");
           String mName = rs.getString("lName");
           String lName = rs.getString("lName");
           mName = mName.substring( 0, 1);
           fullName = fName + " " + mName + ". "+ lName;
           schedName.setText(fullName);
           searchBar.setText("Search ID #.");
           
        } else {
            JOptionPane.showMessageDialog(null, "No ID Found! Search again.");
            searchBar.setText("Search ID #.");
            sql = "SELECT * FROM employee";
            ViewEmployee();
        }

      rs.close();
            
        } catch(SQLException se){
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
    }//GEN-LAST:event_searchNowMouseClicked

    private void searchBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBarMouseClicked
        // TODO add your handling code here:
        
        if(searchBar.getText().equals("Search ID #.")) {
            searchBar.setText("");
        } else {
            searchBar.setText(searchBar.getText());
        }
    }//GEN-LAST:event_searchBarMouseClicked

    private void helpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "If the instructor don't have class,\n just leave the time empty.");
    }//GEN-LAST:event_helpMouseClicked

    private void RScheduleCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RScheduleCloseMouseClicked
        // TODO add your handling code here:
        recheckSchedule.dispose();
        this.setVisible(true);
        this.setEnabled(true);
    }//GEN-LAST:event_RScheduleCloseMouseClicked

    private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBarKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
                     
        
        searchText = searchBar.getText();
        schedName.setText("");
        if (searchText.length()== 0) {
            
             JOptionPane.showMessageDialog(null, "Field can't be empty!");
             sql = "SELECT * FROM employee";
             ViewEmployee();
        } else if (searchText.equals("Search ID #.")) {
        JOptionPane.showMessageDialog(null, "Please input valid keyword!");
        sql = "SELECT * FROM employee";
        ViewEmployee();
    } 
        
        else {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            xstmt = conn.createStatement();
            sql = "SELECT * FROM employee WHERE empId = '" + searchText +"'";
            ViewEmployee();
            
            
            ResultSet rs = xstmt.executeQuery(sql);
        if (rs.next()){
           String fName = rs.getString("fName");
           String mName = rs.getString("lName");
           String lName = rs.getString("lName");
           mName = mName.substring( 0, 1);
           fullName = fName + " " + mName + ". "+ lName;
           schedName.setText(fullName);
           searchBar.setText("Search ID #.");
        } else {
            JOptionPane.showMessageDialog(null, "No ID Found! Search again.");
            searchBar.setText("Search ID #.");
            sql = "SELECT * FROM employee";
            ViewEmployee();
        }

      rs.close();
            
        } catch(SQLException se){
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
         }
    }//GEN-LAST:event_searchBarKeyPressed

    private void cancelRecheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelRecheckMouseClicked
        // TODO add your handling code here:
        recheckSchedule.dispose();
        this.setVisible(true);
        this.setEnabled(true);
//                DateFormat dNow = new SimpleDateFormat("yyyy");
//        String yearNow = dNow.format(new Date());
//        searchNow.setVisible(true);
//        searchBar.setVisible(true);
//        schoolYear.setSelectedItem(yearNow);
//        EMRoot.setVisible(true);
//        EMRoot.removeAll();
//        EMRoot.repaint();
//        EMRoot.revalidate();
//        EMRoot.add(EMSchedule);
//        EMRoot.repaint();
//        EMRoot.revalidate();
    }//GEN-LAST:event_cancelRecheckMouseClicked

    private void scheduleNowDoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleNowDoneMouseClicked
    
        try {           
            String monQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + searchText + "', 'Monday','" + monAMTotalTime + "','" + monAMStartTime + "','" + monAMEndTime + "','"+ monPMTotalTime+"'"
                + ",'" + monPMStartTime + "', '"+monPMEndTime+"','"+ monTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            theQuery(monQuery);
      JOptionPane.showMessageDialog(null, "Monday Schedule Success!");
        } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Monday Schedule Failed!");
    }
  
        try {
            String tueQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + searchText + "', 'Tuesday','" + tueAMTotalTime + "','" + tueAMStartTime 
                    + "','" + tueAMEndTime + "','"+ tuePMTotalTime + "','" + tuePMStartTime + "', '"+tuePMEndTime+"','"+ tueTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            theQuery(tueQuery);
            JOptionPane.showMessageDialog(null, "Tuesday Schedule Success!");
        } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Tuesday Schedule Failed!");
    }
        try {
            String wedQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + searchText + "', 'Wednesday','" + wedAMTotalTime + "','" + wedAMStartTime + "','" + wedAMEndTime 
                + "','"+ wedPMTotalTime+"'"
                + ",'" + wedPMStartTime + "', '"+wedPMEndTime+"','"+ wedTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            theQuery(wedQuery);
       JOptionPane.showMessageDialog(null, "Wednesday Schedule Success!");
        } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Wednesday Schedule Failed!");
    }
        try {
            String thurQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + searchText + "', 'Thursday','" + thurAMTotalTime + "','" + thurAMStartTime 
                    + "','" + thurAMEndTime + "','"+ thurPMTotalTime+"'"
                    + ",'" + thurPMStartTime + "', '"+thurPMEndTime+"','"+ thurTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            theQuery(thurQuery);
        JOptionPane.showMessageDialog(null, "Thursday Schedule Success!"); 
        } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Thursday Schedule Failed!");
    }
        try {
            String friQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                ") VALUES ('" + searchText + "', 'Friday','" + friAMTotalTime + "','" + friAMStartTime + "','" + friAMEndTime 
                    + "','"+ friPMTotalTime+"'"
                + ",'" + friPMStartTime + "', '"+friPMEndTime+"','"+ friTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            JOptionPane.showMessageDialog(null, "Friday Schedule Success!");
            theQuery(friQuery);
       } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Friday Schedule Failed!");
    }
        try {
            String satQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + searchText + "', 'Saturday','" + satAMTotalTime + "','" + satAMStartTime + "','" 
                    + satAMEndTime + "','"+ satPMTotalTime+"'"
                    + ",'" + satPMStartTime + "', '"+satPMEndTime+"','"+ satTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
           JOptionPane.showMessageDialog(null, "Saturday Schedule Success!");
            theQuery(satQuery);
               } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Saturday Schedule Failed!");
    }
        try {
            String sunQuery = "INSERT INTO schedule (empId, day, amTotal, amStart, "
                    + "amEnd, pmTotal, pmStart, pmEnd, totalTime, schoolYear, semester\n" +
                    ") VALUES ('" + searchText + "', 'Sunday','" + sunAMTotalTime + "','" + sunAMStartTime 
                    + "','" + sunAMEndTime + "','"+ sunPMTotalTime+"'"
                    + ",'" + sunPMStartTime + "', '"+sunPMEndTime+"','"+ sunTot +":00','" + schoolYearx+ "','" +yearSem+ "')";
            theQuery(sunQuery);
            JOptionPane.showMessageDialog(null, "Sunday Schedule Success!");
    } catch(Exception ex) {
        JOptionPane.showMessageDialog(null, "Sunday Schedule Failed!");
    }
        
        monAMStart.clear();
        monAMEnd.clear();
        monPMStart.clear();
        monPMEnd.clear();
        monAMTotal.clear();
        monPMTotal.clear();
        
        tueAMStart.clear();
        tueAMEnd.clear();
        tuePMStart.clear();
        tuePMEnd.clear();
        tueAMTotal.clear();
        tuePMTotal.clear();
        
        wedAMStart.clear();
        wedAMEnd.clear();
        wedPMStart.clear();
        wedPMEnd.clear();
        wedAMTotal.clear();
        wedPMTotal.clear();
        
        thuAMStart.clear();
        thuAMEnd.clear();
        thuPMStart.clear();
        thuPMEnd.clear();
        thuAMTotal.clear();
        thuPMTotal.clear();
        
        friAMStart.clear();
        friAMEnd.clear();
        friPMStart.clear();
        friPMEnd.clear();
        friAMTotal.clear();
        friPMTotal.clear();
        
        satAMStart.clear();
        satAMEnd.clear();
        satPMStart.clear();
        satPMEnd.clear();
        satAMTotal.clear();
        satPMTotal.clear();
        
        sunAMStart.clear();
        sunAMEnd.clear();
        sunPMStart.clear();
        sunPMEnd.clear();
        sunAMTotal.clear();
        sunPMTotal.clear();
        
        schoolYear.setSelectedItem(yearNow);
        semester.setSelectedItem("First Semester");
        
        recheckSchedule.dispose();
        this.setVisible(true);
        this.setEnabled(true);
    }//GEN-LAST:event_scheduleNowDoneMouseClicked

    private void EmployeeViewTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmployeeViewTableMouseClicked
        // TODO add your handling code here:
        int index = EmployeeViewTable.getSelectedRow();
        ShowItem(index);
    }//GEN-LAST:event_EmployeeViewTableMouseClicked

    private void EUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EUpdateMouseClicked
            
        String empIdForm;
        String empFNameForm;
        String empMNameForm;
        String empLNameForm;
        String empJTForm;
        String empPath;

        
        if(checkInputsUpdate() && ImgPath != null) {
            empIdForm = EId.getText();
            empFNameForm = EFName.getText();
            empMNameForm = EMName.getText();
            empLNameForm = ELName.getText();
            empJTForm = EType.getSelectedItem()+"";
            
            String empJTFormx = "";
            
            
                         try {
                         Class.forName("com.mysql.jdbc.Driver");
                        conn = dbC.getConnection();
                       Statement stmtx = conn.createStatement();

                        String sqlx = "SELECT * FROM empType WHERE typeName ='" + empJTForm + "'";
                        ResultSet rsxx = stmtx.executeQuery(sqlx);

                        while(rsxx.next()) {
                            empJTFormx = rsxx.getString("id");
                        }
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
           
if(!ImgPath.equals("build/classes/Resources/defaultIcon.png")) {           
            try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            stmt = conn.prepareStatement("UPDATE employee SET fName = ?, mName= ?,lName= ?, empType = ?, image =? WHERE empId = ?");
            stmt.setString(1,empFNameForm); 
            stmt.setString(2,empMNameForm);
            stmt.setString(3,empLNameForm);
            stmt.setString(4,empJTFormx);
            InputStream img = new FileInputStream(new File(ImgPath));
            stmt.setBlob(5,img);
            stmt.setString(6,empIdForm);
            stmt.executeUpdate();
            
            empId.setText(empIdx+2 +"");
            empFName.setText(null);
            empMName.setText(null);
            empLName.setText(null);
            empJTypeList.setSelectedItem("Select");
            ImgPath = DefImgPath;

            empImage.setIcon(ResizeImage(ImgPath,null));
            
            JOptionPane.showMessageDialog(null, "Employee added!");
            ViewEmployee();

            
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
            
} else {
    
            try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            stmt = conn.prepareStatement("UPDATE employee SET fName = ?, mName= ?,lName= ?, empType = ? WHERE empId = ?");
            stmt.setString(1,empFNameForm); 
            stmt.setString(2,empMNameForm);
            stmt.setString(3,empLNameForm);
            stmt.setString(4,empJTFormx);
            stmt.setString(5,empIdForm);
            stmt.executeUpdate();
            
            empId.setText(empIdx+2 +"");
            empFName.setText(null);
            empMName.setText(null);
            empLName.setText(null);
            empJTypeList.setSelectedItem("Select");
            ImgPath = DefImgPath;

            empImage.setIcon(ResizeImage(ImgPath,null));
            
            JOptionPane.showMessageDialog(null, "Employee added!");
            ViewEmployee();

            
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
            
            
//            JOptionPane.showMessageDialog(null, "Nice");
        } else {
            JOptionPane.showMessageDialog(null, "Some fields are empty!");
        }
    }//GEN-LAST:event_EUpdateMouseClicked

    private void chooseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseMouseClicked
         JFileChooser file = new JFileChooser();
//        file.setCurrentDirectory(new File(System.getProperty("user.pictures")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images","jpg","png");
        file.addChoosableFileFilter(filter);
        result = file.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            EImg.setIcon(ResizeImage2(path,null));
            ImgPath = path;
        } else {
 
//            String path = "D:/.. Jariel/Programs/Payroll/CTUPayroll/build/classes/Resources/defaultIcon.png";
            EImg.setIcon(ResizeImage2(ImgPath,null));
//            empImage.setText("No image file selected!");
        }
    }//GEN-LAST:event_chooseMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeMgmt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeMgmt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeMgmt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeMgmt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeMgmt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EFName;
    private javax.swing.JTextField EId;
    private javax.swing.JLabel EImg;
    private javax.swing.JTextField ELName;
    private javax.swing.JPanel EMAdd;
    private javax.swing.JLabel EMAddBG;
    private javax.swing.JLabel EMAddBtn;
    private javax.swing.JLabel EMAschedBG;
    private javax.swing.JLabel EMHome;
    private javax.swing.JTextField EMName;
    private javax.swing.JPanel EMRoot;
    private javax.swing.JLabel EMSchedBtn;
    private javax.swing.JPanel EMSchedule;
    private javax.swing.JLabel EMUpdateBG;
    private javax.swing.JPanel EMView;
    private javax.swing.JLabel EMViewBG;
    private javax.swing.JLabel EMViewBtn;
    private javax.swing.JComboBox<String> EType;
    private javax.swing.JLabel EUpdate;
    private javax.swing.JTable EmployeeViewTable;
    private javax.swing.JLabel RSFriAMEnd;
    private javax.swing.JLabel RSFriAMStart;
    private javax.swing.JLabel RSFriAMTotal;
    private javax.swing.JLabel RSFriPMEnd;
    private javax.swing.JLabel RSFriPMStart;
    private javax.swing.JLabel RSFriPMTotal;
    private javax.swing.JLabel RSFriTotal;
    private javax.swing.JLabel RSMonAMEnd;
    private javax.swing.JLabel RSMonAMStart;
    private javax.swing.JLabel RSMonAMTotal;
    private javax.swing.JLabel RSMonPMEnd;
    private javax.swing.JLabel RSMonPMStart;
    private javax.swing.JLabel RSMonPMTotal;
    private javax.swing.JLabel RSMonTotal;
    private javax.swing.JLabel RSName;
    private javax.swing.JLabel RSSatAMEnd;
    private javax.swing.JLabel RSSatAMStart;
    private javax.swing.JLabel RSSatAMTotal;
    private javax.swing.JLabel RSSatPMEnd;
    private javax.swing.JLabel RSSatPMStart;
    private javax.swing.JLabel RSSatPMTotal;
    private javax.swing.JLabel RSSatTotal;
    private javax.swing.JLabel RSSchoolYear;
    private javax.swing.JLabel RSSem;
    private javax.swing.JLabel RSSunAMEnd;
    private javax.swing.JLabel RSSunAMStart;
    private javax.swing.JLabel RSSunAMTotal;
    private javax.swing.JLabel RSSunPMEnd;
    private javax.swing.JLabel RSSunPMStart;
    private javax.swing.JLabel RSSunPMTotal;
    private javax.swing.JLabel RSSunTotal;
    private javax.swing.JLabel RSThuAMEnd;
    private javax.swing.JLabel RSThuAMStart;
    private javax.swing.JLabel RSThuAMTotal;
    private javax.swing.JLabel RSThuPMEnd;
    private javax.swing.JLabel RSThuPMStart;
    private javax.swing.JLabel RSThuPMTotal;
    private javax.swing.JLabel RSThuTotal;
    private javax.swing.JLabel RSTueAMEnd;
    private javax.swing.JLabel RSTueAMStart;
    private javax.swing.JLabel RSTueAMTotal;
    private javax.swing.JLabel RSTuePMEnd;
    private javax.swing.JLabel RSTuePMStart;
    private javax.swing.JLabel RSTuePMTotal;
    private javax.swing.JLabel RSTueTotal;
    private javax.swing.JLabel RSWedAMEnd;
    private javax.swing.JLabel RSWedAMStart;
    private javax.swing.JLabel RSWedAMTotal;
    private javax.swing.JLabel RSWedPMEnd;
    private javax.swing.JLabel RSWedPMStart;
    private javax.swing.JLabel RSWedPMTotal;
    private javax.swing.JLabel RSWedTotal;
    private javax.swing.JLabel RScheduleBG;
    private javax.swing.JLabel RScheduleClose;
    private javax.swing.JLabel backHome;
    private javax.swing.JLabel cancelRecheck;
    private javax.swing.JLabel choose;
    private javax.swing.JLabel empAddBtn;
    private javax.swing.JButton empCImage;
    private javax.swing.JTextField empFName;
    private javax.swing.JTextField empId;
    private javax.swing.JLabel empImage;
    private javax.swing.JComboBox<String> empJTypeList;
    private javax.swing.JTextField empLName;
    private javax.swing.JTextField empMName;
    private javax.swing.JButton empTImage;
    private com.github.lgooddatepicker.components.TimePicker friAMEnd;
    private com.github.lgooddatepicker.components.TimePicker friAMStart;
    private com.github.lgooddatepicker.components.TimePicker friAMTotal;
    private com.github.lgooddatepicker.components.TimePicker friPMEnd;
    private com.github.lgooddatepicker.components.TimePicker friPMStart;
    private com.github.lgooddatepicker.components.TimePicker friPMTotal;
    private javax.swing.JLabel help;
    private javax.swing.JScrollPane jScrollPane1;
    private com.github.lgooddatepicker.components.TimePicker monAMEnd;
    private com.github.lgooddatepicker.components.TimePicker monAMStart;
    private com.github.lgooddatepicker.components.TimePicker monAMTotal;
    private com.github.lgooddatepicker.components.TimePicker monPMEnd;
    private com.github.lgooddatepicker.components.TimePicker monPMStart;
    private com.github.lgooddatepicker.components.TimePicker monPMTotal;
    private javax.swing.JLabel navAttendance;
    private javax.swing.JDialog recheckSchedule;
    private com.github.lgooddatepicker.components.TimePicker satAMEnd;
    private com.github.lgooddatepicker.components.TimePicker satAMStart;
    private com.github.lgooddatepicker.components.TimePicker satAMTotal;
    private com.github.lgooddatepicker.components.TimePicker satPMEnd;
    private com.github.lgooddatepicker.components.TimePicker satPMStart;
    private com.github.lgooddatepicker.components.TimePicker satPMTotal;
    private javax.swing.JLabel schedName;
    private javax.swing.JLabel scheduleNow;
    private javax.swing.JLabel scheduleNowDone;
    private javax.swing.JComboBox<String> schoolYear;
    private javax.swing.JTextField searchBar;
    private javax.swing.JLabel searchNow;
    private javax.swing.JComboBox<String> semester;
    private com.github.lgooddatepicker.components.TimePicker sunAMEnd;
    private com.github.lgooddatepicker.components.TimePicker sunAMStart;
    private com.github.lgooddatepicker.components.TimePicker sunAMTotal;
    private com.github.lgooddatepicker.components.TimePicker sunPMEnd;
    private com.github.lgooddatepicker.components.TimePicker sunPMStart;
    private com.github.lgooddatepicker.components.TimePicker sunPMTotal;
    private javax.swing.JLabel take;
    private com.github.lgooddatepicker.components.TimePicker thuAMEnd;
    private com.github.lgooddatepicker.components.TimePicker thuAMStart;
    private com.github.lgooddatepicker.components.TimePicker thuAMTotal;
    private com.github.lgooddatepicker.components.TimePicker thuPMEnd;
    private com.github.lgooddatepicker.components.TimePicker thuPMStart;
    private com.github.lgooddatepicker.components.TimePicker thuPMTotal;
    private com.github.lgooddatepicker.components.TimePicker tueAMEnd;
    private com.github.lgooddatepicker.components.TimePicker tueAMStart;
    private com.github.lgooddatepicker.components.TimePicker tueAMTotal;
    private com.github.lgooddatepicker.components.TimePicker tuePMEnd;
    private com.github.lgooddatepicker.components.TimePicker tuePMStart;
    private com.github.lgooddatepicker.components.TimePicker tuePMTotal;
    private javax.swing.JLabel userBG;
    private com.github.lgooddatepicker.components.TimePicker wedAMEnd;
    private com.github.lgooddatepicker.components.TimePicker wedAMStart;
    private com.github.lgooddatepicker.components.TimePicker wedAMTotal;
    private com.github.lgooddatepicker.components.TimePicker wedPMEnd;
    private com.github.lgooddatepicker.components.TimePicker wedPMStart;
    private com.github.lgooddatepicker.components.TimePicker wedPMTotal;
    private javax.swing.JLabel winClose;
    private javax.swing.JLabel winDrag;
    private javax.swing.JLabel winMin;
    // End of variables declaration//GEN-END:variables
}
