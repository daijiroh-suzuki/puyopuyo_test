package game.puyopuyo.animation;

import java.awt.Graphics;

public abstract class BaseAnimation {

	/** 実行中フラグ */
	protected boolean running = true;

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

	/**
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
