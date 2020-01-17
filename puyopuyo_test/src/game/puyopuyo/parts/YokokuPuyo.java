package game.puyopuyo.parts;

import java.awt.Graphics;

import game.puyopuyo.common.ImageManager;

public class YokokuPuyo {

	/** レート：王冠ぷよ */
	private static final int OKANPUYO = 400;
	/** レート：星ぷよ */
	private static final int HOSHIPUYO = 300;
	/** レート：キノコぷよ */
	private static final int KINOKOPUYO = 200;
	/** レート：岩ぷよ */
	private static final int IWAPUYO = 30;
	/** レート：大ぷよ */
	private static final int BIGPUYO = 6;
	/** レート：小ぷよ */
	private static final int SMALLPUYO = 1;

	/** おじゃまぷよの種類毎のレート */
	private static final int[] YOKOKU_TYPE = {OKANPUYO, HOSHIPUYO, KINOKOPUYO, IWAPUYO, BIGPUYO, SMALLPUYO};

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;
	/** おじゃまぷよの個数 */
	private int count;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 */
	public YokokuPuyo(int x, int y) {
		this.x = x;
		this.y = y;
		count = 0;
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 計算用おじゃまぷよ個数
		int calcCount = count;
		// おじゃまぷよの種類カウント
		int[] typeCount = new int[YOKOKU_TYPE.length];
		// おじゃまぷよの種類毎の個数を計算
		for(int i=0; i<YOKOKU_TYPE.length; i++) {
			typeCount[i] = calcCount / YOKOKU_TYPE[i];
			calcCount = calcCount % YOKOKU_TYPE[i];
		}

		int dispCount = 0;
		for(int i=0; i<typeCount.length; i++) {
			for(int j=0; j<typeCount[i]; j++) {

				g.drawImage(ImageManager.yokokuImage,
						x + dispCount * Field.TILE_SIZE,
						y,
						x + dispCount * Field.TILE_SIZE + Field.TILE_SIZE,
						y + Field.TILE_SIZE,
						i * Field.TILE_SIZE,
						0,
						i * Field.TILE_SIZE + Field.TILE_SIZE,
						Field.TILE_SIZE,
						null);

				dispCount++;
			}
		}
	}

	/**
	 * おじゃまぷよの個数を加算
	 *
	 * @param count
	 */
	public void addCount(int count) {
		this.count += count;
	}
}
