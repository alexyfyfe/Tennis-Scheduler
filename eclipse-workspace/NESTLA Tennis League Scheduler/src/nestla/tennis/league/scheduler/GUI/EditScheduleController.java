package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;
import nestla.tennis.league.scheduler.league.Team;

public class EditScheduleController implements Initializable {

	/* SchedulerGA Variables */
	private League league;

	/* GUI Variables */
	@FXML
	private AnchorPane rootPane;
	@FXML
	private VBox vBox;
	@FXML
	private Button editScheduleBack;

	/* Table Variables */
	// @FXML
	// private TableView<Match[]> scheduleTable;
	// @FXML
	// private TableColumn<Schedule, String> weekIDCol;

	private ObservableList<Team> teamsList;
	private ObservableList<Team> teamsSelected, allTeams;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initData(League league) {
		this.league = league;
		league.setUp();
		System.out.println(
				"Division NumWeeks: " + league.getNumberOfWeeks() + " NumCourts: " + league.getNumberOfCourts());

		Match[][] matchArray = league.getSchedule().getMatchArray();

		int rowCount = league.getNumberOfWeeks();
		int columnCount = league.getNumberOfCourts();
		// System.out.println("RowNum: " + rowCount + " columnCount: " + columnCount);
		GridBase grid = new GridBase(rowCount, columnCount);
		grid.getColumnHeaders().clear();
		for (int i = 0; i < columnCount; i++) {
			//Courts without lights only have 2 available no matches
			if (league.getCourts().get(i).hasLights() == false) {
				league.getCourts().get(i).setAvailableSlots(2);
			}
			grid.getColumnHeaders().add(i, league.getCourts().get(i).getCourtName() + " ("+ league.getCourts().get(i).getNumberOfAvailableSlots() + ")");
		}
		grid.getRowHeaders().clear();
		for (int i = 0; i < rowCount; i++) {
			grid.getRowHeaders().add("Week " + (i + 1));
		}

		ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
		for (int row = 0; row < grid.getRowCount(); ++row) {
			final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
			for (int column = 0; column < grid.getColumnCount(); ++column) {
				String output = "Match Allowed";
				if (matchArray[row][column].isFixed()) {
					output = "No Match Allowed";
				}
				list.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, output));
			}
			rows.add(list);
		}
		grid.setRows(rows);

		SpreadsheetView spv = new SpreadsheetView(grid);
		spv.setRowHeaderWidth(70);
		spv.setEditable(false);
		spv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		spv.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				for (TablePosition cell : spv.getSelectionModel().getSelectedCells()) {
					// System.out.println(cell.getRow() + "/" + cell.getColumn());
					if (matchArray[cell.getRow()][cell.getColumn()].isFixed()) {
						matchArray[cell.getRow()][cell.getColumn()] = new Match(-1, false);
						spv.getGrid().setCellValue(cell.getRow(), cell.getColumn(), "Match Allowed");
						league.getCourts().get(cell.getColumn()).addOneToAvailableSlots();
					} else {
						if (league.getCourts().get(cell.getColumn()).getNumberOfAvailableSlots() > 0) {
							matchArray[cell.getRow()][cell.getColumn()] = new Match(-1, true);
							spv.getGrid().setCellValue(cell.getRow(), cell.getColumn(), "No Match Allowed");
							league.getCourts().get(cell.getColumn()).takeOneFromAvailableSlots();
						}
					}
					spv.getGrid().getColumnHeaders().set(cell.getColumn(),
							league.getCourts().get(cell.getColumn()).getCourtName() + " ("
									+ league.getCourts().get(cell.getColumn()).getNumberOfAvailableSlots() + ")");
					Schedule s = new Schedule(matchArray);
					// System.out.println(s.toString());
					league.setScheduleWithMatchArray(matchArray);
				}
			}
		});

		vBox.getChildren().add(0, spv);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == editScheduleBack) {
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
