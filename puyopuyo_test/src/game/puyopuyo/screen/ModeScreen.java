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

	/** モード選択画像：基準座標y */
	private static final int IMG_MODE_Y = 100;
	/** モード選択画像：幅 */
	private static final int IMG_MODE_W = 112;
	/** モード選択画像：高さ */
	private static final int IMG_MODE_H = 88;

	/** 右矢印画像：基準座標y */
	private static final int IMG_ARROWR_Y = 50;
	/** 右矢印画像：幅 */
	private static final int IMG_ARROWR_W = 32;
	/** 右矢印画像：高さ */
	private static final int IMG_ARROWR_H = 50;

	/** 左矢印画像：基準座標y */
	private static final int IMG_ARROWL_Y = 0;
	/** 左矢印画像：幅 */
	private static final int IMG_ARROWL_W = 32;
	/** 左矢印画像：高さ */
	private static final int IMG_ARROWL_H = 50;

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

		// モードを描画(3倍表示)
		g.drawImage(ImageManager.modeImage,
				(MainPanel.WIDTH  / 2) - (IMG_MODE_W * 3 / 2),
				(MainPanel.HEIGHT / 2) - (IMG_MODE_H * 3 / 2),
				(MainPanel.WIDTH  / 2) - (IMG_MODE_W * 3 / 2) + IMG_MODE_W * 3,
				(MainPanel.HEIGHT / 2) - (IMG_MODE_H * 3 / 2) + IMG_MODE_H * 3,
				select * IMG_MODE_W,
				IMG_MODE_Y,
				select * IMG_MODE_W + IMG_MODE_W,
				IMG_MODE_Y + IMG_MODE_H,
				null);

		if(select != 0) {
			// 左矢印を描画(3倍表示)
			g.drawImage(ImageManager.modeImage,
					30,
					160,
					30  + IMG_ARROWL_W * 3,
					160 + IMG_ARROWL_H * 3,
					(frameCount / 10) % 13 * IMG_ARROWL_W,
					IMG_ARROWL_Y,
					(frameCount / 10) % 13 * IMG_ARROWL_W + IMG_ARROWL_W,
					IMG_ARROWL_Y + IMG_ARROWL_H,
					null);
		}

		if(select != MODE_NUM -1) {
			// 右矢印を描画(3倍表示)
			g.drawImage(ImageManager.modeImage,
					515,
					160,
					515 + IMG_ARROWR_W * 3,
					160 + IMG_ARROWR_H * 3,
					(frameCount / 10) % 13 * IMG_ARROWR_W,
					IMG_ARROWR_Y,
					(frameCount / 10) % 13 * IMG_ARROWR_W + IMG_ARROWR_W,
					IMG_ARROWR_Y + IMG_ARROWR_H,
					null);
		}
	}
}
