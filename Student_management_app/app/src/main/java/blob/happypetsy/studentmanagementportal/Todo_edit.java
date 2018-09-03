package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.Task;

public class Todo_edit extends AppCompatActivity {

    Calendar calendar;
    int day, month, year;

    EditText taskNameInput, taskLocationInput, taskDueInput;
    Spinner completeFlag;
    ImageButton deleteBut;
    Button update;

    int taskID;

    DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo_edit);

        Intent intent = getIntent();
        final Task taskToEdit = (Task) intent.getSerializableExtra("Task_to_edit");
        Log.d("shipped task: " , taskToEdit.toString());

        taskID = taskToEdit.getTaskID();

        taskNameInput = (EditText) findViewById(R.id.taskName_edit_input);
        taskNameInput.setText(taskToEdit.getTaskName(), TextView.BufferType.EDITABLE);

        taskLocationInput = (EditText) findViewById(R.id.taskLoc_edit_input);
        taskLocationInput.setText(taskToEdit.getLocation(), TextView.BufferType.EDITABLE);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        taskDueInput = findViewById(R.id.taskDue_edit_input);
        taskDueInput.setText(taskToEdit.getDate(), TextView.BufferType.EDITABLE);
        taskDueInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Todo_edit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int day) {
                                Calendar examCalendar = Calendar.getInstance();
                                examCalendar.set(year,month, day);
                                taskDueInput.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy").format(examCalendar.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        List<String> completedStatus = new ArrayList<String>();
        completedStatus.add("Yes");
        completedStatus.add("No");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, completedStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        completeFlag = (Spinner) findViewById(R.id.checkbox_dropdown);
        completeFlag.setAdapter(dataAdapter);

        // 1 is no
        // 0 is yes
        if (taskToEdit.getDoneFlag() == 1){
            completeFlag.setSelection(0);
        }
        else{
            completeFlag.setSelection(1);
        }

        update = (Button) findViewById(R.id.update_task_but);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameInput.getText().toString();
                String taskLoc = taskLocationInput.getText().toString();
                String taskDue = taskDueInput.getText().toString();
                int taskComplete = completeFlag.getSelectedItemPosition();  // need to flip value later on

                ArrayList<String> column = new ArrayList<String>();
                ArrayList<String> values = new ArrayList<String>();

                column.add("name");
                values.add(taskName);

                column.add("date");
                values.add(taskDue);

                column.add("location");
                values.add(taskLoc);

                column.add("done");
                if (taskComplete==1){
                    values.add("0");
                }
                else{
                    values.add("1");
                }
                
                db.updateTODO(taskID, column, values);



            }
        });


    }

}
