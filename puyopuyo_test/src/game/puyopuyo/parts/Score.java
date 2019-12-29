package game.puyopuyo.parts;

import java.awt.Color;
import java.awt.Graphics;

public class Score {

	/** 表示位置x座標 */
	private int x;
	/** 表示位置y座標 */
	private int y;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 */
	public Score(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("SCORE", x, y);
	}
}
