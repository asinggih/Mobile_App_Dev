package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Add_Exam extends AppCompatActivity {

    Button closeBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add__exam);

        closeBut = (Button) findViewById(R.id.newExam_close_button);
        closeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Exam.this, Exam.class);
                startActivity(intent);
            }
        });



    }

}
