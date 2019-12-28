package game.puyopuyo.parts;

import java.awt.Graphics;
import java.util.Random;

import game.puyopuyo.common.ImageManager;

public class NextPuyo {

	// 組ぷよのサイズ
	private static final int COL = 3;
	private static final int ROW = 3;

	/** 1マスのサイズ(ピクセル) */
	private static final int TILE_SIZE = Field.TILE_SIZE;

	/** 難易度（出現するぷよの色数） */
	private int difficulty;

	private int x;
	private int y;

	/** 組ぷよの形を格納 */
	private int[][] form;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 */
	public NextPuyo(int x, int y) {
		this.x = x;
		this.y = y;

		// 難易度を設定
		difficulty = 5;

		form = new int[ROW][COL];
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				form[i][j] = Field.COLOR_NONE;
			}
		}
		form[1][1] = new Random().nextInt(difficulty);
		form[0][1] = new Random().nextInt(difficulty);
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				// 空白マスは描画しない
				if(form[i][j] == Field.COLOR_NONE) {
					continue;
				}
				g.drawImage(ImageManager.puyoImage,
						x + j * TILE_SIZE,
						y + i * TILE_SIZE,
						x + j * TILE_SIZE + TILE_SIZE,
						y + i * TILE_SIZE + TILE_SIZE,
						form[i][j] * TILE_SIZE,
						0,
						form[i][j] * TILE_SIZE + TILE_SIZE,
						TILE_SIZE,
						null);
			}
		}
	}
}
