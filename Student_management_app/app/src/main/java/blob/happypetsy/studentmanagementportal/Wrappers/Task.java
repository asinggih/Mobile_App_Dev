
package blob.happypetsy.studentmanagementportal.Wrappers;

import java.io.Serializable;
import java.util.Arrays;

public class Task implements Serializable {

    private int taskID, doneFlag;
    private String taskName, date, location;

    public Task (String taskName, String date, String location){
        this.taskName = taskName;
        this.date = date;
        this.location = location;
        this.doneFlag = 0;
    }

    public Task (int taskID, String taskName, String date, String location, int doneFlag){
        this.taskID = taskID;
        this.taskName = taskName;
        this.date = date;
        this.location = location;
        this.doneFlag = doneFlag;
    }

    public int getDoneFlag() {return this.doneFlag;}

    public int getTaskID() {return this.taskID;}

    public String getTaskName() {
        return this.taskName;
    }

    public String getDate() {
        return this.date;
    }

    public String getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        String[] out = new String[] {
                String.valueOf(taskID),
                taskName,
                date,
                location,
                String.valueOf(doneFlag)
        };
        return Arrays.toString(out);
    }
}
