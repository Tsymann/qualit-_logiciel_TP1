package com.tactfactory.tp1junit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tactfactory.tp1junit.manager.Jeu;

public class NavireTest {

	@Test
	public void test1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Navire> carte = new ArrayList<Navire>();
		
		Jeu jeu = new Jeu(4,24,18);
		Method method = Jeu.class.getDeclaredMethod("placeBateau");
		method.setAccessible(true);
		Navire navire = new PorteAvion(); 
		
		method.invoke(jeu, navire, 0, 0, 0);
		
		for(Case item : navire.getCases()){
			
			System.out.println(item.getX() + " : " + item.getY());
		}
	}
	
}
