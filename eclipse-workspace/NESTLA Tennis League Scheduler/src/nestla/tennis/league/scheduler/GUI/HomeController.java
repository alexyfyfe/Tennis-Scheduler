package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import nestla.tennis.league.scheduler.csp.SchedulerCSP;
import nestla.tennis.league.scheduler.ga.SchedulerGA;
import nestla.tennis.league.scheduler.league.League;

public class HomeController implements Initializable {

	/* SchedulerGA Variables */
	private League league;
	private Thread schedulerThread;

	/* GUI Variables */
	@FXML
	private Button addNewDivision, editDivisions, addNewClub, editClubs, addNewTeam, editTeams, editSchedule,
			runCancelScheduler;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private ProgressBar runScheduleProgressBar;
	@FXML
	private ProgressIndicator runScheduleProgressIndicator;
	@FXML
	private ToggleSwitch toggleSwitch;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initData(League league) {
		this.league = league;
		System.out.println(this.league.toString());
		if (league.getTeams().size() == 0) {
			runCancelScheduler.setDisable(true);
			editSchedule.setDisable(true);
		} else {
			runCancelScheduler.setDisable(false);
			editSchedule.setDisable(false);
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		/* ADD BUTTONS */
		if (event.getSource() == addNewDivision) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("AddNewDivision.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			AddNewDivisionController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		if (event.getSource() == addNewClub) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("AddNewClub.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			AddNewClubController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		if (event.getSource() == addNewTeam) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("AddNewTeam.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			AddNewTeamController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		/* EDIT BUTTONS */
		if (event.getSource() == editDivisions) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("EditDivisions.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			EditDivisionsController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		if (event.getSource() == editClubs) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("EditClubs.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			EditClubsController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		if (event.getSource() == editTeams) {
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("EditTeams.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			EditTeamsController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		/* Edit Schedule */
		if (event.getSource() == editSchedule) {
			System.out.println("OPEN");
			FXMLLoader Loader = new FXMLLoader();
			Loader.setLocation(getClass().getResource("EditSchedule.fxml"));
			try {
				Loader.load();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			EditScheduleController display = Loader.getController();
			display.initData(league);
			AnchorPane pane = Loader.getRoot();
			rootPane.getChildren().setAll(pane);
		}
		/* Run SchedulerGA */
		if (event.getSource() == runCancelScheduler) {
			// Cancel Scheduler
			if (runCancelScheduler.getText() == "Reset") {
				runCancelScheduler.setText("Run Scheduler");
				schedulerThread.interrupt();
			} 
			// Run Scheduler
			else {
				runCancelScheduler.setText("Reset");
				if (toggleSwitch.isSelected()) {
					// Constraint Satisfaction Problem Approach
					System.out.println("CSP Approach");
					SchedulerCSP schedulerCSP = new SchedulerCSP(league);
					runScheduleProgressBar.progressProperty().bind(schedulerCSP.progressProperty());
					runScheduleProgressIndicator.progressProperty().bind(schedulerCSP.progressProperty());
					schedulerThread = new Thread(schedulerCSP);
					schedulerThread.start();
				} else {
					// Genetic Algoritm Approach
					System.out.println("GA Approach");
					SchedulerGA schedulerGA = new SchedulerGA(league);
					runScheduleProgressBar.progressProperty().bind(schedulerGA.progressProperty());
					runScheduleProgressIndicator.progressProperty().bind(schedulerGA.progressProperty());
					schedulerThread = new Thread(schedulerGA);
					schedulerThread.start();
				}
			}
		}
	}

	public League getDivisions() {
		return league;
	}

}
