package game.puyopuyo.parts;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

public class KumiPuyo {

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

	/** フィールド参照 */
	private Field field;

	/** 難易度（出現するぷよの色数） */
	private int difficulty;

	/** 画像イメージ */
	private Image image;

	/** 組ぷよの形を格納 */
	private int[][] kumiPuyo;

	/** 組ぷよの位置 */
	private Point pos;

	/**
	 * コンストラクタ
	 */
	public KumiPuyo(Field field) {

		// フィールド参照を取得
		this.field = field;

		// 画像ファイルをロード
		loadImage();

		// 難易度を設定
		difficulty = 5;

		// 組ぷよを初期化
		kumiPuyo = new int[ROW][COL];
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				kumiPuyo[i][j] = Field.COLOR_NONE;
			}
		}

		kumiPuyo[1][1] = new Random().nextInt(difficulty);
		kumiPuyo[2][1] = new Random().nextInt(difficulty);

		pos = new Point(2, 0);
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
			if(field.isMovable(newPos, kumiPuyo)) {
				pos = newPos;
			}
			break;
		case DIR_DOWN:
			newPos = new Point(pos.x, pos.y + 1);
			if(field.isMovable(newPos, kumiPuyo)) {
				pos = newPos;
			} else {
				field.fixPuyo(pos, kumiPuyo);
				return true;
			}
			break;
		case DIR_RIGHT:
			newPos = new Point(pos.x + 1, pos.y);
			if(field.isMovable(newPos, kumiPuyo)) {
				pos = newPos;
			}
			break;
		case DIR_LEFT:
			newPos = new Point(pos.x - 1, pos.y);
			if(field.isMovable(newPos, kumiPuyo)) {
				pos = newPos;
			}
			break;
		}
		return false;
	}

	/**
	 * 回転処理
	 */
	public void turn() {
		int[][] turnedKumiPuyo = new int[ROW][COL];

		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				turnedKumiPuyo[j][ROW - 1 - i] = kumiPuyo[i][j];
			}
		}

		if(field.isMovable(pos, turnedKumiPuyo)) {
			kumiPuyo = turnedKumiPuyo;
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
				if(kumiPuyo[i][j] == Field.COLOR_NONE) {
					continue;
				}
				g.drawImage(image,
						(pos.x + j) * TILE_SIZE,
						(pos.y + i) * TILE_SIZE,
						(pos.x + j) * TILE_SIZE + TILE_SIZE,
						(pos.y + i) * TILE_SIZE + TILE_SIZE,
						kumiPuyo[i][j] * TILE_SIZE,
						0,
						kumiPuyo[i][j] * TILE_SIZE + TILE_SIZE,
						TILE_SIZE,
						null);
			}
		}
	}

	/**
	 * 画像ファイルをロードする
	 */
	private void loadImage() {
		// ぷよ画像をロード
		URL url = this.getClass().getClassLoader().getResource("images/puyo.gif");
		ImageIcon icon = new ImageIcon(url);
		image = icon.getImage();
	}
}
