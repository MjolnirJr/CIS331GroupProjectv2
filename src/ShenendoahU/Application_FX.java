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
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    
    //ArrayList initialization
    //ObservableList used for Building Course ComboBox controls
    public static ArrayList<Student> studentArray  = new ArrayList<>();
    public static ArrayList<Course> courseArray  = new ArrayList<>();
    public static ArrayList<Instructor> instructorArray  = new ArrayList<>();
    public static ObservableList<Student> olStu = FXCollections.observableArrayList();
    public static ObservableList<Course> olCourse = FXCollections.observableArrayList();
    public static ObservableList<Instructor> olInstruc = FXCollections.observableArrayList();

    //Controls for Student Pane
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
    
    
    //Controls for Course Pane
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
    
    //Controls for Instructor Pane
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
    
    //Controls for Edit Course Pane
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
        
        // Add controls to Student Pane
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
        
        // Add controls to Course Pane
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
        
        // Add controls to Instructor Pane
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
        
        // Add controls to Build Course Pane
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
        buildCoursePane.setVgap(5);
        buildCoursePane.setPadding(new Insets(10));
        rdoAddStu.setToggleGroup(rdoGroupEditCourse);
        rdoRemoveStu.setToggleGroup(rdoGroupEditCourse);
        boxStu.setPrefWidth(150);
        boxCourse.setPrefWidth(150);
        boxInstruc.setPrefWidth(150);       
        
        // Add output textbox to Table Pane
        tablePane.setAlignment(Pos.CENTER);
        tablePane.add(txtOut, 0, 0);
        ColumnConstraints tbcol1 = new ColumnConstraints();
        RowConstraints tbrow1 = new RowConstraints();
        tbcol1.setHgrow(Priority.ALWAYS);
        tbrow1.setVgrow(Priority.ALWAYS);
        tablePane.getColumnConstraints().add(tbcol1);
        tablePane.getRowConstraints().add(tbrow1);
        tablePane.setPadding(new Insets(10, 10, 10, 10));
        
        // Format panes into Overall Pane
        overallPane.setAlignment(Pos.TOP_LEFT);
        overallPane.add(addStuPane, 0, 0, 2, 1);
        overallPane.add(addCoursePane, 2, 0);
        overallPane.add(addInstrucPane, 3, 0);
        overallPane.add(buildCoursePane, 4, 0);
        overallPane.add(tablePane, 0, 1, 5, 1);
        overallPane.setPadding(new Insets(10, 0, 0, 0));
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        ColumnConstraints col5 = new ColumnConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        col5.setHgrow(Priority.ALWAYS);
        row2.setVgrow(Priority.ALWAYS);
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
        
        // If checkbox is pressed, enable ComboBox
        // If checkbox is unpressed, disable ComboBox
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
            //First attempts to add instructor to a course, if instructor checkbox is selected
            //Requires both a course and 
            if(cbNewInstruc.isSelected())
            {
                if((!boxInstruc.getSelectionModel().isEmpty())&&(!boxCourse.getSelectionModel().isEmpty()))
                {
                    addInstructorToCourse();
                }
                else
                {
                    txtOut.appendText(String.format("%nCannot Add Instructor to Course - "
                            + "Please Select a Course and Instructor to Continue%n"));
                }
            }
            if(rdoAddStu.isSelected())
            {
                if((!boxStu.getSelectionModel().isEmpty())&&(!boxCourse.getSelectionModel().isEmpty()))
                {
                    addStuToCourse();
                }
                else
                {
                    txtOut.appendText(String.format("%nCannot Add Student to Course - "
                            + "Please Select a Course and Instructor to Continue %n"));
                }
                
            }
            else if(rdoRemoveStu.isSelected())
            {
                if((!boxStu.getSelectionModel().isEmpty())&&(!boxCourse.getSelectionModel().isEmpty()))
                {
                    if(cbNewInstruc.isSelected())
                    {
                        addInstructorToCourse();
                    }
                    removeStuFromCourse();
                }
                else
                {
                    txtOut.appendText(String.format("%nCannot Remove Student from Course - "
                            + "Please Select a Course and Instructor to Continue%n"));
                }
            }
        });
        
        //Cell factory code allowing for comboboxes to contain list of objects but only display the object names
        //***********Written By Chris Torchia***********
        boxStu.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() 
        {
            @Override 
            public ListCell<Student> call(ListView<Student> p) 
            {
                return new ListCell<Student>() 
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
                };
            }
        });
        
        boxCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() 
        {
            @Override 
            public ListCell<Course> call(ListView<Course> p) 
            {
                return new ListCell<Course>() 
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
                };
            }
        });
        
        boxInstruc.setCellFactory(new Callback<ListView<Instructor>, ListCell<Instructor>>() 
        {
            @Override 
            public ListCell<Instructor> call(ListView<Instructor> p) 
            {
                return new ListCell<Instructor>() 
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
                };
            }
        });
        
        //Event handlers which ensures value displayed in box upon selection 
        //is the name of the object (Student/Course/Instructor)
        //Otherwise the box would attempt to display the address of the instance object
        //***********Written By Chris Torchia***********
        boxStu.setOnAction(e ->
        {
            boxStu.setButtonCell(new ListCell<Student>()
            {
                @Override
                protected void updateItem(Student stu, boolean empty)
                {
                    super.updateItem(stu, empty);
                    if(stu != null) 
                    {                
                        setText(stu.getName());
                    }
                }
            });
        });
        boxCourse.setOnAction(e ->
        {
            boxCourse.setButtonCell(new ListCell<Course>()
            {
                @Override
                protected void updateItem(Course course, boolean empty)
                {
                    super.updateItem(course, empty);
                    if(course != null) 
                    {                
                        setText(course.getCourseName());
                    }
                }
            });
        });
        boxInstruc.setOnAction(e ->
        {
            boxInstruc.setButtonCell(new ListCell<Instructor>()
            {
                @Override
                protected void updateItem(Instructor ins, boolean empty)
                {
                    super.updateItem(ins, empty);
                    if(ins != null) 
                    {                
                        setText(ins.getTitle());
                    }
                }
            });
        });
    }
    
    public void addStuToCourse()
    {
        
        Student stu = olStu.get(boxStu.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
        
        //Attempts to add a Student to a course
        //If there is sufficent capacity, returns true
        boolean result = course.enrollStudentWithCheck(stu);
        if(result)
        {
            txtOut.clear();
            txtOut.appendText(String.format("%nSuccessfully Added Student to:%n"));
            txtOut.appendText(String.format("%n%s%n",course.toString()));
            txtOut.appendText(String.format("%nUpdated Roster:%n%s%n",course.getRoster()));
        }
        else
        {
            txtOut.clear();
            txtOut.appendText(String.format("%nUnable to add Student: course is at maximum capacity!%n"));
            txtOut.appendText(String.format("%n%s%n",course.toString()));
            txtOut.appendText(String.format("%nCourse Roster:%n%s%n",course.getRoster()));
        }
    }
            
    public void removeStuFromCourse()
    {
        Student stu = olStu.get(boxStu.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
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
    }
    
    public void addInstructorToCourse()
    {
        Instructor ins = olInstruc.get(boxInstruc.getSelectionModel().getSelectedIndex());
        Course course = olCourse.get(boxCourse.getSelectionModel().getSelectedIndex());
        course.assignInstructor(ins);
        txtOut.clear();
        txtOut.appendText(String.format("%nSuccessfully assigned instructor: %s to course: %s%n",
                ins.getTitle(),course.getCourseName()));
    }
    
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
            
    public void sendDBCommand(String sqlQuery)
    {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; 
        String userPASS = "javapass";
        OracleDataSource ds;
        
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
            System.out.println(e.toString());
        }
    }
    
    public void readDB()
    {
        
    }
    
    public void saveToDB()
    {
        
    }

    public void stop()
    {
                
    }  
    
    public static void main(String[] args) {
        launch(args);
    }
    
}