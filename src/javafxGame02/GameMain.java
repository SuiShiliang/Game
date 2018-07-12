package javafxGame02;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameMain extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MyBorderPane.setBorderPane(primaryStage);
		primaryStage.setTitle("saolei");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
