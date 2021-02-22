package com.tactfactory.tp1junit.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Navire implements Cloneable {
	private int identifiant;
	private int taille;
	private List<Case> cases;

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public List<Case> getCases() {
		return cases;
	}

	public Navire(){
		cases = new ArrayList<Case>();
	}

	public boolean getVivant() {
		boolean vivant = false;
		for (Case caseE : cases) {
			if (!caseE.isTouche()) {
				vivant = true;
			}
		}
		return vivant;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		this.cases = new ArrayList<Case>();
		return super.clone();
	}
}
