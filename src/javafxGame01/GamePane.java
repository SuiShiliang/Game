package javafxGame01;

import java.awt.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GamePane {
	// 屏幕大小
	public static double screen_width_size;
	public static double screen_height_size;
	// 游戏等级
	private static double game_level = 1;
	// 格子的大小
	private static double tile_size = 20;
	// 横向有多少个格子
	private static double game_level_grid_width;
	// 纵向有多少个格子
	private static double game_level_grid_height;
	// pane中的上下左右间距，也是下部时间和雷显示区域的高度
	private static double timeAndCountPadding;
	// 游戏总布局
	public static BorderPane pane;

	private static void initGameParameter() {

		if (game_level == 1) {
			// 设置空白处的大小
			timeAndCountPadding = tile_size * 2;
			// 设置横向格子的数量
			game_level_grid_width = 9 + 4;
			// 设置纵向格子的数量，其中4个格子用来作为左右间距空白
			game_level_grid_height = 9 + 4;
		} else if (game_level == 2) {
			timeAndCountPadding = tile_size * 3;
			game_level_grid_width = 16 + 4;
			game_level_grid_height = 16 + 4;
		} else {
			timeAndCountPadding = tile_size * 5;
			game_level_grid_width = 30 + 4;
			game_level_grid_height = 16 + 4;
		}
		// 设置游戏区域的宽度
		screen_width_size = game_level_grid_width * tile_size;
		// 设置游戏区域的高度
		screen_height_size = game_level_grid_height * tile_size;

	}

	public static BorderPane getGamePane() {

		initGameParameter();

		pane = new BorderPane();
		pane.setMaxWidth(screen_width_size);
		pane.setMaxHeight(screen_height_size);
		// pane.setBackground(new Background(fills));
//		System.out.println(pane.getLayoutX());
//		System.out.println(pane.getMaxWidth());
		// 设置菜单栏
		pane.setTop(MyMenuBar.getMenuBar());
		// 设置中间游戏的pane
		BorderPane gamePane = new BorderPane();
		gamePane.setMaxWidth((game_level_grid_width - 4) * tile_size);
		gamePane.setMaxHeight((game_level_grid_height - 4) * tile_size);
		System.out.println(pane.getMaxWidth());
		System.out.println(pane.getMaxHeight());
		System.out.println(gamePane.getMaxWidth());
		System.out.println(gamePane.getMaxHeight());

		gamePane.setLayoutY(35+2 * tile_size);
		gamePane.setLayoutX(2.5 * tile_size);
//		System.out.println(gamePane.getLayoutX());
//		System.out.println(gamePane.getMaxWidth());
		for (int i = 0; i < 9; i++) {
			for(int j=0;j<9;j++) {
				StackPane pane = new StackPane();
				pane.setLayoutX(tile_size * i);
				pane.setLayoutY(tile_size * j);
				Rectangle rectangle = new Rectangle(tile_size - 1, tile_size - 1);
				rectangle.setStroke(Color.BLUE);
				Text text = new Text(i + "");
				text.setFill(Color.WHITE);
//				text.setVisible(false);
				pane.getChildren().addAll(rectangle,text);
				if(i==0&&j==1) {
					System.out.println(pane.getLayoutX());
					System.out.println(pane.getLayoutY());
				}
				gamePane.getChildren().addAll(pane);	
			}
			
		}
		pane.getChildren().addAll(gamePane);
		
		try {
			Image timeImage = new Image(new FileInputStream(new File("C:/Program Files/myJavaFXGame/image/lei.png")));
			Image leiImage = new Image(new FileInputStream(new File("src/javafxGame01/resource/lei.png")));
			ImageView imageViewTime = new ImageView();
			ImageView imageViewLei = new ImageView();
			imageViewTime.setImage(timeImage);
			imageViewTime.setX(tile_size*2);
			imageViewTime.setY(gamePane.getMaxHeight()+tile_size*3+35);
			imageViewTime.setFitWidth(25);
			imageViewTime.setFitHeight(25);
			Text timeText = new Text("测试");
			Text countText = new Text("cw试");
			timeText.setX(imageViewTime.getX()+imageViewTime.getFitWidth());
			timeText.setY(gamePane.getMaxHeight()+tile_size*3+35);
			countText.setX(gamePane.getMaxWidth()+tile_size);
			countText.setY(gamePane.getMaxHeight()+tile_size*3+35);
			
			pane.getChildren().addAll(imageViewTime,timeText,countText);

		} catch (FileNotFoundException e) {
			System.out.println("时间图片未找到");
		}

		return pane;

	}

	public static BorderPane getGamePane(double minScreenSize) {
		tile_size = minScreenSize;
		return getGamePane();
	}

}
