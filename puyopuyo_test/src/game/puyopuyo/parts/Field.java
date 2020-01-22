package game.puyopuyo.parts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import game.puyopuyo.animation.BaseAnimation;
import game.puyopuyo.animation.ChainAnimation;
import game.puyopuyo.animation.DropAnimation;
import game.puyopuyo.animation.VanishAnimation;
import game.puyopuyo.common.ImageManager;

public class Field {

	/** フィールドの列数 （マス数）*/
	private static final int COL = 8;
	/** フィールドの行数 （マス数）*/
	private static final int ROW = 15;

	/** 1マスのサイズ(ピクセル) */
	public static final int TILE_SIZE = 32;

	/** ぷよの色：空白マス */
	public static final int COLOR_NONE   = -1;
	/** ぷよの色：赤 */
	public static final int COLOR_RED    = 0;
	/** ぷよの色：青 */
	public static final int COLOR_BLUE   = 1;
	/** ぷよの色：黄色 */
	public static final int COLOR_YELLOW = 2;
	/** ぷよの色：緑 */
	public static final int COLOR_GREEN  = 3;
	/** ぷよの色：紫 */
	public static final int COLOR_PURPLE = 4;
	/** ぷよの色：おじゃま */
	public static final int COLOR_O      = 5;
	/** ぷよの色：壁マス */
	public static final int COLOR_WALL   = 9;

	/** 一時非表示 */
	public static final int TILE_HIDDEN = 10;

	/** x方向参照配列(上、右、下、左) */ /* Collection.unmodifiableListを利用した方がいいけど妥協 */
	private static final int[] DIRECTION_X = {0, 1, 0, -1};
	/** y方向参照配列 (上、右、下、左)*/
	private static final int[] DIRECTION_Y = {-1, 0, 1, 0};
	/** 連結方向値配列 (上、右、下、左)*/
	private static final int[] CONNECT_VAL = {1, 8, 2, 4};

	/** 連鎖ボーナステーブル */
	private static final int[] CHAIN_BONUS = {0, 8, 16, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, 480, 512};
	/** 連結ボーナステーブル */
	private static final int[] CONNECT_BONUS = {0, 2, 3, 4, 5, 6, 7, 10};
	/** 色数ボーナステーブル */
	private static final int[] COLOR_BONUS = {0, 3, 6, 12, 24};

	/** ぷよが消滅する連結数 */
	private static final int VANISH_COUNT = 4;

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** フィールド配列 */
	private int[][] field;
	/** 連結方向配列 */
	private int[][] connect;
	/** 消滅チェック配列 */
	private boolean[][] vanish;

	/** 連結数カウンタ */
	private int connectCount = 0;
	/** 消滅対象リスト */
	private List<Point> vanishList;
	/** 連鎖数 */
	private int chain = 0;

	/** スコア */
	private int score = 0;
	/** ぷよの消えた数 */
	private int pcnt = 0;
	/** 連鎖ボーナス */
	private int chainBonus = 0;
	/** 連結ボーナス */
	private int connectBonus = 0;
	/** 色数ボーナス */
	private int colorBonus = 0;

	/** ゲームオーバーマス */
	private Point gameOver;

	/** プレイヤー位置 */
	private PlayerSide p;

