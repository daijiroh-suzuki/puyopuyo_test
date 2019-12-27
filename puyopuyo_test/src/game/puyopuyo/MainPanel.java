package game.puyopuyo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import game.puyopuyo.common.ImageManager;
import game.puyopuyo.controller.Controller;
import game.puyopuyo.screen.BaseScreen;
import game.puyopuyo.screen.GameScreen;

public class MainPanel extends JPanel implements Runnable {

	/** パネルの幅 */
	public static final int WIDTH = 640;
	/** パネルの高さ */
	public static final int HEIGHT = 480;

	/** ダブルバッファリング(db)用Graphics */
	private Graphics dbg;
	/** ダブルバッファリング(db)用Image */
	private Image dbImage = null;

	/** コントローラー */
	private Controller controller;
	/** 表示画面 */
	private BaseScreen screen;

	/**
	 * コンストラクタ
	 */
	public MainPanel() {
		// パネルサイズの設定
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// フォーカスを設定
		setFocusable(true);

		// コントローラーオブジェクトを生成
		controller = new Controller();

		// キーリスナーの追加
		addKeyListener(controller);

		// ゲームループ開始
		Thread gameLoop = new Thread(this);
		gameLoop.start();
	}

	/**
	 * ゲームループ
	 */
	@Override
	public void run() {
		// 画像ファイルをロード
		ImageManager.init();
		// ゲーム画面を生成
		screen = new GameScreen(controller);

		while(true) {
			// ゲーム状態を更新
			gameUpdate();
			// バッファにレンダリング
			gameRender();
			// バッファを画面に描画
			printScreen();

			// 休止
			try {
				Thread.sleep(60);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ゲーム状態を更新
	 */
	private void gameUpdate() {
		// ゲーム画面を更新する
		screen.update();
	}

	/**
	 * バッファにレンダリング（ダブルバッファリング）
	 */
	private void gameRender() {
		// 初回の呼び出し時にダブルバッファリング用オブジェクトを作成
		if(dbImage == null) {
			// バッファイメージを生成
			dbImage = createImage(WIDTH, HEIGHT);
			if(dbImage == null) {
				System.out.println("dbImageが作成できません。");
				return;
			} else {
				// バッファイメージの描画オブジェクト
				dbg = dbImage.getGraphics();
			}
		}

		// バッファをクリアする
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		// ゲーム画面をバッファに描画
		screen.draw(dbg);
	}

	/**
	 * バッファを画面に描画
	 */
	private void printScreen() {
		try {
			// グラフィックスオブジェクトを取得
			Graphics g = getGraphics();
			if((g != null) && (dbImage != null)) {
				// バッファイメージを画面に描画
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			if(g != null) {
				// グラフィックスオブジェクトを破棄
				g.dispose();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
