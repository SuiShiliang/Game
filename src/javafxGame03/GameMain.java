package javafxGame03;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
//		GamePane.createGamePane(primaryStage);
		new GamePane().createGamePane(primaryStage);
		primaryStage.setTitle("扫雷");
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
