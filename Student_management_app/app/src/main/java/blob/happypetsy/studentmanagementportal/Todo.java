package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import blob.happypetsy.studentmanagementportal.Adapters.*;
import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.*;

public class Todo extends AppCompatActivity {

    Button newTaskBut;
    RelativeLayout newTaskContainer;
    CardView newTaskCard, completedCard, incompleteCard;
    TextView cancelLink, addLink, toggleCompleted;
    FloatingActionButton markComplete;
    Task task;


    TextInputLayout nil, lil, dil;
    EditText taskNameInput, taskDueInput, taskLocInput;
    Calendar calendar;
    int day, month, year;

    ArrayList<Task> tasksEntries, completedTasks;
    boolean[] checkFlag;
    TaskListAdapter tla, cla;
    ListView taskList, completedTaskList;


    DatabaseManager db;


    boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo);

        /* -----------------------------------------------------------------------------------------

                                     New todo task creation

         ----------------------------------------------------------------------------------------- */

        db = new DatabaseManager(Todo.this);

        incompleteCard = (CardView) findViewById(R.id.task_list_card);
        completedCard = (CardView) findViewById(R.id.completed_task_list);

        tasksEntries = db.getAllTask("0");
        taskList = (ListView) findViewById(R.id.todo_list);

        completedTasks = db.getAllTask("1");
        completedTaskList = (ListView) findViewById(R.id.completed_list);


        newTaskContainer = (RelativeLayout) findViewById(R.id.new_task_container);
        newTaskCard = (CardView) findViewById(R.id.new_task_card);

        newTaskBut = (Button) findViewById(R.id.newTask_button);
        newTaskBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskCard.setVisibility(View.VISIBLE);
                if (completedCard.getVisibility() == View.VISIBLE){
                    completedCard.setVisibility(View.GONE);
                }
            }
        });

        // Setting up task name:
        nil = (TextInputLayout) findViewById(R.id.task_name_input_layout);
        taskNameInput = (EditText) findViewById(R.id.task_name_input);

        // Setting up task location:
        taskLocInput = (EditText) findViewById(R.id.task_loc_input);

        // Setting up calendar:
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        taskDueInput = findViewById(R.id.task_due_input);
        taskDueInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genDateDialog();
            }
        });


        // Submission Handling:
        cancelLink = (TextView) findViewById(R.id.cancel_task_link);
        cancelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskCard.setVisibility(View.GONE);

            }
        });

        addLink = (TextView) findViewById(R.id.add_task_link);
        addLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()){
                    Task task = new Task(taskNameInput.getText().toString(), taskDueInput.getText().toString(), taskLocInput.getText().toString());
                    db.insertTask(task);
                    tasksEntries.add(task);

                    taskNameInput.getText().clear();
                    taskDueInput.getText().clear();
                    taskLocInput.getText().clear();

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                else{
                    Log.d("nope ", "here");
                }

            }
        });


        /* -----------------------------------------------------------------------------------------

                                   Onging Todo List View Init

         ----------------------------------------------------------------------------------------- */

//        checkFlag = new boolean[tasksEntries.size()];
        tla = new TaskListAdapter(Todo.this, tasksEntries);
        taskList.setAdapter(tla);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task toBeEdited= (Task) taskList.getAdapter().getItem(position);
                Log.d("to be edited", toBeEdited.toString());
                Intent intent = new Intent(Todo.this, Todo_edit.class);
                intent.putExtra("Task_to_edit", toBeEdited);    // send variable to another activity
                startActivity(intent);

            }
        });


        /* -----------------------------------------------------------------------------------------

                                   Completed Todo List View Init

         ----------------------------------------------------------------------------------------- */

        cla = new TaskListAdapter(Todo.this, completedTasks);
        completedTaskList.setAdapter(cla);

        completedTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task toBeEdited= (Task) completedTaskList.getAdapter().getItem(position);
                Log.d("to be edited", toBeEdited.toString());
                Intent intent = new Intent(Todo.this, Todo_edit.class);
                intent.putExtra("Task_to_edit", toBeEdited);    // send variable to another activity
                startActivity(intent);

            }
        });

        toggleCompleted = (TextView) findViewById(R.id.toggle_completed);
        if (completedTasks.size() > 0 ){
            toggleCompleted.setVisibility(View.VISIBLE);
        }
        else{
            toggleCompleted.setVisibility(View.GONE);
        }
        toggleCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (completedCard.getVisibility()){
                    case View.VISIBLE:
                        completedCard.setVisibility(View.GONE);
                        toggleCompleted.setText("Show Completed");
                        break;

                    case View.GONE:
                        if (tasksEntries.size() == 0){
                            incompleteCard.setVisibility(View.GONE);
                        }
                        completedCard.setVisibility(View.VISIBLE);
                        toggleCompleted.setText("Hide Completed");
                        break;
                }
            }
        });





        markComplete = (FloatingActionButton) findViewById(R.id.fab_confirm);
        markComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] flag = tla.getCheckBoxFlag();
                Log.d("flag: ", Arrays.toString(flag));
                boolean[] otherFlag = cla.getCheckBoxFlag();
                Log.d("other flag: ", Arrays.toString(otherFlag));

                ArrayList<Task> completed = new ArrayList<Task>();

                for (int i=0; i < tasksEntries.size(); i++){
                    if (flag[i] == true) {
                        ArrayList<String> column = new ArrayList<String>();
                        ArrayList<String> values = new ArrayList<String>();
                        int completedTaskID = tla.getItem(i).getTaskID();
                        column.add("done");
                        values.add("1");
                        db.updateTODO(completedTaskID, column, values);
                    }

                }

                for (int i=0; i < completedTasks.size(); i++){
                    if (otherFlag[i] == false) {
                        ArrayList<String> column = new ArrayList<String>();
                        ArrayList<String> values = new ArrayList<String>();
                        int incompletedTaskID = cla.getItem(i).getTaskID();
                        column.add("done");
                        values.add("0");
                        Log.d("here ", "here");
                        db.updateTODO(incompletedTaskID, column, values);
                    }

                }

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

        BottomNavigationView botNav = (BottomNavigationView) findViewById(R.id.navigation);
        botNav.setSelectedItemId(R.id.navigation_todo);

        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_todo:

                        break;

                    case R.id.navigation_sturec:
                        Intent intent = new Intent(Todo.this, Sturec.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_exam:
                        intent = new Intent(Todo.this, Exam.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });

    }


    private void genDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Todo.this,
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

    private boolean isValid(){

        boolean flag = true;

        if(taskNameInput.getText().toString().trim().equals("") ){      // generating pretty error message
            nil.setError("Exam name is required");
            flag = false;
        }
        else{
            nil.setError(null);
        }

        return flag;

    }
}
