package 简单扫雷;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SaoLeiMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*
		 * Rectangle2D rct2 = Screen.getPrimary().getVisualBounds();
		 * primaryStage.setX(rct2.getMinX()); primaryStage.setY(rct2.getMinY());
		 * primaryStage.setWidth(rct2.getWidth());
		 * primaryStage.setHeight(rct2.getHeight());
		 */
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 300, 300);
		
		scene.setOnDragOver(e -> setScreen(pane) );
//		pane.setOnMouseClicked(e -> setScreen(pane));
		
		MenuBar bar = new MenuBar();
		bar.setPrefHeight(30);
		
		Menu menuF = new Menu("游戏");
		MenuItem item1 = new MenuItem("新游戏");
		MenuItem item2 = new MenuItem("保存");
		MenuItem item3 = new MenuItem("退出");
		item3.setOnAction(e -> Platform.exit());
		menuF.getItems().addAll(item1,item2,new SeparatorMenuItem(),item3);
		
		
		Menu menuS = new Menu("分数");
		MenuItem item4 = new MenuItem("各等级分数");
		MenuItem item5 = new MenuItem("选择难度");
		menuS.getItems().addAll(item4,item5);
		
		Menu menuSet = new Menu("设置");
		MenuItem item6 = new MenuItem("更改外观");
		menuSet.getItems().addAll(item6);
		
		Menu menuH = new Menu("帮助");
		MenuItem item7 = new MenuItem("关于");
		menuH.getItems().addAll(item7);
		
		bar.getMenus().addAll(menuF,menuS,menuSet,menuH);
		
		pane.setTop(bar);
		
		VBox vBox = new VBox();
		for(int i = 1; i <= 10; i++) {
			HBox box = new HBox();
			for(int j = 1; j <= 10; j++) {
				Button button = new Button(""+j);
				button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				HBox.setHgrow(button, Priority.ALWAYS);
				box.getChildren().add(button);
			}
			VBox.setVgrow(box, Priority.ALWAYS);
			vBox.getChildren().add(box);
		}			
		pane.setCenter(vBox);
		pane.setOnMouseClicked(e->{System.out.println("点击了panne");});
		primaryStage.setTitle("saolei");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setScreen(Pane pane) {
		
		int width = (int) pane.getWidth();
		int height = (int) pane.getHeight();
		System.out.println(width+","+height);
		
		return ;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
