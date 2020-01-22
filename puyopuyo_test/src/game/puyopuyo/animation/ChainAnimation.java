package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.PlayerSide;

public class ChainAnimation extends BaseAnimation {

	/** 1P連鎖画像：基準座標x */
	private static int IMG_CHAIN1_X = 153;
	/** 1P連鎖画像：基準座標y */
	private static int IMG_CHAIN1_Y = 3;
	/** 1P連鎖画像：幅 */
	private static int IMG_CHAIN1_W = 16;
	/** 1P連鎖画像：高さ */
	private static int IMG_CHAIN1_H = 6;

	/** 2P連鎖画像：基準座標x */
	private static int IMG_CHAIN2_X = 415;
	/** 2P連鎖画像：基準座標y */
	private static int IMG_CHAIN2_Y = 3;
	/** 2P連鎖画像：幅 */
	private static int IMG_CHAIN2_W = 16;
	/** 2P連鎖画像：高さ */
	private static int IMG_CHAIN2_H = 6;

	/** 1Pフォント画像：基準座標x */
	private static int IMG_FONT1_X = 89;
	/** 1Pフォント画像：基準座標y */
	private static int IMG_FONT1_Y = 2;
	/** 1Pフォント画像：幅 */
	private static int IMG_FONT1_W = 6;
	/** 1Pフォント画像：高さ */
	private static int IMG_FONT1_H = 12;

	/** 2Pフォント画像：基準座標x */
	private static int IMG_FONT2_X = 351;
	/** 2Pフォント画像：基準座標y */
	private static int IMG_FONT2_Y = 2;
	/** 2Pフォント画像：幅 */
	private static int IMG_FONT2_W = 6;
	/** 2Pフォント画像：高さ */
	private static int IMG_FONT2_H = 12;

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
	/** プレイヤー位置 */
	private PlayerSide p;

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param pos 表示位置
	 * @param chain 連鎖数
	 * @param p プレイヤー位置
	 */
	public ChainAnimation(int x, int y, Point pos, int chain, PlayerSide p) {
		// 基準座標を設定
		this.x = x;
		this.y = y;
		// プレイヤー位置を設定
		this.p = p;

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
			if(p == PlayerSide.PLAYER1) {
				g.drawImage(ImageManager.fontImage,
						x + pos.x + i * IMG_FONT1_W * 2,
						y + pos.y,
						x + pos.x + i * IMG_FONT1_W * 2 + IMG_FONT1_W * 2,
						y + pos.y + IMG_FONT1_H * 2,
						IMG_FONT1_X + chain[i] * IMG_FONT1_W,
						IMG_FONT1_Y,
						IMG_FONT1_X + chain[i] * IMG_FONT1_W + IMG_FONT1_W,
						IMG_FONT1_Y + IMG_FONT1_H,
						null);
			} else {
				g.drawImage(ImageManager.fontImage,
						x + pos.x + i * IMG_FONT2_W * 2,
						y + pos.y,
						x + pos.x + i * IMG_FONT2_W * 2 + IMG_FONT2_W * 2,
						y + pos.y + IMG_FONT2_H * 2,
						IMG_FONT2_X + chain[i] * IMG_FONT2_W,
						IMG_FONT2_Y,
						IMG_FONT2_X + chain[i] * IMG_FONT2_W + IMG_FONT2_W,
						IMG_FONT2_Y + IMG_FONT2_H,
						null);
			}
		}

		// 「れんさ」を描画
		if(p == PlayerSide.PLAYER1) {
			g.drawImage(ImageManager.fontImage,
					x + pos.x + chain.length * IMG_FONT1_W * 2,
					y + pos.y,
					x + pos.x + chain.length * IMG_FONT1_W * 2 + IMG_CHAIN1_W * 2,
					y + pos.y + IMG_CHAIN1_H * 2,
					IMG_CHAIN1_X,
					IMG_CHAIN1_Y,
					IMG_CHAIN1_X + IMG_CHAIN1_W,
					IMG_CHAIN1_Y + IMG_CHAIN1_H,
					null);
		} else {
			g.drawImage(ImageManager.fontImage,
					x + pos.x + chain.length * IMG_FONT2_W * 2,
					y + pos.y,
					x + pos.x + chain.length * IMG_FONT2_W * 2 + IMG_CHAIN2_W * 2,
					y + pos.y + IMG_CHAIN2_H * 2,
					IMG_CHAIN2_X,
					IMG_CHAIN2_Y,
					IMG_CHAIN2_X + IMG_CHAIN2_W,
					IMG_CHAIN2_Y + IMG_CHAIN2_H,
					null);
		}
	}
}
