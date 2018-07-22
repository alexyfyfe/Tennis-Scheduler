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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nestla.tennis.league.scheduler.league.Division;
import nestla.tennis.league.scheduler.league.League;

public class EditDivisionsController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button editDivisionsBack, editDivisionAddEdit, editDivisionDelete;
	@FXML
	private TextField editDivisionID, editDivisionName;
	/* Table Variables */
	@FXML
	private TableView<Division> divisionsTable;
	// @FXML
	// private TableColumn<Division, Integer> divisionIDCol;
	@FXML
	private TableColumn<Division, String> divisionNameCol;

	private ObservableList<Division> divisionsList;
	private ObservableList<Division> divisionsSelected, allDivisions;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initData(League league) {
		this.league = league;
		divisionsList = FXCollections.observableArrayList(league.getDivisions());
		// divisionIDCol.setCellValueFactory(new PropertyValueFactory<Division, Integer>("divisionID"));
		divisionNameCol.setCellValueFactory(new PropertyValueFactory<Division, String>("divisionName"));

		divisionsTable.setItems(divisionsList);

		// editDivisionID.setText(league.getIDOfDivisions() + "");

	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == editDivisionAddEdit) {
			if (editDivisionAddEdit.getText() == "Edit") {
				System.out.println("EDIT");
				Division divisionSelected = divisionsSelected.get(0);
				// divisionSelected.setID(Integer.parseInt(editDivisionID.getText()));
				divisionSelected.setName(editDivisionName.getText());
				league.setDivisions(allDivisions);
				this.resetTextFields();
			} else {
				league.addNewDivision(editDivisionName.getText(), (league.getHighestDivisionID() + 1));
				this.initData(league);
				divisionsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			}
			divisionsTable.refresh();

		}
		if (event.getSource() == editDivisionDelete) {
			System.out.println("DELETE");
			allDivisions = divisionsTable.getItems();
			divisionsSelected = divisionsTable.getSelectionModel().getSelectedItems();
			league.deleteDivision(divisionsSelected);
			// System.out.println(divisionsSelected);
			divisionsSelected.forEach(allDivisions::remove);
			allDivisions = divisionsTable.getItems();
			divisionsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}
		if (event.getSource() == editDivisionsBack) {
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
			allDivisions = divisionsTable.getItems();
			divisionsSelected = divisionsTable.getSelectionModel().getSelectedItems();
			Division divisionSelected = divisionsSelected.get(0);
			if (divisionSelected == null) {
				divisionsTable.getSelectionModel().clearSelection();
				this.resetTextFields();
			} else {
				editDivisionAddEdit.setText("Edit");
				// editDivisionID.setDisable(true);
				// editDivisionID.setText(divisionSelected.getDivisionID() + "");
				editDivisionName.setText(divisionSelected.getDivisionName());
			}
		}

		// clear selection on click anywhere but on a filled row
		if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
			divisionsTable.getSelectionModel().clearSelection();
			this.resetTextFields();
		}

	}

	private void resetTextFields() {
		editDivisionAddEdit.setText("Add");
		// editDivisionID.setDisable(false);
		// editDivisionID.setEditable(true);
		// editDivisionID.setText(league.getHighestDivisionID()+1 + "");
		// editDivisionID.setEditable(false);
		editDivisionName.clear();
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
