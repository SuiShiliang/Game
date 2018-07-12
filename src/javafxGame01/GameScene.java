package javafxGame01;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameScene {
	
	private static int scene_width ;
	private static int scene_height;
	
	public static Scene getScene() {
		BorderPane pane = GamePane.getGamePane();
		scene_width = (int) pane.getMaxWidth();
		scene_height = (int) pane.getMaxHeight();
		Scene scene = new Scene(pane,scene_width,scene_height);
		return scene;
		
	}

}
