package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.puyopuyo.MainPanel;
import game.puyopuyo.common.CommonUtil;
import game.puyopuyo.common.ImageManager;
import game.puyopuyo.controller.Controller;

public class ModeScreen extends BaseScreen {

	private static final int MODE_W = 111;
	private static final int MODE_H = 88;

	/** コントローラー */
	private Controller  controller;
	/** 選択中のゲームモード */
	private int select;

	/**
	 * コンストラクタ
	 *
	 * @param controller
	 */
	public ModeScreen(Controller controller) {
		// コントローラーを設定
		this.controller = controller;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update(){
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
				(MainPanel.WIDTH / 2) - (MODE_W * 3 / 2),
				(MainPanel.HEIGHT / 2) - (MODE_H * 3 / 2),
				(MainPanel.WIDTH / 2) - (MODE_W * 3 / 2) + MODE_W * 3,
				(MainPanel.HEIGHT / 2) - (MODE_H * 3 / 2) + MODE_H * 3,
				0,
				100,
				0 + 111,
				100 + 88,
				null);

		// 左矢印を描画
		g.drawImage(ImageManager.modeImage,
				30,
				160,
				30 + 32 * 3,
				160 + 50 * 3,
				0,
				0,
				32,
				50,
				null);

		// 右矢印を描画
		g.drawImage(ImageManager.modeImage,
				515,
				160,
				515 + 32 * 3,
				160 + 50 * 3,
				0,
				50,
				32,
				100,
				null);
	}
}
