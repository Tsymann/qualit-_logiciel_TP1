package com.tactfactory.tp1junit.model;

public class Case implements Cloneable {

	private int x;
	private int y;
	private boolean touche;

	public Integer getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouche() {
		return touche;
	}

	public void setTouche(boolean touche) {
		this.touche = touche;
	}

	public Case(int x, int y){
		this.x = x;
		this.y = y;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
