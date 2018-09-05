
package blob.happypetsy.studentmanagementportal.Helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import blob.happypetsy.studentmanagementportal.Wrappers.Student;
import blob.happypetsy.studentmanagementportal.Wrappers.Task;

public class DatabaseManager extends SQLiteOpenHelper {

    // DB details
    private static final String DB_NAME = "SM_Portal.db";
    private static final int DB_VERSION = 1;

    /*  -----------------------------------------------

                        Table Names

    ---------------------------=====--------------- */
    private static final String USER ="users";
    private static final String STUDENT_INFO ="student_info";
    private static final String PROGRAMS ="programs";
    private static final String PROGRAM_ENROLMENT ="program_enrolment";
    private static final String EXAM ="exam";
    private static final String EXAM_ALLOCATION ="exam_allocation";
    private static final String TODO ="todo";



    /*  -----------------------------------------------

                        Column Names

        ---------------------------=====--------------- */

    // user table
    private static final String USER_ID = "id";
    private static final String USER_FNAME = "first_name";
    private static final String USER_LNAME = "last_name";
    private static final String USER_UNAME = "username";
    private static final String USER_PASS = "hashed_password";


    // student_info table
    private static final String STUDENT_ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String DOB = "dob";
    private static final String GENDER = "gender";
    private static final String ADDRESS = "address";
    private static final String IMG_PATH= "img_path";


    // programs_table
    private static final String PROGRAM_ID = "id";
    private static final String PROGRAM_NAME = "name";

    // program_enrolment table
    private static final String ENROLMENT_ID = "id";
    private static final String STUDENT = "student";
    private static final String PROGRAM = "program";


    // exam table
    private static final String EXAM_ID ="id";
    private static final String EXAM_NAME ="exam_name";
    private static final String EXAM_DATE ="date";
    private static final String EXAM_START_TIME ="time_start";
    private static final String EXAM_END_TIME ="time_end";
    private static final String EXAM_LOCATION ="location";

    // exam_allocation table
    private static final String E_ALLOC_ID ="id";
//    private static final String EXAM = "exam";            // previously defined
//    private static final String PROGRAM ="program";

    // todo table
    private static final String TODO_ID = "id";
    private static final String TODO_NAME = "name";
    private static final String TODO_DUE = "date";
    private static final String TODO_LOCATION = "location";
    private static final String TODO_DONE_FLAG = "done";



