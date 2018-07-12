package javafxGame03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.json.JSONObject;

public class GamePane {

	private static double borderSize = 0;
	private static double borderPaneWidth = 0;
	private static double borderPaneHeight = 00;
	private static int gridWidthCount;
	private static int gridHeightCount;
	@SuppressWarnings("unused")
	private static int gameLevel = 1;
	// 雷数量
	private static int mineCount = 0;
	// 显示剩余雷的text组件
	private static Text mineCountText = new Text();
	// 游戏时间
	private static int payTime = 0;
	//显示时间的text组件
	private static Text tiemText = new Text(Integer.toString(payTime));
	// 设置文件保存路径
	// private static String settingFilePath = "";
	// 分数文件保存路径
	// private static String scoreFilePath = "";
	// 时间图像保存路径
	private static String timeImagePath = "";
	// 雷图片保存路径
	private static String mineImagePath = "";
	// 格子的颜色
	private static String gridColor = "";
	// 格子的间隔颜色
	private static String gridSpacingColor = "";
	// 地图
	private static GridPane map[][];
	//游戏是否开始标志
	private static boolean gameBeginFlag = false;
	//还有多少个格子没有打开
	private static int gridNotOpenCount = 0;
	//时间显示的线程
	private static Thread timeThread;
	
	
	//创建菜单栏
	private static MenuBar createMenuBar(Stage stage) {
		MenuBar bar = MyMenuBar.getMenuBar(stage);
		return bar;
	}
	//创建游戏主体页面
	private static BorderPane createGameBodyPane(Pane mianpane) {

		map = new GridPane[gridWidthCount][gridHeightCount];

		BorderPane borderPane = new BorderPane();
		borderPane.setLayoutX(borderSize * 2);
		borderPane.setLayoutY(borderSize * 2 + 30);

		//创建所有的格子，设置其点击事件，并放到map和游戏主体页面中
		for (int x = 0; x < gridWidthCount; x++) {
			for (int y = 0; y < gridHeightCount; y++) {
				GridPane gridPane = new GridPane(x, y, borderSize, gridSpacingColor, gridColor);
				gridPane.setBomb(false);
				map[x][y] = gridPane;
				//给格子设置点击事件，判断是左还是右
				gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						MouseButton button = event.getButton();
						if(button == MouseButton.PRIMARY) {
							open(gridPane);//翻开这个格子
							//System.out.println("点击了鼠标左键");
						}
						/*
						 * if(button == MouseButton.MIDDLE) { // System.out.println("点击鼠标中间"); }
						 */
						if(button == MouseButton.SECONDARY) {
							markMine(gridPane);//标记这个格子
							//System.out.println("点击鼠标右键");
						}
					}
				});
				borderPane.getChildren().addAll(gridPane);
			}
		}
		//设置该游戏等级应有的雷
		for (int i = 0; i < mineCount; ) {
			int x = (int) (Math.random() * gridWidthCount);
			int y = (int) (Math.random() * gridHeightCount);
			if(map[x][y].isBomb())continue;
			map[x][y].setBomb(true);
			map[x][y].setMineImagePath(mineImagePath);
			i++;
		}
		//设置格子附近的雷的数量
		for (int x = 0; x < gridWidthCount; x++) {
			for (int y = 0; y < gridHeightCount; y++) {

				GridPane gridPane = map[x][y];
				//如果当前格子是雷，则直接跳过
				if(gridPane.isBomb())
					continue;
				//stream().filter 为Java8及以上版本所有的一种循环函数，形似foreach
				int hasBombCount = (int) getNeighbors(gridPane).stream().filter(e -> e.isBomb()).count();
				/*//上面写法的的for循环写法
				int hasBombCount = 0;
				List<GridPane> list =getNeighbors(gridPane);
				for(int i=0;i<list.size();i++) {
					if(list.get(i).isBomb())hasBombCount++;
				}*/
				gridPane.setHasBombCount(hasBombCount);
			}
		}

		mianpane.getChildren().addAll(borderPane);
		mianpane.setStyle("-fx-background-color: black ;-fx-border-width: 1px ;");
		return borderPane;
	}
	//获取当前格子周围的格子
	public static List<GridPane> getNeighbors(GridPane gridPane){
		List<GridPane> list = new ArrayList<>();
		/* 当前格子周围的坐标
		 * (-1,-1) (-1, 0) (-1, 1)
		 * (0 ,-1)  所在位置     ( 0, 1)
		 * (1 ,-1) ( 1, 0) ( 1, 1)
		 * */
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int xx = gridPane.getX() + i;
				int yy = gridPane.getY() + j;
				/*如果是自己所在位置，则直接跳过*/
				if((i == j) && (i == 0))continue;
				if(xx >= 0 && xx < gridWidthCount && yy >= 0 && yy < gridHeightCount) {
					list.add(map[xx][yy]);
				}
			}
		}
		
		return list;
	}
	

	//点中雷之后，显示所有的雷
	@SuppressWarnings("deprecation")
	private static void gameOver() {
		//游戏结束时使标志重新为FALSE
		gameBeginFlag = false;
		//游戏结束将线程种植
		timeThread.stop();
		for (int x = 0; x < gridWidthCount; x++) {
			for (int y = 0; y < gridHeightCount; y++) {
				if (map[x][y].isBomb()) {
					map[x][y].showMineImage();
				}
				if(map[x][y].getHasBombCount()!=0) {
					map[x][y].getText().setText(Integer.toString(map[x][y].getHasBombCount()));
				}
				else {
					map[x][y].getText().setText("");
				}
				map[x][y].getText().setVisible(true);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void gameWinner() {
		//游戏胜利将线程结束
		timeThread.stop();
		System.out.println("你赢了");
	}
	
	//标记雷
	private static void markMine(GridPane gridPane) {
		String mark = gridPane.getText().getText().toString();
		if(mark.equals("")) {
			gridPane.getText().setText("X");
			mineCount--;
			mineCountText.setText(Integer.toString(mineCount));
			//标记为雷则设置让未打开的数量减1
			gridNotOpenCount--;
			
			//如果没有剩下的雷，则胜利
			if(mineCount == 0 && gridNotOpenCount == 0) gameWinner();
		}
		else if(mark.equals("X")) {
			gridPane.getText().setText("?");
			mineCount++;
			mineCountText.setText(Integer.toString(mineCount));
			//取消标记，则让未打开的数量加1
			gridNotOpenCount++;
		}
		else if(mark.equals("?")) {
			gridPane.getText().setText("");
		}
		
	}
	
	public static void timeBegin() {
		System.out.println("调用了线程");
		
		timeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(gameBeginFlag) {
					try {
						Thread.sleep(1000);
						payTime += 1;
						tiemText.setText(Integer.toString(payTime));
					} catch (InterruptedException e) {
						System.out.println("线程出现问题了");
					}
				}
			}
		});
		timeThread.start();
		
	}
	
	//翻开点击的格子
	private static void open(GridPane gridPane) {
		
		if(gameBeginFlag == false) {
			gameBeginFlag = true;
			timeBegin();
		}
		
		//已经打开则不需要再次打开
		if(gridPane.isOpen)
			return;
		//如果已经标记了则直接跳过
		if (gridPane.getText().getText().toString().equals("?") 
				|| gridPane.getText().getText().toString().equals("X"))
			return;
		
		//点击雷之后，直接显示所有的雷，并提示游戏结束
		if(gridPane.isBomb()) {
			gameOver();
			return;
		}
		//只要打开了一个就让未打开的数量减1
		gridNotOpenCount --;
		
		gridPane.setOpen(true);
		
		if(mineCount ==0 && gridNotOpenCount == 0)gameWinner();
		//只有当前的格子的周围雷的数量不为0 的时候，才设置text显示雷的数量
		if(gridPane.getHasBombCount() != 0)
			gridPane.getText().setText(Integer.toString(gridPane.getHasBombCount()));
		gridPane.getText().setVisible(true);
		//设置雷数量的不同，颜色的深浅
		setTextFontColor(gridPane, gridPane.getHasBombCount());
		
		gridPane.getRectangle().setFill(Color.WHITE);
		//如果当前格子周围雷的数量为0，则获取其周围所有格子，并打开
		if(gridPane.getHasBombCount() == 0) {
			//获取周围格子，并打开，注意此函数由于调用了自身，所有是一个递归函数
			getNeighbors(gridPane).forEach(e -> open(e));
		}
		
	}
	
	private static void setTextFontColor(GridPane gridPane, int hasBombCount) {

		switch (hasBombCount) {
		case 1:gridPane.getText().setFill(Color.web("#00bcd4"));	break;

		case 2:gridPane.getText().setFill(Color.web("#009688"));	break;
		case 3:gridPane.getText().setFill(Color.web("#8bc34a")); break;
		case 4:gridPane.getText().setFill(Color.web("#ffeb3b")); break;
		case 5:gridPane.getText().setFill(Color.web("#ff9800")); break;
		case 6:gridPane.getText().setFill(Color.web("#ff5722")); break;
		case 7:gridPane.getText().setFill(Color.web("#795548")); break;
		case 8:gridPane.getText().setFill(Color.web("#673ab7")); break;
		}
	}
	//创建成绩显示项
	private static BorderPane createScoreDisplayPane() {
		BorderPane borderPane = new BorderPane();
		borderPane.setLayoutX(borderSize * 2);
		// 38为菜单栏的高度，需要添加进去
		borderPane.setLayoutY(borderSize * (2 + gridHeightCount) + 38);
		borderPane.setStyle("-fx-background-color: white ;");

		StackPane leftStackPane = new StackPane();

		try {
			// 时钟的图片
			Image timeImage = new Image(new FileInputStream(timeImagePath));
			ImageView timeImageView = new ImageView();
			timeImageView.setImage(timeImage);
			timeImageView.setFitWidth(borderSize * 1.5);
			timeImageView.setFitHeight(borderSize * 1.5);
			timeImageView.setTranslateX(10);
			timeImageView.setTranslateY(5);
			// 添加进stackpane中
			leftStackPane.getChildren().add(timeImageView);
			// 设置rectangle的宽度和高度
			Rectangle rectangle = new Rectangle(borderSize * 2, borderSize);
			// 设置填充颜色
			rectangle.setFill(Color.LIGHTGRAY);
			// 设置坐标
			rectangle.setTranslateX(borderSize * 2 + 10);
			rectangle.setTranslateY(10);
			tiemText.setFill(Color.web("#9c27b0"));
			tiemText.setFont(Font.font(18));
			// 设置坐标
			tiemText.setTranslateX(10 + borderSize * 2);
			tiemText.setTranslateY(9);
			leftStackPane.getChildren().addAll(rectangle, tiemText);

		} catch (FileNotFoundException e1) {
			System.out.println("时钟图片没有找到");
			e1.printStackTrace();
		}

		StackPane rightStackPane = new StackPane();
		try {
			// 雷的图片图片
			Image mineIamge = new Image(new FileInputStream(mineImagePath));
			ImageView mineIamgeView = new ImageView();
			mineIamgeView.setImage(mineIamge);
			mineIamgeView.setFitWidth(borderSize * 1.5);
			mineIamgeView.setFitHeight(borderSize * 1.5);
			mineIamgeView.setTranslateX((gridWidthCount - 1.5) * borderSize);
			mineIamgeView.setTranslateY(5);
			leftStackPane.getChildren().add(mineIamgeView);
			Rectangle rectangle = new Rectangle(borderSize * 2, borderSize);
			// 设置填充颜色
			rectangle.setFill(Color.LIGHTGRAY);
			// 设置坐标
			rectangle.setTranslateX((gridWidthCount - 3.5) * borderSize);
			rectangle.setTranslateY(10);
			mineCountText.setText(Integer.toString(mineCount));
			mineCountText.setFill(Color.web("#9c27b0"));
			mineCountText.setFont(Font.font(18));
			// 设置坐标
			mineCountText.setTranslateX((gridWidthCount - 3.5) * borderSize);
			mineCountText.setTranslateY(9);
			leftStackPane.getChildren().addAll(rectangle, mineCountText);

		} catch (FileNotFoundException e) {
			System.out.println("雷的图片没有找到");
			e.printStackTrace();
		}

		borderPane.getChildren().addAll(leftStackPane, rightStackPane);

		return borderPane;
	}
	//加载游戏数据
	private static void initGame() {
		// 最外面的JSON包装
		JSONObject setting = GameSettingFile.initGameSetting();
		JSONObject gameloadJson = setting.getJSONObject("gameLoad");
		JSONObject gamelevleJson = new JSONObject();
		// 判断是加载用户自定义还是加载默认等级
		if (gameloadJson.getInt("gameLevel") != 0) {
			gamelevleJson = setting.getJSONArray("gameLevelArray").getJSONObject(gameloadJson.getInt("gameLevel") - 1);
		} else {
			gamelevleJson = setting.getJSONObject("userGameLevelContent");
		}
		gameLevel = gamelevleJson.getInt("level");
		gridWidthCount = gamelevleJson.getInt("widthCount");
		gridHeightCount = gamelevleJson.getInt("heightCount");
		mineCount = gamelevleJson.getInt("mineCount");
		// 获取雷样式的Json对象
		JSONObject gameMineAp = setting.getJSONArray("gameAppearanceOfMineContent")
				.getJSONObject(gameloadJson.getInt("mine") - 1);
		mineImagePath = gameMineAp.getString("mine");
		// 获取格子样式的Json对象
		JSONObject gameGridAp = setting.getJSONArray("gameAppearanceOfGirdContent")
				.getJSONObject(gameloadJson.getInt("gird") - 1);
		gridColor = gameGridAp.getString("grid");
		// 获取格子的间隔颜色
		JSONObject gameGridSpacingColor = setting.getJSONArray("gameAppearanceOfGirdSpacing")
				.getJSONObject(gameloadJson.getInt("gridSpacing") - 1);
		gridSpacingColor = gameGridSpacingColor.getString("spacing");
		// 获取时间样式的Json对象
		JSONObject gameTimeAp = setting.getJSONArray("gameTimeAppearance")
				.getJSONObject(gameloadJson.getInt("time") - 1);
		timeImagePath = gameTimeAp.getString("time");
		/*
		 * //获取文件保存的路径 String gameFilePath = gameloadJson.getString("FilePath");
		 * //获取图片保存路径 String gameImageFilePath =
		 * gameloadJson.getString("imageFilePath");
		 */
		// 获取格子的大小
		borderSize = setting.getJSONObject("gridSize").getInt("gridSize");
		
		//设置未翻开格子的总数
		gridNotOpenCount = gridWidthCount * gridHeightCount;
		
		borderPaneWidth = (gridWidthCount + 3) * borderSize;
		borderPaneHeight = (gridHeightCount + 3) * borderSize + 35;

	}
	//创建游戏页面
	public static void createGamePane(Stage stage) {
		// 初始化游戏数据
		initGame();
		//初始化时设置游戏开始标志为FALSE，即未开始
		gameBeginFlag = false;
		Pane mainPane = new Pane();
		// 设置菜单栏
		MenuBar bar = createMenuBar(stage);
		// 使用setMaxWidth 和 setPrefWidth设置菜单栏宽度为最大
		bar.setMaxWidth(borderPaneWidth);
		bar.setPrefWidth(Double.MAX_VALUE);
		mainPane.getChildren().add(bar);
		// 设置游戏主体
		createGameBodyPane(mainPane);
		// 设置时间显示和雷的显示
		mainPane.getChildren().add(createScoreDisplayPane());

		Scene scene = new Scene(mainPane, borderPaneWidth, borderPaneHeight);
		// 设置stage的宽度和高度，以免新创建pane时产生收缩或扩张的情况
		stage.setWidth(borderPaneWidth);
		stage.setHeight(borderPaneHeight + 38);// 38为标题栏的高度
		stage.setScene(scene);

	}

}
