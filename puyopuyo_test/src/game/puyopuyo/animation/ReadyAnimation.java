package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ReadyAnimation extends BaseAnimation {

	/** フレームカウント */
	private int frameCount;

	/**
	 * コンストラクタ
	 */
	public ReadyAnimation() {
		frameCount = 0;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 30) {
			running = false;
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		// 変更前のフォントを取得
		Font tmp = g.getFont();
		// 描画フォントを設定
		g.setFont(new Font("Meiryo UI", Font.BOLD, 24));
		// フォント情報を取得
		FontMetrics fm = g.getFontMetrics();
		// アニメーション背景を描画
		g.setColor(Color.BLACK);
		g.fillRect(0, 240-fm.getHeight(), 256, fm.getHeight()+10);
		// 文字列を描画
		g.setColor(Color.WHITE);
		if(frameCount <= 25) {
			/// 適当に25フレームまではREADYを表示
			g.drawString("READY", 80, 240);
		} else {
			// 残りフレームはGOを表示
			g.drawString("GO", 110, 240);
		}
		// フォントを変更前に戻す
		g.setFont(tmp);
	}
}
