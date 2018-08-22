package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Todo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo);

        TextView title = (TextView) findViewById(R.id.title_todo);
        title.setText("Todo activity");

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
}
