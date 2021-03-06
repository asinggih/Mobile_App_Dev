package blob.happypetsy.studentmanagementportal.Wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Student implements Serializable {

    private int studentID, age;
    private String firstName, lastName, course, address, imgPath;
    private ArrayList<Integer> upcomingExams; // ArrayList of examIDs
    private char gender;

    public Student (){
        this.upcomingExams = new ArrayList<Integer>();
    }

    public Student (int sID, String fName, String lName, int age, char gender, String course, String address, ArrayList<Integer> upcomingExams, String imgPath) {
        this.studentID = sID;
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
        this.gender = gender;
        this.course = course;
        this.address = address;
        this.upcomingExams = upcomingExams;
        this.imgPath = imgPath;
    }

    // Setters
    public void setStudentID(int sID){
        this.studentID = sID;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAge (int age){
        this.age = age;
    }

    public void setGender (char gender){
        this.gender = gender;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public void setUpcomingExams(int examID) {
        this.upcomingExams.add(examID);
    }

    public void setImgPath(String imgPath) {this.imgPath = imgPath; }


    // Getters
    public int getStudentID(){
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public String getCourse() {
        return course;
    }

    public String getAddress() { return address; }

    public ArrayList<Integer> getUpcomingExams() { return upcomingExams; }

    public String getImgPath() { return imgPath; }


    @Override
    public String toString() {
        String[] out = new String[] {
                String.valueOf(studentID),
                firstName,
                lastName,
                String.valueOf(age),
                String.valueOf(gender),
                course,
                address,
                upcomingExams.toString(),
                imgPath
        };
        return Arrays.toString(out);
    }

}