    public DatabaseManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENT_INFO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROGRAMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROGRAM_ENROLMENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXAM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXAM_ALLOCATION);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        SQLiteStatement stmt;

        // CREATE USERS TABLE
        String createUsers = "CREATE TABLE IF NOT EXISTS " + USER + "(" +

                USER_ID     +" INTEGER NOT NULL PRIMARY KEY,"+
                USER_FNAME  +" VARCHAR(255) NOT NULL," +
                USER_LNAME  +" VARCHAR(255) NOT NULL," +
                USER_UNAME  +" VARCHAR(255) NOT NULL," +
                USER_PASS   +" VARCHAR(255) NOT NULL" +

                ")";

        stmt = db.compileStatement(createUsers);
        stmt.execute();


        // CREATE STUDENT_INFO TABLE
        String createStuInfo = "CREATE TABLE IF NOT EXISTS " + STUDENT_INFO + "(" +

                STUDENT_ID  +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                FIRST_NAME  +" VARCHAR(255) NOT NULL," +
                LAST_NAME   +" VARCHAR(255) NOT NULL," +
                DOB         +" DATE NOT NULL," +
                GENDER      +" CHARACTER(1) NOT NULL," +  // [M, F, or O]
                ADDRESS     +" VARCHAR(255) NOT NULL," +
                IMG_PATH    +" VARCHAR(255) " +

                ")";

        stmt = db.compileStatement(createStuInfo);
        stmt.execute();


        // CREATE PROGRAMS TABLE
        String createPrograms = "CREATE TABLE IF NOT EXISTS " + PROGRAMS + "(" +

                PROGRAM_ID      +" INTEGER NOT NULL PRIMARY KEY," +
                PROGRAM_NAME    +" VARCHAR(255) " +

                ")";

        stmt = db.compileStatement(createPrograms);
        stmt.execute();



        // CREATE PROGRAM_ENROLMENT TABLE
        String createEnrolment = "CREATE TABLE IF NOT EXISTS " + PROGRAM_ENROLMENT + "(" +

                ENROLMENT_ID    +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                STUDENT         +" INTEGER NOT NULL," +
                PROGRAM         +" INTEGER NOT NULL," +

                "FOREIGN KEY (" + STUDENT + ") REFERENCES " + STUDENT_INFO + "(id)," +
                "FOREIGN KEY (" + PROGRAM + ") REFERENCES " + PROGRAMS + "(id)" +

                ")";

        stmt = db.compileStatement(createEnrolment);
        stmt.execute();

        // CREATE EXAM TABLE
        String createExam = "CREATE TABLE IF NOT EXISTS " + EXAM + "(" +

                EXAM_ID         +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                EXAM_NAME       +" VARCHAR(255) NOT NULL, " +
                EXAM_DATE       +" DATE NOT NULL, " +
                EXAM_START_TIME +" NUMERIC, " +
                EXAM_END_TIME   +" NUMERIC, " +
                EXAM_LOCATION   +" VARCHAR(255) " +

                ")";

        stmt = db.compileStatement(createExam);
        stmt.execute();


        // CREATE EXAM_ALLOCATION TABLE
        String createExamAllocation = "CREATE TABLE IF NOT EXISTS " + EXAM_ALLOCATION + "(" +

                E_ALLOC_ID      +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                STUDENT         +" INTEGER NOT NULL," +
                EXAM            +" INTEGER NOT NULL," +


                "FOREIGN KEY (" + STUDENT + ") REFERENCES " + STUDENT + "(id)," +
                "FOREIGN KEY (" + EXAM + ") REFERENCES " + EXAM + "(id)" +

                ")";

        stmt = db.compileStatement(createExamAllocation);
        stmt.execute();

        // CREATE TODO TABLE
        String createTodo = "CREATE TABLE IF NOT EXISTS " + TODO + "(" +

                TODO_ID         +" INTEGER NOT NULL PRIMARY KEY,"+
                TODO_NAME       +" VARCHAR(255) NOT NULL," +
                TODO_DUE        +" DATE NOT NULL," +
                TODO_LOCATION   +" VARCHAR(255)," +
                TODO_DONE_FLAG  +" INTEGER NOT NULL" +

                ")";

        stmt = db.compileStatement(createTodo);
        stmt.execute();
    }

    /* -------------------------------------------------------------------------------------------

                                          User Ops

       -------------------------------------------------------------------------------------------*/

    public void insertUser(long id, String fn, String ln, String uname, String pass){

        String sql = "INSERT OR REPLACE INTO " + USER + " ( " +

                USER_ID     + "," +
                USER_FNAME  + "," +
                USER_LNAME  + "," +
                USER_UNAME  + "," +
                USER_PASS   +

                ")" +
                "VALUES (?, ?, ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindLong(1, id);
        stmt.bindString(2, fn);
        stmt.bindString(3, ln);
        stmt.bindString(4, uname);
        stmt.bindString(5, pass);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

    public void deleteUser(String username){

        try {
            String sql = "DELETE FROM " + USER + " WHERE " + USER_UNAME + "= ?";

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, username);
            stmt.executeUpdateDelete();
            stmt.clearBindings();
            db.close();

        } catch (SQLException e) {
            Log.w("Exception:", e);
        }
    }


    /* -------------------------------------------------------------------------------------------

                                         Student Records Ops

       -------------------------------------------------------------------------------------------*/


    public void insertStudent(String fn, String ln, String dob, String gender, String addr, String imgPath){

        String sql = "INSERT OR REPLACE INTO " + STUDENT_INFO + " ( " +

                FIRST_NAME  + "," +
                LAST_NAME   + "," +
                DOB         + "," +
                GENDER      + "," +
                ADDRESS     + "," +
                IMG_PATH    +

                ")" +
                "VALUES ( ?, ?, ?, ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, fn);
        stmt.bindString(2, ln);
        stmt.bindString(3, dob);
        stmt.bindString(4, gender);
        stmt.bindString(5, addr);
        stmt.bindString(6, imgPath);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();

    }

    public void deleteStudent(ArrayList<String> stuList){

        String whereCon = stuList.toString();
        whereCon = whereCon.replace("[","(");
        whereCon = whereCon.replace("]",")");
        Log.d("here: ", whereCon);

        try {
            String sql = "DELETE FROM " + STUDENT_INFO + " WHERE " + STUDENT_ID + " IN " + whereCon;

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.executeUpdateDelete();
            stmt.clearBindings();

            sql = "DELETE FROM program_enrolment where student in " + whereCon;
            stmt = db.compileStatement(sql);
            stmt.executeUpdateDelete();
            stmt.clearBindings();

            sql = "DELETE FROM exam_allocation where student in" + whereCon;
            stmt = db.compileStatement(sql);
            stmt.executeUpdateDelete();
            stmt.clearBindings();

            db.close();

        } catch (SQLException e) {
            Log.w("Exception:", e);
        }
    }

    public void updateStudentInfo(long id, ArrayList<String> keys, ArrayList<String> values){

        String cond = TextUtils.join(" = ?, ", keys);

        try {

            String sql = "UPDATE " + STUDENT_INFO + " SET " +
                    cond + " = ? " + "WHERE " +
                    STUDENT_ID + " = ?";

            Log.d("updateStudent: ", sql);

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(sql);

            for (int i=0; i < values.size() ; i++){
                stmt.bindString(i+1, values.get(i));
            }
            stmt.bindLong(values.size()+1, id);

            stmt.executeUpdateDelete();
            stmt.clearBindings();
            db.close();

        }
        catch (SQLException e) {
            Log.w("Exception:", e);
        }


    }

    public ArrayList<Student> getAllStudents() {

        LocalDate currentDate = LocalDate.now();
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> productRows = new ArrayList<Student>();

//        Log.d("test getting upcoming exam", Arrays.toString(getUpcomingStudentExam(1).get(0)));

        String[] columns = null;
        Cursor cursor = db.query(STUDENT_INFO, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            ArrayList<Integer> upcomingExams = new ArrayList<>();

            try{
                for(String[] detail : getUpcomingStudentExam(Long.valueOf(cursor.getInt(0)))){
                    upcomingExams.add(Integer.parseInt(detail[0]));
                }
            }
            catch (Exception e){
                upcomingExams = new ArrayList<>();
            }

            Student student;

            productRows.add( student = new Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    calculateAge(cursor.getString(3), currentDate),
                    cursor.getString(4).charAt(0),
                    getProgramEnrolment(cursor.getInt(0)).get(2),
                    cursor.getString(5),
                    upcomingExams,
                    cursor.getString(6)
                    )
            );
            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public String getStudentDOB(long sID) {

        String dob = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] { DOB };
        String[] selectionArgs = new String[] { String.valueOf(sID) };
        Cursor cursor = db.query(STUDENT_INFO, columns, STUDENT_ID+"=?", selectionArgs, null, null, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            dob = cursor.getString(0);

            cursor.moveToNext();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return dob;


    }


    private static int calculateAge(String birthDate, LocalDate currentDate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate finalBirthDate;

        finalBirthDate = LocalDate.parse(birthDate, formatter);

        if ((finalBirthDate != null) && (currentDate != null)) {
            return Period.between(finalBirthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }


    public void clearRecords (String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
    }


    /* -------------------------------------------------------------------------------------------

                                          Programs Ops

       -------------------------------------------------------------------------------------------*/

    public void insertPrograms(long progID, String progName){

        String sql = "INSERT OR REPLACE INTO " + PROGRAMS + " ( " +

                PROGRAM_ID      + "," +
                PROGRAM_NAME    +

                ")" +
                "VALUES (?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindLong(1, progID);
        stmt.bindString(2, progName);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

    public void insertProgEnrolment(long studentID, long progID){

        String sql = "INSERT OR REPLACE INTO " + PROGRAM_ENROLMENT + " ( " +

                STUDENT     + "," +
                PROGRAM     +

                ")" +
                "VALUES ( ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindLong(1, studentID);
        stmt.bindLong(2, progID);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

//    select student, program, programs.name
//    from program_enrolment
//    inner join programs on programs.id = program_enrolment.program
//    where student = 0;

    private ArrayList<String> getProgramEnrolment(long sID){
        String[] columns = new String[] {PROGRAM}; // we can use null so that it returns everything
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select "+ STUDENT + ", " + PROGRAM + "," + PROGRAMS +"." + PROGRAM_NAME +
                        " from " + PROGRAM_ENROLMENT +
                        " inner join " + PROGRAMS + " on " + PROGRAMS+ "."+ PROGRAM_ID + " = " + PROGRAM_ENROLMENT+ "."+ PROGRAM +
                        " where "+ STUDENT + "= ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(sID)});

//        Cursor cursor = db.query(PROGRAM_ENROLMENT, columns, STUDENT+"=?", new String[] { String.valueOf(sID) }, null, null, null);

        ArrayList<String> output = new ArrayList<String>();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){ // while cursor is not at the last entry
            output.add(String.valueOf(cursor.getInt(0)));
            output.add(String.valueOf(cursor.getInt(1)));
            output.add(cursor.getString(2));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        Log.d("output size: ", String.valueOf(output.size()));

        return output;
    }


    /* -------------------------------------------------------------------------------------------

                                          Exams Ops

       -------------------------------------------------------------------------------------------*/

    public void insertExam(String name, String date, String startTime, String endTime, String location){

        String sql = "INSERT OR REPLACE INTO " + EXAM + " ( " +

                EXAM_NAME       + "," +
                EXAM_DATE       + "," +
                EXAM_START_TIME + "," +
                EXAM_END_TIME   + "," +
                EXAM_LOCATION   +

                ")" +

                "VALUES ( ?, ?, ?, ?, ? )";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, name);
        stmt.bindString(2, date);
        stmt.bindString(3, startTime);
        stmt.bindString(4, endTime);
        stmt.bindString(5, location);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

    public void insertExamAllocation(long studentID, long examID){

        String sql = "INSERT OR REPLACE INTO " + EXAM_ALLOCATION + " ( " +

                STUDENT     + "," +
                EXAM        +

                ")" +
                "VALUES ( ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindLong(1, studentID);
        stmt.bindLong(2, examID);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

//    select exam.id exam.exam_name, exam.date, exam.start_time, exam.end_time
//    from exam_allocation
//    inner join exam on exam.id = exam_allocation.exam
//    where student = 1;
//select exam.id, exam.exam_name, exam.date, exam.time_start, exam.time_end from exam_allocation inner join exam on exam.id = exam_allocation.exam where student=1;
    private ArrayList<String[]> getUpcomingStudentExam(long sID){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select "+ EXAM + "." + EXAM_ID + "," + EXAM + "." + EXAM_NAME +"," + EXAM+ "." +EXAM_DATE + "," + EXAM+ "." +EXAM_START_TIME + ","+ EXAM+ "." +EXAM_END_TIME +
                " from " + EXAM_ALLOCATION +
                " inner join " + EXAM + " on " + EXAM+ "."+ EXAM_ID + " = " + EXAM_ALLOCATION+ "."+ EXAM +
                " where "+ STUDENT + "= ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(sID)});

        ArrayList<String[]> clean;
        ArrayList<String[]> output = new ArrayList<String[]>();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){ // while cursor is not at the last entry
            String[] temp = new String[] {
                    String.valueOf(cursor.getInt(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            };

            output.add(temp);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        Log.d("output size: ", String.valueOf(output.size()));

        if (output.size() == 0){
            clean = new ArrayList<String[]>();
        }else{
            clean = validateExams(output);
        }


        return clean;
    }

    private ArrayList<String[]> validateExams(ArrayList<String[]> upcomingExam){

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime currentTime = LocalTime.now();
        String timeNow = formatter.format(currentTime);
        timeNow = timeNow.substring(0, 2);

        DateTimeFormatter dateFromatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy");

        ArrayList<String[]> clean = new ArrayList<>();

        for(String[] detail: upcomingExam){
            LocalDate examDate = LocalDate.parse(detail[2], dateFromatter);
            if (examDate.isEqual(currentDate)){
                String startTime = detail[3].substring(0, 2);
                if (Integer.parseInt(startTime) >= Integer.parseInt(timeNow)){  // e.g., if exam starts at 8 and now is 6
                    clean.add(detail);
                }
            }
            else if (examDate.isAfter(currentDate)){  // e.g if currentDate is 6th and exam date is 7th
                clean.add(detail);
            }
        }

        return clean;

    }

    /* -------------------------------------------------------------------------------------------

                                          TODO Ops

       -------------------------------------------------------------------------------------------*/

    public void insertTask(Task task){

        String sql = "INSERT OR REPLACE INTO " + TODO + " ( " +

                TODO_NAME     + "," +
                TODO_DUE        + "," +
                TODO_LOCATION     + "," +
                TODO_DONE_FLAG  +

                ")" +
                "VALUES ( ?, ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, task.getTaskName());
        stmt.bindString(2, task.getDate());
        stmt.bindString(3, task.getLocation());
        stmt.bindLong(4, Long.valueOf(task.getDoneFlag()));


        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

//    select exam.exam_name from exam inner join exam on exam.id = exam_allocation.exam where exam_allocation.student = 1;



    public void updateTODO(long id, ArrayList<String> keys, ArrayList<String> values){

        String cond = TextUtils.join(" = ?, ", keys);

        try {

            String sql = "UPDATE " + TODO + " SET " +
                    cond + " = ? " + "WHERE " +
                    TODO_ID + " = ?";

            Log.d("updateTODO: ", sql);

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(sql);

            for (int i=0; i < values.size() ; i++){
                stmt.bindString(i+1, values.get(i));
            }
            stmt.bindLong(values.size()+1, id);

            Log.d("statement to string: ", stmt.toString());

            stmt.executeUpdateDelete();
            stmt.clearBindings();
            db.close();

        }
        catch (SQLException e) {
            Log.w("Exception:", e);
        }


    }

    public void deleteTask(long taskID){

        try {
            String sql = "DELETE FROM " + TODO + " WHERE " + TODO_ID + "= ?";

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindLong(1, taskID);
            stmt.executeUpdateDelete();
            stmt.clearBindings();
            db.close();

        } catch (SQLException e) {
            Log.w("Exception:", e);
        }
    }


    public ArrayList<Task> getAllTask(String doneFlag) {

        ArrayList<Task> TaskList = new ArrayList<Task>();

        String[] columns = new String[] {TODO_ID, TODO_NAME, TODO_DUE, TODO_LOCATION, TODO_DONE_FLAG}; // we can use null so that it returns everything
        SQLiteDatabase db = this.getReadableDatabase();

        Task task;

        Cursor cursor = db.query(TODO, columns, TODO_DONE_FLAG+"=?", new String[] { doneFlag }, null, null, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){ // while cursor is not at the last entry
            TaskList.add(
                    task = new Task(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            (int)cursor.getLong(4)
                    )
            );
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return TaskList;

    }


        /* -------------------------------------------------------------------------------------------

                                          MISC

       -------------------------------------------------------------------------------------------*/

    //    String table = "table_name";
    //    String[] columnsToReturn = { "column_1", "column_2" };
    //    String selection = "column_1 =?";
    //    String[] selectionArgs = { someValue }; // matched to "?" in selection
    //    Cursor dbCursor = db.query(table, columnsToReturn, selection, selectionArgs, null, null, null);

    public long printAutoIncrements(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT seq FROM SQLITE_SEQUENCE LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        long output = 0;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){ // while cursor is not at the last entry
            output = cursor.getLong(0);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return output;


    }



}