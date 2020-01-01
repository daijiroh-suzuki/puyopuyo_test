package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.Field;

public class DropAnimation extends BaseAnimation {

	private Point current;
	private Point to;
	private int color;

	/**
	 * コンストラクタ
	 *
	 * @param from
	 * @param to
	 * @param color
	 */
	public DropAnimation(Point from, Point to, int color) {

		this.current = CommonUtil.grid2Pixel(from);
		this.to      = CommonUtil.grid2Pixel(to);
		this.color   = color;
		this.running = true;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		if(current.y < to.y) {
			current.y = current.y + 8;
		} else {
			running = false;
		}
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	@Override
	public void draw(Graphics g) {

		g.drawImage(ImageManager.puyoImage,
				current.x,
				current.y,
				current.x + Field.TILE_SIZE,
				current.y + Field.TILE_SIZE,
				color * Field.TILE_SIZE,
				0,
				color * Field.TILE_SIZE + Field.TILE_SIZE,
				Field.TILE_SIZE,
				null);
	}

	/**
	 * To座標を取得
	 *
	 * @return
	 */
	public Point getTo() {
		return CommonUtil.pixel2Grid(to);
	}
}
