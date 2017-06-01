
package AppPackage;



import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Payroll extends javax.swing.JFrame {
    
    dbConnect dbC = new dbConnect();
    printPDF pPDF = new printPDF();
    int yMouse;
    int xMouse;
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement stmtx = null;
    String DefImgPath = "D:/.. Jariel/Programs/Payroll/CTUPayroll/build/classes/Resources/defaultIcon.png";
    String ImgPath = DefImgPath;
    int empIdx;
    String searchText;
    String searchText2;
    timeDiff getDiff = new timeDiff();
    String OverAllTotalx;
    long totalMinutesx;
    String totalHours;
    long totalMinutes;
    String fTotalHours;
    String pEmpId;
    String pEmpType;
    String pEmpName;
    String empTypeName;
    String empTypeRate;
    Salary getSalary = new Salary();
    String sqlr = "SELECT * FROM released";
    String dateRange;
    byte[] empImagex;
    String fDate;
    String lDate;
    String listSize = "";
    ArrayList<DTRSched> list;
    ArrayList<Released> listr;
    String  totalWHours;
    double salary;
    long totHTotal = 0;
    Date totH;
    long totalWHoursx = 0;
    long wMills = 0;
    long OverAllTotal = 0;
    int reportCount = 001;
    int lastRId = 0;
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

            
    public Payroll() {
        initComponents();
        
        setTitle("CTU Employee Information System");
        setIconImage(new ImageIcon(getClass().getResource("/imgPackage/edtrps32x32.png")).getImage());
        UIManager.put("ComboBox.selectionBackground", new Color(24,31,51));
        UIManager.put("ComboBox.selectionForeground", new Color(254,252,252));
        EMRoot.setVisible(false);

       
    }
            private static long parseInterval(final String s)
    {
        final Pattern p = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
        final Matcher m = p.matcher(s);
        if (m.matches())
        {
            final long hr = Long.parseLong(m.group(1)) * TimeUnit.HOURS.toMillis(1);
            final long min = Long.parseLong(m.group(2)) * TimeUnit.MINUTES.toMillis(1);
            final long sec = Long.parseLong(m.group(3)) * TimeUnit.SECONDS.toMillis(1);
            return hr + min + sec;
        }
        else
        {
            throw new IllegalArgumentException(s + " is not a supported interval format!");
        }
    }

    private static String formatInterval(final long l)
    {
        final long hr = TimeUnit.MILLISECONDS.toHours(l);
        final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
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
    
    public ArrayList<DTRSched> getDTRSched() {
        
            ArrayList<DTRSched> DTRSchedList = new ArrayList<DTRSched>();
            conn = dbC.getConnection();
            parser.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date Start;
            Date In;
            long amInDiffTotal = 0;
            long amOutDiffTotal = 0;
            long pmInDiffTotal = 0;
            long pmOutDiffTotal = 0;
            
           
            Date amInDiffT;
            Date amOutDiffT;
            Date pmInDiffT;
            Date pmOutDiffT;

           
            
            
            String amInDiff;
            String pmOutDiff;
            String pmInDiff;
            String amOutDiff;
            String dayHours;
            String workedHours;
            
           

            String sql = "SELECT  employee.empId, employee.fName, employee.mName, employee.lName, employee.empType, employee.image, "
                      + "timeinout.day, timeinout.date, schedule.totalTime,\n" +
                        "schedule.amTotal, schedule.amStart, timeinout.amTimeIn, \n" +
                        "schedule.amEnd, timeinout.amTimeOut, \n" +
                        "schedule.pmTotal, schedule.pmStart, timeinout.pmTimeIn, \n" +
                        "schedule.pmEnd, timeinout.pmTimeOut\n" +
                        "FROM timeinout LEFT JOIN (schedule, employee)\n" +
                        "ON (employee.empId = timeinout.empId AND timeinout.day = schedule.day)\n" +
                        "WHERE  timeinout.empId ='" + searchText2 +"' AND  schedule.empId = '" + searchText2 +"' AND paymentStatus = 'unpaid'";
            ResultSet rs;
        
        try {
            stmtx = conn.createStatement();
            rs = stmtx.executeQuery(sql);
            
            DTRSched dtrsched;
            
            while(rs.next()) {
                 empImagex = rs.getBytes("image");
                 String empId = rs.getString("employee.empId");
                 String fName = rs.getString("employee.fName");
                 String mName = rs.getString("employee.mName");
                 String lName = rs.getString("employee.lName");
                 String empType = rs.getString("employee.empType");
                 String totalH = rs.getString("schedule.totalTime");
                 String totalAM = rs.getString("schedule.amTotal");
                 String totalPM = rs.getString("schedule.pmTotal");
                 String day = rs.getString("timeinout.day");
                 String date = rs.getString("timeinout.date");
                 
                
                 String amStart = rs.getString("schedule.amStart");
                 String amIn = rs.getString("timeinout.amTimeIn"); 

                 String amEnd = rs.getString("schedule.amEnd");
                 String amOut = rs.getString("timeinout.amTimeOut");
                 
                                                           
                 if(!amEnd.equals("00:00:00") && !amOut.equals("00:00:00")) {
                                            Start = parser.parse(amEnd);
                                            In = parser.parse(amOut);
                                           if (Start.after(In)) {
                                               amOutDiff = getDiff.timeDiff(amOut, amEnd );
                                               
                                               amOutDiffT = parser.parse(amOutDiff);
                                               amOutDiffTotal += amOutDiffT.getTime();
                                               
                                               
                                           } else {
                                               amOutDiff = "00:00:00";
                                           }
                  } else {
                     amOutDiff = totalAM;
                     amOutDiffT = parser.parse(amOutDiff);
                     amOutDiffTotal += amOutDiffT.getTime();
                    }  
                                           
                                            Start = parser.parse(amStart);
                                            In = parser.parse(amIn);
                                           if (Start.after(In)) {
                                               amInDiff = "00:00:00";
                                           } else if(amOutDiff.equals(totalAM)) {
                                               amInDiff = "00:00:00";
                                           } else {
                                               amInDiff = getDiff.timeDiff(amStart, amIn );
                                               
                                               amInDiffT = parser.parse(amInDiff);
                                               amInDiffTotal += amInDiffT.getTime();
                                               
                                           }
                                           
                                           
                                     
                 String pmStart = rs.getString("schedule.pmStart");
                 String pmIn = rs.getString("timeinout.pmTimeIn");
                 
                 String pmEnd = rs.getString("schedule.pmEnd");
                 String pmOut = rs.getString("timeinout.pmTimeOut"); 

                                           
                 if(!pmEnd.equals("00:00:00") && !pmOut.equals("00:00:00")) {
                                            Start = parser.parse(pmEnd);
                                            In = parser.parse(pmOut);
                                           if (Start.after(In)) {
                                              pmOutDiff = getDiff.timeDiff(pmOut,pmEnd );
                                               
                                               pmOutDiffT = parser.parse(pmOutDiff);
                                               pmOutDiffTotal += pmOutDiffT.getTime();
                                               
                                               
                                           } else {
                                               pmOutDiff = "00:00:00";
                                           }
                  } else {
                     pmOutDiff = totalPM;
                     pmOutDiffT = parser.parse(pmOutDiff);
                     pmOutDiffTotal += pmOutDiffT.getTime();
                    }  
                                           
                                            Start = parser.parse(pmStart);
                                            In = parser.parse(pmIn);
                                           if (Start.after(In)) {
                                                 pmInDiff = "00:00:00";
                                           } else if(pmOutDiff.equals(totalPM)) {
                                                pmInDiff = "00:00:00";
                                           } else {
                                               pmInDiff = getDiff.timeDiff(pmStart, pmIn );
                                               
                                               pmInDiffT = parser.parse(pmInDiff);
                                               pmInDiffTotal += pmInDiffT.getTime();
                                               
                                           }
                                           
                                           
                                           

                          Date amInDiffx = parser.parse(amInDiff);
                          Date amOutDiffx = parser.parse(amOutDiff);
                          Date pmInDiffx = parser.parse(pmInDiff);
                          Date pmOutDiffx = parser.parse(pmOutDiff);
                          
                          
                    totH = parser.parse(totalH);
                    totHTotal += totH.getTime();
                    
                    long hoursx = totH.getTime() / 3600000;
                    long minutesx = (totH.getTime() % 3600000) / 60000;
                    long secondsx = (totH.getTime() % 60000) / 1000;
                    dayHours = String.format("%02d:%02d:%02d",hoursx, minutesx, secondsx);  
                    
                    long workedHoursx =    amInDiffx.getTime()  + amOutDiffx.getTime() + pmInDiffx.getTime() + pmOutDiffx.getTime();            
                    long workedHoursxx = totH.getTime() - workedHoursx;
                    if ((workedHoursxx < 0)) {
                        workedHoursxx = 0000;
                    }
                    long hoursxx = workedHoursxx / 3600000;
                    long minutesxx = (workedHoursxx % 3600000) / 60000;
                    long secondsxx = (workedHoursxx % 60000) / 1000;
                    workedHours = String.format("%02d:%02d:%02d",hoursxx, minutesxx, secondsxx);
                    
                dtrsched = new DTRSched( day, date, amStart, amIn, amInDiff, amEnd, amOut, amOutDiff, pmStart, pmIn, pmInDiff, pmEnd, pmOut, pmOutDiff, dayHours, workedHours);
                DTRSchedList.add(dtrsched);
                


                    long hours = totHTotal / 3600000;
                    long minutes = (totHTotal % 3600000) / 60000;
                    long seconds = (totHTotal % 60000) / 1000;
                    totalHours = String.format("%02d:%02d:%02d",hours, minutes, seconds);


                   OverAllTotal = amInDiffTotal +   amOutDiffTotal + pmInDiffTotal + pmOutDiffTotal;
                   
                   
                   totalMinutesx = OverAllTotal / 60000;
                   totalMinutes = totHTotal / 60000;
                   System.out.println(totalMinutesx + " Overall Diff");
                   OverAllTotalx = parser.format(new Date(OverAllTotal));
                   
                    mName = mName.substring(0, 1);
                    pEmpName = fName + " " + mName + ". " + lName;
                    pEmpId = empId;
                    pEmpType = empType;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DTRSchedList; 
        
    }
    


    
    public void ViewDTRSched() throws ParseException {
        list = getDTRSched();
        DefaultTableModel model = (DefaultTableModel)DTRSchedViewTable.getModel();
        model.setRowCount(0);
        int i;
        Object[] row = new Object[12];
                for(i = 0; i < list.size(); i++) {
                    row[0] = list.get(i).getDay();
                    row[1] = list.get(i).getDate();
//                    row[2] = list.get(i).getAmStart();
                    row[2] = list.get(i).getAmIn();
//                    row[4] = list.get(i).getAmInDiff();
//                    row[4] = list.get(i).getAmEnd();
                    row[3] = list.get(i).getAmOut();
//                    row[7] = list.get(i).getAmOutDiff();
//                    row[6] = list.get(i).getPmStart();
                    row[4] = list.get(i).getPmIn();
//                    row[10] = list.get(i).getPmInDiff();
//                    row[8] = list.get(i).getPmEnd();
                    row[5] = list.get(i).getPmOut();
//                    row[13] = list.get(i).getPmOutDiff();
                    row[6] = list.get(i).getDayHours();
                    row[7] = list.get(i).getWorkedHours();
                    
                    
                    model.addRow(row);
                }
                

                    
                
                
   }
    
    public ArrayList<Released> getReleasedList() {
        
            ArrayList<Released> ReleasedList = new ArrayList<Released>();
            conn = dbC.getConnection();
            
            
            ResultSet rs;
        
        try {
            stmtx = conn.createStatement();
            rs = stmtx.executeQuery(sqlr);
            
            Released released;
            
            while(rs.next()) {
                int relId = rs.getInt("releasedId");
                int empIdxx = rs.getInt("empId");
                String fDate = rs.getString("firstDate");
                String lDate = rs.getString("lastDate");
                String totalHours = rs.getString("totalHours");
                String lateUT = rs.getString("lateUnderTime");
                String workedHours = rs.getString("workedHours");
                String salary = rs.getString("salary");
                String fileName = rs.getString("fileName");
                String date = rs.getString("releasedDate");

                
                released = new Released(relId, empIdxx, fDate, lDate, totalHours, lateUT, workedHours, salary, fileName, date);
                ReleasedList.add(released);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeMgmt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ReleasedList; 
    }
    
    public void ViewReleased() throws ParseException {
        listr = getReleasedList();
        DefaultTableModel model = (DefaultTableModel)realeasedTable.getModel();
        model.setRowCount(0);
        int i;
        Object[] row = new Object[10];
                for(i = 0; i < listr.size(); i++) {
                    row[0] = listr.get(i).getRelId();
                    row[1] = listr.get(i).getEmpId();
                    row[2] = listr.get(i).getFullName();
                    row[3] = listr.get(i).getrDate();
                    row[4] = listr.get(i).getTotalHours();
                    row[5] = listr.get(i).getLateUT();
                    row[6] = listr.get(i).getWorkedHours();
                    row[7] = listr.get(i).getSalary();
                    row[8] = listr.get(i).getFileName();
                    row[9] = listr.get(i).getDate();
                    model.addRow(row);
                    
                }
                

                    
                
                
   }
    public void ShowItem(int index) {
        relName.setText(getReleasedList().get(index).getFullName());
        relFName.setText(getReleasedList().get(index).getFileName());
    }

 
 
    
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        backHome = new javax.swing.JLabel();
        winClose = new javax.swing.JLabel();
        winMin = new javax.swing.JLabel();
        navAttendance = new javax.swing.JLabel();
        winDrag = new javax.swing.JLabel();
        EMRoot = new javax.swing.JPanel();
        PSearch = new javax.swing.JPanel();
        searchResults = new javax.swing.JLabel();
        searchBox = new javax.swing.JTextField();
        searchBotton = new javax.swing.JLabel();
        EMViewBG = new javax.swing.JLabel();
        PayrollMain = new javax.swing.JPanel();
        empImage = new javax.swing.JLabel();
        pId = new javax.swing.JLabel();
        pFullName = new javax.swing.JLabel();
        pDateRange = new javax.swing.JLabel();
        totalLUT = new javax.swing.JLabel();
        totHours = new javax.swing.JLabel();
        pType = new javax.swing.JLabel();
        pHRate = new javax.swing.JLabel();
        pWhours = new javax.swing.JLabel();
        pSalary = new javax.swing.JLabel();
        releaseNow = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DTRSchedViewTable = new javax.swing.JTable();
        EMAddBG = new javax.swing.JLabel();
        Released = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        realeasedTable = new javax.swing.JTable();
        relFName = new javax.swing.JLabel();
        relName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        ReleasedBG = new javax.swing.JLabel();
        EMHome = new javax.swing.JLabel();
        EMViewBtn = new javax.swing.JLabel();
        ReleasedBtn = new javax.swing.JLabel();
        searchBar = new javax.swing.JTextField();
        userBG = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

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

        PSearch.setBackground(new java.awt.Color(31, 37, 61));
        PSearch.setMaximumSize(new java.awt.Dimension(45, 24));
        PSearch.setMinimumSize(new java.awt.Dimension(45, 24));
        PSearch.setPreferredSize(new java.awt.Dimension(45, 24));
        PSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchResults.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        searchResults.setForeground(new java.awt.Color(255, 255, 255));
        searchResults.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchResults.setText("No results.");
        searchResults.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchResultsMouseClicked(evt);
            }
        });
        PSearch.add(searchResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 330, 440, 20));

        searchBox.setBackground(new java.awt.Color(230, 231, 232));
        searchBox.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        searchBox.setText("Search ID #.");
        searchBox.setBorder(null);
        searchBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBoxMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchBoxMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchBoxMouseExited(evt);
            }
        });
        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        searchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBoxKeyPressed(evt);
            }
        });
        PSearch.add(searchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 490, 60));

        searchBotton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchBotton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBottonMouseClicked(evt);
            }
        });
        PSearch.add(searchBotton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 60, 60));

        EMViewBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/EmployeeSearch.png"))); // NOI18N
        PSearch.add(EMViewBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(PSearch, "card2");

        PayrollMain.setBackground(new java.awt.Color(31, 37, 61));
        PayrollMain.setMaximumSize(new java.awt.Dimension(45, 24));
        PayrollMain.setMinimumSize(new java.awt.Dimension(45, 24));
        PayrollMain.setPreferredSize(new java.awt.Dimension(45, 24));
        PayrollMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PayrollMain.add(empImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 84, 150, 150));

        pId.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pId.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pId, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 78, 200, 30));

        pFullName.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pFullName.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 118, 200, 30));

        pDateRange.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pDateRange.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pDateRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 157, 200, 30));

        totalLUT.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        totalLUT.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(totalLUT, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 195, 200, 30));

        totHours.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        totHours.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(totHours, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 156, 110, 30));

        pType.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pType.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pType, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 78, 110, 30));

        pHRate.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pHRate.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pHRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 118, 110, 30));

        pWhours.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        pWhours.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pWhours, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 197, 110, 30));

        pSalary.setFont(new java.awt.Font("Ubuntu", 0, 30)); // NOI18N
        pSalary.setForeground(new java.awt.Color(255, 255, 255));
        PayrollMain.add(pSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 476, 320, 30));

        releaseNow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        releaseNow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                releaseNowMouseClicked(evt);
            }
        });
        PayrollMain.add(releaseNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 465, 224, 50));

        DTRSchedViewTable.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        DTRSchedViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Day", "Date", "AM In", "AM Out", "PM In", "PM Out", "Hours", "Worked Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(DTRSchedViewTable);

        PayrollMain.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 768, 170));

        EMAddBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/PayrollMain.png"))); // NOI18N
        PayrollMain.add(EMAddBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(PayrollMain, "card2");

        Released.setBackground(new java.awt.Color(31, 37, 61));
        Released.setMaximumSize(new java.awt.Dimension(45, 24));
        Released.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        realeasedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Released ID", "Employee ID", "Name", "Date Range", "Hours", "Late/UT", "Worked Hours", "Salary", "File Name", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        realeasedTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                realeasedTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(realeasedTable);

        Released.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 730, 250));

        relFName.setFont(new java.awt.Font("Ubuntu Light", 0, 16)); // NOI18N
        relFName.setForeground(new java.awt.Color(255, 255, 255));
        Released.add(relFName, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 352, 230, 40));

        relName.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        relName.setForeground(new java.awt.Color(255, 255, 255));
        Released.add(relName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 352, 260, 40));

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        Released.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(327, 430, 180, 50));

        ReleasedBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/ReleasedBG.png"))); // NOI18N
        Released.add(ReleasedBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 530));

        EMRoot.add(Released, "card2");

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

        ReleasedBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ReleasedBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReleasedBtnMouseClicked(evt);
            }
        });
        getContentPane().add(ReleasedBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 347, 138, 50));

        searchBar.setEditable(false);
        searchBar.setBackground(new java.awt.Color(31, 37, 61));
        searchBar.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        searchBar.setForeground(new java.awt.Color(255, 255, 255));
        searchBar.setBorder(null);
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        getContentPane().add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(732, 98, 250, 28));

        userBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgPackage/Payroll.png"))); // NOI18N
        getContentPane().add(userBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1026, 682));

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
        
