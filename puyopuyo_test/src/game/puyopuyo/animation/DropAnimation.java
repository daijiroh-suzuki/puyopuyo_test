package game.puyopuyo.animation;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.parts.Field;

public class DropAnimation extends BaseAnimation {

//	/** 指定フレーム数で落下処理を完了させる */
//	private static final int FRAME = 24;

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** 描画座標 */
	private Point current;
	/** 落下後座標 */
	private Point to;
	/** ぷよの色 */
	private int color;
	/** 落下速度 */
	private int vy = 5;

	/**
	 * コンストラクタ
	 *
	 * @param from 落下前座標(マス目単位)
	 * @param to 落下後座標(マス目単位)
	 * @param color 落下対象ぷよの色
	 */
	public DropAnimation(Point from, Point to, int color) {
		this(0, 0, from, to, color);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param from 落下前座標(マス目単位)
	 * @param to 落下後座標(マス目単位)
	 * @param color 落下対象ぷよの色
	 */
	public DropAnimation(int x, int y, Point from, Point to, int color) {

		// 基準座標を設定
		this.x = x;
		this.y = y;

		// マス目座標からピクセル座標に変換して設定
		this.current = CommonUtil.grid2Pixel(from);
		this.to      = CommonUtil.grid2Pixel(to);
		this.color   = color;

		// 落下速度計算
//		vy = (this.to.y - this.current.y) / FRAME;
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
				x + current.x,
				y + current.y,
				x + current.x + Field.TILE_SIZE,
				y + current.y + Field.TILE_SIZE,
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

	/**
	 * Current座標(マス目単位)を取得
	 *
	 * @return
	 */
	public Point getCurrentGrid() {
		return CommonUtil.pixel2Grid(current);
	}

	/**
	 * Current座標(マス目単位)を設定
	 *
	 * @param current Current座標(マス目単位)
	 */
	public void setCurrentGrid(Point current) {
		this.current = CommonUtil.grid2Pixel(current);
	}
}
