package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Exam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_exam);

        TextView title = (TextView) findViewById(R.id.title_exam);
        title.setText("exam activity");



        BottomNavigationView botNav = (BottomNavigationView) findViewById(R.id.navigation);
        botNav.setSelectedItemId(R.id.navigation_exam);

        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.navigation_todo:
                        Intent intent = new Intent(Exam.this, Todo.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_sturec:
                        intent = new Intent(Exam.this, Sturec.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_exam:

                        break;
                }
//                if (R.id.navigation_sturec == menuItem.getItemId()){
//                    Intent intent = new Intent(Exam.this, Sturec.class);
//                    startActivity(intent);
//                }
//
//                else if (R.id.navigation_todo == menuItem.getItemId()){
//                    Intent intent = new Intent(Exam.this, Todo.class);
//                    startActivity(intent);
//                }

//                else if (R.id.navigation_exam == menuItem.getItemId()){
//
//                }

                return false;
            }
        });
    }
}
