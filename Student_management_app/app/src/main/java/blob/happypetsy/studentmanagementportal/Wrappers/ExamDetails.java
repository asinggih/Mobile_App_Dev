

package blob.happypetsy.studentmanagementportal.Wrappers;


import java.io.Serializable;
import java.util.Calendar;

public class ExamDetails implements Serializable {

    private String name, location, startTime, date;
    private int duration, id;

    public ExamDetails(int id, String name, String location, String date, String startTime, int duration){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getId() {return this.id;}

    public String getName(){
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDate() {
        return this.date;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public int getDuration(){
        return this.duration;
    }
}
