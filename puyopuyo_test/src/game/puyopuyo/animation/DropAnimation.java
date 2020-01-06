package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.Field;

public class DropAnimation extends BaseAnimation {

	/** 指定フレーム数で落下処理を完了させる */
	private static final int FRAME = 8;

	/** 描画座標 */
	private Point current;
	/** 落下後座標 */
	private Point to;
	/** ぷよの色 */
	private int color;
	/** 落下速度 */
	private int vy;

	/**
	 * コンストラクタ
	 *
	 * @param from
	 * @param to
	 * @param color
	 */
	public DropAnimation(Point from, Point to, int color) {

		// マス目座標からピクセル座標に変換して設定
		this.current = CommonUtil.grid2Pixel(from);
		this.to      = CommonUtil.grid2Pixel(to);
		this.color   = color;

		// 落下速度計算
		vy = (this.to.y - this.current.y) / FRAME;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		if(current.y < to.y) {
			current.y = current.y + vy;
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
