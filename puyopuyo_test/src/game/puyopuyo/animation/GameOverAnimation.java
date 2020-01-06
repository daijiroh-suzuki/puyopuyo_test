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
		// 描画フォントを設定
		g.setFont(new Font("Meiryo UI", Font.BOLD, 24));
		// フォント情報を取得
		FontMetrics fm = g.getFontMetrics();
		// アニメーション背景を描画
		g.setColor(Color.BLACK);
		g.fillRect(0, 240-fm.getHeight(), 256, fm.getHeight()+10);
		// ゲームオーバー文字列を描画
		g.setColor(Color.RED);
		g.drawString("GAME OVER", 60, 240);
		// フォントを変更前に戻す
		g.setFont(tmp);
	}
}
