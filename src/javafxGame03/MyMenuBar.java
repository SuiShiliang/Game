package javafxGame03;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

public class MyMenuBar{
	
	private GridPane[][] map;
	
	public MenuBar getMenuBar(Stage stage) {
		//定义菜单栏
		MenuBar bar = new MenuBar();
		//设置菜单栏高度
//		bar.setMaxHeight(50);
//		bar.setMaxWidth(200);
		//定义菜单
		Menu menuGame = new Menu("游戏");
		//设置菜单子菜单
		MenuItem itemNewGame = new MenuItem("新游戏");//开始新游戏
		MenuItem itemOption = new MenuItem("选择和设置难度");//设置，设置多少个类，多少个方格
		MenuItem itemChange = new MenuItem("更改外观");//设置游戏的外观和雷的形状
		MenuItem itemExit = new MenuItem("退出");//退出
		//添加子菜单到菜单中
		menuGame.getItems().addAll(itemNewGame, itemOption, itemChange, new SeparatorMenuItem(), itemExit);
		
		Menu menuScore = new Menu("分数");
		MenuItem itemScore = new MenuItem("显示分数");
		menuScore.getItems().addAll(itemScore);
		
		Menu menuHelp = new Menu("帮助");
		MenuItem itemHelp = new MenuItem("关于"); 
		menuHelp.getItems().addAll(itemHelp);
		
		itemNewGame.setOnAction(e -> newGame());
		itemOption.setOnAction(e -> optionGame());
		itemChange.setOnAction(e -> changeGame());
		//退出
		itemExit.setOnAction(e -> Platform.exit());
		itemScore.setOnAction(e -> displayScore());
		itemHelp.setOnAction(e -> gameHelp());

		bar.getMenus().addAll(menuGame,menuScore,menuHelp);
		
		return bar;
	}

	public void newGame() {
//		new GamePane().createGamePane(mainStage);
		new GamePane().newGame();
//		GamePane.createGamePane(mainStage);
		System.out.println("新游戏");
	}
	public void optionGame() {
		System.out.println("设置");
	}
	public void changeGame() {
		System.out.println("更改外观");
		for (int x = 0; x < map[0].length; x++) {
			for (int y = 0; y < map.length; y++) {
				System.out.print(map[y][x].getHasBombCount()+" ");
			}
			System.out.println();
		}
		
	}
	public void displayScore() {
		System.out.println("显示成绩");
	}
	public void gameHelp() {
		System.out.println("帮助");
	}

	public GridPane[][] getMap() {
		return map;
	}

	public void setMap(GridPane[][] map) {
		this.map = map;
	}

	
}
