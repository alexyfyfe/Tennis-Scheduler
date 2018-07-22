package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nestla.tennis.league.scheduler.league.League;

public class AddNewClubController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button addNewClubAddClub, addNewClubCancel;
	@FXML
	private TextField addNewClubClubName;
	@FXML
	private ComboBox<String> addNewClubNumberOfCourts, addNewClubNumberOfCourtsWithLights;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initData(League league) {
		this.league = league;
		addNewClubNumberOfCourts.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		addNewClubNumberOfCourts.setVisibleRowCount(5);
		addNewClubNumberOfCourts.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				addNewClubNumberOfCourtsWithLights.getItems().clear();
				for (int i = 0; i <= Integer.parseInt(t1); i++) {
					addNewClubNumberOfCourtsWithLights.getItems().add(i + "");
				}
			}
		});
		addNewClubNumberOfCourtsWithLights.setVisibleRowCount(5);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == addNewClubAddClub) {
			league.addNewClub(addNewClubClubName.getText(), (league.getHighestClubID() + 1),
					Integer.parseInt(addNewClubNumberOfCourts.getValue()),
					Integer.parseInt(addNewClubNumberOfCourtsWithLights.getValue()));

			this.goHome();
		}
		if (event.getSource() == addNewClubCancel) {
			this.goHome();
		}
	}

	private void goHome() {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("Home.fxml"));
		try {
			Loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		HomeController display = Loader.getController();
		display.initData(league);
		AnchorPane pane = Loader.getRoot();
		rootPane.getChildren().setAll(pane);
	}
}