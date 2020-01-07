package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.puyopuyo.MainPanel;
import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.controller.Controller;

public class ModeScreen extends BaseScreen {

	/** 選択可能なゲームモード数 */
	private static final int MODE_NUM = 4;
//	/** ゲームモード：ひとりでぷよぷよ */
//	private static final int MODE_HITORIDE = 0;
//	/** ゲームモード：ふたりでぷよぷよ */
//	private static final int MODE_FUTARIDE = 1;
	/** ゲームモード：とことんぷよぷよ */
	private static final int MODE_TOKOTON  = 2;
//	/** ゲームモード：おぷしょん */
//	private static final int MODE_OPTION   = 3;

	/** コントローラー */
	private Controller  controller;
	/** 選択ゲームモード */
	private int select;
	/** フレームカウント */
	private int frameCount;

	/**
	 * コンストラクタ
	 *
	 * @param controller
	 */
	public ModeScreen(Controller controller) {
		// コントローラーを設定
		this.controller = controller;
		// 選択ゲームモードを初期化
		select = 0;
		// フレームカウントを初期化
		frameCount = 0;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update(){

		frameCount++;
		if(frameCount >= 130) {
			frameCount = 0;
		}

		// 右方向キー押下時
		if(controller.isKeyRight()) {
			if(select + 1 < MODE_NUM) {
				select++;
			}
			controller.setKeyRight(false);
		}
		// 左方向キー押下時
		if(controller.isKeyLeft()) {
			if(select - 1 >= 0) {
				select--;
			}
			controller.setKeyLeft(false);
		}
		// Startキー押下時
		if(controller.isKeyStart()) {
			if(select == MODE_TOKOTON) {
				next = new TokotonScreen(controller);
			}
			controller.setKeyStart(false);
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		// 背景を描画
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);

		g.setColor(Color.WHITE);
		CommonUtil.drawString("ゲームモードを選択してください", 10, 50, new Font("Meiryo UI", Font.BOLD, 24), g);

		// モードを描画
		g.drawImage(ImageManager.modeImage,
				(MainPanel.WIDTH / 2) - (112 * 3 / 2),
				(MainPanel.HEIGHT / 2) - (88 * 3 / 2),
				(MainPanel.WIDTH / 2) - (112 * 3 / 2) + 112 * 3,
				(MainPanel.HEIGHT / 2) - (88 * 3 / 2) + 88 * 3,
				select * 112,
				100,
				select * 112 + 112,
				100 + 88,
				null);

		if(select != 0) {
			// 左矢印を描画
			g.drawImage(ImageManager.modeImage,
					30,
					160,
					30 + 32 * 3,
					160 + 50 * 3,
					(frameCount / 10) % 13 * 32,
					0,
					(frameCount / 10) % 13 * 32 + 32,
					50,
					null);
		}

		if(select != MODE_NUM -1) {
			// 右矢印を描画
			g.drawImage(ImageManager.modeImage,
					515,
					160,
					515 + 32 * 3,
					160 + 50 * 3,
					(frameCount / 10) % 13 * 32,
					50,
					(frameCount / 10) % 13 * 32+ 32,
					100,
					null);
		}
	}
}
