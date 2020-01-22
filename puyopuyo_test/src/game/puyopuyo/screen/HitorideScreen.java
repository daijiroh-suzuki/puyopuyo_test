package game.puyopuyo.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import game.puyopuyo.parts.PlayerSide;
import game.puyopuyo.parts.Score;
import game.puyopuyo.parts.Stage;
import game.puyopuyo.parts.YokokuPuyo;

public class HitorideScreen extends BaseScreen {

	/** コントローラー */
	private Controller controller;
	/** ステージ */
	private Stage stage;
	/** プレイヤーセット(1P) */
	private PlayerSet player;
	/** プレイヤーセット(NPC) */
	private PlayerSet npc;

	/** 組ぷよが自動落下するフレーム数 */
	private int autoDropCount = 30;

	/** 一時停止フラグ */
	private boolean pause;
	/** 一時停止アニメーション */
	private PauseAnimation pa;
	/** ゲームオーバーアニメーション */
	private GameOverAnimation ga;

	/**
	 * プレイヤーセット
	 */
	private class PlayerSet {
		/** フィールド */
		public Field field;
		/** NEXTぷよ */
		public NextPuyo nextPuyo;
		/** 組ぷよ */
		public KumiPuyo kumiPuyo;
		/** 予告ぷよ */
		public YokokuPuyo yokokuPuyo;
		/** 相手の予告ぷよ */
		public YokokuPuyo enemyYokoku;
		/** スコア */
		public Score score;
		/** アニメーションリスト */
		public List<BaseAnimation> animation;
		/** 処理フェーズ */
		public Phase phase;
		/** フレームカウント */
		public int frameCount = 0;
	}

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

		// プレイヤーセット(1P)を生成
		player            = new PlayerSet();
		player.field      = new Field(0, 0);
		player.nextPuyo   = new NextPuyo(224, 128, 4);
		player.yokokuPuyo = new YokokuPuyo(32, 32);
		player.score      = new Score(8*Field.TILE_SIZE, 12*Field.TILE_SIZE);
		player.animation  = new ArrayList<BaseAnimation>();
		player.phase      = Phase.READY;

		// プレイヤーセット(NPC)を生成
		npc = new PlayerSet();
		npc.field      = new Field(384, 0, PlayerSide.PLAYER2);
		npc.nextPuyo   = new NextPuyo(320, 128, 4, PlayerSide.PLAYER2);
		npc.yokokuPuyo = new YokokuPuyo(416, 32);
		npc.score      = new Score(9*Field.TILE_SIZE, 13*Field.TILE_SIZE, PlayerSide.PLAYER2);
		npc.animation  = new ArrayList<BaseAnimation>();
		npc.phase      = Phase.READY;

		// 相手の予告ぷよを設定
		player.enemyYokoku = npc.yokokuPuyo;
		npc.enemyYokoku = player.yokokuPuyo;

