
package blob.happypetsy.studentmanagementportal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import blob.happypetsy.studentmanagementportal.R;
import blob.happypetsy.studentmanagementportal.Wrappers.*;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> entries;
    private boolean checked = false;
    private boolean[] checkBoxFlag;

    public TaskListAdapter(Context context, ArrayList<Task> entries){
        super(context, R.layout.content_todo, entries);
        this.context = context;
        this.entries = entries;
        checkBoxFlag = new boolean [entries.size()];
    }

    public View getView(final int position, View convertView,  ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryItem = inflater.inflate(R.layout.todo_list_template, parent, false);

        CheckBox doneFlag = (CheckBox) entryItem.findViewById(R.id.choose);
        LinearLayout taskDetail = (LinearLayout) entryItem.findViewById(R.id.task_detail_container);
        TextView taskName = (TextView) entryItem.findViewById(R.id.task_name);
        TextView taskDue = (TextView) entryItem.findViewById(R.id.task_due_date);
        TextView taskLoc = (TextView) entryItem.findViewById(R.id.task_location);

        Task task = entries.get(position);

        taskName.setText(task.getTaskName());
        taskDue.setText(task.getDate());
        taskLoc.setText(task.getLocation());


        doneFlag.setChecked(checkBoxFlag[position]);
        doneFlag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxFlag[position] = true;
                }
                else
                    checkBoxFlag[position]=false;
            }
        });

        return entryItem;

    }

    public boolean[] getCheckBoxFlag(){
        return checkBoxFlag;
    }




}
