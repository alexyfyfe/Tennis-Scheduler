package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nestla.tennis.league.scheduler.league.Club;
import nestla.tennis.league.scheduler.league.Division;
import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Team;

public class EditTeamsController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button editTeamsBack, editTeamAddEdit, editTeamDelete;
	@FXML
	private TextField editTeamName;
	@FXML
	private ComboBox<Club> clubComboBox;
	@FXML
	private ComboBox<Division> divisionComboBox;

	/* Table Variables */
	@FXML
	private TableView<Team> teamsTable;
	@FXML
	private TableColumn<Team, String> teamNameCol, clubNameCol, divisionNameCol;

	private ObservableList<Team> teamsList;
	private ObservableList<Team> teamsSelected, allTeams;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initData(League league) {
		this.league = league;
		this.league.divisionUpdateTeamClubNameDivisionName();
		teamsList = FXCollections.observableArrayList(league.getTeams());

		teamNameCol.setCellValueFactory(new PropertyValueFactory<Team, String>("teamName"));
		clubNameCol.setCellValueFactory(new PropertyValueFactory<Team, String>("clubName"));
		divisionNameCol.setCellValueFactory(new PropertyValueFactory<Team, String>("divisionName"));

		clubComboBox.getItems().clear();
		clubComboBox.getItems().addAll(league.getClubs());
		// Define rendering of selected value shown in ComboBox.
		clubComboBox.setConverter(new StringConverter<Club>() {
			@Override
			public Club fromString(String clubString) {
				Club output = null;
				for (int i = 0; i < league.getClubs().size(); i++) {
					if (league.getClubs().get(i).getClubName() == clubString) {
						output = league.getClubs().get(i);
					}
				}
				return output;
			}

			@Override
			public String toString(Club club) {
				return club.getClubName();
			}
		});
		// Define rendering of the list of values in ComboBox drop down.
		clubComboBox.setCellFactory(new Callback<ListView<Club>, ListCell<Club>>() {
			@Override
			public ListCell<Club> call(ListView<Club> p) {
				return new ListCell<Club>() {
					@Override
					protected void updateItem(Club item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							this.setText("Club");
						} else {
							this.setText(item.getClubName());
						}
					}
				};
			}
		});
		clubComboBox.setVisibleRowCount(5);

		divisionComboBox.getItems().clear();
		divisionComboBox.getItems().addAll(league.getDivisions());
		// Define rendering of selected value shown in ComboBox.
		divisionComboBox.setConverter(new StringConverter<Division>() {
			@Override
			public Division fromString(String divisionString) {
				Division output = null;
				for (int i = 0; i < league.getDivisions().size(); i++) {
					if (league.getDivisions().get(i).getDivisionName() == divisionString) {
						output = league.getDivisions().get(i);
					}
				}
				return output;
			}

			@Override
			public String toString(Division division) {
				return division.getDivisionName();
			}
		});
		// Define rendering of the list of values in ComboBox drop down.
		divisionComboBox.setCellFactory(new Callback<ListView<Division>, ListCell<Division>>() {
			@Override
			public ListCell<Division> call(ListView<Division> p) {
				return new ListCell<Division>() {
					@Override
					protected void updateItem(Division item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							this.setText("Division");
						} else {
							this.setText(item.getDivisionName());
						}
					}
				};
			}
		});
		divisionComboBox.setVisibleRowCount(5);

		teamsTable.setItems(teamsList);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == editTeamAddEdit) {
			if (editTeamAddEdit.getText() == "Edit") {
				System.out.println("EDIT");
				Team teamSelected = teamsSelected.get(0);
				teamSelected.setTeamName(editTeamName.getText());
				teamSelected.setClubID(clubComboBox.getValue().getClubID());
				teamSelected.setDivisionID(divisionComboBox.getValue().getDivisionID());
				teamSelected.setClubName(clubComboBox.getValue().getClubName());
				teamSelected.setDivisionName(divisionComboBox.getValue().getDivisionName());
				league.setTeams(allTeams);
				System.out.println(league);
				this.resetTextFields();
			} else {
				// teamName,teamID,clubID,divisionID
				league.addNewTeam(editTeamName.getText(), (league.getHighestTeamID() + 1),
						clubComboBox.getValue().getClubID(), divisionComboBox.getValue().getDivisionID());
				this.initData(league);
				teamsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			}
			teamsTable.refresh();
		}
		if (event.getSource() == editTeamDelete) {
			System.out.println("DELETE");
			allTeams = teamsTable.getItems();
			teamsSelected = teamsTable.getSelectionModel().getSelectedItems();
			league.deleteTeam(teamsSelected);
			teamsSelected.forEach(allTeams::remove);
			allTeams = teamsTable.getItems();
			teamsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}
		if (event.getSource() == editTeamsBack) {
			this.goHome();
		}
	}

	@FXML
	private void handleMousePressed(MouseEvent mouseEvent) throws IOException {
		Node source = mouseEvent.getPickResult().getIntersectedNode();
		// move up through the node hierarchy until a TableRow or scene root is found
		while (source != null && !(source instanceof TableRow)) {
			source = source.getParent();
			System.out.println("Mouse Pressed");
			allTeams = teamsTable.getItems();
			teamsSelected = teamsTable.getSelectionModel().getSelectedItems();
			Team teamSelected = teamsSelected.get(0);
			if (teamSelected == null) {
				teamsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			} else {
				editTeamAddEdit.setText("Edit");
				editTeamName.setText(teamSelected.getTeamName());
				clubComboBox.setValue(league.getClubX(teamSelected.getClubID()));
				divisionComboBox.setValue(league.getDivisionX(teamSelected.getDivisionID()));
			}
		}

		// clear selection on click anywhere but on a filled row
		if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
			teamsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}

	}

	private void resetTextFields() {
		editTeamAddEdit.setText("Add");
		clubComboBox.setValue(new Club("Club", -1, -1, -1));
		divisionComboBox.setValue(new Division("Division", -1));
		editTeamName.clear();
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
