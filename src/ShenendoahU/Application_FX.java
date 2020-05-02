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
    
    //ArrayList initialization
    //ObservableList used for Building Course ComboBox controls
    public static ArrayList<Student> studentArray  = new ArrayList<>();
    public static ArrayList<Course> courseArray  = new ArrayList<>();
    public static ArrayList<Instructor> instructorArray  = new ArrayList<>();
    public static ObservableList olStu = FXCollections.observableArrayList();
    public static ObservableList olCourse = FXCollections.observableArrayList();
    public static ObservableList olInstruc = FXCollections.observableArrayList();

    //Controls for Student Pane
    public Label lblAddStu = new Label("Add Student:");
    public Label lblStuName = new Label("Name: ");
    public Label lblStuYear = new Label("Year: ");
    public Label lblStuMajor = new Label("Major: ");
    public Label lblStuGPA = new Label("GPA: ");
    public Label lblStuMail = new Label("Email: ");
    public TextField txtStuName = new TextField();
    public ComboBox boxStuYear = new ComboBox();
    public TextField txtStuMajor = new TextField();
    public TextField txtStuGPA = new TextField();
    public TextField txtStuMail = new TextField();
    public Button btnAddStu = new Button("Add Student ->");
    
    //Controls for Course Pane
    public Label lblAddCourse = new Label("Add Course: ");
    public Label lblCourseName = new Label("Name: ");
    public Label lblBuilding = new Label("Building: ");
    public Label lblRoom = new Label ("Room: ");
    public Label lblMaxCap = new Label("Max Capacity: ");
    public TextField txtCourseName = new TextField();
    public ComboBox boxBuilding = new ComboBox();
    public TextField txtRoom = new TextField();
    public TextField txtMaxCap = new TextField();
    public Button btnAddCourse = new Button("Add Course ->");
    
    //Controls for Instructor Pane
    public Label lblAddInstruc = new Label("Add Instructor: ");
    public Label lblInstrucName = new Label("Name: ");
    public Label lblPrefix = new Label("Prefix: ");
    public Label lblOffice = new Label("Office: ");
    public Label lblDepartment = new Label("Department: ");
    public Label lblInstrucMail = new Label("Email: ");
    public TextField txtInstrucName = new TextField();
    public ComboBox boxPrefix = new ComboBox();
    public TextField txtOffice = new TextField();
    public TextField txtDepartment = new TextField();
    public TextField txtInstrucMail = new TextField();
    public Button btnAddInstruc = new Button("Add Instructor ->");
    
    //Controls for Building Course Pane
    public Label lblBuildCourse = new Label("Build a Course: ");
    public Label lblAddStu2 = new Label("Add Student: ");
    public Label lblToCourse = new Label("To Course: ");
    public CheckBox cbNewInstruc = new CheckBox("New Instructor?");
    public Label lblAddInstruc2 = new Label("Instructor is: ");
    public Button btnUpdtCourse = new Button("Update Course ->");
    public ComboBox boxStu = new ComboBox(olStu);
    public ComboBox boxCourse = new ComboBox(olCourse);
    public ComboBox boxInstruc = new ComboBox(olInstruc);
    
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
        addStuPane.setAlignment(Pos.CENTER);
        addStuPane.add(lblAddStu, 0, 0);
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
        
        // Add controls to Course Pane
        addCoursePane.setAlignment(Pos.CENTER);
        addCoursePane.add(lblAddCourse, 0, 0);
        addCoursePane.add(lblCourseName, 0, 1);
        addCoursePane.add(lblBuilding, 0, 2);
        addCoursePane.add(lblRoom, 0, 3);
        addCoursePane.add(lblMaxCap, 0, 4);
        addCoursePane.add(txtCourseName, 1, 1);
        addCoursePane.add(boxBuilding, 1, 2);
        addCoursePane.add(txtRoom, 1, 3);
        addCoursePane.add(txtMaxCap, 1, 4);
        addCoursePane.add(btnAddCourse, 1, 5);
        
        // Add controls to Instructor Pane
        addInstrucPane.setAlignment(Pos.CENTER);
        addInstrucPane.add(lblAddInstruc, 0, 0);
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
        
        // Add controls to Build Course Pane
        buildCoursePane.setAlignment(Pos.CENTER);
        buildCoursePane.add(lblBuildCourse, 0, 0);
        buildCoursePane.add(lblAddStu2, 0, 1);
        buildCoursePane.add(lblToCourse, 0, 2);
        buildCoursePane.add(cbNewInstruc, 0, 3);
        buildCoursePane.add(lblAddInstruc2, 0, 4);
        buildCoursePane.add(boxStu, 1, 1);
        buildCoursePane.add(boxCourse, 1, 2);
        buildCoursePane.add(boxInstruc, 1, 4);
        buildCoursePane.add(btnUpdtCourse, 1, 5);
        
        // Add output textbox to Table Pane
        tablePane.setAlignment(Pos.CENTER);
        tablePane.add(txtOut, 0, 0);
        
        // Format panes into Overall Pane
        overallPane.setAlignment(Pos.CENTER);
        overallPane.add(addStuPane, 0, 0);
        overallPane.add(addCoursePane, 1, 0);
        overallPane.add(addInstrucPane, 2, 0);
        overallPane.add(buildCoursePane, 0, 1);
        overallPane.add(tablePane, 1, 1);
        overallPane.setVgap(25);
        overallPane.setHgap(25);
        
        // Set up primary window
        Scene primaryScene = new Scene(overallPane, 800, 500);
        primaryStage.setTitle("ShenandoahU Student Management System");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        // Disable ComboBox 
        lblAddInstruc2.setDisable(true);
        boxInstruc.setDisable(true);
        
        // If checkbox is pressed, enable ComboBox
        // If checkbox is unpressed, disable ComboBox
        cbNewInstruc.setOnAction(e -> 
        {
            if(!cbNewInstruc.isSelected())
            {
                lblAddInstruc2.setDisable(true);
                boxInstruc.setDisable(true);               
            }
            else
            {
                lblAddInstruc2.setDisable(false);
                boxInstruc.setDisable(false);                  
            }            
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