		// 準備アニメーションを追加
		player.animation.add(new ReadyAnimation());
		npc.animation.add(new ReadyAnimation(384, 0));

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
				player.animation.add(pa);
			} else {
				// 一時停止フラグOFF
				pause = false;
				pa.setRunning(false);
			}
			controller.setKeySelect(false);
		}
		// 一時停止中の場合は処理を中断
		if(pause) return;

		// === プレイヤー ===
		switch(player.phase) {
		case READY:
			// 準備中フェーズ
			readyPhase(player);
			break;
		case CONTROL:
			// 操作中フェーズ
			controlPhase(player);
			break;
		case DROP:
			// 落下処理中フェーズ
			dropPhase(player);
			break;
		case VANISH:
			// 消滅処理中フェーズ
			vanishPhase(player);
			break;
		case GAMEOVER:
			// ゲームオーバーフェーズ
			gameOverPhase(player);
			break;
		default:
			// とりあえず難易度選択はなし
			break;
		}

		// === NPC ===
		switch(npc.phase) {
		case READY:
			// 準備中フェーズ
			readyPhase(npc);
			break;
		case CONTROL:
			// 操作中フェーズ
			controlPhaseNPC(npc);
			break;
		case DROP:
			// 落下処理中フェーズ
			dropPhase(npc);
			break;
		case VANISH:
			// 消滅処理中フェーズ
			vanishPhase(npc);
			break;
		case GAMEOVER:
			// ゲームオーバーフェーズ
			gameOverPhase(npc);
			break;
		default:
			// とりあえず難易度選択はなし
			break;
		}

		// アニメーション更新
		updateAnimation(player);
		updateAnimation(npc);

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
		player.field.draw(g);


		npc.field.draw(g);
		// スコアを描画
		player.score.draw(g);
		npc.score.draw(g);

		// NEXTぷよを描画
		if(player.nextPuyo != null) {
			player.nextPuyo.draw(g);
		}
		if(npc.nextPuyo != null) {
			npc.nextPuyo.draw(g);
		}

		// 組ぷよを描画
		if(player.kumiPuyo != null) {
			player.kumiPuyo.draw(g);
		}
		if(npc.kumiPuyo != null) {
			npc.kumiPuyo.draw(g);
		}

		// ステージ(前景)を描画
		stage.drawFg(g);

		// 予告ぷよを描画
		player.yokokuPuyo.draw(g);
		npc.yokokuPuyo.draw(g);

		// アニメーション描画
		for(BaseAnimation a : player.animation) {
			a.draw(g);
		}
		for(BaseAnimation a : npc.animation) {
			a.draw(g);
		}
	}

	/**
	 * 準備中フェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void readyPhase(PlayerSet p) {
		// 準備中処理が完了したか？
		if(!isReady(p.animation)) {
			// 組ぷよを生成
			p.kumiPuyo = new KumiPuyo(p.field.getX(), p.field.getY(),
					p.field, p.nextPuyo.pop());
			// 処理フェーズを操作中に変更
			p.phase = Phase.CONTROL;
		}
	}

	/**
	 * 操作中フェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void controlPhase(PlayerSet p) {

		// フレームカウントを加算
		p.frameCount++;

		// 上方向キー押下時（削除予定）
		if(controller.isKeyUp()) {
			// 組ぷよを上移動
			p.kumiPuyo.move(KumiPuyo.DIR_UP);
			controller.setKeyUp(false);
		}
		// 下方向キー押下時 or 組ぷよ自動落下時
		if(controller.isKeyDown() || p.frameCount >= autoDropCount) {
			// フレームカウントを初期化
			p.frameCount = 0;
			// 組ぷよを下移動
			boolean isFixed = p.kumiPuyo.move(KumiPuyo.DIR_DOWN);
			if(isFixed) {
				// 落下処理
				p.field.drop(p.animation);
				// 組ぷよを削除
				p.kumiPuyo = null;
				// 処理フェーズを落下処理中に変更
				p.phase = Phase.DROP;
				return;
			}
			controller.setKeyDown(false);
		}
		// 右方向キー押下時
		if(controller.isKeyRight()) {
			// 組ぷよを右移動
			p.kumiPuyo.move(KumiPuyo.DIR_RIGHT);
			controller.setKeyRight(false);
		}
		// 左方向キー押下時
		if(controller.isKeyLeft()) {
			// 組ぷよを左移動
			p.kumiPuyo.move(KumiPuyo.DIR_LEFT);
			controller.setKeyLeft(false);
		}
		// Aキー押下時
		if(controller.isKeyA()) {
			// 組ぷよを右回転
			p.kumiPuyo.turn(true);
			controller.setKeyA(false);
		}
		// Bキー押下時
		if(controller.isKeyB()) {
			// 組ぷよを左回転
			p.kumiPuyo.turn(false);
			controller.setKeyB(false);
		}
	}

	/**
	 * 操作中フェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void controlPhaseNPC(PlayerSet p) {

		// フレームカウントを加算
		p.frameCount++;

		// 組ぷよ自動落下
		if(p.frameCount % autoDropCount == 0) {
			// 組ぷよを下移動
			boolean isFixed = p.kumiPuyo.move(KumiPuyo.DIR_DOWN);
			if(isFixed) {
				// 落下処理
				p.field.drop(p.animation);
				// 組ぷよを削除
				p.kumiPuyo = null;
				// 処理フェーズを落下処理中に変更
				p.phase = Phase.DROP;
				return;
			}
		}

		if(p.frameCount >= 50) {
			// フレームカウントを初期化
			p.frameCount = 0;
			// NPC行動をランダムに取得
			int action = new Random().nextInt(5);
			if(action == 0) {
				// 組ぷよを下移動
				boolean isFixed = p.kumiPuyo.move(KumiPuyo.DIR_DOWN);
				if(isFixed) {
					// 落下処理
					p.field.drop(p.animation);
					// 組ぷよを削除
					p.kumiPuyo = null;
					// 処理フェーズを落下処理中に変更
					p.phase = Phase.DROP;
					return;
				}
			} else if(action == 1) {
				// 組ぷよを右移動
				p.kumiPuyo.move(KumiPuyo.DIR_RIGHT);
			} else if(action == 2) {
				// 組ぷよを左移動
				p.kumiPuyo.move(KumiPuyo.DIR_LEFT);
			} else if(action == 3) {
				// 組ぷよを右回転
				p.kumiPuyo.turn(true);
			} else if(action == 4) {
				// 組ぷよを左回転
				p.kumiPuyo.turn(false);
			}
		}

	}

	/**
	 * 落下処理中フェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void dropPhase(PlayerSet p) {
		// 落下処理が完了したか？
		if(!p.field.isDrop(p.animation)) {
			// 消滅処理
			if(p.field.vanish(p.animation)) {
				// 消滅対象ありの場合
				// スコアを更新
				p.score.addScore(p.field.getScore());
				// 相手の予告ぷよを更新
				p.enemyYokoku.addCount(p.field.getScore());
				// 連鎖開始時->相手の予告ぷよをロック
				p.enemyYokoku.setLock(true);
				// 処理フェーズを消滅処理中に変更
				p.phase = Phase.VANISH;

			} else if(p.field.checkGameOver()) {
				// 消滅対象なし & ゲームオーバーマスにぷよが存在する場合
				// ゲームオーバーアニメーションをリストに追加
				ga = new GameOverAnimation(p.field.getX(), p.field.getY());
				p.animation.add(ga);
				// 処理フェーズをゲームオーバーに変更
				p.phase = Phase.GAMEOVER;

			} else {
				// 消滅対象なしの場合
				// 連鎖終了->相手の予告ぷよのロック解除
				p.enemyYokoku.setLock(false);
				if(p.yokokuPuyo.getCount() != 0 && !p.yokokuPuyo.isLock()) {
					// 予告ぷよありの場合
					// おじゃまぷよを追加
					p.yokokuPuyo.subCount(p.field.addOjamaPuyo(p.yokokuPuyo.getCount()));
					// 処理フェーズを消滅処理中に変更
					p.phase = Phase.VANISH;
				} else {
					// 予告ぷよなしの場合
					// 新しい組ぷよを生成
					p.kumiPuyo = new KumiPuyo(p.field.getX(), p.field.getY(),
							p.field, p.nextPuyo.pop());
					// 処理フェーズを操作中に変更
					p.phase = Phase.CONTROL;
				}
			}
		}
	}

	/**
	 * 消滅処理中フェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void vanishPhase(PlayerSet p) {
		// 消滅処理が完了したか？
		if(!p.field.isVanish(p.animation)) {
			// 落下処理
			p.field.drop(p.animation);
			// 処理フェーズを落下処理中に変更
			p.phase = Phase.DROP;
		}
	}

	/**
	 * ゲームオーバーフェーズ
	 *
	 * @param p プレイヤーセット
	 */
	private void gameOverPhase(PlayerSet p) {
		// Startキー押下時
		if(controller.isKeyStart()) {
			// ゲームオーバーアニメーションを削除
			ga.setRunning(false);
			// フィールドを初期化
			p.field.init();
			// スコアを初期化
			p.score.init();
			// 準備アニメーションを追加
			p.animation.add(new ReadyAnimation(p.field.getX(), p.field.getY()));
			// 処理フェーズを操作中に変更
			p.phase = Phase.READY;
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

	/**
	 * アニメーション更新
	 *
	 * @param p プレイヤーセット
	 */
	private void updateAnimation(PlayerSet p) {

		for(int i=0; i<p.animation.size(); i++) {
			BaseAnimation a = p.animation.get(i);
			a.update();
			if(!a.isRunning()) {
				if(a instanceof DropAnimation) {
					// 一時非表示を解除
					p.field.displayTile(((DropAnimation)a).getTo());
				}
				// 完了したアニメーションを削除
				p.animation.remove(i);
				i--;
			}
		}
	}
}
