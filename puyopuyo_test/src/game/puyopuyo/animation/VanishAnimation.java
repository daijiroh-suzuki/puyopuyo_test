package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.Field;

public class VanishAnimation extends BaseAnimation {

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	private Point pos;
	private int color;
	private int frameCount;

	/**
	 * コンストラクタ
	 *
	 * @param pos 削除対象ぷよの座標(マス目単位)
	 * @param color 削除対象ぷの色
	 */
	public VanishAnimation(Point pos, int color) {
		this(0, 0, pos, color);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param pos 削除対象ぷよの座標(マス目単位)
	 * @param color 削除対象ぷの色
	 */
	public VanishAnimation(int x, int y, Point pos, int color) {
		// 基準座標を設定
		this.x = x;
		this.y = y;

		this.pos = pos;
		this.color = color;
		frameCount = 0;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 30) { // 適当に30フレーム経過したら終了
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		g.drawImage(ImageManager.puyoImage,
				x + pos.x * Field.TILE_SIZE,
				y + pos.y * Field.TILE_SIZE,
				x + pos.x * Field.TILE_SIZE + Field.TILE_SIZE,
				y + pos.y * Field.TILE_SIZE + Field.TILE_SIZE,
				color * Field.TILE_SIZE,
				17 * Field.TILE_SIZE,
				color * Field.TILE_SIZE + Field.TILE_SIZE,
				17 * Field.TILE_SIZE + Field.TILE_SIZE,
				null);
	}
}
