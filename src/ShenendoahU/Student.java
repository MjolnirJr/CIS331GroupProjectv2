/*
 * Project Authors: Vincent Hoang, Thomas Knupp, Tran Le, Jom Zeng, Chris Torchia
 * Date: 04/26/20
 * Assignment: HW 7 - ShenendoahU
 * Project Purpose: Desktop Applciation for Shenendoah U to manage course registration including 
                    course creation, student registration/removal, instructor assignment and roster creation.
                    This will minimize effort, errors, and redundancy, and allow for easy expansion and
                    training of the Registrar’s team.

 * Class Purpose: Student class defines behaviors and attributes for a given student instance. 
                  Student instances will be initalized in the main application class with no variables initialized
                  and will utilize "setter" methods to handle vaariable initlaiiztion to allow for 
                  class level data entry validation as in the setStudentWithEmail() method
 */

// ******** Student class written by Tran Le ************

package ShenendoahU;
public class Student {
    
    private String firstName;
    private String lastName;
    private String studentYear;
    private String studentMajor;
    private String studentEmail;
    private double GPA;
    private int studentID;
    private static int nextStudentID = 200000;
        
    public Student(String name, int year, String studentMajor, double GPA, 
            String studentEmail){
        setStudentName(name);
        setStudentYear(year);
        this.studentMajor = studentMajor;
        this.GPA = GPA;
        this.studentEmail = studentEmail;
        this.studentID = nextStudentID;
        nextStudentID++;        
    }   
    
    public Student(){
        this.studentID = nextStudentID;
        nextStudentID++;
    }
    
    public void setStudentName(String name){
        if(name.contains(" "))
        {
            String[] splitName = name.split(" ");
            this.firstName = splitName[0];
            this.lastName = splitName[1]; 
        }
        else
        {
            this.firstName = name;
            this.lastName = "";
        }        
    }
    
    public void setStudentYear(int year){
        switch(year){
            case 1:
                this.studentYear = "Freshman";
                break;
            case 2:
                this.studentYear = "Sophomore";
                break;
            case 3:
                this.studentYear = "Junior";
                break;
            case 4:
                this.studentYear = "Senior";
                break;
            default:
                this.studentYear = "Undefined";
                break;
        }
    }
        
    public void setGPA(double GPA){
        this.GPA = GPA;
    }
    
    //Checks to make sure GPA is valid, i.e. between 0 and 5 inclusive
    //If valid, initializes GPA and returns true, else does not init GPA and returns false
    public boolean setGPAWithCheck(double GPA)
    {
        if((GPA >= 0) && (GPA<=5))
        {
            this.GPA = GPA;
            return true;
        }
        else
            return false;
    }
    
    public void setStudentEmail(String studentEmail){
        this.studentEmail = studentEmail;
    }
    
    public boolean setStudentEmailWithCheck(String email){
        //Checks to makre sure that email contains "@" as well as a valid domain ending (.com,.edu,etc)
        //If valid, sets email and returns true, else returns false 
        if((email.contains("@")) && ((email.contains(".com") || email.contains(".net") || 
                email.contains(".edu")) || email.contains(".org")))
        {
            this.studentEmail = email;
            return true;
        }
        else
            return false;        
    }
    
    public void setStudentMajor(String major){
        this.studentMajor = major;
    }         
    
    public double getGPA(){
        return this.GPA;
    }
    
    public String getName(){
        // Returns Student FirstName and LastName as one formatted string
        return String.format("%s %s",this.firstName,this.lastName);
    }
    
    public int getStudentID(){
        return this.studentID;
    }
    
    public String getStudentYear(){
        return this.studentYear;
    }
    
    public String getStudentMajor(){
        return this.studentMajor;
    }
}
