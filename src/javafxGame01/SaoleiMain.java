package javafxGame01;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SaoleiMain extends Application {
	
	private static double scene_width ;
	private static double scene_height;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane pane = GamePane.getGamePane();
		scene_width = (int) pane.getMaxWidth();
		scene_height = (int) pane.getMaxHeight();
		Scene scene = new Scene(pane,scene_width,scene_height+30);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("扫雷");
		primaryStage.show();
		
		primaryStage.widthProperty().addListener(changeListener(scene));
		primaryStage.heightProperty().addListener(changeListener(scene));
	}
	/*
	 * 窗体拉伸后调用此函数
	 * */
	public ChangeListener<Number> changeListener(Scene scene){
		ChangeListener<Number> listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				scene_width = scene.getWidth();
				scene_height = scene.getHeight();
				System.out.println(scene_width +":"+scene_height);
			}
		};
		return listener;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
