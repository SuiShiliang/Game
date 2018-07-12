package xml文件使用;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GamePane {


	private static void initGame() {

		/*
		 * xml帮助文档
		 * https://www.cnblogs.com/fnz0/p/5538459.html
		 * */
		try {
			//读取文件
			FileInputStream fis = new FileInputStream(
					new File("E:/JavaStudyFile/eclipse/Game/src/javafxGame03/setting.xml"));
			//定义xml读取类
			SAXReader reader = new SAXReader();
			//打开xml文件，使之创建实体
			Document doc = reader.read(fis);
			//获取xml文件中的根路径，即最外面的参数
			Element elem = doc.getRootElement();
			//加载游戏等级数据
			List<Element> gameLevelContent = elem.element("gameLevelContent").elements();
			//加载游戏自定义数据
			List<Element> userGameLevelContent = elem.element("userGameLevelContent").elements();
			//加载雷的样式数据
			List<Element> gameAppearanceOfMineContent = elem.element("gameAppearanceOfMineContent").elements();
			//加载格子的样式数据
			List<Element> gameAppearanceOfGirdContent = elem.element("gameAppearanceOfGirdContent").elements();
			//加载时钟的样式数据
			List<Element> gameTimeAppearance = elem.element("gameTimeAppearance").elements();
			//加载格子大小数据
			List<Element> gridSize = elem.element("gridSize").elements();
			//游戏加载初始化数据
			List<Element> gameLoad = elem.element("gameLoad").elements();
			
			Element gameLevelElement = gameLoad.get(0);
			Element mineElement = gameLoad.get(1);
			Element girdElement = gameLoad.get(2);
			Element sizeElement = gameLoad.get(3);
			
			System.out.println(gameLevelElement.getText());
			System.out.println(mineElement.getText());
			System.out.println(girdElement.getText());
			System.out.println(sizeElement.getText());
			System.out.println(gridSize.get(Integer.parseInt(girdElement.getText().toString())-1).getText());
			
			int borderSize = Integer.parseInt(gridSize.get(Integer.parseInt(girdElement.getText().toString())-1).getText().toString());
			System.out.println(borderSize);
		} catch (DocumentException e) {
			System.out.println("不能加载xml文件");
		} catch (FileNotFoundException e1) {
			System.out.println("没有找到xml文件哦");
		}

	}


}
