package AppPackage;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.util.*;
import java.text.*;
 
 
class ClockLabel extends JLabel implements ActionListener {
 
  String type;
  SimpleDateFormat sdf;
 
  public ClockLabel(String type) {
    this.type = type;
    setForeground(new Color(241,242,242));
 
    switch (type) {
      case "date" : sdf = new SimpleDateFormat("E,  MMMM dd, yyyy");
                    setFont(new Font("sans-serif", Font.PLAIN, 18));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
      case "time" : sdf = new SimpleDateFormat("hh:mm:ss a");
                    setFont(new Font("sans-serif", Font.PLAIN, 23));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
//      case "day"  : sdf = new SimpleDateFormat("  ");
//                    setFont(new Font("sans-serif", Font.PLAIN, 18));
//                    setHorizontalAlignment(SwingConstants.RIGHT);
//                    break;
      default     : sdf = new SimpleDateFormat();
                    break;
    }
 
    Timer t = new Timer(1000, this);
    t.start();
  }
 
  public void actionPerformed(ActionEvent ae) {
    Date d = new Date();
    setText(sdf.format(d));
  }
}
 