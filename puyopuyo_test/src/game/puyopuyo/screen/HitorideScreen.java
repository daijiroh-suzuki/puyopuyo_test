package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.puyopuyo.MainPanel;
import game.puyopuyo.animation.BaseAnimation;
import game.puyopuyo.animation.DropAnimation;
import game.puyopuyo.animation.GameOverAnimation;
import game.puyopuyo.animation.PauseAnimation;
import game.puyopuyo.animation.ReadyAnimation;
import game.puyopuyo.controller.Controller;
import game.puyopuyo.parts.Field;
import game.puyopuyo.parts.KumiPuyo;
import game.puyopuyo.parts.NextPuyo;
import game.puyopuyo.parts.Phase;
import game.puyopuyo.parts.Score;
import game.puyopuyo.parts.Stage;

public class HitorideScreen extends BaseScreen {

	/** コントローラー */
	private Controller controller;
	/** ステージ */
	private Stage stage;
	/** フィールド(1P) */
	private Field field1;
	/** フィールド(NPC) */
	private Field field2;
	/** NEXTぷよ(1P) */
	private NextPuyo nextPuyo1;
	/** NEXTぷよ(NPC) */
	private NextPuyo nextPuyo2;
	/** 組ぷよ(1P) */
	private KumiPuyo kumiPuyo1;
	/** 組ぷよ(NPC) */
	private KumiPuyo kumiPuyo2;
	/** スコア(1P) */
	private Score score1;
	/** スコア(NPC) */
	private Score score2;
	/** アニメーションリスト(1P) */
	private List<BaseAnimation> animation1;
	/** アニメーションリスト(NPC) */
	private List<BaseAnimation> animation2;
	/** 処理フェーズ(1P) */
	private Phase phase1;
	/** 処理フェーズ(NPC) */
	private Phase phase2;

	/** フレームカウント */
	private int frameCount = 0;
	/** 組ぷよが自動落下するフレーム数 */
	private int autoDropCount = 30;

	/** 一時停止フラグ */
	private boolean pause;
	/** 一時停止アニメーション */
	private PauseAnimation pa;
	/** ゲームオーバーアニメーション */
	private GameOverAnimation ga;

	/**
	 * コンストラクタ
	 *
	 * @param controller
	 */
	public HitorideScreen(Controller controller) {
		// コントローラーを設定
		this.controller = controller;
		// ステージを生成
		stage = new Stage();
		// フィールドを生成
		field1 = new Field(0, 0);
		field2 = new Field(384, 0);
		// スコアを生成
		score1 = new Score(8*Field.TILE_SIZE, 12*Field.TILE_SIZE);
		score2 = new Score(9*Field.TILE_SIZE, 13*Field.TILE_SIZE);
		// NEXTぷよを生成
		nextPuyo1 = new NextPuyo(224, 128, 4);
		nextPuyo2 = new NextPuyo(320, 128, 4);
		// アニメーションリストを生成
		animation1 = new ArrayList<BaseAnimation>();
		animation2 = new ArrayList<BaseAnimation>();
		// 準備アニメーションを追加
		animation1.add(new ReadyAnimation());
		animation2.add(new ReadyAnimation(384, 0));
		// 処理フェーズを初期化
		phase1 = Phase.READY;
		phase2 = Phase.READY;
		// 一時停止フラグ
		pause = false;
	}

