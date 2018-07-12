package 窗口最大化;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Saolei extends Application {
	// 屏幕的宽和高
	private static int W_SCREEN = 800;// 设置画布的宽度
	private static int H_SCREEN = 600;// 设置画布的高度
	private static int TILE_SIZE = 40;// 设置类的大小 40 * 40
	// 定义一个场景
	private Scene scene;
	// 定义横向格子的数目
	private static int X_COUNT = W_SCREEN / TILE_SIZE;
	// 定义纵向格子的数目
	private static int Y_COUNT = H_SCREEN / TILE_SIZE;

	// 内部类: 设置格子的格式
	private class Tile extends StackPane {
		// 格子的坐标
		private int x, y;
		// 格子是否为类
		private boolean hasBomb;
		// 格子是否被打开
		private boolean isOpen = false;
		// 设置格子大小 ： -2 表示留下2像素的边距
		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		// 为格子设置一个text组件:这里只代表文本
		private Text text = new Text();

		public Tile(int x, int y, boolean hasBomb) {
			this.x = x;
			this.y = y;
			this.hasBomb = hasBomb;

			// 设置格子边框的颜色
			border.setStroke(Color.LIGHTGRAY);
			// 设置text组件的填充颜色
			text.setFill(Color.WHITE);
			// 设置text组件的字体大小
			text.setFont(Font.font(18));
			// 判断是否是类，是雷，则显示X，否则为空
			text.setText(hasBomb ? "X" : "");
			// 设置text组件内容不显示
			text.setVisible(false);
			//获取子节点，并将border（格子）和text（文本）添加进去
			getChildren().addAll(border, text);

		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	public static void main(String[] args) {
		// 启动程序
		launch(args);
	}

}