	/**
	 * コンストラクタ
	 */
	public Field() {
		this(0, 0, PlayerSide.PLAYER1);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 */
	public Field(int x, int y) {
		this(x, y, PlayerSide.PLAYER1);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 * @param p プレイヤー位置
	 */
	public Field(int x, int y, PlayerSide p) {

		// 基準座標を設定
		this.x = x;
		this.y = y;
		// プレイヤー位置を設定
		this.p = p;

		// フィールド配列を生成
		field = new int[ROW][COL];
		// 連結方向配列を生成
		connect = new int[ROW][COL];
		// 消滅チェック配列を生成
		vanish = new boolean[ROW][COL];
		// 消滅対象リストを生成
		vanishList = new ArrayList<Point>();

		// 初期化処理
		init();
	}

	/**
	 * 初期化処理
	 */
	public void init() {
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
		// ゲームオーバーマスを設定
		gameOver = new Point(3, 2);
		// スコアを初期化
		score = 0;
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 連結方向情報を更新
		updateConnect();

		// フィールド配列を描画
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {

				// 空白マスと壁マスは描画しない
				if(field[i][j] == COLOR_NONE || field[i][j] == COLOR_WALL) {
					continue;
				}

				// 一時非表示マスは描画しない
				if(field[i][j] / TILE_HIDDEN > 0) {
					continue;
				}

				// ぷよを描画
				g.drawImage(ImageManager.puyoImage,
						x + j * TILE_SIZE,
						y + i * TILE_SIZE,
						x + j * TILE_SIZE + TILE_SIZE,
						y + i * TILE_SIZE + TILE_SIZE,
						field[i][j]   * TILE_SIZE,
						connect[i][j] * TILE_SIZE,
						field[i][j]   * TILE_SIZE + TILE_SIZE,
						connect[i][j] * TILE_SIZE + TILE_SIZE,
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
	 *
	 * @param list アニメーションリスト
	 */
	public void drop(List<BaseAnimation> list) {
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
					list.add(new DropAnimation(
							x,
							y,
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
	}

	/**
	 * 落下処理中判定
	 *
	 * @param list
	 * @return true:処理中、false:処理完了
	 */
	public boolean isDrop(List<BaseAnimation> list) {
		for(BaseAnimation a : list) {
			if(a instanceof DropAnimation) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 消滅処理
	 *
	 * @return true:消滅処理対象あり、false:消滅処理対象なし
	 */
	public boolean vanish(List<BaseAnimation> list) {

		boolean isVanish = false;

		// ぷよの消えた数を初期化
		pcnt = 0;
		// 連結ボーナスを初期化
		connectBonus = 0;
		// 色数ボーナスを初期化
		colorBonus = 0;
		// 色数取得用HashSet
		HashSet<Integer> colorSet = new HashSet<Integer>();

		// 消滅チェック配列の初期化
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {
				vanish[i][j] = false;
			}
		}
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {
				// 空白マスと壁マスとおじゃまぷよは処理対象外
				if(field[i][j] == COLOR_NONE
						|| field[i][j] == COLOR_WALL
						|| field[i][j] == COLOR_O) {
					continue;
				}

				// チェック済みのマスは処理対象外
				if(vanish[i][j]) {
					continue;
				}
				// 連結数カウンタを初期化
				connectCount = 0;
				// 消滅対象リストをクリア
				vanishList.clear();
				// 連結数をカウント
				checkVanish(j, i);
				// 連結数が規定値以上の場合
				if(connectCount >= VANISH_COUNT) {
					for(Point pos : vanishList) {
						int clr = field[pos.y][pos.x];
						if(clr != COLOR_O) {
							// ぷよの消えた数を加算
							pcnt++;
							// 色数取得用HashSetに追加
							colorSet.add(clr);
						}
						// 消滅アニメーションをリストに追加
						list.add(new VanishAnimation(x, y, pos, clr));
						field[pos.y][pos.x] = COLOR_NONE;
					}
					// 連鎖アニメーションをリストに追加
					Point pos = vanishList.get(0);
					list.add(new ChainAnimation(x, y, pos, chain+1, p));

					// 連結ボーナスを計算
					int idx = connectCount - VANISH_COUNT;
					connectBonus += CONNECT_BONUS[idx];

					// 消滅対象あり
					isVanish = true;
				}
			}
		}
		if(isVanish) {
			// 消滅対象ありの場合
			// 連鎖数を加算
			chain++;
			// 連鎖ボーナスを計算
			chainBonus = CHAIN_BONUS[chain - 1];
			// 色数ボーナスを計算
			colorBonus = COLOR_BONUS[colorSet.size() - 1];

			// 各ボーナスの合計値が0の場合、1として計算
			int bonus = chainBonus + connectBonus + colorBonus;
			if(bonus == 0) {
				bonus = 1;
			}
			// スコアを計算
			// ぷよの消えた数×10 (連鎖ボーナス + 連結ボーナス + 色数ボーナス)
			score = pcnt * 10 * bonus;
		} else {
			// 消滅対象なしの場合は連鎖数を初期化
			chain = 0;
		}
		return isVanish;
	}

	/**
	 * 同じ色のぷよの連結数をカウントする
	 *
	 * @param x チェックの基点とするx座標
	 * @param y チェックの基点とするy座標
	 */
	private void checkVanish(int x, int y) {
		// 連結数カウンタを加算
		connectCount++;
		// 当マスはチェック済みとする
		vanish[y][x] = true;
		// 当マスを消滅対象リストに追加
		vanishList.add(new Point(x, y));

		// 4方向をチェックする
		for(int i=0; i<DIRECTION_X.length && i<DIRECTION_Y.length; i++) {
			// チェックする座標を取得
			int distX = x + DIRECTION_X[i];
			int distY = y + DIRECTION_Y[i];
			if(!vanish[distY][distX] && field[y][x] == field[distY][distX]) {
				checkVanish(distX, distY);
			}
			// 隣接するおじゃまぷよも削除対象リストに追加
			if(field[distY][distX] == COLOR_O) {
				vanishList.add(new Point(distX, distY));
			}
		}
	}

	/**
	 * 消滅処理中判定
	 *
	 * @param list
	 * @return true:処理中、false:処理完了
	 */
	public boolean isVanish(List<BaseAnimation> list) {
		for(BaseAnimation a : list) {
			if(a instanceof VanishAnimation) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 連結方向情報の更新
	 */
	private void updateConnect() {
		for(int i=0; i<field.length; i++) {
			for(int j=0; j<field[i].length; j++) {
				connect[i][j] = 0;

				// 空白マスと壁マスとおじゃまぷよは処理対象外
				if(field[i][j] == COLOR_NONE
						|| field[i][j] == COLOR_WALL
						|| field[i][j] == COLOR_O) {
					continue;
				}

				int newConnect = 0;
				// 4方向をチェックする
				for(int k=0; k<DIRECTION_X.length && k<DIRECTION_Y.length; k++) {
					if(field[i][j] == field[i+DIRECTION_Y[k]][j+DIRECTION_X[k]]) {
						newConnect = newConnect + CONNECT_VAL[k];
					}
				}
				connect[i][j] = newConnect;
			}
		}
	}

	/**
	 * ゲームオーバー判定
	 *
	 * @return true:ゲームオーバー
	 */
	public boolean checkGameOver() {
		if(field[gameOver.y][gameOver.x] != COLOR_NONE) {
			return true;
		}
		return false;
	}

	/**
	 * おじゃまぷよを追加
	 *
	 * @param count
	 * @return 追加したおじゃまぷよ数
	 */
	public int addOjamaPuyo(int count) {

		// 追加したおじゃまぷよ数
		int rtCount = 0;

		if(count >= 6) {
			for(int i=1; i<=6; i++) {
				field[2][i] = COLOR_O;
				rtCount++;
			}
		} else {
			for(int i=0; i<count; i++) {
				int distX, distY;
				do {
					distX = new Random().nextInt(6) + 1;
					distY = 2;
				} while(field[distY][distX] == COLOR_O);

				field[distY][distX] = COLOR_O;
				rtCount++;
			}
		}

		return rtCount;
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
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x セットする x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y セットする y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * スコアを取得
	 *
	 * @return
	 */
	public int getScore() {
		return score;
	}
}
