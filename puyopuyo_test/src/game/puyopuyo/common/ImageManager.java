package game.puyopuyo.common;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageManager {

	/** 背景画像Image */
	public static Image bgImage;
	/** ぷよ画像Image */
	public static Image puyoImage;
	/** ぷよ画像2Image */
	public static Image puyoImage2;
	/** モード選択画像Image */
	public static Image modeImage;
	/** ステージ画像Image */
	public static Image stageImage;
	/** フォント画像Image */
	public static Image fontImage;
	/** 難易度画像Image */
	public static Image levelImage;
	/** 予告ぷよ画像Image */
	public static Image yokokuImage;
	/** その他画像Image */
	public static Image miscImage;

	/**
	 * 初期化処理
	 */
	public static void init() {
		loadImage();
	}

	/**
	 * 画像ファイルをロードする
	 */
	private static void loadImage() {
		// 背景画像をロード
		URL url = ImageManager.class.getClassLoader().getResource("images/bg.png");
		ImageIcon icon = new ImageIcon(url);
		bgImage = icon.getImage();

		// ぷよ画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/puyo.gif");
		icon = new ImageIcon(url);
		puyoImage = icon.getImage();

		// ぷよ画像2をロード
		url = ImageManager.class.getClassLoader().getResource("images/puyo2.gif");
		icon = new ImageIcon(url);
		puyoImage2 = icon.getImage();

		// モード選択画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/mode.png");
		icon = new ImageIcon(url);
		modeImage = icon.getImage();

		// ステージ画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/stage.png");
		icon = new ImageIcon(url);
		stageImage = icon.getImage();

		// フォント画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/font.png");
		icon = new ImageIcon(url);
		fontImage = icon.getImage();

		// 難易度画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/level.png");
		icon = new ImageIcon(url);
		levelImage = icon.getImage();

		// 予告ぷよ画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/yokoku.gif");
		icon = new ImageIcon(url);
		yokokuImage = icon.getImage();

		// その他画像をロード
		url = ImageManager.class.getClassLoader().getResource("images/misc.png");
		icon = new ImageIcon(url);
		miscImage = icon.getImage();
	}
}
