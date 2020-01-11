package game.puyopuyo.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ReadyAnimation extends BaseAnimation {

	/** 基準座標x */
	private int x;
	/** 基準座標y */
	private int y;

	/** フレームカウント */
	private int frameCount;

	/**
	 * コンストラクタ
	 */
	public ReadyAnimation() {
		this(0, 0);
	}

	/**
	 * コンストラクタ
	 *
	 * @param x 基準座標x
	 * @param y 基準座標y
	 */
	public ReadyAnimation(int x, int y) {
		// 基準座標を設定
		this.x = x;
		this.y = y;
		// フレームカウントを初期化
		frameCount = 0;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {
		frameCount++;
		if(frameCount >= 60) {
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
		// 文字列を描画
		g.setColor(Color.WHITE);
		if(frameCount <= 45) {
			/// 適当に50フレームまではREADYを表示
			g.drawString("READY", x+80, y+240);
		} else {
			// 残りフレームはGOを表示
			g.drawString("GO", x+110, y+240);
		}
		// フォントを変更前に戻す
		g.setFont(tmp);
	}
}
