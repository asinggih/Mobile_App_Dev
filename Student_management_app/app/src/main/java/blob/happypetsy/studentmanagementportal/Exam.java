package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Exam extends AppCompatActivity {

    private CustomPageAdapter pa;

    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_exam);


        pa = new CustomPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        vp = (ViewPager) findViewById(R.id.content);
        setupViewPager(vp);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);


        tabLayout.getTabAt(0).setText(R.string.upcoming_tab_text);
        tabLayout.getTabAt(1).setText(R.string.all_tab_text);

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

                return false;
            }
        });

        pa = new CustomPageAdapter(getSupportFragmentManager());

    }

    private void setupViewPager(ViewPager vp) {
        CustomPageAdapter adapter = new CustomPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Upcoming_exam_fragment());
        adapter.addFragment(new All_exams_fragment());
        vp.setAdapter(adapter);
    }
}
