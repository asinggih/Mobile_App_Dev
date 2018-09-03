package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import blob.happypetsy.studentmanagementportal.Adapters.*;
import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.*;

public class Todo extends AppCompatActivity {

    Button newTaskBut;
    RelativeLayout newTaskContainer;
    CardView newTaskCard;
    TextView cancelLink, addLink;
    Task task;


    TextInputLayout nil, lil, dil;
    EditText taskNameInput, taskDueInput, taskLocInput;
    Calendar calendar;
    int day, month, year;

    ArrayList<Task> tasksEntries;
    boolean[] checkFlag;
    TaskListAdapter tla;
    ListView taskList;

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

//        tasksEntries = new ArrayList<Task>();
        tasksEntries = db.getAllTask();

        newTaskContainer = (RelativeLayout) findViewById(R.id.new_task_container);
        newTaskCard = (CardView) findViewById(R.id.new_task_card);

        newTaskBut = (Button) findViewById(R.id.newTask_button);
        newTaskBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskCard.setVisibility(View.VISIBLE);

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

//        Log.d("inside tasksEntries are: ", tasksEntries.toString());

        /* -----------------------------------------------------------------------------------------

                                     Todo List View Init

         ----------------------------------------------------------------------------------------- */

//        Task t1 = new Task("do this", "12/9/18", "Sydney SCC");
//        Task t2 = new Task("blijkl", "12/9/18", "Sydney SCC");
//        Task t3 = new Task("asdf", "12/9/18", "Sydney SCC");
//        Task t4 = new Task("asdfeeew", "12/9/18", "");
//        Task t5 = new Task("faf", "12/9/18", "");
//        Task t6 = new Task("sdfewa", "12/9/18", "");
//        Task t7 = new Task("sss", "12/9/18", "Parramatta");
//
//        entries = new ArrayList<Task>();
//
//        entries.add(t1);
//        entries.add(t2);
//        entries.add(t3);
//        entries.add(t4);
//        entries.add(t5);
//        entries.add(t6);
//        entries.add(t7);

        checkFlag = new boolean[tasksEntries.size()];
        tla = new TaskListAdapter(Todo.this, tasksEntries);

        taskList = (ListView) findViewById(R.id.todo_list);
        taskList.setAdapter(tla);

//        TextView title = (TextView) findViewById(R.id.title_todo);
//        title.setText("Todo activity");

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
