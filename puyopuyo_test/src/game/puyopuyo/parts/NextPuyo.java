package game.puyopuyo.parts;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import game.puyopuyo.common.ImageManager;

public class NextPuyo {

	// NEXTぷよのサイズ
	private static final int COL = 3;
	private static final int ROW = 3;

	/** 1マスのサイズ(ピクセル) */
	private static final int TILE_SIZE = Field.TILE_SIZE;

	/** 表示位置x座標(ピクセル) */
	private int x;
	/** 表示位置y座標(ピクセル) */
	private int y;

	/** 難易度（出現するぷよの色数） */
	private int level;

	/** NEXTぷよリスト */
	private LinkedList<int[][]> nextList;
	/** NEXTぷよリストのサイズ */
	private int size;

	/**
	 * コンストラクタ
	 *
	 * @param x 表示位置x座標(ピクセル単位)
	 * @param y 表示位置y座標(ピクセル単位)
	 * @param level 難易度
	 */
	public NextPuyo(int x, int y, int level) {
		this.x = x;
		this.y = y;

		// 難易度を設定
		this.level = level;
		// NEXTぷよリストのサイズを設定
		size = 2;
		// NEXTぷよリストを生成
		nextList = new LinkedList<int[][]>();
		for(int i=0; i<size; i++) {
			generate();
		}
	}

	/**
	 * NEXTぷよ生成処理
	 */
	private void generate() {
		// NEXTぷよ配列を生成
		int[][] form = new int[ROW][COL];

		// NEXTぷよを初期化
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				form[i][j] = Field.COLOR_NONE;
			}
		}
		// とりあえず縦2つのパターンのみ生成
		// 後々フィーバーのパターンにも対応できるように組ぷよの形を配列で保持する
		form[1][1] = new Random().nextInt(level);
		form[0][1] = new Random().nextInt(level);

		// NEXTぷよリストに追加
		nextList.add(form);
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		if(nextList.size() == 2) {
			// とりあえずNEXTぷよ数２つ固定で表示位置を設定
			int[][] next = nextList.get(0);
			drawNext(x, y, next, g);

			next = nextList.get(1);
			drawNext(x + TILE_SIZE, y + TILE_SIZE, next, g);

		} else {
			int cnt = 0;
			for(int[][] next : nextList) {
				drawNext(x, y + cnt * ROW * TILE_SIZE, next, g);
				cnt++;
			}
		}
	}

	/**
	 * NEXTぷよを描画
	 *
	 * @param x
	 * @param y
	 * @param next
	 * @param g
	 */
	private void drawNext(int x, int y, int[][] next, Graphics g) {
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COL; j++) {
				// 空白マスは描画しない
				if(next[i][j] == Field.COLOR_NONE) {
					continue;
				}
				g.drawImage(ImageManager.puyoImage,
				x + j * TILE_SIZE,
				y + i * TILE_SIZE,
				x + j * TILE_SIZE + TILE_SIZE,
				y + i * TILE_SIZE + TILE_SIZE,
				next[i][j] * TILE_SIZE,
				0,
				next[i][j] * TILE_SIZE + TILE_SIZE,
				TILE_SIZE,
				null);
			}
		}
	}

	/**
	 * NEXTぷよを取得
	 *
	 * @return
	 */
	public int[][] pop() {
		generate();
		return nextList.pop();
	}
}
