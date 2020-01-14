package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

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

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;
	/** 表示位置 */
	private Point pos;
	/** 連鎖数 */
	private int[] chain;
	/** フレームカウント */
	private int frameCount;

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param pos 表示位置
	 * @param chain 連鎖数
	 */
	public ChainAnimation(int x, int y, Point pos, int chain) {
		// 基準座標を設定
		this.x = x;
		this.y = y;

		this.pos = new Point(
				CommonUtil.grid2Pixel(pos.x),
				CommonUtil.grid2Pixel(pos.y));

		// フレームカウントを初期化
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
					x + pos.x + i * IMG_FONT_W * 2,
					y + pos.y,
					x + pos.x + i * IMG_FONT_W * 2 + IMG_FONT_W * 2,
					y + pos.y + IMG_FONT_H * 2,
					IMG_FONT_X + chain[i] * IMG_FONT_W,
					IMG_FONT_Y,
					IMG_FONT_X + chain[i] * IMG_FONT_W + IMG_FONT_W,
					IMG_FONT_Y + IMG_FONT_H,
					null);
		}

		// 「れんさ」を描画
		g.drawImage(ImageManager.fontImage,
				x + pos.x + chain.length * IMG_FONT_W * 2,
				y + pos.y,
				x + pos.x + chain.length * IMG_FONT_W * 2 + IMG_CHAIN_W * 2,
				y + pos.y + IMG_CHAIN_H * 2,
				IMG_CHAIN_X,
				IMG_CHAIN_Y,
				IMG_CHAIN_X + IMG_CHAIN_W,
				IMG_CHAIN_Y + IMG_CHAIN_H,
				null);
	}
}
