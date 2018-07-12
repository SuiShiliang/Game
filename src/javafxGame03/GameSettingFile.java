package javafxGame03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GameSettingFile {
	
	//图片保存地址目录
	private static String imagePath = "C:/Program Files/myJavaFXGame/image/";
	//设置文件保存地址目录
	private static String filePath = "C:/Program Files/myJavaFXGame/file/";

	private static JSONArray gameLevleSetting() {
		JSONArray array = new JSONArray();
		for(int i=0;i<3;i++) {
			JSONObject gameLevelJ = new JSONObject();
			gameLevelJ.put("level", i+1);
			int width =9;
			int height =9;
			int mincount = 10;
			if(i==0) {
				width = 9;
				height =9;
				mincount = 10;
			}
			if(i==1) {
				width = 16;
				height =16;
				mincount = 40;
			}
			if(i==2) {
				width = 30;
				height =16;
				mincount = 99;
			}
			gameLevelJ.put("widthCount", width);
			gameLevelJ.put("heightCount", height);
			gameLevelJ.put("mineCount", mincount);
			array.add(gameLevelJ);
		}
		return array;
	}

	private static JSONObject gameLevleSettingByUser() {
		JSONObject object = new JSONObject();
		object.put("widthCount", 10);
		object.put("heightCount", 10);
		object.put("mineCount", 10);
		return object;
	}
	
	private static JSONArray gameAppearanceOfMineContent() {
		JSONArray object = new JSONArray();
		JSONObject mine = new JSONObject();
		//雷外形的相对地址
		mine.put("mine", imagePath+"lei.png");
		object.add(mine);
		return object;
	}
	
	private static JSONArray gameAppearanceOfGirdContent() {
		JSONArray object = new JSONArray();
		JSONObject grid = new JSONObject();
		//格子的样式
		grid.put("grid", "#03a9f4");
		object.add(grid);
		grid.put("grid", "#ff9800");
		object.add(grid);
		return object;
	}
	private static JSONArray gameAppearanceOfGirdSpacing() {
		JSONArray array = new JSONArray();
		JSONObject gridSpacing = new JSONObject();
		gridSpacing.put("spacing", "#D3D3D3");
		array.add(gridSpacing);
		gridSpacing.put("spacing", "#000000");
		array.add(gridSpacing);
		gridSpacing.put("spacing", "#ffeb3b");
		array.add(gridSpacing);
		return array;
	}
	private static JSONArray gameTimeAppearance() {
		JSONArray object = new JSONArray();
		JSONObject timeImage = new JSONObject();
		timeImage.put("time", imagePath+"time5.png");
		object.add(timeImage);
		return object;
	}
	private static JSONObject gridSize() {
		JSONObject grid = new JSONObject();
		grid.put("gridSize", 30);
		return grid;
	}
	private static JSONObject gameLoad() {
		JSONObject object = new JSONObject();
		//初始化游戏时设置游戏等级为1，加载时如果等级为0，则加载用户自定义
		object.put("gameLevel", 1);
		//初始化游戏设置雷的外形为第一个
		object.put("mine", 1);
		//初始化游戏设置格子的外形为第一个
		object.put("gird", 2);
		//格子的间隔颜色为第一个
		object.put("gridSpacing", 3);
		//初始化游戏设置时钟的外形为第一个
		object.put("time", 1);
		//文件保存路径
		object.put("FilePath", filePath);
		//图片保存路径
		object.put("imageFilePath", imagePath);
		return object;
	}
	//第一次使用时增加游戏设置文件
	private static JSONObject createSettingFile() {
		//最外面的JSON包装
		JSONObject setting = new JSONObject();
		//游戏等级数据
		JSONArray gameLevelArray = gameLevleSetting();
		//游戏自定义数据
		JSONObject userGameLevelContent = gameLevleSettingByUser();
		//雷的外形
		JSONArray gameAppearanceOfMineContent = gameAppearanceOfMineContent();
		//格子的外形
		JSONArray gameAppearanceOfGirdContent = gameAppearanceOfGirdContent();
		//格子的间隔颜色
		JSONArray gameAppearanceOfGirdSpacing = gameAppearanceOfGirdSpacing();
		//时钟的外形
		JSONArray gameTimeAppearance = gameTimeAppearance();
		//格子的大小
		JSONObject gridSize = gridSize();
		//游戏加载数据
		JSONObject gameLoad = gameLoad();
		setting.put("gameLevelArray", gameLevelArray);
		setting.put("userGameLevelContent", userGameLevelContent);
		setting.put("gameAppearanceOfMineContent", gameAppearanceOfMineContent);
		setting.put("gameAppearanceOfGirdContent", gameAppearanceOfGirdContent);
		setting.put("gameAppearanceOfGirdSpacing", gameAppearanceOfGirdSpacing);
		setting.put("gameTimeAppearance", gameTimeAppearance);
		setting.put("gridSize", gridSize);
		setting.put("gameLoad", gameLoad);
		return setting;
	}
	
	private static JSONObject createScoreFile() {
		JSONObject score = new JSONObject();
		//初始数据都为0
		JSONObject object = new JSONObject();
		object.put("time", 0);
		object.put("minecount", 0);
		object.put("findminecount", 0);
		JSONArray levelarray1 = new JSONArray();
		levelarray1.add(object);
		JSONArray levelarray2 = new JSONArray();
		levelarray2.add(object);
		JSONArray levelarray3 = new JSONArray();
		levelarray3.add(object);
		JSONArray levelarrayuser = new JSONArray();
		levelarrayuser.add(object);
		score.put("level1", levelarray1);
		score.put("level2", levelarray2);
		score.put("level3", levelarray3);
		//用0表示是用户自定义的
		score.put("level0", levelarrayuser);
		return score;
	}
	@SuppressWarnings("resource")
	public static JSONObject initGameSetting() {
		JSONObject setting = new JSONObject();
		File settingFile = new File(filePath+"setting.game");
		if(settingFile.exists()) {
			//设置文件存在
			try {
				Long fileLenght = settingFile.length();
				byte[] allData = new byte[fileLenght.intValue()];
				@SuppressWarnings("resource")
				FileInputStream settingFileStream = new FileInputStream(settingFile);
				settingFileStream.read(allData);
				//byte数组转换成字符串
				String settingString = new String(allData);
				setting = JSONObject.fromObject(settingString);
				
			} catch (FileNotFoundException e) {
				System.out.println("设置文件无法打开");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("文件读取错误");
				e.printStackTrace();
			}
		}
		else {
			
			File scoreFile = new File(filePath+"score.game");
			//设置文件不存在
			/*创建文件保存文件夹*/
			new File(filePath).mkdirs();
			new File(imagePath).mkdirs();
			try {
				//创建设置文件
				settingFile.createNewFile();
				//将字符串转换成byte数组，并写入到文件中
				new FileOutputStream(settingFile).write(createSettingFile().toString().getBytes());
				//创建分数文件
				scoreFile.createNewFile();
				new FileOutputStream(scoreFile).write(createScoreFile().toString().getBytes());
				//将图片导出到文件夹中
				File[] listfile = new File("src/javafxGame01/resource").listFiles();
				for(int i=0;i<listfile.length;i++) {
					//输出文件全名
					//System.out.println(listfile[i].getPath());
					@SuppressWarnings("resource")
					FileChannel inputChannel = new FileInputStream(listfile[i]).getChannel();
					@SuppressWarnings("resource")
					FileChannel outChannel = new FileOutputStream(new File(imagePath+listfile[i].getName())).getChannel();
					//0代表从第一个字符开始，至输入的文件末尾inputChannel.size()
					outChannel.transferFrom(inputChannel, 0, inputChannel.size());
					//如果文件不存在，则设置setting的数据，
					setting = createSettingFile();
				}
				
			} catch (IOException e) {
				System.out.println("文件创建失败");
				e.printStackTrace();
			}
			
		}
		
		//最外面的JSON包装
		return setting;
	}
}
