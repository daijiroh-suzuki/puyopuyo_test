package game.puyopuyo.parts;

/**
 * 処理フェーズ列挙
 */
public enum Phase {
	/** 難易度選択中 */
	SELECT,
	/** 準備中 */
	READY,
	/** 操作中 */
	CONTROL,
	/** 落下処理中 */
	DROP,
	/** 消滅処理中 */
	VANISH,
	/** ゲームオーバー */
	GAMEOVER
}
