package javafxGame01;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MyMenuBar{
	
	public static MenuBar getMenuBar() {
		//定义菜单栏
		MenuBar bar = new MenuBar();
		//设置菜单栏高度
		bar.setMaxHeight(30);
		//定义菜单
		Menu menuGame = new Menu("游戏");
		//设置菜单子菜单
		MenuItem itemNewGame = new MenuItem("新游戏");//开始新游戏
		MenuItem itemOption = new MenuItem("选择和设置难度");//设置，设置多少个类，多少个方格
		MenuItem itemChange = new MenuItem("更改外观");//设置游戏的外观和雷的形状
		MenuItem itemExit = new MenuItem("退出");//退出
		//添加子菜单到菜单中
		menuGame.getItems().addAll(itemNewGame, itemOption, itemChange, new SeparatorMenuItem(), itemExit);
		
		itemNewGame.setOnAction(e -> newGameAction());
		
		Menu menuScore = new Menu("分数");
		MenuItem itemScore = new MenuItem("显示分数");
		menuScore.getItems().addAll(itemScore);
		
		Menu menuHelp = new Menu("帮助");
		MenuItem itemHelp = new MenuItem("关于"); 
		menuHelp.getItems().addAll(itemHelp);

		bar.getMenus().addAll(menuGame,menuScore,menuHelp);
		
		return bar;
	}

	public static void newGameAction() {
	}
}
