package com.tactfactory.tp1junit.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tactfactory.tp1junit.model.Case;
import com.tactfactory.tp1junit.model.Corvette;
import com.tactfactory.tp1junit.model.Croiseur;
import com.tactfactory.tp1junit.model.Destroyer;
import com.tactfactory.tp1junit.model.Joueur;
import com.tactfactory.tp1junit.model.Navire;
import com.tactfactory.tp1junit.model.PorteAvion;

public class Jeu {

	private int tailleX = 18;
	private int tailleY = 24;

	Random rand = new Random();

	private List<Joueur> joueurs;
	private List<Joueur> deads;

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public Jeu() {
		this.joueurs = new ArrayList<Joueur>();
		this.deads = new ArrayList<Joueur>();
	}

	public Jeu(int nbJoueur, int x, int y) {
		this(nbJoueur);
		this.tailleX = x;
		this.tailleY = y;
	}

	public Jeu(int nbJoueur) {
		this();
		for (int i = 0; i < nbJoueur; i++) {
			Joueur joueur = new Joueur("joueur " + (i + 1));
			for (int j = 0; j < 1; j++) {
				joueur.getMap().add(new Corvette());
			}
			for (int j = 0; j < 2; j++) {
				joueur.getMap().add(new Destroyer());
			}
			for (int j = 0; j < 2; j++) {
				joueur.getMap().add(new Croiseur());
			}
			for (int j = 0; j < 1; j++) {
				joueur.getMap().add(new PorteAvion());
			}
			this.joueurs.add(joueur);
		}
	}

	public Jeu(int nbJoueur, List<Navire> navires) throws CloneNotSupportedException {
		this();
		for (int i = 0; i < nbJoueur; i++) {
			Joueur joueur = new Joueur("joueur " + (i + 1));

			for (Navire navire : navires) {
				joueur.getMap().add((Navire) navire.clone());
			}

			this.joueurs.add(joueur);
		}
	}

	public Jeu(int nbJoueur, List<Navire> navire, int mapX, int mapY) throws CloneNotSupportedException {
		this(nbJoueur, navire);
		this.tailleX = mapX;
		this.tailleY = mapY;
	}

	public void play() {
		for (Joueur joueur : joueurs) {
			placementBateaux(joueur);
		}

		afficherPlacement();

		boolean continuer = true;
		int i = 1;
		while (continuer) {
			System.out.println("Tour :" + i);
			for (Joueur joueur : joueurs) {
				if (joueur.getVivant() && !deads.contains(joueur)) {
					Joueur adversaire = trouveAdversaire(joueur);

					if (adversaire != null) {
						i++;
						System.out
								.println("Le joueur " + joueur.getNom() + " attaque le joueur " + adversaire.getNom());
						attaquer(joueur, adversaire);

						if (!adversaire.getVivant()) {
							deads.add(adversaire);
						}
					}
				}
			}

			if (deads.size() >= joueurs.size() - 1) {
				continuer = false;
			}

			afficherTour();
		}

		System.out.println("Fin de partie");
		this.joueurs.removeAll(deads);
		System.out.println("Le gagnant est le joueur " + this.joueurs.get(0).getNom());
	}

	private void afficherTour() {
		for (Joueur joueur : joueurs) {
			afficheMapTire(joueur);
		}
	}

	private void afficherPlacement() {
		for (Joueur joueur : joueurs) {
			afficheMap(joueur);
		}
	}

	private void attaquer(Joueur joueur, Joueur adversaire) {
		int x = rand.nextInt(tailleX)%tailleX;
		int y = rand.nextInt(tailleY)%tailleY;
		while (!simpleFireTry(joueur, adversaire, x, y)) {
			x = rand.nextInt(tailleX)%tailleX;
			y = rand.nextInt(tailleY)%tailleY;
		}
	}

	private boolean simpleFireTry(Joueur joueur, Joueur adversaire, int x, int y) {
		boolean result = false;
		Case testCase = new Case(x, y);
		boolean dejaTire = false;

		if (!joueur.getTires().containsKey(adversaire)) {
			joueur.getTires().put(adversaire, new ArrayList<Case>());
		}

		for (Case caseE : joueur.getTires().get(adversaire)) {
			if (caseE.getX() == x && caseE.getY() == y) {
				dejaTire = true;
			}
		}
		if (!dejaTire) {
			for (Navire navire : adversaire.getMap()) {
				for (Case caseE : navire.getCases()) {
					if (caseE.getX() == x && caseE.getY() == y) {
						caseE.setTouche(true);
						testCase.setTouche(true);
					}
				}
			}
			joueur.getTires().get(adversaire).add(testCase);
			for (Joueur ennemi : joueurs) {
				if (!ennemi.equals(joueur) && !ennemi.equals(adversaire)) {
					if (!ennemi.getTires().containsKey(adversaire)) {
						ennemi.getTires().put(adversaire, new ArrayList<Case>());
					}
					ennemi.getTires().get(adversaire).add(testCase);
				}
			}
			result = true;
		}
		return result;
	}

