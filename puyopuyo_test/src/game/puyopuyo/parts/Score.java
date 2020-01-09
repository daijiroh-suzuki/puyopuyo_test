package game.puyopuyo.parts;

import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class Score {

	/** フォント画像：基準座標x */
	private static final int IMG_FONT_X = 1;
	/** フォント画像：基準座標y */
	private static final int IMG_FONT_Y = 2;
	/** フォント画像：幅 */
	private static final int IMG_FONT_W = 8;
	/** フォント画像：高さ */
	private static final int IMG_FONT_H = 15;

	/** 表示できるスコアの最大値 */
	private int MAX = 9999999;

	/** 表示位置x座標 */
	private int x;
	/** 表示位置y座標 */
	private int y;

	/** スコア */
	private int score;
	/** スコア描画用配列 */
	private int[] scoreArray;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 */
	public Score(int x, int y) {
		this.x = x;
		this.y = y;
		init();
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		for(int i=0; i<scoreArray.length; i++) {
			g.drawImage(ImageManager.fontImage,
					x + i * IMG_FONT_W * 2,
					y,
					x + i * IMG_FONT_W * 2 + IMG_FONT_W * 2,
					y + IMG_FONT_H * 2,
					IMG_FONT_X + scoreArray[i] * IMG_FONT_W,
					IMG_FONT_Y,
					IMG_FONT_X + scoreArray[i] * IMG_FONT_W+ IMG_FONT_W,
					IMG_FONT_Y + IMG_FONT_H,
					null);
		}
	}

	/**
	 * スコアを初期化
	 */
	public void init() {
		score = 0;
		setScore(0);
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
		if(score > MAX) {
			this.score = MAX;
		} else {
			this.score = score;
		}

		char[] str = String.format("%07d", score).toCharArray();
		scoreArray = new int[str.length];
		for(int i=0; i<str.length; i++) {
			scoreArray[i] = Character.getNumericValue(str[i]);
		}
	}
}
