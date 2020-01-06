package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.Field;

public class VanishAnimation extends BaseAnimation {

	private Point pos;
	private int color;
	private int frameCount;

	/**
	 * コンストラクタ
	 *
	 * @param pos
	 * @param color
	 */
	public VanishAnimation(Point pos, int color) {
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
		if(frameCount >= 10) { // 適当に10フレーム経過したら終了
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		g.drawImage(ImageManager.puyoImage,
				pos.x * Field.TILE_SIZE,
				pos.y * Field.TILE_SIZE,
				pos.x * Field.TILE_SIZE + Field.TILE_SIZE,
				pos.y * Field.TILE_SIZE + Field.TILE_SIZE,
				color * Field.TILE_SIZE,
				17 * Field.TILE_SIZE,
				color * Field.TILE_SIZE + Field.TILE_SIZE,
				17 * Field.TILE_SIZE + Field.TILE_SIZE,
				null);
	}
}
