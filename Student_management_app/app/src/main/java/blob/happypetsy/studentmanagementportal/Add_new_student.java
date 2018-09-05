package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;

public class Add_new_student extends AppCompatActivity {

    Context context;
    Spinner genderDropdown, programDropdown;
    Button closeWindow, submitRecord;

    TextInputLayout fnIL, lnIL, addrIL, dobIL;
    EditText stuFNameInput, stuLNameInput, stuAddrInput, stuDobInput;
    ImageView stuPhoto;
    Calendar calendar;
    int day, month, year;

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_new_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = Add_new_student.this;

        db = new DatabaseManager(context);

        closeWindow = (Button) findViewById(R.id.newStudent_close_but);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sturec.class);
                startActivity(intent);
            }
        });


        fnIL = (TextInputLayout) findViewById(R.id.student_fname_input_layout);
        lnIL = (TextInputLayout) findViewById(R.id.student_lname_input_layout);
        addrIL = (TextInputLayout) findViewById(R.id.student_address_input_layout);
        dobIL = (TextInputLayout) findViewById(R.id.student_dob_input_layout);

        stuFNameInput = (EditText) findViewById(R.id.student_fname_input);
        stuLNameInput = (EditText) findViewById(R.id.student_lname_input);
        stuAddrInput = (EditText) findViewById(R.id.student_address_input);


        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        stuDobInput = findViewById(R.id.student_dob_input);
        stuDobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int day) {
                                Calendar examCalendar = Calendar.getInstance();
                                examCalendar.set(year,month, day);
                                stuDobInput.setText(new SimpleDateFormat("dd/MM/yyyy").format(examCalendar.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().updateDate(1995, 2 - 1, 1);
                datePickerDialog.show();

            }
        });


        /* -------------------------------------------------------------------------------------------

                                              Gender Dropdown Init

        ------------------------------------------------------------------------------------------- */

        String gender[] = new String[]{
                "Male", "Female", "Other"
        };

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderDropdown = (Spinner) findViewById(R.id.gender_dropdown);
        genderDropdown.setAdapter(genderAdapter);


        /* -------------------------------------------------------------------------------------------

                                              Programs Dropdown Init

        ------------------------------------------------------------------------------------------- */

        String programs[] = new String[]{
                "Master of ICT",
                "Master of Business Administration",
                "Master of Data Science",
                "Bachelor of Arts",
                "Bachelor of Science"
        };

        ArrayAdapter<String> programAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        programDropdown = (Spinner) findViewById(R.id.program_dropdown);
        programDropdown.setAdapter(programAdapter);





        /* -------------------------------------------------------------------------------------------

                                              Submission Handling

        ------------------------------------------------------------------------------------------- */

        submitRecord = (Button) findViewById(R.id.submit_new_student_form);
        submitRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (areValid()){

                    String fname = stuFNameInput.getText().toString().trim();
                    String lname = stuLNameInput.getText().toString().trim();
                    String addr = stuAddrInput.getText().toString().trim();
                    String dob = stuDobInput.getText().toString().trim();

                    String gender = String.valueOf(genderDropdown.getSelectedItem()).substring(0,1);

                    String selectedProgram = String.valueOf(programDropdown.getSelectedItem());
                    int programID = getProgramID(selectedProgram.trim());


                    db.insertStudent(
                            fname,
                            lname,
                            dob,
                            gender,
                            addr,
                            ""
                    );

                    Log.d("here", "here");

//                    db.inser
                    db.insertProgEnrolment(db.printAutoIncrements(), programID);

                    Intent intent = new Intent(context, Sturec.class);
                    startActivity(intent);
                }

            }
        });

    }

    private boolean areValid(){

        boolean flag = true;

        if(stuFNameInput.getText().toString().trim().equals("") ){      // generating pretty error message
            fnIL.setError("First name is required");
            flag = false;
        }
        else{
            fnIL.setError(null);
        }

        if(stuLNameInput.getText().toString().trim().equals("") ){
            lnIL.setError("Last name is required");
            flag = false;
        }
        else{
            lnIL.setError(null);
        }

        if(stuAddrInput.getText().toString().trim().equals("") ){
            addrIL.setError("Address is required");
            flag = false;
        }
        else{
            addrIL.setError(null);
        }

        if(stuDobInput.getText().toString().trim().equals("") ){
            dobIL.setError("Date of birth is required");
            flag = false;
        }
        else{
            dobIL.setError(null);
        }

        return flag;
    }

    private int getProgramID(String programName){

        int id;

        if (programName.equals("Master of ICT")){
            id = 0;
        }
        else if (programName.equals("Master of Business Administration")){
            id = 1;
        }
        else if (programName.equals("Master of Data Science")){
            id = 2;
        }
        else if (programName.equals("Bachelor of Arts")){
            id = 3;
        }
        else{
            id = 4;
        }

        return id;

    }

}
