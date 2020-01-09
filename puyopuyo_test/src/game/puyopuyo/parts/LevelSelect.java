package game.puyopuyo.parts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class LevelSelect {

	/** 難易度 */
	private static final int[] LEVEL = {3, 4, 5};
	/** 難易度文言 */
	private static final String[] MESSAGE = {"激甘", "中辛", "激辛"};

	/** 描画座標x */
	private int x;
	/** 描画座標y */
	private int y;
	/** 枠の幅 */
	private int width;
	/** 枠の高さ */
	private int height;
	/** 選択中の難易度インデックス */
	private int selectIdx;

	/**
	 * コンストラクタ
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public LevelSelect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		selectIdx = 0;
	}

	/**
	 * 選択難易度変更
	 */
	public void keyUp() {
		selectIdx =
				(selectIdx - 1) >= 0 ? selectIdx - 1 : LEVEL.length - 1;
	}

	/**
	 * 選択難易度変更
	 */
	public void keyDown() {
		selectIdx =
				(selectIdx + 1) <= LEVEL.length - 1 ? selectIdx + 1 : 0;
	}

	/**
	 * 選択難易度取得
	 *
	 * @return
	 */
	public int getLevel() {
		return LEVEL[selectIdx];
	}

	/**
	 * 描画処理
	 *
	 * @param g
	 */
	public void draw(Graphics g) {

		// 枠線を描画
		Graphics2D g2 = (Graphics2D)g;
		Stroke tmps = g2.getStroke();
		BasicStroke bs = new BasicStroke(5);
		g2.setStroke(bs);
		g2.setColor(Color.WHITE);
		g2.drawRoundRect(x, y, width, height, 30, 30);
		g2.setStroke(tmps);

		Font tmpf = g.getFont();
		g.setFont(new Font("Meiryo UI", Font.BOLD, 24));
		for(int i=0; i<MESSAGE.length; i++) {
			// 難易度文言の描画色を設定
			if(i == selectIdx) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			// 難易度文言を描画
			g.drawString(MESSAGE[i], x+30, y+(50+i*50));
		}
		g.setFont(tmpf);
	}
}
