package game.puyopuyo.parts;

import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class LevelSelect {

	/** SELECT画像：基準座標x */
	private static final int IMG_SELECT_X = 201;
	/** SELECT画像：基準座標y */
	private static final int IMG_SELECT_Y = 4;
	/** SELECT画像：幅 */
	private static final int IMG_SELECT_W = 48;
	/** SELECT画像：高さ */
	private static final int IMG_SELECT_H = 15;

	/** LEVEL画像：基準座標x */
	private static final int IMG_LEVEL_X = 201;
	/** LEVEL画像：基準座標y */
	private static final int IMG_LEVEL_Y = 21;
	/** LEVEL画像：幅 */
	private static final int IMG_LEVEL_W = 40;
	/** LEVEL画像：高さ */
	private static final int IMG_LEVEL_H = 15;

	/** 枠画像：基準座標x */
	private static final int IMG_FRAME_X = 6;
	/** 枠画像：基準座標y */
	private static final int IMG_FRAME_Y = 4;
	/** 枠画像：幅 */
	private static final int IMG_FRAME_W = 70;
	/** 枠画像：高さ */
	private static final int IMG_FRAME_H = 134;

	/** 数字画像：基準座標x */
	private static final int IMG_NUM_X = 1;
	/** 数字画像：基準座標y */
	private static final int IMG_NUM_Y = 71;
	/** 数字画像：幅 */
	private static final int IMG_NUM_W = 8;
	/** 数字画像：高さ */
	private static final int IMG_NUM_H = 9;

	/** カレー画像：基準座標x */
	private static final int IMG_CURRY_X = 78;
	/** カレー画像：基準座標y */
	private static final int IMG_CURRY_Y = 2;
	/** カレー画像：幅 */
	private static final int IMG_CURRY_W = 40;
	/** カレー画像：高さ */
	private static final int IMG_CURRY_H = 17;

	/** 難易度画像：基準座標x */
	private static final int IMG_LEVEL_STR_X = 121;
	/** 難易度画像：基準座標y */
	private static final int IMG_LEVEL_STR_Y = 2;
	/** 難易度画像：幅 */
	private static final int IMG_LEVEL_STR_W = 36;
	/** 難易度画像：高さ */
	private static final int IMG_LEVEL_STR_H = 17;

	/** 難易度 */
	private static final int[] LEVEL = {3, 3, 4, 4, 5};

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;
	/** 選択中の難易度インデックス */
	private int selectIdx;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public LevelSelect(int x, int y) {
		this.x = x;
		this.y = y;

		selectIdx = 2;
	}

	/**
	 * 選択難易度変更
	 */
	public void keyUp() {
		selectIdx =
				(selectIdx - 1) >= 0 ? selectIdx - 1 : LEVEL.length - 1;
	}

	/**
	 * 選択難易度変更
	 */
	public void keyDown() {
		selectIdx =
				(selectIdx + 1) <= LEVEL.length - 1 ? selectIdx + 1 : 0;
	}

	/**
	 * 選択難易度取得
	 *
	 * @return
	 */
	public int getLevel() {
		return LEVEL[selectIdx];
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 「SELECT LEVEL」を描画
		g.drawImage(ImageManager.levelImage,
				x + 32,
				y + 80,
				x + 32 + IMG_SELECT_W * 2,
				y + 80 + IMG_SELECT_H * 2,
				IMG_SELECT_X,
				IMG_SELECT_Y,
				IMG_SELECT_X + IMG_SELECT_W,
				IMG_SELECT_Y + IMG_SELECT_H,
				null);
		g.drawImage(ImageManager.levelImage,
				x + 144,
				y + 80,
				x + 144 + IMG_LEVEL_W * 2,
				y + 80  + IMG_LEVEL_H * 2,
				IMG_LEVEL_X,
				IMG_LEVEL_Y,
				IMG_LEVEL_X + IMG_LEVEL_W,
				IMG_LEVEL_Y + IMG_LEVEL_H,
				null);

		// 枠線を描画
		g.drawImage(ImageManager.levelImage,
				x + 40,
				y + 128,
				x + 40  + IMG_FRAME_W * 2,
				y + 128 + IMG_FRAME_H * 2,
				IMG_FRAME_X,
				IMG_FRAME_Y,
				IMG_FRAME_X + IMG_FRAME_W,
				IMG_FRAME_Y + IMG_FRAME_H,
				null);

		int ty = 150;
		for(int i=0; i<LEVEL.length; i++) {
			// 数字を描画
			g.drawImage(ImageManager.fontImage,
					x + 96,
					y + ty + i * IMG_NUM_H * 4,
					x + 96 + IMG_NUM_W * 4,
					y + ty + i * IMG_NUM_H * 4 + IMG_NUM_H * 4,
					IMG_NUM_X + (i + 1) * IMG_NUM_W,
					IMG_NUM_Y,
					IMG_NUM_X + (i + 1) * IMG_NUM_W + IMG_NUM_W,
					IMG_NUM_Y + IMG_NUM_H,
					null);

			// 選択中の難易度の場合
			if(selectIdx == i) {
				// カレーを描画
				g.drawImage(ImageManager.levelImage,
						x + 138,
						y + ty + i * IMG_NUM_H * 4,
						x + 138 + IMG_CURRY_W * 2,
						y + ty + i * IMG_NUM_H * 4 + IMG_CURRY_H * 2,
						IMG_CURRY_X,
						IMG_CURRY_Y + i * IMG_CURRY_H,
						IMG_CURRY_X + IMG_CURRY_W,
						IMG_CURRY_Y + i * IMG_CURRY_H + IMG_CURRY_H,
						null);

				// 難易度を描画
				g.drawImage(ImageManager.levelImage,
						x + 138,
						y + ty + i * IMG_NUM_H * 4 + IMG_CURRY_H * 2,
						x + 138 + IMG_LEVEL_STR_W * 2,
						y + ty + i * IMG_NUM_H * 4 + IMG_CURRY_H * 2 + IMG_LEVEL_STR_H * 2,
						IMG_LEVEL_STR_X,
						IMG_LEVEL_STR_Y + i * IMG_LEVEL_STR_H,
						IMG_LEVEL_STR_X + IMG_LEVEL_STR_W,
						IMG_LEVEL_STR_Y + i * IMG_LEVEL_STR_H + IMG_LEVEL_STR_H,
						null);
			}
			ty += 12;
		}
	}
}
