/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShenendoahU;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*; 
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.util.Callback;

// For Arraylist and Oracle DB
import java.sql.*;
import oracle.jdbc.pool.*;
import java.util.*;

// Enable ComboBox 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

/**
 *
 * @author hoang
 */
public class Application_FX extends Application 
{
    //Initialize Database Connection Objects    
    public static Connection dbConn;
    public static Statement commStmt;
    public static ResultSet dbResults;
    
    //Lists initialization
    //ObservableList used for Building Course ComboBox controls
    public static ArrayList<Student> studentArray  = new ArrayList<>();
    public static ArrayList<Course> courseArray  = new ArrayList<>();
    public static ArrayList<Instructor> instructorArray  = new ArrayList<>();
    public static ObservableList<Student> olStu = FXCollections.observableArrayList();
    public static ObservableList<Course> olCourse = FXCollections.observableArrayList();
    public static ObservableList<Instructor> olInstruc = FXCollections.observableArrayList();

    //Initalize Controls for Student Pane
    public Label lblAddStu = new Label("Add Student:");
    public Label lblStuName = new Label("Name: ");
    public Label lblStuYear = new Label("Year: ");
    public Label lblStuMajor = new Label("Major: ");
    public Label lblStuGPA = new Label("GPA: ");
    public Label lblStuMail = new Label("Email: ");
    public Button btnAddStu = new Button("Add Student ->");
    public ComboBox boxStuYear = new ComboBox();
    public TextField txtStuName = new TextField();
    public TextField txtStuMajor = new TextField();
    public TextField txtStuGPA = new TextField();
    public TextField txtStuMail = new TextField();
    
    
    //Initalize Controls for Course Pane
    public Label lblAddCourse = new Label("Add Course: ");
    public Label lblCourseName = new Label("Name: ");
    public Label lblBuilding = new Label("Building: ");
    public Label lblRoom = new Label ("Room: ");
    public Label lblMaxCap = new Label("Max Capacity: ");
    public Button btnAddCourse = new Button("Add Course ->");
    public ComboBox boxBuilding = new ComboBox();
    public TextField txtCourseName = new TextField();
    public TextField txtRoom = new TextField();
    public TextField txtMaxCap = new TextField();
    
    //Initalize Controls for Instructor Pane
    public Label lblAddInstruc = new Label("Add Instructor: ");
    public Label lblInstrucName = new Label("Name: ");
    public Label lblPrefix = new Label("Prefix: ");
    public Label lblOffice = new Label("Office: ");
    public Label lblDepartment = new Label("Department: ");
    public Label lblInstrucMail = new Label("Email: ");
    public Button btnAddInstruc = new Button("Add Instructor ->");
    public ComboBox boxPrefix = new ComboBox();
    public TextField txtInstrucName = new TextField();
    public TextField txtOffice = new TextField();
    public TextField txtDepartment = new TextField();
    public TextField txtInstrucMail = new TextField();
    
    //Initalize Controls for Edit Course Pane
    public Label lblBuildCourse = new Label("Edit a Course: ");
    public Label lblAddStu2 = new Label("Choose Student: ");
    public Label lblToCourse = new Label("Choose Course: ");
    public Label lblAddInstruc2 = new Label("Instructor is: ");
    public Button btnUpdtCourse = new Button("Update Course ->");
    public CheckBox cbNewInstruc = new CheckBox("New Instructor?");
    public ComboBox boxStu = new ComboBox(olStu);
    public ComboBox boxCourse = new ComboBox(olCourse);
    public ComboBox boxInstruc = new ComboBox(olInstruc);
    public RadioButton rdoAddStu = new RadioButton("Add Student");
    public RadioButton rdoRemoveStu = new RadioButton("Remove Student");
    public ToggleGroup rdoGroupEditCourse = new ToggleGroup();
    
    // TextArea for general output and information
    public TextArea txtOut = new TextArea();
    
