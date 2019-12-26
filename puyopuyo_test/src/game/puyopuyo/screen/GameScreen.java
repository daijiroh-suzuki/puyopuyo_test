package game.puyopuyo.screen;

import java.awt.Graphics;

import game.puyopuyo.controller.Controller;
import game.puyopuyo.parts.Field;
import game.puyopuyo.parts.KumiPuyo;

public class GameScreen extends BaseScreen {

	private Controller controller;

	/** フィールド */
	private Field field;
	/** 組ぷよ */
	private KumiPuyo kumiPuyo;

	/**
	 * コンストラクタ
	 *
	 * @param controller
	 */
	public GameScreen(Controller controller) {
		// コントローラーを設定
		this.controller = controller;
		// フィールドを生成
		field = new Field();
		// 組ぷよを生成
		kumiPuyo = new KumiPuyo(field);
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {

		// キー操作
		if(controller.isKeyUp()) {
			// 上方向キー押下時
			kumiPuyo.move(KumiPuyo.DIR_UP);
			controller.setKeyUp(false);
		} else if(controller.isKeyDown()) {
			// 下方向キー押下時
			boolean isFixed = kumiPuyo.move(KumiPuyo.DIR_DOWN);
			if(isFixed) {
				// 落下処理
				field.drop();
				// 新しい組ぷよを生成
				kumiPuyo = new KumiPuyo(field);
			}
			controller.setKeyDown(false);
		} else if(controller.isKeyRight()) {
			// 右方向キー押下時
			kumiPuyo.move(KumiPuyo.DIR_RIGHT);
			controller.setKeyRight(false);
		} else if(controller.isKeyLeft()) {
			// 左方向キー押下時
			kumiPuyo.move(KumiPuyo.DIR_LEFT);
			controller.setKeyLeft(false);
		} else if(controller.isKeyA()) {
			kumiPuyo.turn();
			controller.setKeyA(false);
		}

		field.update();
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {
		// フィールドを描画
		field.draw(g);
		// 組ぷよを描画
		kumiPuyo.draw(g);
	}
}
