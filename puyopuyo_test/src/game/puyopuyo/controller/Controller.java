package game.puyopuyo.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

	/** Startボタンキーフラグ */
	private boolean keyStart = false;
	/** Selectボタンキーフラグ */
	private boolean keySelect = false;
	/** Aボタンキーフラグ */
	private boolean keyA     = false;
	/** Bボタンキーフラグ */
	private boolean keyB     = false;
	/** 上方向キーフラグ */
	private boolean keyUp    = false;
	/** 下方向キーフラグ */
	private boolean keyDown  = false;
	/** 右方向キーフラグ */
	private boolean keyRight = false;
	/** 左方向キーフラグ */
	private boolean keyLeft  = false;

	/**
	 * キー押下時の処理
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
//			System.out.println("Startボタンを押下");
			keyStart = true;
			break;
		case KeyEvent.VK_SPACE:
//			System.out.println("Selectボタンを押下");
			keySelect = true;
			break;
		case KeyEvent.VK_A:
//			System.out.println("Aボタンを押下");
			keyA = true;
			break;
		case KeyEvent.VK_B:
//			System.out.println("Bボタンを押下");
			keyB = true;
			break;
		case KeyEvent.VK_UP:
//			System.out.println("上方向ボタンを押下");
			keyUp = true;
			break;
		case KeyEvent.VK_DOWN:
//			System.out.println("下方向ボタンを押下");
			keyDown = true;
			break;
		case KeyEvent.VK_RIGHT:
//			System.out.println("右方向ボタンを押下");
			keyRight = true;
			break;
		case KeyEvent.VK_LEFT:
//			System.out.println("左方向ボタンを押下");
			keyLeft = true;
			break;
		}
	}

	/**
	 * キーが離された時の処理
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
//			System.out.println("Startボタンが離された");
			keyStart = false;
			break;
		case KeyEvent.VK_SPACE:
//			System.out.println("Selectボタンが離された");
			keySelect = false;
			break;
		case KeyEvent.VK_A:
//			System.out.println("Aボタンが離された");
			keyA = false;
			break;
		case KeyEvent.VK_B:
//			System.out.println("Bボタンが離された");
			keyB = false;
			break;
		case KeyEvent.VK_UP:
//			System.out.println("上方向ボタンが離された");
			keyUp = false;
			break;
		case KeyEvent.VK_DOWN:
//			System.out.println("下方向ボタンが離された");
			keyDown = false;
			break;
		case KeyEvent.VK_RIGHT:
//			System.out.println("右方向ボタンが離された");
			keyRight = false;
			break;
		case KeyEvent.VK_LEFT:
//			System.out.println("左方向ボタンが離された");
			keyLeft = false;
			break;
		}
	}

	/**
	 * キーがタイプされた時の処理
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * @return keyStart
	 */
	public boolean isKeyStart() {
		return keyStart;
	}
	/**
	 * @param keyStart セットする keyStart
	 */
	public void setKeyStart(boolean keyStart) {
		this.keyStart = keyStart;
	}
	/**
	 * @return keySelect
	 */
	public boolean isKeySelect() {
		return keySelect;
	}

	/**
	 * @param keySelect セットする keySelect
	 */
	public void setKeySelect(boolean keySelect) {
		this.keySelect = keySelect;
	}

	/**
	 * @return keyA
	 */
	public boolean isKeyA() {
		return keyA;
	}
	/**
	 * @param keyA セットする keyA
	 */
	public void setKeyA(boolean keyA) {
		this.keyA = keyA;
	}
	/**
	 * @return keyB
	 */
	public boolean isKeyB() {
		return keyB;
	}
	/**
	 * @param keyB セットする keyB
	 */
	public void setKeyB(boolean keyB) {
		this.keyB = keyB;
	}
	/**
	 * @return keyUp
	 */
	public boolean isKeyUp() {
		return keyUp;
	}
	/**
	 * @param keyUp セットする keyUp
	 */
	public void setKeyUp(boolean keyUp) {
		this.keyUp = keyUp;
	}
	/**
	 * @return keyDown
	 */
	public boolean isKeyDown() {
		return keyDown;
	}
	/**
	 * @param keyDown セットする keyDown
	 */
	public void setKeyDown(boolean keyDown) {
		this.keyDown = keyDown;
	}
	/**
	 * @return keyRight
	 */
	public boolean isKeyRight() {
		return keyRight;
	}
	/**
	 * @param keyRight セットする keyRight
	 */
	public void setKeyRight(boolean keyRight) {
		this.keyRight = keyRight;
	}
	/**
	 * @return keyLeft
	 */
	public boolean isKeyLeft() {
		return keyLeft;
	}
	/**
	 * @param keyLeft セットする keyLeft
	 */
	public void setKeyLeft(boolean keyLeft) {
		this.keyLeft = keyLeft;
	}
}
