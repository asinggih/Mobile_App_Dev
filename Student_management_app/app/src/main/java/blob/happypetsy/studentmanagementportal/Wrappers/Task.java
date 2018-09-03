
package blob.happypetsy.studentmanagementportal.Wrappers;

import java.util.Arrays;

public class Task {

    private String taskName, date, location;

    public Task (String taskName, String date, String location){
        this.taskName = taskName;
        this.date = date;
        this.location = location;
    }

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
                taskName,
                date,
                location
        };
        return Arrays.toString(out);
    }
}
