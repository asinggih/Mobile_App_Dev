package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Todo extends AppCompatActivity {

    Button newTaskBut;
    RelativeLayout newTaskContainer;
    CardView newTaskCard;
    TextView cancelLink, addLink;
    blob.happypetsy.studentmanagementportal.Task task;

    boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo);

        newTaskContainer = (RelativeLayout) findViewById(R.id.new_task_container);
        newTaskCard = (CardView) findViewById(R.id.new_task_card);

        newTaskBut = (Button) findViewById(R.id.newTask_button);
        newTaskBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskCard.setVisibility(View.VISIBLE);

            }
        });

        cancelLink = (TextView) findViewById(R.id.cancel_task_link);
        cancelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskCard.setVisibility(View.GONE);

            }
        });

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