    @Override
    public void start(Stage primaryStage) 
    {
        //System.out.println("test\n");
        //Read exisiting students in from Database
        importStudents();
        
        //Pre-populate ComboBox for Student, Course, Instructor
        boxStuYear.getItems().addAll(
            "Freshman",
            "Sophomore",
            "Junior",
            "Senior",
            "Undefined (Year 5+)"
        );
        boxBuilding.getItems().addAll(
            "Showker",
            "Chandler",
            "Burris Hall"
        );
        boxPrefix.getItems().addAll(
            "Dr.",
            "Ms.",
            "Mrs.",
            "Mr."
        );
        
        // Add panes for Student, Course, Instructor, Build Course, Output
        GridPane addStuPane = new GridPane();
        GridPane addCoursePane = new GridPane();
        GridPane addInstrucPane = new GridPane();
        GridPane buildCoursePane = new GridPane();
        GridPane tablePane = new GridPane();
        GridPane overallPane = new GridPane();
        
        //-----Add Student Pane Creation and Formatting-----
        addStuPane.setAlignment(Pos.TOP_CENTER);
        addStuPane.getColumnConstraints().add(new ColumnConstraints(50));
        addStuPane.add(lblAddStu, 0,0,2,1);
        addStuPane.add(lblStuName, 0, 1);
        addStuPane.add(lblStuYear, 0, 2);
        addStuPane.add(lblStuMajor, 0, 3);
        addStuPane.add(lblStuGPA, 0, 4);
        addStuPane.add(lblStuMail, 0, 5);
        addStuPane.add(txtStuName, 1, 1);
        addStuPane.add(boxStuYear, 1, 2);
        addStuPane.add(txtStuMajor, 1, 3);
        addStuPane.add(txtStuGPA, 1, 4);
        addStuPane.add(txtStuMail, 1, 5);
        addStuPane.add(btnAddStu, 1, 6);
        addStuPane.setPadding(new Insets(10));
        
        //-----Add Course Pane Creation and Formatting-----
        addCoursePane.setAlignment(Pos.TOP_CENTER);
        addCoursePane.getColumnConstraints().add(new ColumnConstraints(85));
        addCoursePane.add(lblAddCourse, 0, 0, 2, 1);
        addCoursePane.add(lblCourseName, 0, 1);
        addCoursePane.add(lblBuilding, 0, 2);
        addCoursePane.add(lblRoom, 0, 3);
        addCoursePane.add(lblMaxCap, 0, 4);
        addCoursePane.add(txtCourseName, 1, 1);
        addCoursePane.add(boxBuilding, 1, 2);
        addCoursePane.add(txtRoom, 1, 3);
        addCoursePane.add(txtMaxCap, 1, 4);
        addCoursePane.add(btnAddCourse, 1, 5);
        addCoursePane.setPadding(new Insets(10));
        
        //-----Add Instructor Pane Creation and Formatting-----
        addInstrucPane.setAlignment(Pos.TOP_CENTER);
        addCoursePane.getColumnConstraints().add(new ColumnConstraints(100));
        addInstrucPane.add(lblAddInstruc, 0, 0, 2, 1);
        addInstrucPane.add(lblInstrucName, 0, 1);
        addInstrucPane.add(lblPrefix, 0, 2);
        addInstrucPane.add(lblOffice, 0, 3);
        addInstrucPane.add(lblDepartment, 0, 4);
        addInstrucPane.add(lblInstrucMail, 0, 5);
        addInstrucPane.add(txtInstrucName, 1, 1);
        addInstrucPane.add(boxPrefix, 1, 2);
        addInstrucPane.add(txtOffice, 1, 3);
        addInstrucPane.add(txtDepartment, 1, 4);
        addInstrucPane.add(txtInstrucMail, 1, 5);
        addInstrucPane.add(btnAddInstruc, 1, 6);
        addInstrucPane.setPadding(new Insets(10));
        
        //-----Edit Course Pane Creation-----
        buildCoursePane.setAlignment(Pos.CENTER_LEFT);
        buildCoursePane.getColumnConstraints().add(new ColumnConstraints(100));
        buildCoursePane.add(lblBuildCourse, 0, 0, 2, 1);
        buildCoursePane.add(rdoAddStu, 0, 1);
        buildCoursePane.add(rdoRemoveStu, 1, 1);
        buildCoursePane.add(lblAddStu2, 0, 2);
        buildCoursePane.add(lblToCourse, 0, 3);
        buildCoursePane.add(cbNewInstruc, 0, 4, 2, 1);
        buildCoursePane.add(lblAddInstruc2, 0, 5);
        buildCoursePane.add(boxStu, 1, 2);
        buildCoursePane.add(boxCourse, 1, 3);
        buildCoursePane.add(boxInstruc, 1, 5);
        buildCoursePane.add(btnUpdtCourse, 0, 6, 2, 1);
        
        //-----Edit Course Pane Formatting-----
        buildCoursePane.setVgap(5);
        buildCoursePane.setPadding(new Insets(10));
        rdoAddStu.setToggleGroup(rdoGroupEditCourse);
        rdoRemoveStu.setToggleGroup(rdoGroupEditCourse);
        boxStu.setPrefWidth(150);
        boxCourse.setPrefWidth(150);
        boxInstruc.setPrefWidth(150);       
        
        //-----Table Pane Creation and Formatting-----
        tablePane.setAlignment(Pos.CENTER);
        tablePane.add(txtOut, 0, 0);
        ColumnConstraints tbcol1 = new ColumnConstraints();
        RowConstraints tbrow1 = new RowConstraints();
        tbcol1.setHgrow(Priority.ALWAYS); //Forces textArea pane to fill all available horizontal space
        tbrow1.setVgrow(Priority.ALWAYS); //Forces textArea pane to fill all available vertical space
        tablePane.getColumnConstraints().add(tbcol1);
        tablePane.getRowConstraints().add(tbrow1);
        tablePane.setPadding(new Insets(10, 10, 10, 10));
        
        //-----Overall Pane Creation-----
        overallPane.setAlignment(Pos.TOP_LEFT);
        overallPane.add(addStuPane, 0, 0, 2, 1);
        overallPane.add(addCoursePane, 2, 0);
        overallPane.add(addInstrucPane, 3, 0);
        overallPane.add(buildCoursePane, 4, 0);
        overallPane.add(tablePane, 0, 1, 5, 1);
        
        //-----Overall Pane Formatting-----
        overallPane.setPadding(new Insets(10, 0, 0, 0));
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        col5.setHgrow(Priority.ALWAYS); //Forces textArea pane to fill all available horizontal space
        row2.setVgrow(Priority.ALWAYS); //Forces textArea pane to fill all available vertical space
        overallPane.getColumnConstraints().addAll(col1,col2,col3,col4,col5);
        overallPane.getRowConstraints().addAll(row1,row2);
        
                
        // Set up primary window
        Scene primaryScene = new Scene(overallPane, 1050, 500);
        overallPane.prefWidthProperty().bind(primaryScene.widthProperty());
        primaryStage.setTitle("ShenandoahU Student Management System");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        
        // Disable ComboBox 
        boxInstruc.setDisable(true);
        
        // If checkbox is pressed, enable Instructor ComboBox
        // If checkbox is unpressed, disable Instructor ComboBox
        cbNewInstruc.setOnAction(e -> 
        {
            if(cbNewInstruc.isSelected())
            {
                boxInstruc.setDisable(false);  
            }
            else 
            {
                boxInstruc.setDisable(true);
            }             
        });
        
         // Lambda Event for adding new Student
        btnAddStu.setOnAction(e -> 
        {
           addStu();
        });
        
        // Lambda Event for adding new Course
        btnAddCourse.setOnAction(e -> 
        {
            addCourse();
        });
        
        // Lambda Event for adding new Instructor
        btnAddInstruc.setOnAction(e -> 
        {
            addInstructor();
        });
        
        //Lambda Event for "Update Course" Button
        //Events change based on the selections of Add/Remove Student radio buttons and Add Instructor check box
        btnUpdtCourse.setOnAction(e -> 
        {
            //------Adding Instructor Without Changing Course Roster------
            //First checks to see if "Add Instructor" is selected without a student being selected
            //If so, it will attempt to add an instructor to a selected course without modifying a course's roster
            if((cbNewInstruc.isSelected()) && (boxStu.getSelectionModel().isEmpty()))
            {
                if((!boxInstruc.getSelectionModel().isEmpty())&& (!boxCourse.getSelectionModel().isEmpty()))
                {
                    addInstructorToCourse();
                }
                else
                {
                    txtOut.appendText(String.format("%nCannot Add Instructor to Course - "
                            + "Please Select a Course and Instructor to Continue%n"));
                }
            }
            else //------All Other Cases------
            {
                if(cbNewInstruc.isSelected()) //Attempts to add instructor if "Add Instructor" is selected
                {
                    if((!boxInstruc.getSelectionModel().isEmpty())&& (!boxCourse.getSelectionModel().isEmpty()))
                    {
                        addInstructorToCourse();
                    }
                    else
                    {
                        txtOut.appendText(String.format("%nCannot Add Instructor to Course - "
                                + "Please Select a Course and Instructor to Continue%n"));
                    }
                }
                
                //Checks to make sure that a course and student are both selected before attempting to modify roster
                if((!boxStu.getSelectionModel().isEmpty())&&(!boxCourse.getSelectionModel().isEmpty()))
                {
                    if (rdoAddStu.isSelected()) //Attempts to add student if "Add Student" is selected
                    {
                        addStuToCourse();
                    }
                    else if(rdoRemoveStu.isSelected()) //Attempts to remove student if "Remove Student" is selected
                    {
                        removeStuFromCourse();
                    }
                }
                else
                {
                    txtOut.appendText(String.format("%nPlease Select a Course and Instructor to Continue%n"));
                }
            }
        });
        
        //Lambda Expressions for Cell Factory Initialization allowing for comboboxes to 
        //a contain list of objects but only display the object names
        //List Cell classes defined below so that they could be used for both Cell Factory and Button Cells
        //***********Written By Chris Torchia***********
        
        boxStu.setCellFactory(listView -> new StudentListCell());
        
        boxCourse.setCellFactory(listView -> new CourseListCell());
        
        boxInstruc.setCellFactory(listView -> new InstructorListCell());
        
        //Event handlers which ensures value displayed in box upon selection is the name of the object (Student/Course/Instructor)
        //Otherwise the box would attempt to display the address of the instance object
        //List Cell classes defined below so that they could be used for both Cell Factory and Button Cells
        
        boxStu.setOnAction(e ->
        {
            boxStu.setButtonCell(new StudentListCell());
        });
        
        boxCourse.setOnAction(e ->
        {
            boxCourse.setButtonCell(new CourseListCell());
        });
        
        boxInstruc.setOnAction(e ->
        {
            boxInstruc.setButtonCell(new InstructorListCell());
        });
    }
    
