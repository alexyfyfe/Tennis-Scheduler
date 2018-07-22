package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nestla.tennis.league.scheduler.league.Club;
import nestla.tennis.league.scheduler.league.Division;
import nestla.tennis.league.scheduler.league.League;

public class AddNewTeamController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button addNewTeamAddTeam, addNewTeamCancel;
	@FXML
	private TextField addNewTeamTeamName;
	@FXML
	private ComboBox<Club> addNewTeamClub;
	@FXML
	private ComboBox<Division> addNewTeamDivision;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initData(League league) {
		this.league = league;
		addNewTeamClub.getItems().addAll(league.getClubs());
		addNewTeamClub.setVisibleRowCount(5);
		// Define rendering of selected value shown in ComboBox.
		addNewTeamClub.setConverter(new StringConverter<Club>() {
			@Override
			public Club fromString(String divisionString) {
				return null;
			}

			@Override
			public String toString(Club club) {
				return club.getClubName();
			}
		});
		// Define rendering of the list of values in ComboBox drop down.
		addNewTeamClub.setCellFactory(new Callback<ListView<Club>, ListCell<Club>>() {
			@Override
			public ListCell<Club> call(ListView<Club> p) {
				return new ListCell<Club>() {
					@Override
					protected void updateItem(Club item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							this.setText("No Clubs");
						} else {
							this.setText(item.getClubName());
						}
					}
				};
			}
		});
		addNewTeamDivision.getItems().addAll(league.getDivisions());
		addNewTeamDivision.setVisibleRowCount(5);
		// Define rendering of selected value shown in ComboBox.
		addNewTeamDivision.setConverter(new StringConverter<Division>() {
			@Override
			public Division fromString(String divisionString) {
				return null;
			}

			@Override
			public String toString(Division division) {
				return division.getDivisionName();
			}
		});
		// Define rendering of the list of values in ComboBox drop down.
		addNewTeamDivision.setCellFactory(new Callback<ListView<Division>, ListCell<Division>>() {
			@Override
			public ListCell<Division> call(ListView<Division> p) {
				return new ListCell<Division>() {
					@Override
					protected void updateItem(Division item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							this.setText("No League");
						} else {
							this.setText(item.getDivisionName());
						}
					}
				};
			}
		});

	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == addNewTeamAddTeam) {
			// Create Teams (Name, TeamNum, ClubNum, DivNum)
			league.addNewTeam(addNewTeamTeamName.getText(), (league.getHighestTeamID() + 1),
					addNewTeamClub.getValue().getClubID(), addNewTeamDivision.getValue().getDivisionID());

			this.goHome();
		}
		if (event.getSource() == addNewTeamCancel) {
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