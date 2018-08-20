package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

public class Sturec extends AppCompatActivity {

    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sturec);

//        TextView title = (TextView) findViewById(R.id.title_sturec);
//        title.setText("Sturec activity");

        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setIconified(false);
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
//
//                if (R.id.navigation_todo == menuItem.getItemId()){
//                    Intent intent = new Intent(Sturec.this, Todo.class);
//                    startActivity(intent);
//                }
//
//                else if (R.id.navigation_exam == menuItem.getItemId()){
//                    Intent intent = new Intent(Sturec.this, Exam.class);
//                    startActivity(intent);
//                }

                return false;
            }
        });
    }
}
