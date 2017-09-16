package breadboy.com.tribe.github;

/**
 * Created by FRANKCHUKY on 9/14/2017.
 */

public class Student {

    //Variables that are in our json
    private int StudentId;
    private String StudentName;
    private String StudentMarks;
    private String email;

    public void setName(String name){
        this.email = name;
    }

    public String getName(){
        return email;
    }

    //Getters and setters
    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int bookId) {
        this.StudentId = bookId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String name) {
        this.StudentName = name;
    }

    public String getStudentMarks() {
        return StudentMarks;
    }

    public void setStudentMarks(String price) {
        this.StudentMarks = price;
    }

}