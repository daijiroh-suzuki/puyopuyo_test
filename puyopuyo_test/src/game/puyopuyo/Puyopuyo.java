package game.puyopuyo;

import javax.swing.JFrame;

public class Puyopuyo extends JFrame {

	public Puyopuyo() {
		// フレームのタイトルを設定
		setTitle("ぷよぷよもどき");
		// メインパネルを生成
		MainPanel panel = new MainPanel();
		// メインパネルをフレームに追加
		getContentPane().add(panel);

		pack();
	}

	/**
	 * エントリポイント
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// フレームを生成
		Puyopuyo frame = new Puyopuyo();
		// 閉じるボタン押下時の処理を設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// フレームの表示位置を設定
		frame.setLocationRelativeTo(null);
		// フレームを表示
		frame.setVisible(true);
	}
}