//        ViewEmployee();

        totalMinutes = 0;
        totalMinutesx = 0;
        totHTotal = 0;
        wMills = 0;
        totalWHoursx = 0;
        totalMinutes = 0;
        totalMinutesx = 0;
        OverAllTotal = 0;
        
        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(PSearch);
        EMRoot.repaint();
        EMRoot.revalidate();
        
    }//GEN-LAST:event_EMViewBtnMouseClicked

    private void ReleasedBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReleasedBtnMouseClicked
        try {
            // TODO add your handling code here:
            ViewReleased();
        } catch (ParseException ex) {
            Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchResults.setText("No results.");
        searchBox.setText("Search ID #.");
        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(Released);
        EMRoot.repaint();
        EMRoot.revalidate();
    }//GEN-LAST:event_ReleasedBtnMouseClicked

    private void EMHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EMHomeMouseClicked
        EMRoot.setVisible(false);
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

    private void searchBottonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBottonMouseClicked
        searchText2 = searchBox.getText();
        if (searchText2.length()== 0) {
             JOptionPane.showMessageDialog(null, "Field can't be empty!");
        } else if (searchText2.equals("Search ID #.")) {
        JOptionPane.showMessageDialog(null, "Please input valid keyword!");
    } 
        else {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            stmtx = conn.createStatement();
            String sql = "SELECT * FROM employee WHERE empId = '" + searchText2 +"'";
            ResultSet rs = stmtx.executeQuery(sql);

        
        if (rs.next()){
           int empId = rs.getInt("empId");
           String fName = rs.getString("fName");
           String lName = rs.getString("lName");
           searchResults.setText("ID #: " +empId+" - " + fName + " " + lName);
           searchBox.setText("Search ID #.");
        } else {
         searchResults.setText("No ID Found! Search again.");
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
    }//GEN-LAST:event_searchBottonMouseClicked

    private void searchBoxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBoxMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxMouseEntered

    private void searchBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBoxMouseClicked
        // TODO add your handling code here:
        if(searchBox.getText().equals("Search ID #.")) {
            searchBox.setText("");
        } else {
            searchBox.setText(searchBox.getText());
        }
    }//GEN-LAST:event_searchBoxMouseClicked

    private void searchBoxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBoxMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxMouseExited

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxActionPerformed

    private void searchResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchResultsMouseClicked
        // TODO add your handling code here:
        searchText = searchResults.getText();
        if(searchText.equals("No results.")) {
            JOptionPane.showMessageDialog(null, "Please search employee first!");
        } else if (searchText.equals("No ID Found! Search again.")){
            JOptionPane.showMessageDialog(null, "No ID Found! Search again.");
            
        }  else {

            try {
                ViewDTRSched();
            } catch (ParseException ex) {
                Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Daily Time Record Empty!");
            } else {
                
                    fDate = list.get(0).getDate();
                    System.out.println(fDate );
                    lDate = list.get(list.size()-1).getDate();
                    
                    Date fDatex = null;
                try {
                    fDatex = new SimpleDateFormat("yyyy-MM-dd").parse(fDate);
                } catch (ParseException ex) {
                    Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
                }
                    Date lDatex = null;
                try {
                    lDatex = new SimpleDateFormat("yyyy-MM-dd").parse(lDate);
                } catch (ParseException ex) {
                    Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
                }
                    String fDatexx = new SimpleDateFormat("MMM/dd/yy").format(fDatex);
                    String lDatexx = new SimpleDateFormat("MMM/dd/yy").format(lDatex);
                    dateRange = fDatexx + " - " + lDatexx;
            
                            try {
                            conn = dbC.getConnection();
                            stmtx = conn.createStatement();
                            String sql = "SELECT * FROM empType WHERE id = '" + pEmpType +"'";
                            ResultSet rs = stmtx.executeQuery(sql);

                        if (rs.next()){
                           empTypeName = rs.getString("typeName");
                           empTypeRate = rs.getString("hourlyRate");
                            pType.setText(empTypeName);
                            pHRate.setText(empTypeRate);

                        } else {
                            pType.setText("Not Found");
                            pHRate.setText("Not Found");
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
            System.out.println(totalMinutes + "totMin");
            System.out.println(totalMinutesx + "totMinx");
            
            totalWHours = totalMinutes - totalMinutesx +"";
 
            salary = getSalary.Salary(empTypeRate,totalWHours);
            
            totalWHoursx = Long.parseLong(totalWHours);
            wMills = TimeUnit.MINUTES.toMillis(totalWHoursx);
            long hours = wMills / 3600000;
            long minutes = (wMills % 3600000) / 60000;
            long seconds = (wMills % 60000) / 1000;
            totalWHours = String.format("%02d:%02d:%02d",hours, minutes, seconds);
            
            
            pSalary.setText(salary+" PHP");       
            pId.setText(pEmpId);        
            pFullName.setText(pEmpName);
            totalLUT.setText(OverAllTotalx+":00");
            totHours.setText(totalHours);
            pWhours.setText(totalWHours);
            pDateRange.setText(dateRange);
            empImage.setIcon(ResizeImage(null,empImagex));
            EMRoot.setVisible(true);
            EMRoot.removeAll();
            EMRoot.repaint();
            EMRoot.revalidate();
            EMRoot.add(PayrollMain);
            EMRoot.repaint();
            EMRoot.revalidate();
            searchResults.setText("No results.");
            }
        }
    }//GEN-LAST:event_searchResultsMouseClicked

    private void releaseNowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_releaseNowMouseClicked
        // TODO add your handling code here:
        

                              try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    conn = dbC.getConnection();
                                    stmtx = conn.createStatement();
                                    String sql = "SELECT * FROM released GROUP BY releasedId DESC LIMIT 1";
                                    ResultSet rs = stmtx.executeQuery(sql);

                                if (rs.next()){
                                   lastRId = rs.getInt("releasedId")+1;
                                } else {
                                   lastRId = 1;
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

                              try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    conn = dbC.getConnection();
                                    stmtx = conn.createStatement();
                                    String sql = "UPDATE timeinout SET paymentStatus = 'paid', "
                                            + "releasedId = '" + lastRId +"' WHERE "
                                            + "Date( `Date`) >= '" + fDate + "' AND Date(`Date`) <= '" + lDate + "' "
                                            + "AND empId = '" + pEmpId +"'";
                                    stmtx.executeUpdate(sql);


                                    JOptionPane.showMessageDialog(null, "Generating PDF ....");


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
                              
                              


            pSalary.setText(salary+" PHP");       
            pId.setText(pEmpId);        
            pFullName.setText(pEmpName);
            totalLUT.setText(OverAllTotalx+":00");
            totHours.setText(totalHours);
            pWhours.setText(totalWHours);
            pDateRange.setText(dateRange);
            String OverAllTotalxx = OverAllTotalx+":00";
            
                DateFormat tNow = new SimpleDateFormat("HH:mm:ss");
                DateFormat dNow = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");    
                String dayNow = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().getTime().getTime());
                String timeNowDisplay = sdf.format(new Date());
                String timeNow = tNow.format(new Date());
                String dateNow = dNow.format(new Date());
                String dateTimeNow = timeNowDisplay + " - " + dateNow;
                
                try {
                    pPDF.printNow(pEmpId,pEmpName, dateRange,OverAllTotalxx,totalWHours,totalHours, dateTimeNow, fDate, lDate, empTypeName, empTypeRate, lastRId, salary);
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                
                pPDF.OpenPDFFile();
       
          
        totalMinutes = 0;
        totalMinutesx = 0;
        totHTotal = 0;
        wMills = 0;
        totalWHoursx = 0;
        totalMinutes = 0;
        totalMinutesx = 0;
        OverAllTotal = 0;
        
        EMRoot.setVisible(true);
        EMRoot.removeAll();
        EMRoot.repaint();
        EMRoot.revalidate();
        EMRoot.add(PSearch);
        EMRoot.repaint();
        EMRoot.revalidate();   
        
    }//GEN-LAST:event_releaseNowMouseClicked

    private void searchBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) { 
                searchText2 = searchBox.getText();
        if (searchText2.length()== 0) {
             JOptionPane.showMessageDialog(null, "Field can't be empty!");
        } else if (searchText2.equals("Search ID #.")) {
        JOptionPane.showMessageDialog(null, "Please input valid keyword!");
    } 
        else {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = dbC.getConnection();
            stmtx = conn.createStatement();
            String sql = "SELECT * FROM employee WHERE empId = '" + searchText2 +"'";
            ResultSet rs = stmtx.executeQuery(sql);

        
        if (rs.next()){
           int empId = rs.getInt("empId");
           String fName = rs.getString("fName");
           String lName = rs.getString("lName");
           searchResults.setText("ID #: " +empId+" - " + fName + " " + lName);
           searchBox.setText("Search ID #.");
        } else {
         searchResults.setText("No ID Found! Search again.");
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
    }//GEN-LAST:event_searchBoxKeyPressed

    private void realeasedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_realeasedTableMouseClicked

        int index = realeasedTable.getSelectedRow();
        ShowItem(index);
    }//GEN-LAST:event_realeasedTableMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        String fileName = relFName.getText();
        pPDF.OpenPDFFile2(fileName);
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payroll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DTRSchedViewTable;
    private javax.swing.JLabel EMAddBG;
    private javax.swing.JLabel EMHome;
    private javax.swing.JPanel EMRoot;
    private javax.swing.JLabel EMViewBG;
    private javax.swing.JLabel EMViewBtn;
    private javax.swing.JPanel PSearch;
    private javax.swing.JPanel PayrollMain;
    private javax.swing.JPanel Released;
    private javax.swing.JLabel ReleasedBG;
    private javax.swing.JLabel ReleasedBtn;
    private javax.swing.JLabel backHome;
    private javax.swing.JLabel empImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel navAttendance;
    private javax.swing.JLabel pDateRange;
    private javax.swing.JLabel pFullName;
    private javax.swing.JLabel pHRate;
    private javax.swing.JLabel pId;
    private javax.swing.JLabel pSalary;
    private javax.swing.JLabel pType;
    private javax.swing.JLabel pWhours;
    private javax.swing.JTable realeasedTable;
    private javax.swing.JLabel relFName;
    private javax.swing.JLabel relName;
    private javax.swing.JLabel releaseNow;
    private javax.swing.JTextField searchBar;
    private javax.swing.JLabel searchBotton;
    private javax.swing.JTextField searchBox;
    private javax.swing.JLabel searchResults;
    private javax.swing.JLabel totHours;
    private javax.swing.JLabel totalLUT;
    private javax.swing.JLabel userBG;
    private javax.swing.JLabel winClose;
    private javax.swing.JLabel winDrag;
    private javax.swing.JLabel winMin;
    // End of variables declaration//GEN-END:variables
}
