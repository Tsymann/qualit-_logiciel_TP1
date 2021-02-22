package com.tactfactory.tp1junit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Joueur {

	List<Navire> map;
	Map<Joueur,ArrayList<Case>> tires;
	String nom;

	public List<Navire> getMap() {
		return map;
	}

	public Map<Joueur,ArrayList<Case>> getTires() {
		return tires;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Joueur() {
		map = new ArrayList<Navire>();
		tires = new HashMap<Joueur,ArrayList<Case>>();
	}

	public Joueur(String nom) {
		this();
		this.nom = nom;
	}

	public boolean getVivant() {
		boolean vivant = false;
		for (Navire navire : map) {
			if (navire.getVivant()) {
				vivant = true;
			}
		}
		return vivant;
	}
}
