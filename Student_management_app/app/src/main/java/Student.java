
package blob.happypetsy.studentmanagementportal;

import java.util.Arrays;

public class Student {

    private int studentID, age;
    private String firstName, lastName, course, address;
    private char gender;

    public Student (){
    }

    public Student (int sID, String fName, String lName, int age, char gender, String course, String address) {
        this.studentID = sID;
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
        this.gender = gender;
        this.course = course;
        this.address = address;
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

    public void setAddress(String address){
        this.address = address;
    }


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

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        String[] out = new String[] {
                String.valueOf(studentID),
                firstName,
                lastName,
                String.valueOf(age),
                String.valueOf(gender),
                course,
                address
        };
        return Arrays.toString(out);
    }

}