/*
 * Project Authors: Vincent Hoang, Thomas Knupp, Tran Le, Jom Zeng, Chris Torchia
 * Date: 04/26/20
 * Assignment: HW 7 - ShenendoahU
 * Project Purpose: Desktop Applciation for Shenendoah U to manage course registration including 
                    course creation, student registration/removal, instructor assignment and roster creation.
                    This will minimize effort, errors, and redundancy, and allow for easy expansion and
                    training of the Registrar’s team.

 * Class Purpose: Instructor class defines behaviors and attributes for a given Instructor instance.
                  Instructors will be created by the main application with all variables except email initialized
                  and will initalize email via "setEmailWithCheck() method to allow for data entry validation
 */

// ******** Student class written by Thomas Knupp ************

package ShenendoahU;
public class Instructor {
    private String name;
    private String prefix;
    private String officeLocation;
    private String department;
    private String email;
    private int instructorID;
    private static int nextInstructID = 100000;
    
    public Instructor()
    {
        this.instructorID = nextInstructID;
        nextInstructID++;  
    }
    
    public Instructor(String name, String prefix, String officeLocation, String department)
    {
        this.name = name;
        this.prefix = prefix;
        this.officeLocation = officeLocation;
        this.department = department;
        this.instructorID = nextInstructID;
        nextInstructID++;        
    }
    
    public Instructor(String name, String prefix, String officeLocation, String department, 
            String email)
    {
        this.name = name;
        this.prefix = prefix;
        this.officeLocation = officeLocation;
        this.department = department;
        this.email = email;
        this.instructorID = nextInstructID;
        nextInstructID++;        
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }
    
    public void setDepartment(String department)
    {
        this.department = department;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public boolean setEmailWithCheck(String email){
        //Checks to makre sure that email contains "@" as well as a valid domain ending (.com,.edu,etc)
        //If valid, sets email and returns true, else returns false 
        if((email.contains("@")) && ((email.contains(".com") || email.contains(".net") || 
                email.contains(".edu")) || email.contains(".org")))
        {
            this.email = email;
            return true;
        }
        else
            return false;        
    }
    
    public void setOfficeLocation(String officeLocation)
    {
        this.officeLocation = officeLocation;
    }
    
    public String getTitle()
    {
        //Returns instructor title as a formatted string consisting of Prefix + Name
        return (this.prefix + " " + this.name);
    }
    
    public String toString()
    {
        //Returns formatted string containing information about an instructor instance
        return String.format("Instructor ID: %d Name: %-25s Department: %-15s Office Location: %-15s Email: %-30s", 
                this.instructorID,this.getTitle(),this.department,this.officeLocation,this.email);
    }
    
}
