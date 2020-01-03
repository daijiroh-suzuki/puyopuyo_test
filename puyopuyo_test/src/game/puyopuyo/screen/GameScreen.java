package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.puyopuyo.MainPanel;
import game.puyopuyo.animation.BaseAnimation;
import game.puyopuyo.animation.DropAnimation;
import game.puyopuyo.controller.Controller;
import game.puyopuyo.parts.Field;
import game.puyopuyo.parts.KumiPuyo;
import game.puyopuyo.parts.NextPuyo;
import game.puyopuyo.parts.Score;

public class GameScreen extends BaseScreen {

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
	private Phase phase;

	/** フレームカウント */
	private int frameCount;
	/** 組ぷよが自動落下するフレーム数 */
	private int autoDropCount = 10;

	/** 処理フェーズ列挙 */
	private enum Phase {
		/** 操作中 */
		CONTROL,
		/** 落下処理中 */
		DROP,
		/** 消滅処理中 */
		VANISH,
		/** ゲームオーバー */
		GAMEOVER
	}

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
		phase = Phase.CONTROL;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {

		switch(phase) {
		case CONTROL:
			// 操作中フェーズ
			controlPhase();
			break;
		case DROP:
			// 落下処理中フェーズ
			dropPhase();
			break;
		case VANISH:
			// 消滅処理中フェーズ
			vanishPhase();
			break;
		case GAMEOVER:
			// ゲームオーバーフェーズ
			gameOverPhase();
			break;
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

	/**
	 * 操作中フェーズ
	 */
	private void controlPhase() {

		// フレームカウントを加算
		frameCount++;

		// 上方向キー押下時（削除予定）
		if(controller.isKeyUp()) {
			// 組ぷよを上移動
			kumiPuyo.move(KumiPuyo.DIR_UP);
			controller.setKeyUp(false);
		}
		// 下方向キー押下時 or 組ぷよ自動落下時
		if(controller.isKeyDown() || frameCount >= autoDropCount) {
			// フレームカウントを初期化
			frameCount = 0;
			// 組ぷよを下移動
			boolean isFixed = kumiPuyo.move(KumiPuyo.DIR_DOWN);
			if(isFixed) {
				// 落下処理
				field.drop(animation);
				// 組ぷよを削除
				kumiPuyo = null;
				// 処理フェーズを落下処理中に変更
				phase = Phase.DROP;
			}
			controller.setKeyDown(false);
		}
		// 右方向キー押下時
		if(controller.isKeyRight()) {
			// 組ぷよを右移動
			kumiPuyo.move(KumiPuyo.DIR_RIGHT);
			controller.setKeyRight(false);
		}
		// 左方向キー押下時
		if(controller.isKeyLeft()) {
			// 組ぷよを左移動
			kumiPuyo.move(KumiPuyo.DIR_LEFT);
			controller.setKeyLeft(false);
		}
		// Aキー押下時
		if(controller.isKeyA()) {
			// 組ぷよを右回転
			kumiPuyo.turn(true);
			controller.setKeyA(false);
		}
		// Bキー押下時
		if(controller.isKeyB()) {
			// 組ぷよを左回転
			kumiPuyo.turn(false);
			controller.setKeyB(false);
		}
	}

	/**
	 * 落下処理中フェーズ
	 */
	private void dropPhase() {
		// 落下処理が完了したか？
		if(!field.isDrop(animation)) {
			// 消滅処理
			if(field.vanish(animation)) {
				// 消滅対象ありの場合
				// スコアを更新
				score.setScore(field.getScore());
				// 処理フェーズを消滅処理中に変更
				phase = Phase.VANISH;
			} else if(field.checkGameOver(animation)) {
				// 消滅対象なし & ゲームオーバーマスにぷよが存在する場合
				// 処理フェースをゲームオーバーに変更
				phase = Phase.GAMEOVER;
			} else {
				// 消滅対象なしの場合
				// 新しい組ぷよを生成
				kumiPuyo = new KumiPuyo(field, nextPuyo.pop());
				// 処理フェースを操作中に変更
				phase = Phase.CONTROL;
			}
		}
	}

	/**
	 * 消滅処理中フェーズ
	 */
	private void vanishPhase() {
		// 消滅処理が完了したか？
		if(!field.isVanish(animation)) {
			// 落下処理
			field.drop(animation);
			// 処理フェーズを落下処理中に変更
			phase = Phase.DROP;
		}
	}

	/**
	 * ゲームオーバーフェーズ
	 */
	private void gameOverPhase() {
		// ゲームオーバーアニメーションが完了したか？
		if(!field.isGameOver(animation)) {
			// フィールドを初期化
			field.init();
			// Nextぷよを生成
			nextPuyo = new NextPuyo(8*Field.TILE_SIZE, 1*Field.TILE_SIZE);
			// 組ぷよを生成
			kumiPuyo = new KumiPuyo(field, nextPuyo.pop());
			// スコアを生成
			score = new Score(11*Field.TILE_SIZE, 1*Field.TILE_SIZE);
			// 処理フェーズを操作中に変更
			phase = Phase.CONTROL;
		}
	}
}
