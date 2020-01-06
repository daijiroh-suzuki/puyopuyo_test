package game.puyopuyo.parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.puyopuyo.common.CommonUtil;

public class Score {

	/** 表示位置x座標 */
	private int x;
	/** 表示位置y座標 */
	private int y;

	/** スコア */
	private int score;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 */
	public Score(int x, int y) {
		this.x = x;
		this.y = y;
		score = 0;
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		CommonUtil.drawString("SCORE : " + score,
				x,
				y,
				new Font("Meiryo UI", Font.BOLD, 14),
				g);
	}

	/**
	 * スコアを取得
	 *
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * スコアを設定
	 *
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
