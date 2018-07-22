package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nestla.tennis.league.scheduler.league.Club;
import nestla.tennis.league.scheduler.league.League;

public class EditClubsController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button editClubsBack, editClubAddEdit, editClubDelete;
	@FXML
	private TextField editClubName;
	@FXML
	private ComboBox<String> clubNumberOfCourts, clubNumberOfCourtsWithLights;
	/* Table Variables */
	@FXML
	private TableView<Club> clubsTable;
	@FXML
	private TableColumn<Club, String> clubNameCol;
	@FXML
	private TableColumn<Club, Integer> numberOfCourtsCol, numberOfCourtsWithLightsCol, numberOfTeamsCol;

	private ObservableList<Club> clubsList;
	private ObservableList<Club> clubsSelected, allClubs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clubNumberOfCourts.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		clubNumberOfCourts.setVisibleRowCount(5);
		clubNumberOfCourts.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				int highestNumber = 0;
				try {
					highestNumber = Integer.parseInt(t1);
				} catch (NumberFormatException nfe) {
				}
				clubNumberOfCourtsWithLights.getItems().clear();
				if (highestNumber > 0) {
					for (int i = 0; i <= highestNumber; i++) {
						clubNumberOfCourtsWithLights.getItems().add(i + "");
					}
				}
			}
		});
		clubNumberOfCourtsWithLights.setVisibleRowCount(5);
	}

	public void initData(League league) {
		this.league = league;
		clubsList = FXCollections.observableArrayList(league.getClubs());

		clubNameCol.setCellValueFactory(new PropertyValueFactory<Club, String>("clubName"));
		numberOfCourtsCol.setCellValueFactory(new PropertyValueFactory<Club, Integer>("numberOfCourts"));
		numberOfCourtsWithLightsCol
				.setCellValueFactory(new PropertyValueFactory<Club, Integer>("numberOfCourtsWithLights"));
		numberOfTeamsCol.setCellValueFactory(new PropertyValueFactory<Club, Integer>("numberOfTeams"));

		clubsTable.setItems(clubsList);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == editClubAddEdit) {
			if (editClubAddEdit.getText() == "Edit") {
				System.out.println("EDIT");
				Club clubSelected = clubsSelected.get(0);
				clubSelected.setClubName(editClubName.getText());
				clubSelected.setNumberOfCourts(Integer.parseInt(clubNumberOfCourts.getValue()));
				clubSelected.setNumberOfCourtsWithLights(Integer.parseInt(clubNumberOfCourtsWithLights.getValue()));
				league.setClubs(allClubs);
				this.resetTextFields();
			} else {
				league.addNewClub(editClubName.getText(), (league.getHighestClubID() + 1),
						Integer.parseInt(clubNumberOfCourts.getValue()),
						Integer.parseInt(clubNumberOfCourtsWithLights.getValue()));
				this.initData(league);
				clubsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			}
			clubsTable.refresh();

		}
		if (event.getSource() == editClubDelete) {
			System.out.println("DELETE");
			allClubs = clubsTable.getItems();
			clubsSelected = clubsTable.getSelectionModel().getSelectedItems();
			league.deleteClub(clubsSelected);
			clubsSelected.forEach(allClubs::remove);
			allClubs = clubsTable.getItems();
			clubsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}
		if (event.getSource() == editClubsBack) {
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
			allClubs = clubsTable.getItems();
			clubsSelected = clubsTable.getSelectionModel().getSelectedItems();
			Club clubSelected = clubsSelected.get(0);
			if (clubSelected == null) {
				clubsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			} else {
				editClubAddEdit.setText("Edit");
				clubNumberOfCourts.setValue(clubSelected.getNumberOfCourts() + "");
				clubNumberOfCourtsWithLights.setValue(clubSelected.getNumberOfCourtsWithLights() + "");
				editClubName.setText(clubSelected.getClubName());
			}
		}

		// clear selection on click anywhere but on a filled row
		if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
			clubsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}

	}

	private void resetTextFields() {
		editClubAddEdit.setText("Add");
		clubNumberOfCourts.setValue("No. Of Courts");
		clubNumberOfCourtsWithLights.setValue("No. Of Courts");
		clubNumberOfCourtsWithLights.getItems().clear();
		editClubName.clear();
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
