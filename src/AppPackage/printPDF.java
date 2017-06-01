/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public class printPDF {
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement stmtx = null;
    dbConnect dbC = new dbConnect();
    printPDFFunctions PDFF = new printPDFFunctions();
    timeDiff tDiff = new timeDiff();
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    Date Start;
    Date In;
    int reportCount = 001;
    String id;
    String name;
    String dateRange;
    String underLate;
    String workedHours;
    String totalHours;
    String dateNow;
    String fDate;
    String lDate;
    String type;
    String empTypeRate;
    int lastRId;
    double salary;
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
            String workedHoursx;
            
            String fileName;
            
public void printNow(String id, String name, String dateRange, String underLate, String workedHours, String totalHours, String dateNow, String fDate, String lDate, String type, String empTypeRate, int lastRId, double salary) {
        this.id = id;
        this.name = name;
        this.dateRange = dateRange;
        this.underLate = underLate;
        this.workedHours = workedHours;
        this.totalHours = totalHours;
        this.dateNow = dateNow;
        this.fDate = fDate;
        this.lDate = lDate;
        this.type = type;
        this.empTypeRate = empTypeRate;
        this.lastRId = lastRId;
        this.salary = salary;
       try {

            Document document = new Document();
            Connection conn = null;
            Statement st = null;
            conn = dbC.getConnection();
            st = conn.createStatement();
                
            String query = "SELECT * FROM schedule WHERE empId ='" + id +"'";
            ResultSet rs = st.executeQuery(query); 
            
            PdfWriter.getInstance(document, new FileOutputStream("reports/Report-"+ reportCount +"." + lastRId+"-"+ id + ".pdf"));
            document.open();
            document.setPageSize(PageSize.LETTER);
            document.setMargins(0f, 0f, 0f, 0f);

            Image image = Image.getInstance("Resources/DTRHeaderTwo.png");
            document.add(image);

                PdfPTable ICTable = new PdfPTable(5);
                ICTable.setWidthPercentage(100);
                
                ICTable.getDefaultCell().setBorder(0);
                //create a cell object
                PdfPCell ICTableCell;   

                Phrase ICBlank = new Phrase();
                Phrase ICharge = new Phrase();
                Phrase ICBlanko = new Phrase();
                Phrase ICBlankx = new Phrase();
                Phrase IChargex = new Phrase();
                
                ICBlank.add(new Chunk("", new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                ICTable.addCell(ICBlank);
                ICharge.add(new Chunk("_____________________",  new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));
                ICTable.addCell(ICharge);
                ICBlanko.add(new Chunk("",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                ICTable.addCell(ICBlanko);
                ICBlankx.add(new Chunk("",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                ICTable.addCell(ICBlankx);
                IChargex.add(new Chunk("____________________",  new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));
                ICTable.addCell(IChargex);
                
                
                ICTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                ICTableCell.setBorder(PdfPCell.NO_BORDER);
                ICTable.addCell(ICTableCell);
                ICTableCell = new PdfPCell(new Phrase("In - Charge" , new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                ICTableCell.setBorder(PdfPCell.NO_BORDER);
                ICTable.addCell(ICTableCell);
                ICTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                ICTableCell.setBorder(PdfPCell.NO_BORDER);
                ICTable.addCell(ICTableCell);
                
                ICTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                ICTableCell.setBorder(PdfPCell.NO_BORDER);
                ICTable.addCell(ICTableCell);
                ICTableCell = new PdfPCell(new Phrase("In - Charge", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                ICTableCell.setBorder(PdfPCell.NO_BORDER);
                ICTable.addCell(ICTableCell);
                
                
                PdfPTable dateRecTable = new PdfPTable(5);
                dateRecTable.setWidthPercentage(100);
                
                dateRecTable.getDefaultCell().setBorder(0);
                //create a cell object
                PdfPCell dateRecTableCell;   

                Phrase dRDate = new Phrase();
                Phrase dRNo = new Phrase();
                Phrase dRBlank = new Phrase();
                Phrase dRDatex = new Phrase();
                Phrase dRNox = new Phrase();
                
                dRDate.add(new Chunk("Released #: " + lastRId + "",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                dateRecTable.addCell(dRDate);
                dRNo.add(new Chunk("" + dateNow+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                dateRecTable.addCell(dRNo);
                dRBlank.add(new Chunk("",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                dateRecTable.addCell(dRBlank);
                dRDatex.add(new Chunk("Released #: " + lastRId + "",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                dateRecTable.addCell(dRDatex);
                dRNox.add(new Chunk("" + dateNow+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC)));
                dateRecTable.addCell(dRNox);
                

                
            
                PdfPTable InfoTable = new PdfPTable(5);
                InfoTable.setWidthPercentage(100);
                
                InfoTable.getDefaultCell().setBorder(0);
                //create a cell object
                PdfPCell InfoTableCell;   

                Phrase infoId = new Phrase();
                Phrase infoType = new Phrase();
                Phrase infoBlank = new Phrase();
                Phrase infoIdx = new Phrase();
                Phrase infoTypex = new Phrase();
                
                infoId.add(new Chunk("ID: " + id+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTable.addCell(infoId);
                infoType.add(new Chunk("Type: " + type+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTable.addCell(infoType);
                infoBlank.add(new Chunk("",  new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTable.addCell(infoBlank);
                infoIdx.add(new Chunk("ID: " + id+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTable.addCell(infoIdx);
                infoTypex.add(new Chunk("Type: " + type+ "",  new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTable.addCell(infoTypex);
                
                InfoTableCell = new PdfPCell(new Phrase("Fullname: " + name + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Hourly Rate: " + empTypeRate + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Fullname: " + name + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Hourly Rate: " + empTypeRate + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Date Range: \n" + dateRange + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Total Hours: " + totalHours + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Date Range: \n" + dateRange + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Total Hours: " + totalHours + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                
                InfoTableCell = new PdfPCell(new Phrase("Late & Under Time: " + underLate + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Worked Hours: " + workedHours + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Late & Under Time: " + underLate + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("Worked Hours: " + workedHours + "", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Salary: " + salary + " PHP" , new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                
                InfoTableCell = new PdfPCell(new Phrase("Salary: " + salary + " PHP", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);
                InfoTableCell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL)));
                InfoTableCell.setBorder(PdfPCell.NO_BORDER);
                InfoTable.addCell(InfoTableCell);


                PdfPTable SchedTable = new PdfPTable(17);
                SchedTable.setWidthPercentage(100);
                
                SchedTable.getDefaultCell().setBorder(0);
                //create a cell object
                PdfPCell SchedTableCell;
                
                Phrase phraseDayS = new Phrase();
                Phrase phraseAMTotal = new Phrase();
                Phrase phraseAMStart = new Phrase();
                Phrase phraseAMEnd = new Phrase();
                Phrase phrasePMTotal = new Phrase();
                Phrase phrasePMStart = new Phrase();
                Phrase phrasePMEnd = new Phrase();
                Phrase phraseTotalTime = new Phrase();
                Phrase phraseBlankS = new Phrase();
                Phrase phraseDaySx = new Phrase();
                Phrase phraseAMTotalx = new Phrase();
                Phrase phraseAMStartx = new Phrase();
                Phrase phraseAMEndx = new Phrase();
                Phrase phrasePMTotalx = new Phrase();
                Phrase phrasePMStartx = new Phrase();
                Phrase phrasePMEndx = new Phrase();
                Phrase phraseTotalTimex = new Phrase();
                
                phraseDayS.add(new Chunk("Day",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseDayS);
                phraseAMStart.add(new Chunk("AM Start",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMStart);
                phraseAMEnd.add(new Chunk("AM End",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMEnd);
                phraseAMTotal.add(new Chunk("AM Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMTotal);
                phrasePMStart.add(new Chunk("PM Start",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMStart);
                phrasePMEnd.add(new Chunk("PM End",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMEnd);
                phrasePMTotal.add(new Chunk("PM Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMTotal);
                phraseTotalTime.add(new Chunk("Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseTotalTime);
                phraseBlankS.add(new Chunk("           ",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseBlankS);

                phraseDaySx.add(new Chunk("Day",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseDaySx);
                phraseAMStartx.add(new Chunk("AM Start",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMStartx);
                phraseAMEndx.add(new Chunk("AM End",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMEndx);
                phraseAMTotalx.add(new Chunk("AM Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseAMTotalx);
                phrasePMStartx.add(new Chunk("PM Start",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMStartx);
                phrasePMEndx.add(new Chunk("PM End",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMEndx);
                phrasePMTotalx.add(new Chunk("PM Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phrasePMTotalx);
                phraseTotalTimex.add(new Chunk("Total",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                SchedTable.addCell(phraseTotalTimex);
                
 
            
            while (rs.next()){ 
                SimpleDateFormat readingFormat = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");
                String dayxxx = rs.getString("day");
                String amStartxx = rs.getString("amStart");
                String amEndxx = rs.getString("amEnd");
                String pmStartxx = rs.getString("pmStart");
                String pmEndxx = rs.getString("pmEnd");
                String amTotalxx = rs.getString("amTotal");
                String pmTotalxx = rs.getString("pmTotal");
                String totalHoursxx = rs.getString("totalTime");
                String blank = "          ";
                
                String amStart = null;
                String amEnd = null;
                String amTotal = null;
                String pmStart = null;
                String pmEnd = null;
                String pmTotal = null;
                String totalHoursx = null;
                
                Date dayx = new SimpleDateFormat("EEEE").parse(dayxxx);
                SimpleDateFormat sdfxxx = new SimpleDateFormat("EEE");
                String day = sdfxxx.format(dayx);
                System.out.println(day + " day");
                
                try {
                Date amStartTxx = readingFormat.parse(amStartxx);
                    amStart = outputFormat.format(amStartTxx);
                    if(amStart.equals("12:00 AM")) {
                        amStart = "00:00";
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }
                
                try {
                Date amEndTx = readingFormat.parse(amEndxx);
                    amEnd = outputFormat.format(amEndTx);
                        if(amEnd.equals("12:00 AM")) {
                        amEnd = "00:00";
                    }
                } catch(Exception e){
                         e.printStackTrace();
                } 
                
                try {
                Date amTotalTx = readingFormat.parse(amTotalxx);
                    amTotal = outputFormat.format(amTotalTx);
                    if(amTotal.equals("12:00 AM")) {
                        amTotal = "00:00";
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }
                
                try {
                Date pmStartTxx = readingFormat.parse(pmStartxx);
                    pmStart = outputFormat.format(pmStartTxx);
                    if(pmStart.equals("12:00 AM")) {
                    pmStart = "00:00";
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }  
                
                try {
                Date pmEndTxx = readingFormat.parse(pmEndxx);
                    pmEnd = outputFormat.format(pmEndTxx);
                    if(pmEnd.equals("12:00 AM")) {
                    pmEnd = "00:00"; 
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }  
                
                try {
                Date pmTotalTxx = readingFormat.parse(pmTotalxx);
                    pmTotal = outputFormat.format(pmTotalTxx);
                    if(pmTotal.equals("12:00 AM")) {
                    pmTotal = "00:00"; 
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }
                try {
                Date totalHoursxT = readingFormat.parse(totalHoursxx);
                    totalHoursx = outputFormat.format(totalHoursxT);
                    if(totalHoursx.equals("12:00 AM")) {
                    totalHoursx = "00:00"; 
                    }
                } catch(Exception e){
                         e.printStackTrace();
                }
                System.out.println(day);
                System.out.println(amStart);
                System.out.println(amEnd);
                System.out.println(amTotal);
                System.out.println(pmStart);
                System.out.println(pmEnd);
                System.out.println(pmTotal);
                System.out.println(totalHoursx);

                
                                SchedTableCell=new PdfPCell(new Phrase(day, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                
                                SchedTableCell=new PdfPCell(new Phrase(amStart, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(amEnd, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(amTotal, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(pmStart, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(pmEnd, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(pmTotal, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(totalHoursx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(blank));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(day, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(amStart, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(amEnd, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(amTotal, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(pmStart, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(pmEnd, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);
                                
                                SchedTableCell=new PdfPCell(new Phrase(pmTotal, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

                                SchedTableCell=new PdfPCell(new Phrase(totalHoursx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                                SchedTableCell.setBorder(PdfPCell.NO_BORDER);
                                SchedTable.addCell(SchedTableCell);

            }
            
            
                PdfPTable DTRTable = new PdfPTable(17);
                DTRTable.setWidthPercentage(100);
                
                DTRTable.getDefaultCell().setBorder(0);
                //create a cell object
                PdfPCell DTRTableCell;

                Phrase phraseDay = new Phrase();
                Phrase phraseDate = new Phrase();
                Phrase phraseAMIn = new Phrase();
                Phrase phraseAMOut = new Phrase();
                Phrase phrasePMIn = new Phrase();
                Phrase phrasePMOut = new Phrase();
                Phrase phraseHours = new Phrase();
                Phrase phraseUTL = new Phrase();
                Phrase phraseBlank = new Phrase();
                Phrase phraseDayx = new Phrase();
                Phrase phraseDatex = new Phrase();
                Phrase phraseAMInx = new Phrase();
                Phrase phraseAMOutx = new Phrase();
                Phrase phrasePMInx = new Phrase();
                Phrase phrasePMOutx = new Phrase();
                Phrase phraseHoursx = new Phrase();
                Phrase phraseUTLx = new Phrase();
                
                phraseDay.add(new Chunk("Day",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseDay);
                phraseDate.add(new Chunk("Date",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseDate);
                phraseAMIn.add(new Chunk("AM IN",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseAMIn);
                phraseAMOut.add(new Chunk("AM OUT",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseAMOut);
                phrasePMIn.add(new Chunk("PM IN",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phrasePMIn);
                phrasePMOut.add(new Chunk("PM OUT",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phrasePMOut);
                phraseHours.add(new Chunk("Hours",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseHours);
                phraseUTL.add(new Chunk("Late/U.T",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseUTL);
                phraseBlank.add(new Chunk("           ",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseBlank);

                phraseDayx.add(new Chunk("Day",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseDayx);
                phraseDatex.add(new Chunk("Date",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseDatex);
                phraseAMInx.add(new Chunk("AM IN",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseAMInx);
                phraseAMOutx.add(new Chunk("AM OUT",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseAMOutx);
                phrasePMInx.add(new Chunk("PM IN",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phrasePMInx);
                phrasePMOutx.add(new Chunk("PM OUT",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phrasePMOutx);
                phraseHoursx.add(new Chunk("Hours",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseHoursx);
                phraseUTLx.add(new Chunk("Late/U.T",  new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
                DTRTable.addCell(phraseUTLx);
                
                try {
                        Connection connx = null;
                        Statement stx = null;
                        connx = dbC.getConnection();
                        stx = connx.createStatement();

                        String queryx = "SELECT timeinout.day, timeinout.date, schedule.totalTime, schedule.amTotal, "
                                + "schedule.amStart, timeinout.amTimeIn, schedule.amEnd, timeinout.amTimeOut, "
                                + "schedule.pmTotal, schedule.pmStart, timeinout.pmTimeIn, schedule.pmEnd, "
                                + "timeinout.pmTimeOut FROM timeinout LEFT JOIN "
                                + "(schedule) ON (schedule.empId = timeinout.empId AND timeinout.day = schedule.day) WHERE  "
                                + "timeinout.empId ='"+ id +"' AND  schedule.empId = '"+ id +"' AND date(`Date`) >= '"+ fDate + "' "
                                + "AND date(`Date`) <= '"+ lDate +"'";
                ResultSet rsx = stx.executeQuery(queryx);
                while (rsx.next()) {
                String day = rsx.getString("timeinout.day");
                String date = rsx.getString("timeinout.date");
                String amStartDB = rsx.getString("schedule.amStart");
                String amIn = rsx.getString("timeinout.amTimeIn");
                String amEndDB = rsx.getString("schedule.amEnd");
                String amOut = rsx.getString("timeinout.amTimeOut");
                String pmStartDB = rsx.getString("schedule.pmStart");
                String pmIn = rsx.getString("timeinout.pmTimeIn");
                String pmEndDB = rsx.getString("schedule.pmEnd");
                String pmOut = rsx.getString("timeinout.pmTimeOut");
                String totTime = rsx.getString("schedule.totalTime");
                String wHours;
                String blank = "           ";
                String dayx = rsx.getString("timeinout.day");
                String datex = rsx.getString("timeinout.date");
//                String amStartDBx = rsx.getString("schedule.amStart");
//                String amInx = rsx.getString("timeinout.amTimeIn");
//                String amEndDBx = rsx.getString("schedule.amEnd");
//                String amOutx = rsx.getString("timeinout.amTimeOut");
//                String pmStartDBx = rsx.getString("schedule.pmStart");
//                String pmInx = rsx.getString("timeinout.pmTimeIn");
//                String pmEndDBx = rsx.getString("schedule.pmEnd");
                String pmOutx = rsx.getString("timeinout.pmTimeOut");
                String totalAM = rsx.getString("schedule.amTotal");
                String totalPM = rsx.getString("schedule.pmTotal");
                String totTimex = rsx.getString("schedule.totalTime");
                String wHoursx;
                

                parser.setTimeZone(TimeZone.getTimeZone("UTC"));
                
                                 if(!amEndDB.equals("00:00:00") && !amOut.equals("00:00:00")) {
                                            Start = parser.parse(amEndDB);
                                            In = parser.parse(amOut);
                                           if (Start.after(In)) {
                                               amOutDiff = tDiff.timeDiff(amOut, amEndDB );
                                               
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
                                           
                                            Start = parser.parse(amStartDB);
                                            In = parser.parse(amIn);
                                           if (Start.after(In)) {
                                               amInDiff = "00:00:00";
                                           } else if(amOutDiff.equals(totalAM)) {
                                               amInDiff = "00:00:00";
                                           } else {
                                               amInDiff = tDiff.timeDiff(amStartDB, amIn );
                                               
                                               amInDiffT = parser.parse(amInDiff);
                                               amInDiffTotal += amInDiffT.getTime();
                                               
                                           }
                 if(!pmEndDB.equals("00:00:00") && !pmOut.equals("00:00:00")) {
                                            Start = parser.parse(pmEndDB);
                                            In = parser.parse(pmOut);
                                           if (Start.after(In)) {
                                              pmOutDiff = tDiff.timeDiff(pmOut,pmEndDB );
                                               
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
                                           
                                            Start = parser.parse(pmStartDB);
                                            In = parser.parse(pmIn);
                                           if (Start.after(In)) {
                                                 pmInDiff = "00:00:00";
                                           } else if(pmOutDiff.equals(totalPM)) {
                                                pmInDiff = "00:00:00";
                                           } else {
                                               pmInDiff = tDiff.timeDiff(pmStartDB, pmIn );
                                               
                                               pmInDiffT = parser.parse(pmInDiff);
                                               pmInDiffTotal += pmInDiffT.getTime();
                                               
                                           }
                                           
                    Date totH = parser.parse(totTimex);
                    long totHTotal = 0;
                            totHTotal += totH.getTime();                  
                                           

                          Date amInDiffx = parser.parse(amInDiff);
                          Date amOutDiffx = parser.parse(amOutDiff);
                          Date pmInDiffx = parser.parse(pmInDiff);
                          Date pmOutDiffx = parser.parse(pmOutDiff);
                          
                long workedHoursxxx =    amInDiffx.getTime()  + amOutDiffx.getTime() + pmInDiffx.getTime() + pmOutDiffx.getTime(); 
                
                    long hoursx = workedHoursxxx / 3600000;
                    long minutesx = (workedHoursxxx % 3600000) / 60000;

                    wHours = String.format("%02d:%02d",hoursx, minutesx);  
                    wHoursx = String.format("%02d:%02d",hoursx, minutesx);  
                    
                Date dayxxx = new SimpleDateFormat("EEEE").parse(day);
                SimpleDateFormat sdfxxx = new SimpleDateFormat("EEE");
                String newDayxx = sdfxxx.format(dayxxx);
                System.out.println(newDayxx);
                DTRTableCell = new PdfPCell(new Phrase(newDayxx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                Date datexx = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                String newDate = sdf.format(datexx);
                System.out.println(newDate);
                DTRTableCell = new PdfPCell(new Phrase(newDate, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(amIn);
                DTRTableCell = new PdfPCell(new Phrase(amIn, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);

                System.out.println(amOut);
                DTRTableCell = new PdfPCell(new Phrase(amOut, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(pmIn);
                DTRTableCell = new PdfPCell(new Phrase(pmIn, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(pmOut);
                DTRTableCell = new PdfPCell(new Phrase(pmOut, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(totTime);
                DTRTableCell = new PdfPCell(new Phrase(totTime, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(wHours);
                DTRTableCell = new PdfPCell(new Phrase(wHours, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(blank);
                DTRTableCell = new PdfPCell(new Phrase(blank));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);

                Date dayxx = new SimpleDateFormat("EEEE").parse(dayx);
                SimpleDateFormat sdfxx = new SimpleDateFormat("EEE");
                String newDayx = sdfxx.format(dayxx);
                System.out.println(newDayx);
                DTRTableCell = new PdfPCell(new Phrase(newDayx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                Date datexxx = new SimpleDateFormat("yyyy-MM-dd").parse(datex);
                SimpleDateFormat sdfx = new SimpleDateFormat("dd-MM-yy");
                String newDatex = sdfx.format(datexxx);
                System.out.println(newDatex);
                DTRTableCell = new PdfPCell(new Phrase(newDatex, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                
                System.out.println(amIn);
                DTRTableCell = new PdfPCell(new Phrase(amIn, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);


                System.out.println(amOut);
                DTRTableCell = new PdfPCell(new Phrase(amOut, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);

                System.out.println(pmIn);
                DTRTableCell = new PdfPCell(new Phrase(pmIn, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);

                System.out.println(pmOutx);
                DTRTableCell = new PdfPCell(new Phrase(pmOutx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);

                System.out.println(totTimex);
                DTRTableCell = new PdfPCell(new Phrase(totTimex, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);
                

                System.out.println(wHoursx);
                DTRTableCell = new PdfPCell(new Phrase(wHoursx, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                DTRTableCell.setBorder(PdfPCell.NO_BORDER);
                DTRTable.addCell(DTRTableCell);               
                        
                        
                }       
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            document.add(dateRecTable);
            document.add(InfoTable);
            document.add(new Paragraph("-----------------------------------------------------------------------               ----------------------------------------------------------------------", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("                                   Schedule                                                                                     Schedule\n",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
            document.add(SchedTable);
            document.add(new Paragraph("-----------------------------------------------------------------------               ----------------------------------------------------------------------", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("                             Dailty Time Record                                                                     Dailty Time Record\n",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
            document.add(DTRTable);
            document.add(ICTable);
            
                try {
                    conn = dbC.getConnection();
                  
                    PreparedStatement stmtx = conn.prepareStatement("INSERT INTO released"
                            + "(empId, firstDate, lastDate, totalHours, lateUnderTime, workedHours, fileName, salary) VALUES (?,?,?,?,?,?,?,?)");
                
                    stmtx.setString(1,id);
                    stmtx.setString(2,fDate); 
                    stmtx.setString(3,lDate); 
                    stmtx.setString(4,totalHours); 
                    stmtx.setString(5,workedHours); 
                    stmtx.setString(6,totalHours); 
                    stmtx.setString(7,"Report-"+ reportCount +"." + lastRId+"-"+ id + ".pdf"); 
                    stmtx.setString(8,salary+""); 

                    stmtx.executeUpdate();

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
            document.close();
       }catch(DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
    }   catch (ParseException ex) {
            Logger.getLogger(printPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    

public void OpenPDFFile(){
      
      
   try {

		File pdfFile = new File("reports/Report-"+ reportCount +"." + lastRId+"-"+ id + ".pdf");
		if (pdfFile.exists()) {

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfFile);
			} else {
				System.out.println("Awt Desktop is not supported!");
			}

		} else {
			System.out.println("File is not exists!");
		}

		System.out.println("Done");
                reportCount++;
	  } catch (Exception ex) {
		ex.printStackTrace();
	  }

}

public void OpenPDFFile2(String fileName){
      this.fileName = fileName;
      String finalName = "reports/" + fileName +"";
      System.out.println(finalName);
   try {

		File pdfFile = new File(finalName);
                
		if (pdfFile.exists()) {

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfFile);
			} else {
				System.out.println("Awt Desktop is not supported!");
			}

		} else {
			JOptionPane.showMessageDialog(null, "File Not Found! Maybe Deleted or Moved.");
		}

		System.out.println("Done");
                reportCount++;
	  } catch (Exception ex) {
		ex.printStackTrace();
	  }

}
  
}
