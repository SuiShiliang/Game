package javafxGame02;

import java.util.Stack;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyBorderPane extends BorderPane {
	
	private static Stage mainStage;
	private static BorderPane borderPane = new BorderPane();
	
	private static double screen_width = 200;
	private static double screen_height = 270;
	
	private static double tile_size = 20;
	private static double game_level = 1;
	
	
	public static void createMenuBar() {
		borderPane.setTop(MyMenuBar.getMenuBar(mainStage));
	}
	
	public static void createGamePane() {
		
	}
	public static void createDisplayScore() {
		BorderPane displayPane = new BorderPane();
		displayPane.setLayoutX(40);
		displayPane.setMaxHeight(40);
		
		
		StackPane timetackPane = new StackPane();
		
		Button button = new Button("时间");
		
		timetackPane.getChildren().addAll(button);
		
		StackPane mineStackPane = new StackPane();
		
		Button button2 = new Button("地雷");
		
		mineStackPane.getChildren().addAll(button2);
		
		displayPane.setLeft(timetackPane);
		displayPane.setRight(mineStackPane);
		
		borderPane.setStyle("-fx-background-color: black ;\r\n" + 
				"	-fx-border-width: 1px ;");
		borderPane.setBottom(displayPane);
	}
	
	public static void setBorderPane(Stage stage) {
		
		mainStage = stage;
		createMenuBar();
		createGamePane();
		createDisplayScore();
		
		
		Scene scene = new Scene(borderPane,200,235);
		stage.setScene(scene);
		
	}
}
