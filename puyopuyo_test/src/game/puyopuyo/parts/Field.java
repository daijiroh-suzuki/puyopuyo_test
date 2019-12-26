package game.puyopuyo.parts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Field {

	/** フィールドの列数 （マス数）*/
	private static final int COL = 8;
	/** フィールドの行数 （マス数）*/
	private static final int ROW = 14;

	/** 1マスのサイズ(ピクセル) */
	public static final int TILE_SIZE = 32;

	/** ぷよの色：空白マス */
	public static final int COLOR_NONE   = -1;
	/** ぷよの色：赤 */
	public static final int COLOR_RED    = 0;
	/** ぷよの色：緑 */
	public static final int COLOR_GREEN  = 1;
	/** ぷよの色：青 */
	public static final int COLOR_BLUE   = 2;
	/** ぷよの色：黄色 */
	public static final int COLOR_YELLOW = 3;
	/** ぷよの色：紫 */
	public static final int COLOR_PURPLE = 4;
	/** ぷよの色：おじゃま */
	public static final int COLOR_O      = 5;
	/** ぷよの色：壁マス */
	public static final int COLOR_WALL   = 9;

	/** 一時非表示 */
	public static final int TILE_HIDDEN = 10;

	/** 背景画像イメージ */
	private Image bgImage;
	/** ぷよ画像イメージ */
	private Image puyoImage;

	/** フィールド配列 */
	private int[][] field;

	/**
	 * コンストラクタ
	 */
	public Field() {

		// 画像ファイルの読み込みを行う
		loadImage();

		// フィールド配列を生成
		field = new int[ROW][COL];

		// フィールド配列を初期化
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {
				if(i == 0 || i == field.length-1 || j == 0 || j == field[i].length-1) {
					// 壁マスを設定
					field[i][j] = COLOR_WALL;
				} else {
					// 空白マスを設定
					field[i][j] = COLOR_NONE;
				}
			}
		}
		// TODO
		field[12][1] = COLOR_RED;
		field[12][2] = COLOR_GREEN;
		field[12][3] = COLOR_BLUE;
		field[12][4] = COLOR_YELLOW;
		field[12][5] = COLOR_PURPLE;
		field[12][6] = COLOR_O;
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 背景画像を描画
		g.drawImage(bgImage,
				0,
				0,
				COL * TILE_SIZE,
				ROW * TILE_SIZE,
				0,
				0,
				COL * TILE_SIZE,
				ROW * TILE_SIZE,
				null);

		// フィールド配列を描画
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {
				// TODO (仮処理)枠を描画
				g.setColor(Color.WHITE);
				g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);

				// 空白マスと壁マスは描画しない
				if(field[i][j] == COLOR_NONE || field[i][j] == COLOR_WALL) {
					continue;
				}

				// 一時非表示マスが描画しない
				if(field[i][j] / TILE_HIDDEN > 0) {
					continue;
				}

				// ぷよを描画
				g.drawImage(puyoImage,
						j * TILE_SIZE,
						i * TILE_SIZE,
						j * TILE_SIZE + TILE_SIZE,
						i * TILE_SIZE + TILE_SIZE,
						field[i][j] * TILE_SIZE,
						0,
						field[i][j] * TILE_SIZE + TILE_SIZE,
						TILE_SIZE,
						null);
			}
		}
	}

	/**
	 * 組ぷよが移動可能か判定する
	 *
	 * @param newPos 組ぷよの移動先座標
	 * @param kumiPuyo 組ぷよ
	 * @return true:移動可能, false:移動不可
	 */
	public boolean isMovable(Point newPos, int[][] kumiPuyo) {

		for(int i=0; i<kumiPuyo.length; i++) {
			for(int j=0; j<kumiPuyo[i].length; j++) {
				if(kumiPuyo[i][j] != COLOR_NONE) { // 空白マス以外を判定する
					// 移動先が空白マス以外の場合は移動不可
					if(field[newPos.y+i][newPos.x+j] != COLOR_NONE) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 組ぷよをフィールドに固定する
	 *
	 * @param pos
	 * @param kumiPuyo
	 */
	public void fixPuyo(Point pos, int[][] kumiPuyo) {
		for(int i=0; i<kumiPuyo.length; i++) {
			for(int j=0; j<kumiPuyo[i].length; j++) {
				if(kumiPuyo[i][j] != COLOR_NONE) { // 空白マス以外を処理する
					field[pos.y+i][pos.x+j] =  kumiPuyo[i][j];
				}
			}
		}
	}

	/**
	 * 落下処理
	 */
	public List<Animation> drop() {

		List<Animation> list = new ArrayList<Animation>();

		// フィールドを下から上に向かって走査する
		for(int i=field.length-1; i>=0; i--) {
			for(int j=0; j<field[i].length; j++) {

				// 空白マスと壁マスは処理対象外
				if(field[i][j] == COLOR_NONE || field[i][j] == COLOR_WALL) {
					continue;
				}
				int k = i + 1; // 1マス下のマスを確認
				if(field[k][j] == COLOR_NONE) {
					do {
						k++;
					} while(field[k][j] == COLOR_NONE);

					// 落下アニメーションをリストに追加
					list.add(new Animation(
							new Point(j, i),
							new Point(j, k-1),
							field[i][j]));
					// 落下後マスに移動
					field[k-1][j] = field[i][j];
					field[i][j] = COLOR_NONE;
					// 落下後マスを一時非表示
					hiddenTile(j, k-1);
				}
			}
		}
		return list;
	}

	/**
	 * 指定マスを一時非表示にする
	 *
	 * @param x 対象マスのx座標
	 * @param y 対象マスのy座標
	 */
	public void hiddenTile(int x, int y) {
		field[y][x] = field[y][x] + TILE_HIDDEN;
	}

	/**
	 * 指定マスを一時非表示にする
	 *
	 * @param pos 対象マスのx,y座標
	 */
	public void hiddenTile(Point pos) {
		hiddenTile(pos.x, pos.y);
	}

	/**
	 * 指定マスの一時非表示を解除
	 *
	 * @param x 対象マスのx座標
	 * @param y 対象マスのy座標
	 */
	public void displayTile(int x, int y) {
		field[y][x] = field[y][x] - TILE_HIDDEN;
	}

	/**
	 * 指定マスの一時非表示を解除
	 *
	 * @param pos 対象マスのx,y座標
	 */
	public void displayTile(Point pos) {
		displayTile(pos.x, pos.y);
	}

	/**
	 * 画像ファイルをロードする
	 */
	private void loadImage() {
		// 背景画像をロード
		URL url = this.getClass().getClassLoader().getResource("images/bg.png");
		ImageIcon icon = new ImageIcon(url);
		bgImage = icon.getImage();

		// ぷよ画像をロード
		url = this.getClass().getClassLoader().getResource("images/puyo.gif");
		icon = new ImageIcon(url);
		puyoImage = icon.getImage();
	}
}
