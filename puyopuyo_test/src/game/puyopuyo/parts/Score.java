package game.puyopuyo.parts;

import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class Score {

	/** 1Pフォント画像：基準座標x */
	private static final int IMG_FONT1_X = 1;
	/** 1Pフォント画像：基準座標y */
	private static final int IMG_FONT1_Y = 2;
	/** 1Pフォント画像：幅 */
	private static final int IMG_FONT1_W = 8;
	/** 1Pフォント画像：高さ */
	private static final int IMG_FONT1_H = 15;

	/** 2Pフォント画像：基準座標x */
	private static final int IMG_FONT2_X = 263;
	/** 2Pフォント画像：基準座標y */
	private static final int IMG_FONT2_Y = 2;
	/** 2Pフォント画像：幅 */
	private static final int IMG_FONT2_W = 8;
	/** 2Pフォント画像：高さ */
	private static final int IMG_FONT2_H = 15;

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

	/** プレイヤー位置 */
	private PlayerSide p;

	/**
	 * コンストラクタ
	 *
	 * @param x 表示位置x座標(ピクセル単位)
	 * @param y 表示位置y座標(ピクセル単位)
	 * @param p プレイヤー位置
	 */
	public Score(int x, int y, PlayerSide p) {
		this.x = x;
		this.y = y;
		this.p = p;
		init();
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 表示位置x座標(ピクセル単位)
	 * @param y 表示位置y座標(ピクセル単位)
	 */
	public Score(int x, int y) {
		this(x, y, PlayerSide.PLAYER1);
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		for(int i=0; i<scoreArray.length; i++) {
			if(p == PlayerSide.PLAYER1) {
				g.drawImage(ImageManager.fontImage,
						x + i * IMG_FONT1_W * 2,
						y,
						x + i * IMG_FONT1_W * 2 + IMG_FONT1_W * 2,
						y + IMG_FONT1_H * 2,
						IMG_FONT1_X + scoreArray[i] * IMG_FONT1_W,
						IMG_FONT1_Y,
						IMG_FONT1_X + scoreArray[i] * IMG_FONT1_W+ IMG_FONT1_W,
						IMG_FONT1_Y + IMG_FONT1_H,
						null);
			} else {
				g.drawImage(ImageManager.fontImage,
						x + i * IMG_FONT2_W * 2,
						y,
						x + i * IMG_FONT2_W * 2 + IMG_FONT2_W * 2,
						y + IMG_FONT2_H * 2,
						IMG_FONT2_X + scoreArray[i] * IMG_FONT2_W,
						IMG_FONT2_Y,
						IMG_FONT2_X + scoreArray[i] * IMG_FONT2_W+ IMG_FONT2_W,
						IMG_FONT2_Y + IMG_FONT2_H,
						null);
			}
		}
	}

	/**
	 * スコアを初期化
	 */
	public void init() {
		score = 0;
		addScore(0);
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
	 * スコアを加算
	 *
	 * @param score
	 */
	public void addScore(int score) {
		if(this.score + score > MAX) {
			this.score = MAX;
		} else {
			this.score += score;
		}

		char[] str = String.format("%07d", this.score).toCharArray();
		scoreArray = new int[str.length];
		for(int i=0; i<str.length; i++) {
			scoreArray[i] = Character.getNumericValue(str[i]);
		}
	}
}
