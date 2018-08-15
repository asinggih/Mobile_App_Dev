import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

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



    /*  -----------------------------------------------

                        Column Names

        ---------------------------=====--------------- */
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

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        SQLiteStatement stmt;

        // CREATE USERS TABLE
        String createUsers = "CREATE TABLE IF NOT EXISTS " + USER + "(" +

                USER_ID     +" INTEGER PRIMARY KEY AUTOINCREMENT," +
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

    }

    public void insertUser(String fn, String ln, String uname, String pass){

        String sql = "INSERT OR REPLACE INTO " + USER + " ( " +

                USER_FNAME  + "," +
                USER_LNAME  + "," +
                USER_UNAME  + "," +
                USER_PASS   +

                ")" +
                "VALUES ( ?, ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, fn);
        stmt.bindString(2, ln);
        stmt.bindString(3, uname);
        stmt.bindString(4, pass);

        stmt.executeInsert();
        stmt.clearBindings();
        db.close();
    }

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