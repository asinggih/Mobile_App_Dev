package blob.happypetsy.studentmanagementportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import blob.happypetsy.studentmanagementportal.Adapters.StudentListAdapter;
import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.Student;

public class Sturec extends AppCompatActivity {

    Context context;
    SearchView searchBar;

    ListView studentList;
    ArrayList<Student> listEntries;
    StudentListAdapter sla;
    FloatingActionButton confirm, cancel;

    Button addNewStudentRecord;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sturec);

        context = Sturec.this;

        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setIconified(false);
            }
        });

        confirm = (FloatingActionButton) findViewById(R.id.confirm_action);
//        cancel = (FloatingActionButton) findViewById(R.id.cancel_action);

        addNewStudentRecord = (Button) findViewById(R.id.new_student_record);
        addNewStudentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Add_new_student.class);
                startActivity(intent);
            }
        });

        context = Sturec.this;

        db = new DatabaseManager(context);

        try {
            listEntries = db.getAllStudents();
        }
        catch (Exception e){
            listEntries = new ArrayList<>();
        }

        Log.d("blob: " ,listEntries.toString());
        sla = new StudentListAdapter(context, listEntries);

        studentList = (ListView) findViewById(R.id.student_list);
        studentList.setAdapter(sla);


        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Student toBeEdited = (Student) studentList.getAdapter().getItem(position);

//                Log.d("this student", toBeEdited.toString());
                Intent intent = new Intent (context, Sturec_edit.class);
                intent.putExtra("edit_student", toBeEdited);
                startActivity(intent);
            }
        });


        confirm = (FloatingActionButton) findViewById(R.id.confirm_action);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] flag = sla.getCheckBoxFlag();
                Log.d("flag: ", Arrays.toString(flag));
                // arraylist<string> filled with student IDs;


                ArrayList<String> goneList = new ArrayList<String>();

                for (int i=0; i < listEntries.size(); i++){
                    if (flag[i] == true) {

                        String toBeDeletedID = String.valueOf(sla.getItem(i).getStudentID());
                        goneList.add(toBeDeletedID);

                    }

                }

                db.deleteStudent(goneList);


                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

        BottomNavigationView botNav = (BottomNavigationView) findViewById(R.id.navigation);
        botNav.setSelectedItemId(R.id.navigation_sturec);

        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.navigation_todo:
                        Intent intent = new Intent(Sturec.this, Todo.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_sturec:

                        break;

                    case R.id.navigation_exam:
                        intent = new Intent(Sturec.this, Exam.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
}
