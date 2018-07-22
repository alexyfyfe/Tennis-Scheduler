package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import nestla.tennis.league.scheduler.league.League;

public class AddNewDivisionController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button addNewDivisionAddDivision, addNewDivisionCancel;
	@FXML
	private TextField addNewDivisionDivisionName;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initData(League league) {
		this.league = league;
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == addNewDivisionAddDivision) {
			league.addNewDivision(addNewDivisionDivisionName.getText(), (league.getHighestDivisionID() + 1));

			this.goHome();
		}
		if (event.getSource() == addNewDivisionCancel) {
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
