package blob.happypetsy.studentmanagementportal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import blob.happypetsy.studentmanagementportal.Exam;
import blob.happypetsy.studentmanagementportal.R;
import blob.happypetsy.studentmanagementportal.Wrappers.ExamDetails;
import blob.happypetsy.studentmanagementportal.Wrappers.Student;
import blob.happypetsy.studentmanagementportal.Wrappers.Task;

public class ExamListAdapter extends ArrayAdapter<ExamDetails>{

    private Context context;
    private ArrayList<ExamDetails> entries;
    private boolean checked = false;
    private boolean[] checkBoxFlag;
    private View.OnClickListener onDelButtonClickListener;
    ImageButton examDelete;


    public ExamListAdapter(Context context, ArrayList<ExamDetails> entries){
        super(context, R.layout.upcoming_exam_layout, entries);
        this.context = context;
        this.entries = entries;
        checkBoxFlag = new boolean [entries.size()];
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryItem = inflater.inflate(R.layout.exam_list_template, parent, false);

        CheckBox doneFlcag = (CheckBox) entryItem.findViewById(R.id.choose);
        LinearLayout examDetails = (LinearLayout) entryItem.findViewById(R.id.exam_detail_container);
        TextView examName = (TextView) entryItem.findViewById(R.id.exam_name);
        TextView examDate = (TextView) entryItem.findViewById(R.id.exam_date);
        TextView examTime = (TextView) entryItem.findViewById(R.id.exam_time);
        TextView examLocation = (TextView) entryItem.findViewById(R.id.exam_location);
        examDelete = (ImageButton) entryItem.findViewById(R.id.exam_delete);


        ExamDetails exam = entries.get(position);

        examName.setText(exam.getName());
        examDate.setText(exam.getDate());
        examTime.setText(exam.getStartTime());
        examLocation.setText(exam.getLocation());

        examDelete.setOnClickListener(this.onDelButtonClickListener);
        examDelete.setTag(position);


//        studentName.setText(exa,.getFirstName() + " " + student.getLastName());
//        studentID.setText(String.valueOf(student.getStudentID()));
//        studentProg.setText(student.getCourse());
//
//        if (student.getImgPath().equals("") ){
//            studentPhoto.setBackgroundResource(R.drawable.profpic);
//        }
//        else{
//            studentPhoto.setBackgroundResource(R.mipmap.agenda);
//
//        }
//
//        doneFlag.setChecked(checkBoxFlag[position]);
//        doneFlag.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(((CheckBox)v).isChecked()) {
//                    checkBoxFlag[position] = true;
//                }
//                else
//                    checkBoxFlag[position]=false;
//            }
//        });




        return entryItem;
    }
    public void setOnDelButtonClickListener(final View.OnClickListener onClickListener) {
        this.onDelButtonClickListener = onClickListener;
    }


}
