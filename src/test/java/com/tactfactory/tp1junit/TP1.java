package com.tactfactory.tp1junit;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.tactfactory.tp1junit.manager.*;
import com.tactfactory.tp1junit.model.*;

public class TP1 {

	/**
	 * Test TU-001 par validation de la fonction placeBateau
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testTU001ParPlaceBateau() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<Navire> map = new ArrayList<Navire>();
		Navire navire1 = new PorteAvion();
		Navire navire2 = new PorteAvion();
		map.add(navire1);
		map.add(navire2);

		Jeu jeu = new Jeu(4, 24, 18);
		Method method = Jeu.class.getDeclaredMethod("placeBateau", Navire.class, int.class, int.class, int.class);
		method.setAccessible(true);

		// Placement du premier bateau en 0 0 avec orientation 0
		method.invoke(jeu, map.get(0), 0, 0, 0);

//        for (Case item : map.get(0).getCases()) {
//            System.out.println(item.getX() + " : " + item.getY());
//        }

		// Placement du deuxième bateau en 0 0 avec orientation 0
		method.invoke(jeu, map.get(1), 0, 0, 0);

//        for (Case item : map.get(1).getCases()) {
//            System.out.println(item.getX() + " : " + item.getY());
//        }

		List<Case> caseErros = new ArrayList<Case>();

		// Les deux bateaux on la même taille donc je regarde la taille du bateau 1 et
		// regarde si le bateau 2 est positionné sur au moins une des cases prises par
		// le bateau 1
		for (int i = 0; i < map.get(0).getCases().size(); i++) {
			if (map.get(0).getCases().get(i).getX() == map.get(1).getCases().get(i).getX()
					&& map.get(0).getCases().get(i).getY() == map.get(0).getCases().get(i).getY()) {
				caseErros.add(map.get(0).getCases().get(i));
			}
		}

		// Si il existe au moins une case commune alors c'est une erreur
		if (!caseErros.isEmpty()) {
			StringBuilder errors = new StringBuilder();

			for (int i = 0; i < caseErros.size() - 1; i++) {
				errors.append(caseErros.get(i).getX() + ":" + caseErros.get(i).getY() + " |");
			}
			errors.append(
					caseErros.get(caseErros.size() - 1).getX() + ":" + caseErros.get(caseErros.size() - 1).getY());

			fail("Les cases du bateau 1 et du bateau 2 sont identique aux positions : " + errors.toString());
		}
	}

	/**
	 * Test TU-001 par validation de la fonction bateauPlacable
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testTU001ParBateauPlacable() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<Navire> map = new ArrayList<Navire>();
		Navire navire1 = new PorteAvion();
		Joueur joueur1 = new Joueur();
		map.add(navire1);

		Jeu jeu = new Jeu(4, 24, 18);
		Method method = Jeu.class.getDeclaredMethod("bateauPlacable", Navire.class, int.class, int.class, int.class,
				Joueur.class);

		method.setAccessible(true);

		method.invoke(jeu, map.get(0), 0, 0, 0);
		boolean canPlace = (boolean) method.invoke(jeu, map.get(1), 0, 30, 0, joueur1);

		StringBuilder errors = new StringBuilder();
		if (!canPlace) {
			errors.append("error, can't place");
		} else {
			errors.append("bateau placable");
		}


		fail(errors.toString());
		

	}
}