package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nestla.tennis.league.scheduler.league.League;

public class RunApplication extends Application {
	@Override
	public void start(Stage primaryStage) {
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

	public static void main(String[] args) {
		launch(args);
	}
}
