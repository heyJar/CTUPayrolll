package AppPackage;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
   A component that lets the user enter a number, using 
   a button pad labeled with digits.
*/



public class Keypad extends JPanel
{
    
        public JPanel panel = null;
	public JTextArea textarea = null;
        public JTextArea textareaName = null;
        public JTextArea statusBut = null;
        private String scannedId;
        public JLabel camScanImg;
        public JPanel imgPanel = null;
        dbConnect dbC = new dbConnect();
        Connection conn = null;
        PreparedStatement stmt = null;
        String empId;
        String day;
        String amTimeIn;
        String amTimeOut;
        String pmTimeIn;
        String pmTimeOut;
        String date;
        String DefImgPath = "D:/.. Jariel/Programs/Payroll/CTUPayroll/build/classes/Resources/defaultIcon.png";
    
   public Keypad()
   {  
      setLayout(new BorderLayout());
   
      // Add display field
      
                textarea = new JTextArea();
                textarea.setBorder(null);
		textarea.setEditable(false);
                textarea.setBackground(new Color(57,66,100));     
		textarea.setPreferredSize(new Dimension(220,30));
                textarea.setForeground(Color.WHITE);
                textarea.setFont(new Font("sans-serif", Font.PLAIN, 18));
                
                
                statusBut = new JTextArea();
                statusBut.setText("");
                statusBut.setBorder(null);
		statusBut.setEditable(false);
                statusBut.setBackground(new Color(226,93,119));    
                statusBut.setForeground(new Color(254,252,252));
		statusBut.setPreferredSize(new Dimension(70,40));
                statusBut.setFont(new Font("sans-serif", Font.PLAIN, 18));
                
                textareaName = new JTextArea();
                textareaName.setBorder(null);
		textareaName.setEditable(false);
                textareaName.setBackground(new Color(17,168,171));     
		textareaName.setPreferredSize(new Dimension(220,30));
                textareaName.setForeground(Color.WHITE);
                textareaName.setFont(new Font("sans-serif", Font.PLAIN, 20));
                
                camScanImg = new JLabel();
                camScanImg.setPreferredSize(new Dimension(230,230));      
      
      
      
      
      display = new JTextField();
      display.setBorder(null);
      display.setEditable(false);
      display.setForeground(new Color(254,252,252));
      display.setFont(new Font("sans-serif", Font.PLAIN, 20));
      display.setBackground(new Color(17,168,171));
      display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
      display.setPreferredSize(new Dimension(20, 50));
      
      
      add(display, "North");

      // Make button panel

      buttonPanel = new JPanel();
      
      buttonPanel.setLayout(new GridLayout(4, 3));
      buttonPanel.setBackground(new Color(57,66,100));
      
      // Add digit buttons
   
    
      addButton("7");
      addButton("8");
      addButton("9");
      addButton("4");
      addButton("5");
      addButton("6");
      addButton("1");
      addButton("2");
      addButton("3");
      addButton("0");      
      
      // Add clear entry button
      
      clearButton = new JButton("Clear");
      clearButton.setBackground(new Color(57,66,100));
      clearButton.setFont(new Font("sans-serif", Font.PLAIN, 20));
      clearButton.setPreferredSize(new Dimension(0, 55));
      clearButton.setForeground(Color.WHITE);
      clearButton.setContentAreaFilled(false);
      buttonPanel.add(clearButton);

      class ClearButtonListener implements ActionListener
      {  
         public void actionPerformed(ActionEvent event)
         {  
               String DefImgPath = "D:/.. Jariel/Programs/Payroll/CTUPayroll/build/classes/Resources/picBg.png";
               display.setText("");
               textarea.setText("");
               textareaName.setText("");
               statusBut.setText("");
               camScanImg.setIcon(ResizeImage(DefImgPath,null));

         }
      }
      ActionListener listener = new ClearButtonListener();      

      clearButton.addActionListener(new 
            ClearButtonListener());      
      
      add(buttonPanel, "Center");
      
      okButton = new JButton("OK");
      okButton.setBackground(new Color(57,66,100));
      okButton.setFont(new Font("sans-serif", Font.PLAIN, 20));
      okButton.setPreferredSize(new Dimension(0, 55));
      okButton.setForeground(Color.WHITE);
      okButton.setContentAreaFilled(false);
      buttonPanel.add(okButton);

      class okButtonListener implements ActionListener
      {  
         public void actionPerformed(ActionEvent event)
         {  
           String numpadID = display.getText();
            
           if(numpadID.length()== 0) {
               textareaName.setText("Id # empty!");
               statusBut.setText("");
           } else {
           
            try {
                                    conn = dbC.getConnection();
                                    Statement stmt;
                                    String sql = "SELECT * FROM employee WHERE empId = '" + numpadID +"'";
                                    ResultSet rs;
                                    stmt = conn.createStatement();
                                    rs = stmt.executeQuery(sql);
                                    
                                    
            
                                    if(rs.next()) {
                                        String id = rs.getString("empId");
                                        String fName = rs.getString("fName");
                                        String mName = rs.getString("mName");
                                        String lName = rs.getString("lName");
                                        byte[] empImage = rs.getBytes("image");
                                        String iMName = mName.substring(0,1);
                                        String fullName = fName + " " + iMName + ". " + lName;
           
                                        textareaName.setText(fullName);
                                        textarea.setText(numpadID);
                                        camScanImg.setIcon(ResizeImage(null,empImage));
                                        
                                        
                                    try {
                                             
                                        DateFormat tNow = new SimpleDateFormat("HH:mm:ss");
                                        String timeNow = tNow.format(new Date());
                                        String _24HourTime = timeNow;
                                        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                                        SimpleDateFormat _12HourSDF = new SimpleDateFormat("a");
                                        Date _24HourDt = _24HourSDF.parse(_24HourTime);
                                        String amPm = _12HourSDF.format(_24HourDt);
                                        

                                           SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                                           Date twelve = parser.parse("11:59");
                                           Date thirteen = parser.parse("13:00");
                                           String noonNot = null;

                                           try {
                                               Date userDate = parser.parse(timeNow);
                                               if (userDate.after(twelve) && userDate.before(thirteen)) {
                                               noonNot = "True"; 
                                           } else {
                                               noonNot = "False";     
                                               }
                                           } catch (ParseException e) {
                                               // Invalid date was entered
                       }
                                  
                                    DateFormat dNow = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateNow = dNow.format(new Date());
                                    String dayNow;
                                    dayNow = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().getTime().getTime());
                                   
                                    conn = dbC.getConnection();
                                    PreparedStatement pst = conn.prepareStatement("SELECT * FROM timeinout WHERE empId=? AND day = ? and date = ? GROUP BY id DESC LIMIT 1");    
                                    pst.setString(1, numpadID); 
                                    pst.setString(2, dayNow); 
                                    pst.setString(3, dateNow); 
                                    rs = pst.executeQuery();   
                                    if(rs.next()) {
                                                    empId = rs.getString("empId");
                                                    day = rs.getString("day");
                                                    amTimeIn = rs.getString("amTimeIn");
                                                    amTimeOut = rs.getString("amTimeOut");
                                                    pmTimeIn = rs.getString("pmTimeIn");
                                                    pmTimeOut = rs.getString("pmTimeOut");
                                                    date = rs.getString("date");

                                                    
                                                    if(amPm.equals("AM")) {
                                                        if(amTimeIn.equals("00:00:00") && amTimeOut.equals("00:00:00")) {
                                                                
                                                                    statusBut.setText("AM In");
                                                         
                                                        } else if (!amTimeIn.equals("00:00:00") && amTimeOut.equals("00:00:00")){
                                                              
                                                                    statusBut.setText("AM Out");
                                                              
                                                        } else if (!amTimeOut.equals("00:00:00") && !amTimeIn.equals("00:00:00") && "True".equals(noonNot) || "False".equals(noonNot)) {
                                                             
                                                                    statusBut.setText("AM Limit");
                                                                 
                                                        } else {
                                                             statusBut.setText("Default!");
                                                        }
                                                                   
                                                    } else if (amPm.equals("PM")){
                                                         if (!amTimeIn.equals("00:00:00") && amTimeOut.equals("00:00:00") && "True".equals(noonNot)) {
                                                             
                                                                    statusBut.setText("AM Out");
                                                           
                                                         }
                                                        else if(pmTimeIn.equals("00:00:00") && pmTimeOut.equals("00:00:00")) {
                                                   
                                                                    statusBut.setText("PM In");
                                                                   
                                                        } 
                                                        else if  (!pmTimeIn.equals("00:00:00") && pmTimeOut.equals("00:00:00")) {
                                                          
                                                                    statusBut.setText("PM Out");
                                                                
                                                       
                                                        } else if (!pmTimeIn.equals("00:00:00") && !pmTimeOut.equals("00:00:00")){
                                                          
                                                                    statusBut.setText("PM Limit");
                                                                  
                                                        } 

                                                    } else {
                                                        statusBut.setText("Default");
                                                    }
//                                              
                                    } else {
                                        if(amPm.equals("AM")) {
                                
                                        statusBut.setText("AM In");
                                        } else {
                                   
                                        statusBut.setText("PM In");  
                                        }
                                    }
                                    }catch(SQLException se){
                                  //Handle errors for JDBC
                                  se.printStackTrace();
                               }catch(Exception e){
                                  //Handle errors for Class.forName
                                  e.printStackTrace();
                               }finally{
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
                                     
                                         textarea.setText(numpadID);
                                        textareaName.setText("ID Not Found!");
                                        camScanImg.setIcon(ResizeImage(DefImgPath,null));
                                    }
                                    }catch(SQLException se){
                                  //Handle errors for JDBC
                                  se.printStackTrace();
                               }catch(Exception e){
                                  //Handle errors for Class.forName
                                  e.printStackTrace();
                               }finally{
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
      }
      ActionListener oklistener = new okButtonListener();      
      okButton.addActionListener(new okButtonListener());      
      
      add(buttonPanel, "Center");
      
      
 
      
   }

   /**
      Adds a button to the button panel 
      @param label the button label
   */
   private void addButton(final String label)
   {  
      class DigitButtonListener implements ActionListener
      {  
         public void actionPerformed(ActionEvent event)
         {  

            // Don't add two decimal points
            if (label.equals(".") 
                  && display.getText().indexOf(".") != -1) 
               return;

            // Append label text to button
            display.setText(display.getText() + label);
         }
      }

       
      
      JButton button = new JButton(label);
      button.setBackground(new Color(57,66,100));
      button.setFont(new Font("sans-serif", Font.PLAIN, 30));
      button.setPreferredSize(new Dimension(90, 67));
      button.setForeground(Color.WHITE);
      button.setContentAreaFilled(false);
      buttonPanel.add(button);
      ActionListener listener = new DigitButtonListener();
      button.addActionListener(listener);
   }

   /** 
      Gets the value that the user entered. 
      @return the value in the text field of the keypad
   */
   public double getValue()
   {  
      return Double.parseDouble(display.getText());
   }
   
   /** 
      Clears the display. 
   */
   public void clear()
   {  
      display.setText("");
   }
   
   public void ok()
   {  
//      display.setText("");
   }
   
   private JPanel buttonPanel;
   private JButton clearButton;
   private JButton okButton;
   private JTextField display;

//    public JTextField getDisplay() {
//        return display;
//    }
   
   
   public ImageIcon ResizeImage(String imagePath, byte[] pic){
        ImageIcon myImage = null;
        
        if(imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }
}



