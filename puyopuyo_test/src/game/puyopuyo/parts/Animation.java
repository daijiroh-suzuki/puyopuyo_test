package game.puyopuyo.parts;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;

public class Animation extends BaseAnimation {

	/** 画像イメージ */
	private Image image;

	private Point current;
	private Point to;
	private int color;

	public Animation(Point from, Point to, int color) {

		// 画像ファイルをロード
		loadImage();

		this.current = grid2Pixel(from);
		this.to      = grid2Pixel(to);
		this.color   = color;
		this.running = true;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		if(current.y < to.y) {
			current.y = current.y + 8;
		} else {
			running = false;
		}
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	@Override
	public void draw(Graphics g) {

		g.drawImage(image,
				current.x,
				current.y,
				current.x + Field.TILE_SIZE,
				current.y + Field.TILE_SIZE,
				color * Field.TILE_SIZE,
				0,
				color * Field.TILE_SIZE + Field.TILE_SIZE,
				Field.TILE_SIZE,
				null);
	}

	/**
	 * To座標を取得
	 *
	 * @return
	 */
	public Point getTo() {
		return pixel2Grid(to);
	}

	/**
	 * マス目座標をピクセル座標に変換する
	 *
	 * @param grid マス目座標
	 * @return ピクセル座標
	 */
	private Point grid2Pixel(Point grid) {
		return new Point(grid2Pixel(grid.x), grid2Pixel(grid.y));
	}

	/**
	 * マス目座標をピクセル座標に変換する
	 *
	 * @param grid マス目座標
	 * @return ピクセル座標
	 */
	private int grid2Pixel(int grid) {
		return grid * Field.TILE_SIZE;
	}

	/**
	 * ピクセル座標をマス目座標に変換する
	 *
	 * @param pixel ピクセル座標
	 * @return マス目座標
	 */
	private int pixel2Grid(int pixel) {
		return pixel / Field.TILE_SIZE;
	}

	/**
	 * ピクセル座標をマス目座標に変換する
	 *
	 * @param pixel ピクセル座標
	 * @return マス目座標
	 */
	private Point pixel2Grid(Point pixel) {
		return new Point(pixel2Grid(pixel.x), pixel2Grid(pixel.y));
	}

	/**
	 * 画像ファイルをロードする
	 */
	private void loadImage() {
		// ぷよ画像をロード
		URL url = this.getClass().getClassLoader().getResource("images/puyo.gif");
		ImageIcon icon = new ImageIcon(url);
		image = icon.getImage();
	}
}
