package game.puyopuyo.screen;

import java.awt.Graphics;

public abstract class BaseScreen {

	/**
	 * 画面の状態を更新する
	 */
	public abstract void update();

	/**
	 * 画面を描画する
	 *
	 * @param g
	 */
	public abstract void draw(Graphics g);
}
