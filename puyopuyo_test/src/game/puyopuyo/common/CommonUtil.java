package game.puyopuyo.common;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.parts.Field;

public class CommonUtil {

	/**
	 * マス目座標をピクセル座標に変換する
	 *
	 * @param grid マス目座標
	 * @return ピクセル座標
	 */
	public static Point grid2Pixel(Point grid) {
		return new Point(grid2Pixel(grid.x), grid2Pixel(grid.y));
	}

	/**
	 * マス目座標をピクセル座標に変換する
	 *
	 * @param grid マス目座標
	 * @return ピクセル座標
	 */
	public static int grid2Pixel(int grid) {
		return grid * Field.TILE_SIZE;
	}

	/**
	 * ピクセル座標をマス目座標に変換する
	 *
	 * @param pixel ピクセル座標
	 * @return マス目座標
	 */
	public static Point pixel2Grid(Point pixel) {
		return new Point(pixel2Grid(pixel.x), pixel2Grid(pixel.y));
	}

	/**
	 * ピクセル座標をマス目座標に変換する
	 *
	 * @param pixel ピクセル座標
	 * @return マス目座標
	 */
	public static int pixel2Grid(int pixel) {
		return pixel / Field.TILE_SIZE;
	}

	/**
	 * 指定したフォントで文字列を描画する
	 *
	 * @param str 描画する文字列
	 * @param x x座標
	 * @param y y座標
	 * @param font 描画フォント
	 * @param g
	 */
	public static void drawString(String str, int x, int y, Font font, Graphics g) {
		// 変更前のフォントを取得
		Font tmp = g.getFont();
		// 描画フォントを設定
		g.setFont(font);
		// 文字列を描画
		g.drawString(str, x, y);
		// フォントを変更前に戻す
		g.setFont(tmp);
	}
}