	/**
	 * 更新処理
	 */
	@Override
	public void update() {

		// SELECTキー押下時
		if(controller.isKeySelect()) {
			if(!pause) {
				// 一時停止フラグON
				pause = true;
				// 一時停止アニメーションをリストに追加
				pa = new PauseAnimation();
				animation1.add(pa);
			} else {
				// 一時停止フラグOFF
				pause = false;
				pa.setRunning(false);
			}
			controller.setKeySelect(false);
		}
		// 一時停止中の場合は処理を中断
		if(pause) return;

		switch(phase1) {
		case READY:
			// 準備中フェーズ
			readyPhase();
			break;
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
		default:
			// とりあえず難易度選択はなし
			break;
		}

		// アニメーション更新
		for(int i=0; i<animation1.size(); i++) {
			BaseAnimation a = animation1.get(i);
			a.update();
			if(!a.isRunning()) {
				if(a instanceof DropAnimation) {
					// 一時非表示を解除
					field1.displayTile(((DropAnimation)a).getTo());
				}
				// 完了したアニメーションを削除
				animation1.remove(i);
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

		// ステージ(背景)を描画
		stage.drawBg(g);

		// フィールドを描画
		field1.draw(g);
		field2.draw(g);
		// スコアを描画
		score1.draw(g);
		score2.draw(g);

		// NEXTぷよを描画
		if(nextPuyo1 != null) {
			nextPuyo1.draw(g);
		}
		if(nextPuyo2 != null) {
			nextPuyo2.draw(g);
		}

		// 組ぷよを描画
		if(kumiPuyo1 != null) {
			kumiPuyo1.draw(g);
		}
		if(kumiPuyo2 != null) {
			kumiPuyo2.draw(g);
		}

		// ステージ(前景)を描画
		stage.drawFg(g);

		// アニメーション描画
		for(BaseAnimation a : animation1) {
			a.draw(g);
		}
		for(BaseAnimation a : animation2) {
			a.draw(g);
		}
	}

	/**
	 * 準備中フェーズ
	 */
	private void readyPhase() {
		// 準備中処理が完了したか？
		if(!isReady(animation1)) {
			// 組ぷよを生成
			kumiPuyo1 = new KumiPuyo(field1, nextPuyo1.pop());
			// 処理フェーズを操作中に変更
			phase1 = Phase.CONTROL;
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
			kumiPuyo1.move(KumiPuyo.DIR_UP);
			controller.setKeyUp(false);
		}
		// 下方向キー押下時 or 組ぷよ自動落下時
		if(controller.isKeyDown() || frameCount >= autoDropCount) {
			// フレーム貨運ｔおを初期化
			frameCount = 0;
			// 組ぷよを下移動
			boolean isFixed = kumiPuyo1.move(KumiPuyo.DIR_DOWN);
			if(isFixed) {
				// 落下処理
				field1.drop(animation1);
				// 組ぷよを削除
				kumiPuyo1 = null;
				// 処理フェーズを落下処理中に変更
				phase1 = Phase.DROP;
				return;
			}
			controller.setKeyDown(false);
		}
		// 右方向キー押下時
		if(controller.isKeyRight()) {
			// 組ぷよを右移動
			kumiPuyo1.move(KumiPuyo.DIR_RIGHT);
			controller.setKeyRight(false);
		}
		// 左方向キー押下時
		if(controller.isKeyLeft()) {
			// 組ぷよを左移動
			kumiPuyo1.move(KumiPuyo.DIR_LEFT);
			controller.setKeyLeft(false);
		}
		// Aキー押下時
		if(controller.isKeyA()) {
			// 組ぷよを右回転
			kumiPuyo1.turn(true);
			controller.setKeyA(false);
		}
		// Bキー押下時
		if(controller.isKeyB()) {
			// 組ぷよを左回転
			kumiPuyo1.turn(false);
			controller.setKeyB(false);
		}
	}

	/**
	 * 落下処理中フェーズ
	 */
	private void dropPhase() {
		// 落下処理が完了したか？
		if(!field1.isDrop(animation1)) {
			// 消滅処理
			if(field1.vanish(animation1)) {
				// 消滅対象ありの場合
				// スコアを更新
				score1.setScore(field1.getScore());
				// 処理フェーズを消滅処理中に変更
				phase1 = Phase.VANISH;
			} else if(field1.checkGameOver()) {
				// 消滅対象なし & ゲームオーバーマスにぷよが存在する場合
				// ゲームオーバーアニメーションをリストに追加
				ga = new GameOverAnimation();
				animation1.add(ga);
				// 処理フェーズをゲームオーバーに変更
				phase1 = Phase.GAMEOVER;
			} else {
				// 消滅対象なしの場合
				// 新しい組ぷよを生成
				kumiPuyo1 = new KumiPuyo(field1, nextPuyo1.pop());
				// 処理フェーズを操作中に変更
				phase1 = Phase.CONTROL;
			}
		}
	}

	/**
	 * 消滅処理中フェーズ
	 */
	private void vanishPhase() {
		// 消滅処理が完了したか？
		if(!field1.isVanish(animation1)) {
			// 落下処理
			field1.drop(animation1);
			// 処理フェーズを落下処理中に変更
			phase1 = Phase.DROP;
		}
	}

	/**
	 * ゲームオーバーフェーズ
	 */
	private void gameOverPhase() {
		// Startキー押下時
		if(controller.isKeyStart()) {
			// ゲームオーバーアニメーションを削除
			ga.setRunning(false);
			// フィールドを初期化
			field1.init();
			// スコアを初期化
			score1.init();
			// 準備アニメーションを追加
			animation1.add(new ReadyAnimation());
			// 処理フェーズを操作中に変更
			phase1 = Phase.READY;
			controller.setKeyStart(false);
		}
	}

	/** 準備中判定
	 *
	 * @param list
	 * @return ture:処理中、false:処理完了
	 */
	private boolean isReady(List<BaseAnimation> list) {
		for(BaseAnimation a : list) {
			if(a instanceof ReadyAnimation) {
				return true;
			}
		}
		return false;
	}
}
