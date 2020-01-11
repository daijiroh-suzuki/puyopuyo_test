package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOverAnimation extends BaseAnimation {

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** 描画色 */
	private Color clr;

	/**
	 * コンストラクタ
	 */
	public GameOverAnimation() {
		this(0, 0);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 */
	public GameOverAnimation(int x, int y) {
		// 基準座標を設定
		this.x = x;
		this.y = y;

		clr = new Color(0, 0, 0, 60);
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(clr);
		g.fillRect(32, 64, 192, 416);

		// 変更前のフォントを取得
		Font tmp = g.getFont();
		// 描画フォントを設定
		g.setFont(new Font("Meiryo UI", Font.BOLD, 24));
		// ゲームオーバー文字列を描画
		g.setColor(Color.RED);
		g.drawString("GAME OVER", x+50, y+240);
		// フォントを変更前に戻す
		g.setFont(tmp);
	}
}
