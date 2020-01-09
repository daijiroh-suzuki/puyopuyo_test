package game.puyopuyo.animation;

import java.awt.Graphics;

import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;

public class ChainAnimation extends BaseAnimation {

	/** 連鎖画像：基準座標x */
	private static int IMG_CHAIN_X = 153;
	/** 連鎖画像：基準座標y */
	private static int IMG_CHAIN_Y = 3;
	/** 連鎖画像：幅 */
	private static int IMG_CHAIN_W = 16;
	/** 連鎖画像：高さ */
	private static int IMG_CHAIN_H = 6;

	/** フォント画像：基準座標x */
	private static int IMG_FONT_X = 89;
	/** フォント画像：基準座標y */
	private static int IMG_FONT_Y = 2;
	/** フォント画像：幅 */
	private static int IMG_FONT_W = 6;
	/** フォント画像：高さ */
	private static int IMG_FONT_H = 12;

	/** 表示位置x座標 */
	private int x;
	/** 表示位置y座標 */
	private int y;
	/** 連鎖数 */
	private int[] chain;
	/** フレームカウント */
	private int frameCount;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 * @param chain
	 */
	public ChainAnimation(int x, int y, int chain) {
		this.x = CommonUtil.grid2Pixel(x);
		this.y = CommonUtil.grid2Pixel(y);
		frameCount = 0;

		char[] str = String.valueOf(chain).toCharArray();
		this.chain = new int[str.length];
		for(int i=0; i<str.length; i++) {
			this.chain[i] = Character.getNumericValue(str[i]);
		}
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 40) {
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		// 連鎖数を描画
		for(int i=0; i<chain.length; i++) {
			g.drawImage(ImageManager.fontImage,
					x + i * IMG_FONT_W * 2,
					y,
					x + i * IMG_FONT_W * 2 + IMG_FONT_W * 2,
					y + IMG_FONT_H * 2,
					IMG_FONT_X + chain[i] * IMG_FONT_W,
					IMG_FONT_Y,
					IMG_FONT_X + chain[i] * IMG_FONT_W + IMG_FONT_W,
					IMG_FONT_Y + IMG_FONT_H,
					null);
		}

		// 「れんさ」を描画
		g.drawImage(ImageManager.fontImage,
				x + chain.length * IMG_FONT_W * 2,
				y,
				x + chain.length * IMG_FONT_W * 2 + IMG_CHAIN_W * 2,
				y + IMG_CHAIN_H * 2,
				IMG_CHAIN_X,
				IMG_CHAIN_Y,
				IMG_CHAIN_X + IMG_CHAIN_W,
				IMG_CHAIN_Y + IMG_CHAIN_H,
				null);
	}
}