    public void addStuToCourse()
    {
        //Gets instance object in observable list at selected index
        Student stu = olStu.get(boxStu.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
        
        //Attempts to add a Student to a course, returns an int based on result
        int result = course.enrollStudentWithNewCheck(stu);
        switch(result)
        {
            case 0: //Student already enrolled in course
                txtOut.clear();
                txtOut.appendText(String.format("%nUnable to add Student: Student Already Enrolled in Course!%n"));
                txtOut.appendText(String.format("%n%s%n",course.toString()));
                txtOut.appendText(String.format("%nCourse Roster:%n%s%n",course.getRoster()));
                break;
            case 1: //Course already at maximum capacity
                txtOut.clear();
                txtOut.appendText(String.format("%nUnable to add Student: course is at maximum capacity!%n"));
                txtOut.appendText(String.format("%n%s%n",course.toString()));
                txtOut.appendText(String.format("%nCourse Roster:%n%s%n",course.getRoster()));
                break;
            case 2: //Student successfully enrolled
                txtOut.clear();
                txtOut.appendText(String.format("%nSuccessfully Added Student to:%n"));
                txtOut.appendText(String.format("%n%s%n",course.toString()));
                txtOut.appendText(String.format("%nUpdated Roster:%n%s%n",course.getRoster()));
                break;
        }
        boxStu.getSelectionModel().clearSelection();
        boxCourse.getSelectionModel().clearSelection();
        boxInstruc.getSelectionModel().clearSelection();
    }
            
    public void removeStuFromCourse()
    {
        //Gets instance object in observable list at selected index
        Student stu = olStu.get(boxStu.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
        
        //Attempts to remove student from course, method returns true if successful
        boolean result = course.removeStudent(stu.getStudentID());
        if(result)
        {
            txtOut.clear();
            txtOut.appendText(String.format("%nSuccessfully Removed Student%n"));
            txtOut.appendText(String.format("%n%s%n",course.toString()));
            txtOut.appendText(String.format("%nUpdated Roster:%n%s%n",course.getRoster()));
        }
        else
        {
            txtOut.clear();
            txtOut.appendText(String.format("%nUnable to remove Student: Student not Enrolled in Course%n"));
            txtOut.appendText(String.format("%n%s%n",course.toString()));
            txtOut.appendText(String.format("%nCourse Roster:%n%s%n",course.getRoster()));
        }
        boxStu.getSelectionModel().clearSelection();
        boxCourse.getSelectionModel().clearSelection();
        boxInstruc.getSelectionModel().clearSelection();
    }
    
    public void addInstructorToCourse()
    {
        //Gets instance object in observable list at selected index
        Instructor ins = olInstruc.get(boxInstruc.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
        
        course.assignInstructor(ins);
        
        txtOut.clear();
        txtOut.appendText(String.format("%nSuccessfully assigned instructor: %s to course: %s%n",
                ins.getTitle(),course.getCourseName()));
        
        boxStu.getSelectionModel().clearSelection();
        boxCourse.getSelectionModel().clearSelection();
        boxInstruc.getSelectionModel().clearSelection();
    }
    
    //---------------Object Creation Methods---------------
    
    public void addStu()
    {
        //Error checking code to make sure that user has input all necessary information
        boolean execute = true;
        
        if (txtStuName.getText().isEmpty())
        {
            txtStuName.clear();
            txtOut.appendText(String.format("%nPlease enter a name.%n"));
            execute = false;
        }
        if (boxStuYear.getSelectionModel().isEmpty())
        {
            boxStuYear.getSelectionModel().clearSelection();
            txtOut.appendText(String.format("%nPlease select a year.%n"));
            execute = false;
        }
        if (txtStuMajor.getText().isEmpty())
        {
            txtStuMajor.clear();
            txtOut.appendText(String.format("%nPlease enter a major.%n"));
            execute = false;
        }
        if (txtStuGPA.getText().isEmpty())
        {
            txtStuGPA.clear();
            txtOut.appendText(String.format("%nPlease enter a GPA.%n"));
            execute = false;
        }
        if (txtStuMail.getText().isEmpty())
        {
            txtStuMail.clear();
            txtOut.appendText(String.format("%nPlease enter an email.%n"));
            execute = false;
        }
        
        //Error checking code to make sure that what was entered in GPA field is a number between 0 and 5
        double convertGPA = 0.0;
        try 
        {
            convertGPA = Double.valueOf(txtStuGPA.getText());            
            if (convertGPA < 0 || convertGPA > 5)
            {
                txtStuGPA.clear();
                txtOut.appendText(String.format("%nPlease enter a valid GPa between 0 and 5%n"));
                execute = false;
            }
        } 
        catch(InputMismatchException e) 
        {
            txtStuGPA.clear();
            txtOut.appendText(String.format("%nPlease enter a valid GPa between 0 and 5%n"));
            execute = false;
        }
        
        //Error checking code to make sure that email is valid (contains an "@" character and valid domain name)
        if ((!txtStuMail.getText().contains("@")) || (!txtStuMail.getText().contains(".edu") &&
                !txtStuMail.getText().contains(".com") && !txtStuMail.getText().contains(".net")
                && !txtStuMail.getText().contains(".org")))
        {
            txtStuMail.clear();
            txtOut.appendText(String.format("%nPlease enter a valid email.%n"));
            execute = false;
        }
        
        if (execute)
        {
            txtOut.clear();
            
            //Get Student year from year combobox
            int selectedIndex = boxStuYear.getSelectionModel().getSelectedIndex();
            
            //Create temporary student object
            Student stu = new Student(txtStuName.getText(), selectedIndex, 
                    txtStuMajor.getText(), convertGPA, txtStuMail.getText());
            
            studentArray.add(stu);
            olStu.add(stu);
            txtStuName.clear();
            boxStuYear.getSelectionModel().clearSelection();
            txtStuMajor.clear();
            txtStuGPA.clear();
            txtStuMail.clear();
        }
    }
    
    public void addCourse()
    {
        //Error checking code to make sure that user has input all necessary information
        boolean execute = true;
        if (txtCourseName.getText().isEmpty())
        {
            txtCourseName.clear();
            txtOut.appendText(String.format("%nPlease enter a course name.%n"));
            execute = false;
        }
        if (boxBuilding.getSelectionModel().isEmpty())
        {
            boxBuilding.getSelectionModel().clearSelection();
            txtOut.appendText(String.format("%nPlease select a building.%n"));
            execute = false;
        }
        if (txtRoom.getText().isEmpty())
        {
            txtRoom.clear();
            txtOut.appendText(String.format("%nPlease select a room number.%n"));
            execute = false;
        }
        if (txtMaxCap.getText().isEmpty())
        {
            txtMaxCap.clear();
            txtOut.appendText(String.format("%nPlease select a maximum capacity.%n"));
            execute = false;
        }
         //Error checking code to make sure that what was entered in capacity field is an integer
        int capacity  = 0;
        try 
        {
            capacity = Integer.valueOf(txtMaxCap.getText());
        } 
        catch(InputMismatchException e) 
        {
            txtMaxCap.clear();
            txtOut.appendText(String.format("%nPlease enter an integer for Course Capacity!%n"));
            execute = false;
        }

        //update course combobox
        if (execute)
        {
            txtOut.clear();
            String stringBuilding = boxBuilding.getSelectionModel().getSelectedItem().toString();
            Course course = new Course(txtCourseName.getText(), stringBuilding,
                    txtRoom.getText(), capacity);
            courseArray.add(course);
            olCourse.add(course);
            txtCourseName.clear();
            boxBuilding.getSelectionModel().clearSelection();
            txtRoom.clear();
            txtMaxCap.clear();
        }
    }
    
    public void addInstructor()
    {
        //Error checking code to make sure that user has input all necessary information
        boolean execute = true;
        if (txtInstrucName.getText().isEmpty())
        {    
            txtInstrucName.clear();
            txtOut.appendText(String.format("%nPlease enter a name.%n"));
            execute = false;
        }
        if (boxPrefix.getSelectionModel().isEmpty())
        {    
            boxPrefix.getSelectionModel().clearSelection();
            txtOut.appendText(String.format("%nPlease select a prefix.%n"));
            execute = false;
        }
        if (txtOffice.getText().isEmpty())
        {    
            txtOffice.clear();
            txtOut.appendText(String.format("%nPlease enter an office location.%n"));
            execute = false;
        }
        if (txtDepartment.getText().isEmpty())
        {    
            txtDepartment.clear();
            txtOut.appendText(String.format("%nPlease enter a department.%n"));
            execute = false;
        }
        if(txtInstrucMail.getText().isEmpty())
        {
            txtInstrucMail.clear();
            txtOut.appendText(String.format("%nPlease enter a valid email.%n"));
            execute = false;
        }
        
        //Error checking to make sure that a valid email has been entered
        if ((!txtInstrucMail.getText().contains("@")) || (!txtInstrucMail.getText().contains(".edu") &&
                !txtInstrucMail.getText().contains(".com") && !txtInstrucMail.getText().contains(".net")
                && !txtInstrucMail.getText().contains(".org")))
        {
            txtInstrucMail.clear();
            txtOut.appendText(String.format("%nPlease enter a valid email.%n"));
            execute = false;
        }
        
        if (execute)
        {
            txtOut.clear();
            String stringPre = boxPrefix.getSelectionModel().getSelectedItem().toString(); //needs some reworking
            Instructor ins = new Instructor(txtInstrucName.getText(), stringPre,
                    txtOffice.getText(), txtDepartment.getText(), txtInstrucMail.getText());
            instructorArray.add(ins);
            olInstruc.add(ins);
            txtInstrucName.clear();
            boxPrefix.getSelectionModel().clearSelection();
            txtOffice.clear();
            txtDepartment.clear();
            txtInstrucMail.clear();
        }
    }
    
    //----------ListCell Class Definitions----------
    //-----------Written by Chris Torchia-----------
    private static class InstructorListCell extends ListCell<Instructor>
    {
        @Override 
        protected void updateItem(Instructor ins, boolean empty) 
        {
            super.updateItem(ins, empty);

            if (ins != null) 
            {
                setText(ins.getTitle().trim());
            } 
            else 
            {
                setText(null);
            }
        }
    }
    
    private static class StudentListCell extends ListCell<Student>
    {
        @Override 
        protected void updateItem(Student stu, boolean empty) 
        {
            super.updateItem(stu, empty);

            if (stu != null) 
            {
                setText(stu.getName().trim());
            } 
            else 
            {
                setText(null);
            }
        }
    }
    
    private static class CourseListCell extends ListCell<Course>
    {
        @Override 
        protected void updateItem(Course course, boolean empty) 
        {
            super.updateItem(course, empty);

            if (course != null) 
            {
                setText(course.getCourseName().trim());
            } 
            else 
            {
                setText(null);
            }
        }
    }
    
    //----------DBMS Processing Methods----------
    //DBMS processing code written by Tran Le and Chris Torchia
    public void importStudents()
    {
        String sqlQuery = "SELECT * FROM System.STUDENT";
        sendDBCommand(sqlQuery);
        
        String outputString = "";
        try
        {            
            while (dbResults.next())
            {
                Student stu = new Student(dbResults.getInt(1),dbResults.getString(2).trim(),
                    dbResults.getInt(3),dbResults.getString(4).trim(),dbResults.getDouble(5),
                        dbResults.getString(6).trim());
                studentArray.add(stu);
                olStu.add(stu);                
            }
        }
        catch (SQLException sqle)
        {
            //txtOut.setText(sqle.toString());
        }    
    }
    
    public void saveToDB()
    {
        String query = "";
        for (Course course : courseArray)
        {
            query = "INSERT INTO System.COURSE "
                    + "(COURSEID,COURSENAME,COURSEBLDG,COURSEROOM,COURSECAPACITY) VALUES (";
            query += course.getCourseID() + "," + "'" + course.getCourseName() + "'" + ",";
            query += "'" + course.getCourseBldg() + "'" + "," + "'" + course.getCourseRoom() + "'" + ",";
            query += "'" + course.getCapacity() + "')";
            sendDBCommand(query);
        }
        for (Student stu : studentArray)
        {
            query = "INSERT INTO System.STUDENT "
                    + "(STUDENTID,STUDENTNAME,STUDENTYEAR,STUDENTMAJOR,STUDENTGPA,STUDENTEMAIL) VALUES (";
            query += stu.getStudentID() + "," + "'" + stu.getName() + "'" + ",";
            query += stu.getStudentYearNumber()+ "," + "'" + stu.getStudentMajor() + "'" + ",";
            query += stu.getGPA() + "," + "'" + stu.getStudentEmail() + "')";
            sendDBCommand(query);
        }
        for (Instructor ins : instructorArray)
        {
            query = "INSERT INTO System.INSTRUCTOR "
                    + "(INSTRID,INSTRNAME,INSTRPREFIX,INSTROFFICE,INSTRDEPT,INSTREMAIL) VALUES (";
            query += ins.getInsID() + "," + "'" + ins.getName() + "'" + ",";
            query += "'" + ins.getPrefix() + "'" + "," + "'" + ins.getOffice() + "'" + ",";
            query += "'" + ins.getDepartment() + "'" + "," + "'" + ins.getEmail() + "')";
            sendDBCommand(query);
        }
        for(Course course : courseArray)
        {
            for(Student stu : course.getEnrollment())
            {
                query = "INSERT INTO System.STUDENTENROLLMENT "
                    + "(COURSEID,STUDENTID) VALUES (";
                query += course.getCourseID() + "," + stu.getStudentID() + ")";
                sendDBCommand(query);
            }
        }
    }
    
    public void sendDBCommand(String sqlQuery)
    {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "System"; 
        String userPASS = "ToM89451";
        OracleDataSource ds;
        
        //System.out.println(sqlQuery);
        
        try
        {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID,userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbResults = commStmt.executeQuery(sqlQuery);
        }
        catch (SQLException e)
        {
            //System.out.println(e.toString());
        }
    }
    
    public void stop()
    {
        saveToDB();                
    }  
    
    public static void main(String[] args) {
        launch(args);
    }
    
}