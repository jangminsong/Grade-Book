package org.suffieldacademy.GradebookLab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



/**
 * JavaFX App
 * Gradebook Lab 
 * Jangmin Song 
 */
public class App extends Application {
    

    private void parseGradebook(boolean drop, Label error) { //method
        
        try {
            PrintWriter sourcePrintWriter = new PrintWriter(destinationFile); //the print writer that writes grades and names to the destination file
            Scanner sourceScanner = new Scanner(sourceFile); //the scanner that reads the names and the grades from the source file 
            while(sourceScanner.hasNextLine()){ //if there is more ones to read
                Scanner scanner = new Scanner(sourceScanner.nextLine()); //read the line
                double count = 0; //counting how many grades there are
                double grade = 0; //each grade 
                double total = 0; //the total which is the addition of all the grades for one person
                double avg = 0; //the average of that person
                double lowestGrade = Integer.MAX_VALUE; //set it as max value to make the first number the lowest grade
                String name = scanner.next(); //read the first name 
                String name2 = scanner.next(); //read the second name 

                if (scanner.hasNext()){ //if there is more to read (grades)
                    while(scanner.hasNextDouble()){ //doubles until scanner reads all the number 
                        grade = scanner.nextDouble(); //goes to the next one until there is none
                            if (grade < lowestGrade){   //If the grade is lower than the lowest grade so far,
                                lowestGrade = grade;    //that grade becomes the lowest grade
                            }  
                            count += 1; //adding as the grades are being loaded one by one 
                            total += grade; //adding each grade to the total 
                    }
                    if (drop){  //if dropping the lowest score
                        count -= 1; //substract 1 from count 
                        total -= lowestGrade; //substract the lowest grade from the total
                    }
                    avg = total/count; //calculating the average 
                }
                sourcePrintWriter.println(name + "\t" + name2 + "\t" + avg); //writing names and the average into the file 
            }
            sourcePrintWriter.close(); //closing the print writer
            sourceScanner.close(); //closing the scanner
        }
        catch (FileNotFoundException exception) { //exception
            System.out.println("exception");
        }

        error.setText("Completed!"); //printing completed! when it is done writing 
    }

    private File sourceFile = null; //the file for the source

    private File destinationFile = null; //the file for the destination

    @Override
    public void start(Stage stage) {

        //source section
        var sourceB = new Button("Source");
        var sourceL = new Label("");

        //destination section
        var destinationB = new Button("Destination");
        var destinationL = new Label("");
        
        //go button
        var goB = new Button("Go!");

        //exception
        var checkB = new CheckBox("Drop lowest score");

        //label
        var errorLabel = new Label("");

        //source section
        var sourceHBox = new HBox(20, sourceB, sourceL);
        sourceHBox.setAlignment(Pos.CENTER); //makes the potion center 

        //destinaiton section
        var destinationHBox = new HBox(20, destinationB, destinationL);
        destinationHBox.setAlignment(Pos.CENTER); //makes the potion center 

        //vbox that makes all the sections combined together. 
        var vBox = new VBox(20, sourceHBox, destinationHBox, checkB, goB, errorLabel);
        vBox.setAlignment(Pos.CENTER);

        //makes the background
        var scene = new Scene(new BorderPane(vBox, null, null, null, null),1200,800);

        //Source FileChooser
        var sourceFileChooser = new FileChooser(); //makes source file chooser to be a new file chooser
        sourceB.setOnAction(e -> {
            sourceFile = sourceFileChooser.showOpenDialog(stage); 
            sourceL.setText(sourceFile.getName()); //prints out the name of whatever the file is choosen 
        });

        //Destination FileChooser
        var destinationFileChooser = new FileChooser(); //makes destination file chooser to be a new file chooser
        destinationB.setOnAction(e -> {
            destinationFile = destinationFileChooser.showSaveDialog(stage); 
            destinationL.setText(destinationFile.getName()); //prints out the name of whatever the file is choosen
        });

        //when the go button is clicked 
        goB.setOnAction(e -> {
            parseGradebook(checkB.isSelected(), errorLabel); 
        });


        //show 
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}