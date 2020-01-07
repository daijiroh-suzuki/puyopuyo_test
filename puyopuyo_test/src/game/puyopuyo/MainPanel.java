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
import game.puyopuyo.screen.ModeScreen;

public class MainPanel extends JPanel implements Runnable {

	/** パネルの幅 */
	public static final int WIDTH = 640;
	/** パネルの高さ */
	public static final int HEIGHT = 480;

	/** 1秒間に処理するフレーム数 */
	private static final long FPS = 60;
	/** 1フレームで使える時間(ナノ秒) */
	private static final long PERIOD = (long)(((double)1 /FPS) * 1000000000L); // 約60FPS
	/** FPS計算間隔(ナノ秒) */
	private static final long MAX_STATS_INTERVAL = 1000000000L;

	/** ダブルバッファリング(db)用Graphics */
	private Graphics dbg;
	/** ダブルバッファリング(db)用Image */
	private Image dbImage = null;

	/** コントローラー */
	private Controller controller;
	/** 表示画面 */
	private BaseScreen screen;

	/** フレームカウント */
	private long frameCount = 0L;
	/** FPS再計算までの累計時間(ナノ秒) */
	private long calcInterval = 0L;
	/** 前回FPS計算時間(ナノ秒) */
	private long prevCalcTime = 0L;

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
		screen = new ModeScreen(controller);

		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;

		// 更新・描画処理前の時間を取得
		beforeTime = System.nanoTime();
		prevCalcTime = beforeTime;

		while(true) {
			// ゲーム状態を更新
			gameUpdate();
			// バッファにレンダリング
			gameRender();
			// バッファを画面に描画
			printScreen();

			// 更新・描画処理後の時間を取得
			afterTime = System.nanoTime();
			// 更新・描画処理時間を計算
			timeDiff = afterTime - beforeTime;

			// 休止時間を計算
			sleepTime  = (PERIOD - timeDiff) - overSleepTime;

			if(sleepTime > 0) {
				// 休止時間がとれる場合
				try {
					Thread.sleep(sleepTime / 1000000L); // ナノ秒 ->ミリ秒
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				// sleep()の誤差
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;

			} else {
				// 更新・描画処理で時間を使い切ってしまい
				// 休止時間がとれない場合
				overSleepTime = 0L;
				// 休止なしが16回以上続いたら
				if(++noDelays >= 16) {
					Thread.yield(); // 他スレッドにCPUを譲る
					noDelays = 0;
				}
			}
			// 更新・描画処理前の時間を取得
			beforeTime = System.nanoTime();
			// FPS計算
			calcFPS();
		}
	}

	/**
	 * ゲーム状態を更新
	 */
	private void gameUpdate() {
		// ゲーム画面を更新する
		screen.update();

		// 画面遷移
		BaseScreen next = screen.getNext();
		if(next != null) {
			screen = next;
		}
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

	/**
	 * 実際のFPSを計算する
	 */
	private void calcFPS() {
		frameCount++;
		calcInterval += PERIOD;

		// 1秒おきにFPSを再計算する
		if(calcInterval >= MAX_STATS_INTERVAL) {
			long timeNow = System.nanoTime();
			// 実際の経過時間を計測
			long realElapsedTime = timeNow - prevCalcTime;

			// FPSを計算
			// realElapsedTimeの単位はナノ秒なので秒に変換する
			double fps = ((double)frameCount / realElapsedTime) * 1000000000L;
			System.out.println(fps);

			frameCount = 0L;
			calcInterval = 0L;
			prevCalcTime = timeNow;
		}
	}
}
