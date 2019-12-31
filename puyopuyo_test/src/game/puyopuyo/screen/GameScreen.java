package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import game.puyopuyo.MainPanel;
import game.puyopuyo.controller.Controller;
import game.puyopuyo.parts.BaseAnimation;
import game.puyopuyo.parts.DropAnimation;
import game.puyopuyo.parts.Field;
import game.puyopuyo.parts.KumiPuyo;
import game.puyopuyo.parts.NextPuyo;
import game.puyopuyo.parts.Score;

public class GameScreen extends BaseScreen {

	/** 処理フェーズ：操作中 */
	private static final int PHASE_CONTROL = 1;
	/** 処理フェーズ：落下処理中 */
	private static final int PHASE_DROP = 2;
	/** 処理フェーズ：消滅処理中 */
	private static final int PHASE_VANISH = 3;

	/** コントローラー */
	private Controller controller;
	/** フィールド */
	private Field field;
	/** Nextぷよ */
	private NextPuyo nextPuyo;
	/** 組ぷよ */
	private KumiPuyo kumiPuyo;
	/** スコア */
	private Score score;
	/** アニメーション */
	private List<BaseAnimation> animation;
	/** 処理フェーズ */
	private int phase;

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
		// Nextぷよを生成
		nextPuyo = new NextPuyo(8*Field.TILE_SIZE, 1*Field.TILE_SIZE);
		// 組ぷよを生成
		kumiPuyo = new KumiPuyo(field, nextPuyo.pop());
		// スコアを生成
		score = new Score(11*Field.TILE_SIZE, 1*Field.TILE_SIZE);
		// アニメーション
		animation = new ArrayList<BaseAnimation>();
		// 処理フェーズを初期化
		phase = PHASE_CONTROL;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {

		if(phase == PHASE_CONTROL) { //----------------------------------------
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
					field.drop(animation);
					// 組ぷよを削除
					kumiPuyo = null;
					phase = PHASE_DROP;
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
		} else if(phase == PHASE_DROP) { //------------------------------------
			if(!field.isDrop()) {
				// 消滅処理
				if(field.vanish(animation)) {
					phase = PHASE_VANISH;
				} else if(field.isGameOver()) {
					// ポップアップを表示
					JOptionPane.showMessageDialog(null, "ゲームオーバー");
					// フィールドを初期化
					field.init();
				} else {
					// スコアを更新
					score.setScore(field.getScore());
					// 新しい組ぷよを生成
					kumiPuyo = new KumiPuyo(field, nextPuyo.pop());
					phase = PHASE_CONTROL;
				}
			}
		} else if(phase == PHASE_VANISH) { //----------------------------------
			if(!field.isVanish(animation)) {
				// 落下処理
				field.drop(animation);
				phase = PHASE_DROP;
			}
		}

		// アニメーション更新
		for(int i=0; i<animation.size(); i++) {
			BaseAnimation a = animation.get(i);
			a.update();
			if(!a.isRunning()) {
				if(a instanceof DropAnimation) {
					// 一時非表示を解除
					field.displayTile(((DropAnimation)a).getTo());
				}
				// 完了したアニーメーションを削除
				animation.remove(i);
				i--;
			}
		}
	}

	/**
	 * 描画処理
	 */
	@Override
	public void draw(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);

		// フィールドを描画
		field.draw(g);
		// Nextぷよを描画
		nextPuyo.draw(g);
		// 組ぷよを描画
		if(kumiPuyo != null) {
			kumiPuyo.draw(g);
		}
		// スコアを描画
		score.draw(g);
		// アニメーション描画
		for(BaseAnimation a : animation) {
			a.draw(g);
		}
	}
}
