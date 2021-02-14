package application;
	
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



public class Main extends Application {
	
	int index = 0; // to keep record number
	Person[] arr_AB;
	
	final static int ID_SIZE = 4;
	final static int NAME_SIZE = 32;
	final static int STREET_SIZE = 32;
	final static int CITY_SIZE = 20;
	final static int GENDER_SIZE = 1;
	final static int ZIP_SIZE = 5;
	final static int RECORD_SIZE = (ID_SIZE + NAME_SIZE + STREET_SIZE + CITY_SIZE + GENDER_SIZE + ZIP_SIZE);
	public RandomAccessFile raf;
	
	// constructor (file and array)
	public Main() {
		try {
			raf = new RandomAccessFile("addressbook.dat", "rw");
			arr_AB = new Person[150];
			
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	// TEXT FIELDS
	TextField tfID = new TextField();
	TextField tfSearchUpdateID = new TextField();
	TextField tfName = new TextField();
	TextField tfStreet = new TextField();
	TextField tfCity = new TextField();
	TextField tfGender = new TextField();
	TextField tfZip = new TextField();
	
	// BUTTONS
	Button btAdd = new Button("Add");
	Button btFirst = new Button("First");
	Button btNext = new Button("Next");
	Button btPrevious = new Button("Previous");
	Button btLast = new Button("Last");
	Button btUpdateByID = new Button("UpdateByID");
	Button btSearchByID = new Button("SearchByID");
	Button btCleantextFields = new Button("Clean textFields");
	
	// LABELS
	Label lbID = new Label("ID");
	Label lbSearchUpdateID = new Label("Search/Update ID");
	Label lbName = new Label("Name");
	Label lbStreet = new Label("Street");
	Label lbCity = new Label("City");
	Label lbGender = new Label("Gender");
	Label lbZip = new Label("Zip");

	
	// Clean Text Fields
	public void cleanTextFields() {
		tfID.clear();
		tfName.clear();
		tfStreet.clear();
		tfCity.clear();
		tfGender.clear();
		tfZip.clear();
	}
	
   // STAGE PART
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// ALERTS
			Alert alert1 = new Alert(AlertType.INFORMATION);

			// LENGTHS OF TEXT FIELDS
			tfID.setPrefColumnCount(5);
			tfSearchUpdateID.setPrefColumnCount(5);
			tfGender.setPrefColumnCount(1);
			tfZip.setPrefColumnCount(5);
			tfCity.setPrefColumnCount(12);
			
			
			// GRID PANE
			GridPane p1 = new GridPane();
			p1.setAlignment(Pos.CENTER);
			p1.setHgap(5);
			p1.setVgap(5);
			p1.add(lbID, 0, 0);
			
			// Horizontal Box 1
			HBox p2 = new HBox(5);
			tfID.setDisable(true);// text field ID is disable
			p2.getChildren().addAll(tfID,lbSearchUpdateID,tfSearchUpdateID);
			p1.add(p2, 1, 0); // add p2 to p1
			
			
			p1.add(lbName, 0, 1);
			p1.add(tfName, 1, 1);
			
			p1.add(lbStreet, 0, 2);
			p1.add(tfStreet, 1, 2);
			
			p1.add(lbCity, 0, 3);
			
			// Horizontal Box 2
			HBox p3 = new HBox(5);
			p3.getChildren().addAll(tfCity,lbGender,tfGender,lbZip,tfZip);
			p1.add(p3, 1, 3);
			
			// Horizontal Box 3
			HBox p4 = new HBox(5);
			p4.getChildren().addAll(btAdd,btFirst,btNext,btPrevious,btLast,btUpdateByID,btSearchByID,btCleantextFields);
			p4.setAlignment(Pos.CENTER);
			
			// Connecting p1 and p4 by border pane
			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(p1);
			borderPane.setBottom(p4);
			
			// SCENE
			Scene scene = new Scene(borderPane, 550, 180);
			primaryStage.setTitle("Address Book Project");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			// DISPLAY THE FIRST RECORD IF EXISTS
			try {
				if (raf.length() > 0)  // if file is not empty, fill the array
				{
					long currentPosition = raf.getFilePointer(); // getFilePointer shows beginning point (zero) of the file
					while(currentPosition < raf.length()) {
						readFileAndFillArray(arr_AB,currentPosition);
						currentPosition = raf.getFilePointer(); // getFilePointer's place has changed
					}
					readFileByPos(0); // Prints the first person when the program starts
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		// BUTTON ACTIONS
			
			// ADD
			  btAdd.setOnAction(e->{
				try {
					writeAddressToFile(raf.length()); 
					readFileAndFillArray(arr_AB, RECORD_SIZE*2*(index)); // Add last record into array
					alert1.setContentText("The record is added successfully");
					alert1.showAndWait();
					cleanTextFields();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
			 
			});
			  
			  // FIRST
			  btFirst.setOnAction(e->{
				try {
					readFileAndFillArray(arr_AB, raf.length());
					readFileAndFillArray(arr_AB, RECORD_SIZE*2*(index));
	
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
			 
			});
			  
			  // CLEAN
			  btCleantextFields.setOnAction(e->{
				try {
					cleanTextFields();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
			 
			});			  
		}
			catch(Exception ex) {
				ex.printStackTrace(); }
		
}
	
	public void readFileAndFillArray(Person[] people,long position) throws IOException{
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf);
		int intID = Integer.parseInt(id.trim().toString());
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim(); // trim excludes blanks at the beginning point and end point.
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
		// Here, we will create a person according to data in the file and add it into the array "people"
		Person person = new Person(intID, name, gender, street, city, zip);
		people[index] = person;
		index++;
}
	
	
	public void readFileByPos(long position) throws IOException{
		raf.seek(position);
		String id = FileOperations.readFixedLengthString(ID_SIZE, raf);
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf);
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf);
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf);
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf);
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf);
		
		tfID.setText(id);
		tfName.setText(name);
		tfStreet.setText(street);
		tfCity.setText(city);
		tfGender.setText(gender);
		tfZip.setText(zip);
	}

	
	public void writeAddressToFile(long position) {
		
	try {
		raf.seek(position);
		FileOperations.writeFixedLengthString(tfID.getText(), ID_SIZE, raf);
		FileOperations.writeFixedLengthString(tfName.getText(), NAME_SIZE, raf);
		FileOperations.writeFixedLengthString(tfStreet.getText(), STREET_SIZE, raf);
		FileOperations.writeFixedLengthString(tfCity.getText(), CITY_SIZE, raf);
		FileOperations.writeFixedLengthString(tfGender.getText(), GENDER_SIZE, raf);
		FileOperations.writeFixedLengthString(tfZip.getText(), ZIP_SIZE, raf);
		
	} 
	catch (IOException ex) {
		ex.printStackTrace();
	}
	
}
	public static void main(String[] args) {
		launch(args);
	}
}
