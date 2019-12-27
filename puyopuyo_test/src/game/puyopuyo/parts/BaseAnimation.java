package game.puyopuyo.parts;

import java.awt.Graphics;

public abstract class BaseAnimation {

	/** 実行中フラグ */
	protected boolean running;

	/**
	 * 更新処理
	 */
	public abstract void update();

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public abstract void draw(Graphics g);

	/**
	 * @return running
	 */
	public boolean isRunning() {
		return running;
	}
}
