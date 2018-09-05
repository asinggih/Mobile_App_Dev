package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.Task;

public class Todo_edit extends AppCompatActivity {

    Context context;

    Calendar calendar;
    int day, month, year;

    TextInputLayout neil;
    EditText taskNameInput, taskLocationInput, taskDueInput;
    Spinner completeFlag;
    ImageButton deleteBut;
    Button update, closeWindow;

    long taskID;

    DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo_edit);

        context = Todo_edit.this;

        db = new DatabaseManager(Todo_edit.this);

        Intent intent = getIntent();
        final Task taskToEdit = (Task) intent.getSerializableExtra("Task_to_edit");

        taskID = new Long(taskToEdit.getTaskID());

        neil = (TextInputLayout) findViewById(R.id.taskName_edit_input_layout);
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

        closeWindow = (Button) findViewById(R.id.editTask_close_button);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Todo_edit.this, Todo.class);
                startActivity(intent);
            }
        });


        update = (Button) findViewById(R.id.update_task_but);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameInput.getText().toString();
                String taskLoc = taskLocationInput.getText().toString();
                String taskDue = taskDueInput.getText().toString();
                int taskComplete = completeFlag.getSelectedItemPosition();  // need to flip value later on

                if (isValid(taskName)){
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

                    Log.d("taskID: ", String.valueOf(taskID));
                    Log.d("key: ", column.toString());
                    Log.d("column: ", values.toString());

                    db.updateTODO(taskID, column, values);

                    Intent intent = new Intent(Todo_edit.this, Todo.class);
                    startActivity(intent);

                }

            }
        });

        deleteBut = (ImageButton) findViewById(R.id.task_to_trash);
        deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = taskNameInput.getText().toString();
                alertBox(taskName);
            }
        });

    }

    private void alertBox(final String taskName){
        // initialising new alert object
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Remove the task below ?");

        alert
                .setMessage(taskName)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteTask(taskID);
                        Intent intent = new Intent(Todo_edit.this, Todo.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alert.create();

        // show it
        alertDialog.show();

        Button posBut = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negBut= alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        posBut.setTextColor(getResources().getColor(R.color.colorAccentDark, null));
        posBut.setTypeface(Typeface.DEFAULT_BOLD);

        negBut.setTextColor(getResources().getColor(R.color.colorAccentDark, null));
        negBut.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private boolean isValid(String taskName){

        boolean flag = true;

        if (taskNameInput.getText().toString().trim().equals("")){
            neil.setError("Task name is required");
            flag = false;
        }
        return flag;

    }

}
