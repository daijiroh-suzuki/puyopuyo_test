package game.puyopuyo.parts;

import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class Stage {

	/** ステージ背景画像：基準座標x */
	private static final int IMG_BG_X = 0;
	/** ステージ背景画像：基準座標y */
	private static final int IMG_BG_Y = 0;
	/** ステージ背景画像：幅 */
	private static final int IMG_BG_W = 320;
	/** ステージ背景画像：高さ */
	private static final int IMG_BG_H = 224;

	/** ステージ前景画像：基準座標x */
	private static final int IMG_FG_X = 320;
	/** ステージ前景画像：基準座標y */
	private static final int IMG_FG_Y = 0;
	/** ステージ前景画像：幅 */
	private static final int IMG_FG_W = 320;
	/** ステージ前景画像：高さ */
	private static final int IMG_FG_H = 224;

	/** 数字画像：基準座標x */
	private static final int IMG_NUM_X = 1;
	/** 数字画像：基準座標y */
	private static final int IMG_NUM_Y = 71;
	/** 数字画像：幅 */
	private static final int IMG_NUM_W = 8;
	/** 数字画像：高さ */
	private static final int IMG_NUM_H = 9;

	/** アルファベット画像：基準座標x */
	private static final int IMG_ALP_X = 1;
	/** アルファベット画像：基準座標y */
	private static final int IMG_ALP_Y = 87;
	/** アルファベット画像：幅 */
	private static final int IMG_ALP_W = 8;
	/** アルファベット画像：高さ */
	private static final int IMG_ALP_H = 9;

	/**
	 * コンストラクタ
	 */
	public Stage() {}

	/**
	 * 背景を描画
	 *
	 * @param g
	 */
	public void drawBg(Graphics g) {

		g.drawImage(ImageManager.stageImage,
				0,
				32,
				0  + IMG_BG_W * 2,
				32 + IMG_BG_H * 2,
				IMG_BG_X,
				IMG_BG_Y,
				IMG_BG_X + IMG_BG_W,
				IMG_BG_Y + IMG_BG_H,
				null);
	}

	/**
	 * 前景を描画
	 *
	 * @param g
	 */
	public void drawFg(Graphics g) {

		g.drawImage(ImageManager.fontImage,
				32 * 8 - 8,
				32 * 3,
				32 * 8 - 8 + IMG_NUM_W * 3,
				32 * 3 + IMG_NUM_H * 3,
				IMG_NUM_X + IMG_NUM_W * 1,
				IMG_NUM_Y,
				IMG_NUM_X + IMG_NUM_W * 1 + IMG_NUM_W,
				IMG_NUM_Y + IMG_NUM_H,
				null);
		g.drawImage(ImageManager.fontImage,
				32 * 8 - 8 + IMG_NUM_W * 3,
				32 * 3,
				32 * 8 - 8 + IMG_NUM_W * 3 + IMG_ALP_W * 3,
				32 * 3 + IMG_ALP_H * 3,
				IMG_ALP_X + IMG_ALP_W * 15,
				IMG_ALP_Y,
				IMG_ALP_X + IMG_ALP_W * 15 + IMG_ALP_W,
				IMG_ALP_Y + IMG_ALP_H,
				null);

		g.drawImage(ImageManager.stageImage,
				0,
				32,
				0  + IMG_FG_W * 2,
				32 + IMG_FG_H * 2,
				IMG_FG_X,
				IMG_FG_Y,
				IMG_FG_X + IMG_FG_W,
				IMG_FG_Y + IMG_FG_H,
				null);
	}
}
