package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class GameOverAnimation extends BaseAnimation {

	/** 勝ち画像：基準座標x */
	private static final int IMG_WIN_X = 4;
	/** 勝ち画像：基準座標y */
	private static final int IMG_WIN_Y = 7;
	/** 勝ち画像：幅 */
	private static final int IMG_WIN_W = 76;
	/** 勝ち画像：高さ */
	private static final int IMG_WIN_H = 45;

	/** 負け画像：基準座標x */
	private static final int IMG_LOSS_X = 275;
	/** 負け画像：基準座標y */
	private static final int IMG_LOSS_Y = 3;
	/** 負け画像：幅 */
	private static final int IMG_LOSS_W = 76;
	/** 負け画像：高さ */
	private static final int IMG_LOSS_H = 76;

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** 勝敗(true:勝ち、false:負け) */
	private boolean result = false;

	/** 描画色 */
	private Color clr;

	/**
	 * コンストラクタ
	 */
	public GameOverAnimation() {
		this(0, 0, false);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param result 勝敗(true:勝ち、false:負け)
	 */
	public GameOverAnimation(int x, int y, boolean result) {
		// 基準座標を設定
		this.x = x;
		this.y = y;
		this.result = result;

		clr = new Color(0, 0, 0, 60);
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(clr);
		g.fillRect(x+32, y+64, 192, 384);

		if(result) {
			// 勝ち画像を描画
			g.drawImage(ImageManager.miscImage,
					x + 50,
					y + 190,
					x + 50  + IMG_WIN_W * 2,
					y + 190 + IMG_WIN_H * 2,
					IMG_WIN_X,
					IMG_WIN_Y,
					IMG_WIN_X + IMG_WIN_W,
					IMG_WIN_Y + IMG_WIN_H,
					null);
		} else {
			// 負け画像を描画
			g.drawImage(ImageManager.miscImage,
					x + 50,
					y + 190,
					x + 50  + IMG_LOSS_W * 2,
					y + 190 + IMG_LOSS_H * 2,
					IMG_LOSS_X,
					IMG_LOSS_Y,
					IMG_LOSS_X + IMG_LOSS_W,
					IMG_LOSS_Y + IMG_LOSS_H,
					null);
		}
	}
}
