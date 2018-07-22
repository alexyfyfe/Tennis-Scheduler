package nestla.tennis.league.scheduler.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutTennisSchedulerController implements Initializable {

	@FXML
	private Button OKButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == OKButton) {
			System.out.println("OK");
			Stage stage = (Stage) OKButton.getScene().getWindow();
			stage.close();
		}
	}
}