	private Joueur trouveAdversaire(Joueur joueur) {
		Joueur adversaire = null;
		boolean flag = true;
		int i = joueurs.indexOf(joueur);
		do {
			// Vérification de l'indice pour ne pas sortir du tableau
			if (i + 1 == joueurs.size()) {
				i = 0;
			} else {
				i++;
			}

			if (joueurs.get(i).getVivant()) {
				adversaire = joueurs.get(i);
				flag = false;
			}

			if (adversaire != null && adversaire.equals(joueur)) {
				adversaire = null;
				break;
			}
		} while (flag && adversaire == null);
		return adversaire;
	}

	public void placementBateaux(Joueur joueur) {
		for (Navire navire : joueur.getMap()) {
			placementBateau(joueur, navire);
		}
	}

	private void placementBateau(Joueur joueur, Navire navire) {
		Random rand = new Random();
		int x = rand.nextInt(tailleX)%tailleX;
		int y = rand.nextInt(tailleY)%tailleY;
		int direction = rand.nextInt(2);
		if (bateauPlacable(navire, x, y, direction, joueur)) {
			placeBateau(navire, x, y, direction);
		} else {
			placementBateau(joueur, navire);
		}
	}

	private void placeBateau(Navire navire, int x, int y, int direction) {
		switch (direction) {
		// Droite
		case 0:
			for (int i = 0; i < navire.getTaille(); i++) {
				Case casE = new Case(x + i, y);
				navire.getCases().add(casE);
			}
			break;
		// Bas
		case 1:
			for (int i = 0; i < navire.getTaille(); i++) {
				Case casE = new Case(x, y + i);
				navire.getCases().add(casE);
			}
			break;
		}
	}

	private boolean bateauPlacable(Navire navire, int x, int y, int direction, Joueur joueur) {
		boolean result = true;
		switch (direction) {
		// Droite
		case 0:
			for (int i = 0; i < navire.getTaille(); i++) {
				if (x + i >= tailleX || caseExiste(x + i, y, joueur)) {
					result = false;
				}
			}
			break;
		// Bas
		case 1:
			for (int i = 0; i < navire.getTaille(); i++) {
				if (y + i >= tailleY || caseExiste(x, y + i, joueur)) {
					result = false;
				}
			}
			break;
		}
		return result;
	}

	private boolean caseExiste(int x, int y, Joueur joueur) {
		boolean result = false;
		for (Navire navire : joueur.getMap()) {
			for (Case caseItem : navire.getCases()) {
				if (caseItem.getX() == x && caseItem.getY() == y) {
					result = true;
				}
			}
		}

		return result;
	}

	public void afficheMap(Joueur joueur) {
		boolean dontFindCase = true;
		StringBuilder column = new StringBuilder();
		for (int i = 0; i < tailleX; i++) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < tailleY; j++) {
				for (Navire navire : joueur.getMap()) {
					for (Case caseE : navire.getCases()) {
						if (caseE.getX() == i && caseE.getY() == j) {
							if (!caseE.isTouche()) {
								line.append(" " + navire.getIdentifiant());
							} else {
								line.append(" X");
							}
							dontFindCase = false;
						}
					}
				}
				if (dontFindCase) {
					line.append(" 0");
				} else {
					dontFindCase = true;
				}
			}
			column.append(line.toString() + "\n");
		}
		System.out.println("Carte du joueur " + joueur.getNom());
		System.out.println(column);
	}

	private void afficheMapTire(Joueur joueur) {
		boolean dontFindCase = true;
		StringBuilder column = new StringBuilder();
		for (int i = 0; i < tailleX; i++) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < tailleY; j++) {
				for (Joueur ennemi : joueurs) {
					if (!ennemi.equals(joueur)) {
						for (Case caseE : ennemi.getTires().get(joueur)) {
							if (caseE.getX() == i && caseE.getY() == j) {
								if (!caseE.isTouche()) {
									line.append("()");
								} else {
									line.append(" X");
								}
								dontFindCase = false;
							}
						}
						if (dontFindCase) {
							line.append(" 0");
						} else {
							dontFindCase = true;
						}
						break;
					}
				}
			}
			column.append(line.toString() + "\n");
		}
		System.out.println("Carte du joueur " + joueur.getNom());
		System.out.println(column);
	}
}
