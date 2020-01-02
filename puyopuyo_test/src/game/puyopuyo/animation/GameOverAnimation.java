package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GameOverAnimation extends BaseAnimation {

	/** フレームカウント */
	private int frameCount;
	/** 描画色 */
	private Color clr;

	public GameOverAnimation() {
		this.frameCount = 0;
		this.running = true;
		clr = new Color(0, 0, 0, 60);
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 100) {
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(clr);
		g.fillRect(0, 0, 256, 480);

		// 変更前のフォントを取得
		Font tmp = g.getFont();
		g.setFont(new Font("Arial", Font.BOLD, 24));
		FontMetrics fm = g.getFontMetrics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 240-fm.getHeight(), 256, fm.getHeight()+10);

		g.setColor(Color.RED);
		g.drawString("Game Over", 60, 240);
		// フォントを元に戻す
		g.setFont(tmp);
	}
}
