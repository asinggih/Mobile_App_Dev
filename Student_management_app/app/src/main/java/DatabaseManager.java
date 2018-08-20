
package blob.happypetsy.studentmanagementportal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private static final String EXAM_NAME ="name";
    private static final String EXAM_DATE ="date";
    private static final String EXAM_START_TIME ="start_time";
    private static final String EXAM_END_TIME ="end_time";
    private static final String EXAM_LOCATION ="location";

    // exam_allocation table
    private static final String E_ALLOC_ID ="id";
//    private static final String EXAM = "exam";            // previously defined
//    private static final String PROGRAM ="program";


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

                STUDENT_ID  +" INTEGER PRIMARY KEY AUTOINCREMENT," +
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

                PROGRAM_ID      +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                PROGRAM_NAME    +" VARCHAR(255) " +

                ")";

        stmt = db.compileStatement(createPrograms);
        stmt.execute();



        // CREATE PROGRAM_ENROLMENT TABLE
        String createEnrolment = "CREATE TABLE IF NOT EXISTS " + PROGRAM_ENROLMENT + "(" +

                ENROLMENT_ID    +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                STUDENT         +" INTEGER NOT NULL," +
                PROGRAM         +" INTEGER NOT NULL," +

                "FOREIGN KEY (" + STUDENT + ") REFERENCES " + STUDENT_INFO + "(id)," +
                "FOREIGN KEY (" + PROGRAM + ") REFERENCES " + PROGRAMS + "(id)" +

                ")";

        stmt = db.compileStatement(createEnrolment);
        stmt.execute();

        // CREATE EXAM TABLE
        String createExam = "CREATE TABLE IF NOT EXISTS " + EXAM + "(" +

                EXAM_ID         +" INTEGER PRIMARY KEY AUTOINCREMENT," +
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

                E_ALLOC_ID      +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXAM            +" INTEGER NOT NULL," +
                PROGRAM         +" INTEGER NOT NULL," +

                "FOREIGN KEY (" + EXAM + ") REFERENCES " + EXAM + "(id)," +
                "FOREIGN KEY (" + PROGRAM + ") REFERENCES " + PROGRAMS + "(id)" +

                ")";

        stmt = db.compileStatement(createExamAllocation);
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


    /* -------------------------------------------------------------------------------------------

                                          Programs Ops

       -------------------------------------------------------------------------------------------*/

    public void insertPrograms(String progName){

        String sql = "INSERT OR REPLACE INTO " + PROGRAMS + " ( " +

                PROGRAM_NAME +

                ")" +
                "VALUES (?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, progName);

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

    public void insertExamAllocation(long studentID, long examID, long progID){

        String sql = "INSERT OR REPLACE INTO " + PROGRAM_ENROLMENT + " ( " +

                STUDENT     + "," +
                EXAM        + "," +
                PROGRAM     +

                ")" +
                "VALUES ( ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindLong(1, studentID);
        stmt.bindLong(2, examID);
        stmt.bindLong(3, progID);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }




    //    String table = "table_name";
    //    String[] columnsToReturn = { "column_1", "column_2" };
    //    String selection = "column_1 =?";
    //    String[] selectionArgs = { someValue }; // matched to "?" in selection
    //    Cursor dbCursor = db.query(table, columnsToReturn, selection, selectionArgs, null, null, null);

    public ArrayList<String> getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> productRows = new ArrayList<String>();

        String[] columns = new String[] {"StudentID", "FirstName", "LastName", "YearOfBirth", "Gender"};
        Cursor cursor = db.query(STUDENT_INFO, columns, null, null, null, null, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(
                    cursor.getInt(0)) + ", " +
                    cursor.getString(1) + ", " +
                    cursor.getString(2) + ", " +
                    cursor.getString(3) + ", " +
                    cursor.getString(4)
            );
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public void clearRecords (String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
    }

}