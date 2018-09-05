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
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.Student;

public class Sturec_edit extends AppCompatActivity {

    Context context;
    TextView titleHeading, studentID, delStudent;
    TextInputLayout fnIL, lnIL, addrIL, dobIL;
    EditText stuFNameInput, stuLNameInput, stuAddrInput, stuDobInput;

    Spinner genderDropdown, programDropdown;
    Button closeWindow, updateRecord;

    Calendar calendar;
    int day, month, year;

    DatabaseManager db;

    long sID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sturec_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        context = Sturec_edit.this;

        db = new DatabaseManager(context);

        Intent intent = getIntent();
        final Student studentToEdit = (Student) intent.getSerializableExtra("edit_student");

        Log.d("edit this", studentToEdit.toString());

        /* ------------------------------------------------------------------------------------------

                            Connect Variables With Respective Layouts

         ------------------------------------------------------------------------------------------ */


        titleHeading = (TextView) findViewById(R.id.editStudentTitle);

        closeWindow = (Button) findViewById(R.id.editStudent_close_but);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sturec.class);
                startActivity(intent);
            }
        });

        studentID = (TextView) findViewById(R.id.student_id);

        delStudent = (TextView)findViewById(R.id.delete_student);

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

        String gender[] = new String[]{
                "Male", "Female", "Other"
        };

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderDropdown = (Spinner) findViewById(R.id.gender_dropdown);
        genderDropdown.setAdapter(genderAdapter);


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

        /* ------------------------------------------------------------------------------------------

                            Variables Assignment With Current Student Object

         ------------------------------------------------------------------------------------------ */
        sID = Long.valueOf(studentToEdit.getStudentID());

        delStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> students = new ArrayList<>();
                students.add(String.valueOf(studentToEdit.getStudentID()));
                String studentFullName = studentToEdit.getFirstName() +" "+ studentToEdit.getLastName();


                alertBox(studentFullName, students);
//                db.deleteStudent(students);
//
//                Intent intent = new Intent(context, Sturec.class);
//                startActivity(intent);

            }
        });

        studentID.setText(String.valueOf(studentToEdit.getStudentID()));
        stuFNameInput.setText(studentToEdit.getFirstName());
        stuLNameInput.setText(studentToEdit.getLastName());
        stuAddrInput.setText(studentToEdit.getAddress());
        stuDobInput.setText(db.getStudentDOB(studentToEdit.getStudentID()));
        genderDropdown.setSelection(genderPos(studentToEdit.getGender()));
        programDropdown.setSelection(programPos(studentToEdit.getCourse()));


        updateRecord = (Button) findViewById(R.id.update_student_record);
        updateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (areValid()){

                    long sID = Long.valueOf(studentID.getText().toString());
                    String fname = stuFNameInput.getText().toString().trim();
                    String lname = stuLNameInput.getText().toString().trim();
                    String addr = stuAddrInput.getText().toString().trim();
                    String dob = stuDobInput.getText().toString().trim();
                    String gender = String.valueOf(genderDropdown.getSelectedItem()).substring(0,1);

                    String selectedProgram = String.valueOf(programDropdown.getSelectedItem());
                    int programID = getProgramID(selectedProgram.trim());

                    ArrayList<String> keys = new ArrayList<String>();
                    keys.add("first_name");
                    keys.add("last_name");
                    keys.add("dob");
                    keys.add("gender");
                    keys.add("address");


                    ArrayList<String> values = new ArrayList<String>();
                    values.add(fname);
                    values.add(lname);
                    values.add(dob);
                    values.add(gender);
                    values.add(addr);

                    db.updateStudentInfo(sID, keys, values);


//                    db.insertStudent(
//                            fname,
//                            lname,
//                            dob,
//                            gender,
//                            addr,
//                            ""
//                    );

                    Log.d("here", "here");

//                    db.inser
//                    db.insertProgEnrolment(db.printAutoIncrements(), programID);

                    Intent intent = new Intent(context, Sturec.class);
                    startActivity(intent);

                }

            }
        });


    }

    private int genderPos(char gender){

        int position = 0;

        if (gender == 'F'){
            position = 1;
        }
        else if (gender == 'O'){
            position =2;
        }

        return position;

    }


    private int programPos (String program){

        int position = 0;

        if (program.equals("Master of Business Administration")){
            position =1;
        }

        else if (program.equals("Master of Data Science")){
            position =2;
        }
        else if (program.equals("Bachelor of Arts")){
            position = 3;
        }

        else if (program.equals("Bachelor of Science")){
            position = 4;
        }

        return position;

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

    private void alertBox(final String studentName, final ArrayList<String> studentIDlist){
        // initialising new alert object
        AlertDialog.Builder alert = new AlertDialog.Builder(context);


        alert.setTitle("Remove the student below ?");

        alert
                .setMessage(studentName)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteStudent(studentIDlist);
                        Intent intent = new Intent(context, Sturec.class);
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
}
