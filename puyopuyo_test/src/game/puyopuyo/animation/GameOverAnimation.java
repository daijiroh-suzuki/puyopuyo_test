package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class GameOverAnimation extends BaseAnimation {

	/** 描画位置x座標 */
	private int x;
	/** 描画位置y座標 */
	private int y;

	private int width;
	private int height;

	/** フレームカウント */
	private int frameCount;
	/** 描画色 */
	private Color clr;

	public GameOverAnimation(Point pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.frameCount = 0;
		this.running = true;

		clr = new Color(0, 0, 0, 10);
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 30) {
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(clr);
		g.fillRect(x, y, width, height);
	}
}
