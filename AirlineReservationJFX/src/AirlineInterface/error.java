package AirlineInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class error {

	public void nameTaken() {
		Stage nameTakenStage = new Stage();
		GridPane nameTakenGrid = new GridPane();
		nameTakenGrid.setMinHeight(200);
		nameTakenGrid.setMinWidth(300);
		nameTakenGrid.setAlignment(Pos.CENTER);

		Text nameTakenText = new Text("Name is already taken");

		nameTakenGrid.add(nameTakenText, 5, 5);
		Scene nameTakenScene = new Scene(nameTakenGrid, 300, 200);
		nameTakenStage.setScene(nameTakenScene);
		nameTakenStage.show();
	}

}
