

package blob.happypetsy.studentmanagementportal.Wrappers;


import java.util.Calendar;

public class ExamDetails {

    private String name, location, startTime;
    private Calendar date;
    private int duration;

    public ExamDetails(String name, String location, Calendar date, String startTime, int duration){
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName(){
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public Calendar getDate() {
        return this.date;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public int getDuration(){
        return this.duration;
    }
}
