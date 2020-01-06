package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class PauseAnimation extends BaseAnimation {

	/**
	 * コンストラクタ
	 */
	public PauseAnimation() {}

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
		// 変更前のフォントを取得
		Font tmp = g.getFont();
		g.setFont(new Font("Meiryo UI", Font.BOLD, 24));
		FontMetrics fm = g.getFontMetrics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 240-fm.getHeight(), 256, fm.getHeight()+10);

		g.setColor(Color.WHITE);
		g.drawString("PAUSE", 80, 240);

		// フォントを元に戻す
		g.setFont(tmp);
	}
}
