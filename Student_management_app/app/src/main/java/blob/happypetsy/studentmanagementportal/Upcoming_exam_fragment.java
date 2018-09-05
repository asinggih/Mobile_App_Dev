package blob.happypetsy.studentmanagementportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import blob.happypetsy.studentmanagementportal.Adapters.ExamListAdapter;
import blob.happypetsy.studentmanagementportal.Adapters.TaskListAdapter;
import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;
import blob.happypetsy.studentmanagementportal.Wrappers.ExamDetails;
import blob.happypetsy.studentmanagementportal.Wrappers.Task;

public class Upcoming_exam_fragment extends Fragment{

    private static final String TAG = "UpcomingExam";

    Context context;

    private Button btnTEST;
    ExamListAdapter ela;
    ListView examList;

    ArrayList<ExamDetails> entries;
    DatabaseManager db;

    boolean[] checkFlag;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_exam_layout,container,false);

        db = new DatabaseManager(getActivity());

        entries = db.getUpcomingExams();

        Log.d("entires: ", String.valueOf(entries.size()));

        ela = new ExamListAdapter(getActivity(), entries);
        ela.setOnDelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                ExamDetails toBeDeleted= (ExamDetails) examList.getAdapter().getItem(position);

                db.deleteTask(toBeDeleted.getId());

                Intent intent = new Intent (getActivity(), Exam.class);
                startActivity(intent);

            }
        });

        examList = (ListView)view.findViewById(R.id.upcoming_exam_list);
        examList.setAdapter(ela);

//        examName = (TextView)




        return view;
    }
}
