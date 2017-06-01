
package AppPackage;

public class DTRSched {
    private String day;
    private String date;
    private String amStart;
    private String amIn;
    private String amInDiff;
    private String amEnd;
    private String amOut;
    private String amOutDiff;
    private String pmStart;
    private String pmIn;
    private String pmInDiff;
    private String pmEnd;
    private String pmOut;
    private String pmOutDiff;
    private String dayHours;
    private String workedHours;
    

    public DTRSched(String day, String date, String amStart, String amIn, String amInDiff, String amEnd, String amOut, String amOutDiff, String pmStart, String pmIn, String pmInDiff, String pmEnd, String pmOut, String pmOutDiff, String dayHours, String workedHours) {
        this.day = day;
        this.amStart = amStart;
        this.amIn = amIn;
        this.amInDiff = amInDiff;
        this.amEnd = amEnd;
        this.amOut = amOut;
        this.amOutDiff = amOutDiff;
        this.pmStart = pmStart;
        this.pmIn = pmIn;
        this.pmInDiff = pmInDiff;
        this.pmEnd = pmEnd;
        this.pmOut = pmOut;
        this.pmOutDiff = pmOutDiff;
        this.date = date;
        this.dayHours = dayHours;
        this.workedHours = workedHours;
    }

    public String getDay() {
        return day;
    }

    public String getAmStart() {
        return amStart;
    }

    public String getAmIn() {
        return amIn;
    }

    public String getAmInDiff() {
        return amInDiff;
    }

    public String getAmEnd() {
        return amEnd;
    }

    public String getAmOut() {
        return amOut;
    }

    public String getAmOutDiff() {
        return amOutDiff;
    }

    public String getPmStart() {
        return pmStart;
    }

    public String getPmIn() {
        return pmIn;
    }

    public String getPmInDiff() {
        return pmInDiff;
    }

    public String getPmEnd() {
        return pmEnd;
    }

    public String getPmOut() {
        return pmOut;
    }

    public String getPmOutDiff() {
        return pmOutDiff;
    }

    public String getDate() {
        return date;
    }

    public String getDayHours() {
        return dayHours;
    }

    public String getWorkedHours() {
        return workedHours;
    }
    
    
    
}
