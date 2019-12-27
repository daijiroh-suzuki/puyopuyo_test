package game.puyopuyo.common;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageManager {

	/** 背景画像Image */
	public static Image bgImage;
	/** ぷよ画像Image */
	public static Image puyoImage;

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
	}
}
