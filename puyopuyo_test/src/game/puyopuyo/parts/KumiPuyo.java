package game.puyopuyo.parts;

import java.awt.Graphics;
import java.awt.Point;

import game.puyopuyo.common.ImageManager;

public class KumiPuyo {

	/*
	 * プレイヤーが操作するぷよぷよのことを「組ぷよ」とする
	 */
	// 組ぷよのサイズ
	private static final int COL = 3;
	private static final int ROW = 3;

	/** 1マスのサイズ(ピクセル) */
	private static final int TILE_SIZE = Field.TILE_SIZE;

	// 移動方向
	public static final int DIR_UP = 0;
	public static final int DIR_DOWN = 1;
	public static final int DIR_RIGHT = 2;
	public static final int DIR_LEFT = 3;

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** フィールド参照 */
	private Field field;

	/** 組ぷよの形を格納 */
	private int[][] form;

	/** 組ぷよの位置 */
	private Point pos;

	/** 軸ぷよの位置 */
	private Point axis;

	/**
	 * コンストラクタ
	 *
	 * @param field フィールド参照
	 * @param form 組ぷよの形
	 */
	public KumiPuyo(Field field, int[][] form) {
		this(0, 0, field, form);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param field フィールド参照
	 * @param form 組ぷよの形
	 */
	public KumiPuyo(int x, int y, Field field, int[][] form) {

		// 基準座標を設定
		this.x = x;
		this.y = y;

		// フィールド参照を取得
		this.field = field;
		// 組ぷよの形を取得
		this.form = form;
		// 組ぷよの位置を設定
		pos = new Point(2, 1);
		// 軸ぷよの位置を設定
		axis = new Point(1, 1);
	}

	/**
	 * 移動処理
	 *
	 * @param direction
	 * @return
	 */
	public boolean move(int direction) {
		switch(direction) {
		case DIR_UP:
			Point newPos = new Point(pos.x, pos.y - 1);
			if(field.isMovable(newPos, form)) {
				pos = newPos;
			}
			break;
		case DIR_DOWN:
			newPos = new Point(pos.x, pos.y + 1);
			if(field.isMovable(newPos, form)) {
				pos = newPos;
			} else {
				field.fixPuyo(pos, form);
				return true;
			}
			break;
		case DIR_RIGHT:
			newPos = new Point(pos.x + 1, pos.y);
			if(field.isMovable(newPos, form)) {
				pos = newPos;
			}
			break;
		case DIR_LEFT:
			newPos = new Point(pos.x - 1, pos.y);
			if(field.isMovable(newPos, form)) {
				pos = newPos;
			}
			break;
		}
		return false;
	}

	/**
	 * 回転処理
	 */
	public void turn(boolean dir) {
		int[][] turnedForm = new int[ROW][COL];

		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				if(dir) {
					turnedForm[j][ROW - 1 - i] = form[i][j];
				} else {
					turnedForm[COL - 1 -j][i] = form[i][j];
				}
			}
		}

		if(field.isMovable(pos, turnedForm)) {
			// 移動可能の場合
			form = turnedForm;
		} else {
			// 移動不可の場合
			Point newPos = new Point(pos.x, pos.y);
			if(turnedForm[axis.y][axis.x+1] != Field.COLOR_NONE) { // 右側が接触
				// 組ぷよを左移動
				newPos = new Point(pos.x - 1, pos.y);
			} else if(turnedForm[axis.y][axis.x-1] != Field.COLOR_NONE) { // 左側が接触
				// 組ぷよを右移動
				newPos = new Point(pos.x + 1, pos.y);
			} else if(turnedForm[axis.y+1][axis.x] != Field.COLOR_NONE) { // 下側が接触
				// 組ぷよを上移動
				newPos = new Point(pos.x, pos.y - 1);
			}
			if(field.isMovable(newPos, turnedForm)) {
				pos = newPos;
				form = turnedForm;
			}
		}
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 組ぷよを描画
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				// 空白マスは描画しない
				if(form[i][j] == Field.COLOR_NONE) {
					continue;
				}
				// 軸ぷよは画像を変更
				if(j == axis.x && i == axis.y) {
					g.drawImage(ImageManager.puyoImage2,
							x + (pos.x + j) * TILE_SIZE,
							y + (pos.y + i) * TILE_SIZE,
							x + (pos.x + j) * TILE_SIZE + TILE_SIZE,
							y + (pos.y + i) * TILE_SIZE + TILE_SIZE,
							form[i][j] * TILE_SIZE,
							0,
							form[i][j] * TILE_SIZE + TILE_SIZE,
							TILE_SIZE,
							null);
				} else {
					g.drawImage(ImageManager.puyoImage,
							x + (pos.x + j) * TILE_SIZE,
							y + (pos.y + i) * TILE_SIZE,
							x + (pos.x + j) * TILE_SIZE + TILE_SIZE,
							y + (pos.y + i) * TILE_SIZE + TILE_SIZE,
							form[i][j] * TILE_SIZE,
							0,
							form[i][j] * TILE_SIZE + TILE_SIZE,
							TILE_SIZE,
							null);
				}
			}
		}
	}
}
