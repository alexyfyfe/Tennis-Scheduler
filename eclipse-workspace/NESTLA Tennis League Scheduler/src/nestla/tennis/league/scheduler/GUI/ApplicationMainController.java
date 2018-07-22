package nestla.tennis.league.scheduler.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import nestla.tennis.league.scheduler.league.League;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ApplicationMainController implements Initializable {

	/* SchedulerGA Variables */
	private League league;
	private HomeController display;
	/* Saving/Loading Variables */
	private Object divisionsObj;
	private String FILE_DIRECTORY;
	private FileChooser fc;
	private File selectedFile;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;

	/* Menu Variables */
	@FXML
	private MenuItem menuNew, menuOpen, menuSave, menuSaveAs, menuPreferences, menuQuit, menuAboutTennisScheduler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Setting up directory for Saving/Loading */
		boolean success = false;
		String userHomeFolder = System.getProperty("user.home");
		// Create a directory; all non-existent ancestor directories are automatically created
		File folderName = new File(userHomeFolder + "/Desktop/Tennis-Scheduler");
		if (!folderName.exists()) {
			success = (folderName).mkdirs();
			if (!success) {
				// Directory creation failed
				System.out.println("Directory creation failed");
			}
		}
		FILE_DIRECTORY = userHomeFolder + "/Desktop/Tennis-Scheduler";
		fc = new FileChooser();
		fc.setInitialDirectory(new File(FILE_DIRECTORY));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Txt Files", "*.txt"));
	}

	public void initData(League league) {
		this.league = league;
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("Home.fxml"));
		try {
			Loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		display = Loader.getController();
		display.initData(league);
		AnchorPane pane = Loader.getRoot();
		rootPane.getChildren().setAll(pane);

	}

	public void updateDivisions() {
		league = display.getDivisions();
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		this.updateDivisions();
		if (event.getSource() == menuNew) {
			// New Application
			League league = new League();
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("ApplicationMain.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			ApplicationMainController display = Loader.getController();
			display.initData(league);
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			stage.setTitle("Tennis Scheduler");
			stage.getIcons().add(new Image("/resources/tennis.png"));
			stage.setScene(new Scene(p));
			stage.showAndWait();
		}
		if (event.getSource() == menuOpen) {
			try {
				selectedFile = fc.showOpenDialog(null);
				if (selectedFile != null) {
					FileInputStream filein = new FileInputStream(selectedFile);
					ObjectInputStream is = new ObjectInputStream(filein);
					league = (League) is.readObject(); // Read Object
					is.close();
					this.initData(league);
					System.out.println("The Object  was succesfully read from a file");
					System.out.println("Opened: " + league);
				} else {
					System.out.println("File not valid");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (event.getSource() == menuSave) {
			Object divisionsObj = league;
			try {
				if (selectedFile == null) {
					selectedFile = fc.showSaveDialog(null);
				}
				if (selectedFile != null) {
					FileOutputStream fileOut = new FileOutputStream(selectedFile);
					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
					divisionsObj = league;
					objectOut.writeObject(divisionsObj); // Write Object
					objectOut.close();
					System.out.println("The Object  was succesfully written to a file");
					System.out.println("Saved: " + league);
				} else {
					System.out.println("File not valid");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (event.getSource() == menuSaveAs) {
			selectedFile = fc.showSaveDialog(null);
			if (selectedFile != null) {
				FileOutputStream fileOut = new FileOutputStream(selectedFile);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				divisionsObj = league;
				objectOut.writeObject(divisionsObj); // Write Object
				objectOut.close();
				System.out.println("The Object  was succesfully written to a file");
				System.out.println("Saved: " + league);
			} else {
				System.out.println("File not valid");
			}
		}
		if (event.getSource() == menuPreferences) {

		}
		if (event.getSource() == menuQuit) {
			System.out.println("QUIT");
			System.exit(0);
		}
		if (event.getSource() == menuAboutTennisScheduler) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("AboutTennisScheduler.fxml"));
			/*
			 * if "fx:controller" is not set in fxml fxmlLoader.setController(NewWindowController);
			 */
			Scene scene = new Scene(fxmlLoader.load(), 400, 200);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("About Tennis Scheduler");
			stage.setScene(scene);
			stage.show();
		}
	}

}

// READ AND WRITE OBJECT

/* LOAD NEW SCENE ON SEPERATE WINDOW */
// FXMLLoader Loader = new FXMLLoader();
// Loader.setLocation(getClass().getResource("AddNewDivision.fxml"));
// try {
// Loader.load();
// } catch (IOException ex) {
// ex.printStackTrace();
// }
// AddNewDivisionController display = Loader.getController();
// display.initData(league);
//
// Parent p = Loader.getRoot();
// Stage stage = (Stage) addNewDivision.getScene().getWindow();
//
// stage.setScene(new Scene(p));