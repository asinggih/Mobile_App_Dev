package blob.happypetsy.studentmanagementportal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import blob.happypetsy.studentmanagementportal.Adapters.TaskListAdapter;
import blob.happypetsy.studentmanagementportal.Helpers.DatabaseManager;

public class Upcoming_exam_fragment extends Fragment{

    private static final String TAG = "UpcomingExam";

    Context context;

    private Button btnTEST;
    TaskListAdapter tla;
    ListView examList;

    DatabaseManager db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_exam_layout,container,false);

//        context = Upcoming_exam_fragment.this;
//        db = new DatabaseManager(context);
//
//        examList = new


        return view;
    }
}
