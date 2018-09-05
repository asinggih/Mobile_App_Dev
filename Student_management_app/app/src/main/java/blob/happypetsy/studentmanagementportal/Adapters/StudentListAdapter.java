package blob.happypetsy.studentmanagementportal.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

import blob.happypetsy.studentmanagementportal.R;
import blob.happypetsy.studentmanagementportal.Wrappers.Student;

public class StudentListAdapter extends ArrayAdapter<Student>{

    private Context context;
    private ArrayList<Student> entries;
    private boolean checked = false;
    private boolean[] checkBoxFlag;
    private boolean isLongPressed;



    public StudentListAdapter(Context context, ArrayList<Student> entries){
        super(context, R.layout.content_sturec, entries);
        this.context = context;
        this.entries = entries;
        checkBoxFlag = new boolean [entries.size()];
        isLongPressed = false;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryItem = inflater.inflate(R.layout.student_list_template, parent, false);

        CheckBox doneFlag = (CheckBox) entryItem.findViewById(R.id.choose_student);
        LinearLayout studentDetails = (LinearLayout) entryItem.findViewById(R.id.student_details_container);
        ImageView studentPhoto = (ImageView) entryItem.findViewById(R.id.student_image);
        TextView studentName = (TextView) entryItem.findViewById(R.id.student_name);
        TextView studentID = (TextView) entryItem.findViewById(R.id.student_id);
        TextView studentProg = (TextView) entryItem.findViewById(R.id.student_program);

        Student student = entries.get(position);

        studentName.setText(student.getFirstName() + " " + student.getLastName());
        studentID.setText(String.valueOf(student.getStudentID()));
        studentProg.setText(student.getCourse());

        if (student.getImgPath().equals("") ){
            studentPhoto.setBackgroundResource(R.drawable.profpic);
        }
        else{
            studentPhoto.setBackgroundResource(R.mipmap.agenda);

        }

        doneFlag.setChecked(checkBoxFlag[position]);
        doneFlag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxFlag[position] = true;
                }
                else
                    checkBoxFlag[position]=false;
            }
        });




        return entryItem;
    }

    public void showCheckbox() {
        isLongPressed = true;
        notifyDataSetChanged();  // Required for update
    }

    public boolean[] getCheckBoxFlag(){
        return checkBoxFlag;
    }
}
