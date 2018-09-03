package blob.happypetsy.studentmanagementportal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import blob.happypetsy.studentmanagementportal.Helpers.*;

public class Add_Exam extends AppCompatActivity {

    Button closeBut, submitBut;
    Spinner durationDropdown, startTimeDropdown;
    TextInputLayout nil, lil, dil;
    EditText examNameInput, examLocInput, examDateInput;
    Calendar calendar;
    int day, month, year;

    DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add__exam);

        db = new DatabaseManager(this);

        nil = (TextInputLayout) findViewById(R.id.name_input_layout);
        lil = (TextInputLayout) findViewById(R.id.location_input_layout);
        dil = (TextInputLayout) findViewById(R.id.date_input_layout);

        examNameInput = (EditText) findViewById(R.id.exam_name_input);
        examLocInput  = (EditText) findViewById(R.id.exam_location_input);

        /* -------------------------------------------------------------------------------------------

                                                    Exam Date

        ------------------------------------------------------------------------------------------- */

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        examDateInput = findViewById(R.id.exam_date_input);
        examDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Exam.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int day) {
                                Calendar examCalendar = Calendar.getInstance();
                                examCalendar.set(year,month, day);
                                examDateInput.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy").format(examCalendar.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });


        /* -------------------------------------------------------------------------------------------

                                               Exam Duration

        ------------------------------------------------------------------------------------------- */

        List<String> categories = new ArrayList<String>();
        categories.add("1 hour");
        categories.add("2 hours");
        categories.add("3 hours");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        durationDropdown = (Spinner) findViewById(R.id.exam_duration);
        durationDropdown.setAdapter(dataAdapter);


        /* -------------------------------------------------------------------------------------------

                                               Exam Start Time

        ------------------------------------------------------------------------------------------- */


        String startTime[] = new String[]{
                "10:00",
                "12:00",
                "14:00",
                "16.00"
        };

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(Add_Exam.this, android.R.layout.simple_spinner_item, startTime);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        startTimeDropdown = (Spinner) findViewById(R.id.start_dropdown);
        startTimeDropdown.setAdapter(timeAdapter);


        /* -------------------------------------------------------------------------------------------

                                              Submission Handling

        ------------------------------------------------------------------------------------------- */


        submitBut = (Button) findViewById(R.id.submit_exam_form);
        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(areValid()){

                    String cleanName = examNameInput.getText().toString();
                    String cleanLoc = examLocInput.getText().toString();

                    String ed= examDateInput.getText().toString();
                    String parts[] = ed.split(", ");
                    String cleanDate = parts[1].replace("/", "-");

                    String tempDur = String.valueOf(durationDropdown.getSelectedItem());
                    int cleanDur = Character.getNumericValue(tempDur.charAt(0));

                    String cleanStart = String.valueOf(startTimeDropdown.getSelectedItem());

                    String cleanEnd = calculateEnd(cleanStart, cleanDur);

                    String insMes = cleanName + " exam has been added";

                    db.insertExam(cleanName, cleanDate, cleanStart, cleanEnd, cleanLoc);

                    examNameInput.getText().clear();
                    examLocInput.getText().clear();
                    examDateInput.getText().clear();

                    getCurrentFocus().clearFocus();

                    Toast toast = Toast.makeText(Add_Exam.this, insMes, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                }
                else{
                    Log.d("submit message", "screw you");
                }

            }
        });

        closeBut = (Button) findViewById(R.id.newExam_close_button);
        closeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Exam.this, Exam.class);
                startActivity(intent);
            }
        });

    }

    private boolean areValid(){

        boolean flag = true;

        if(examNameInput.getText().toString().trim().equals("") ){      // generating pretty error message
            nil.setError("Exam name is required");
            flag = false;
        }
        else{
            nil.setError(null);
        }

        if(examLocInput.getText().toString().trim().equals("") ){
            lil.setError("Exam location is required");
            flag = false;
        }
        else{
            lil.setError(null);
        }

        if(examDateInput.getText().toString().trim().equals("") ){
            dil.setError("Exam date is required");
            flag = false;
        }
        else{
            dil.setError(null);
        }

        return flag;
    }

    private String calculateEnd(String startTime, int duration){

        String[] parts = startTime.split(":");
        int start = Integer.parseInt(parts[0]);

        String out = String.valueOf(start + duration);
        out += ":00";

        return out;

    }




}
