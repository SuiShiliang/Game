package javafxGame03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GridPane extends StackPane {

	private int x, y;
	private boolean isBomb;
	public boolean isOpen;
	private Rectangle rectangle;
	private Text text;
	private double borderSize;
	private ImageView view;
	private int hasBombCount = 0;

	public void showMineImage() {
		view.setVisible(true);
	}

	public void setMineImagePath(String mineImagePath) {
		Image mine;
		try {
			mine = new Image(new FileInputStream(mineImagePath));

			view = new ImageView();
			view.setImage(mine);
			view.setFitWidth(borderSize);
			view.setFitHeight(borderSize);
			view.setX(this.x * borderSize);
			view.setY(this.y * borderSize);
			// 初次加载不需要显示雷
			view.setVisible(false);
			getChildren().add(view);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public GridPane(int x, int y, double borderSize, String gridSpacingColor, String gridColor) {

		this.x = x;
		this.y = y;
		this.borderSize = borderSize;
		rectangle = new Rectangle(borderSize - 1, borderSize - 1);
		text = new Text("");
		// 设置字体的颜色
		text.setFill(Color.WHITE);
		// 设置字体不显示
		// text.setVisible(false);
		// 设置字体大小
		text.setFont(Font.font(20));
		// 设置格子的间隔色
		rectangle.setStroke(Color.web(gridSpacingColor));
		// 设置格子的背景颜色
		rectangle.setFill(Color.web(gridColor));
		getChildren().addAll(rectangle, text);
		setTranslateX(x * borderSize);
		setTranslateY(y * borderSize);

	}

	public void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public boolean isBomb() {
		return isBomb;
	}

	public int getHasBombCount() {
		return hasBombCount;
	}

	public void setHasBombCount(int hasBombCount) {
		this.hasBombCount = hasBombCount;
	}

}
