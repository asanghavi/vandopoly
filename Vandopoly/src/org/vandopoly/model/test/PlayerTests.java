package org.vandopoly.model.test;

import org.vandopoly.model.Player;
import org.vandopoly.model.PlayerFree;
import org.vandopoly.model.PlayerInJail;

public class PlayerTests {

	public static void main(String[] args) {
		Player A = new Player(0, "Bob", null, 0);
		Player B = new Player(1, "Mark", null, 1);

		A.goToJail();
		
		if (A.getState() == PlayerInJail.Instance()) {
			System.out.println("Test 1: PASS");
		}
		else
			System.out.print("Test 1: FAIL");
		
		if (B.getState() == PlayerFree.Instance()) {
			System.out.println("Test 2: PASS");
		}
		else
			System.out.println("Test 2: FAIL");
		
		A.collectRent(12, B);
		
		if (A.getCash() == 0)
			System.out.println("Test 3: PASS");
		else
			System.out.println("Test 3: FAIL");
		
		A.updateCash(50);
		B.collectRent(10, A);
		
		if (A.getCash() == 40)
			System.out.println("Test 4: PASS");
		else
			System.out.println("Test 4: FAIL");
		
		if (B.getCash() == 10)
			System.out.println("Test 5: PASS");
		else
			System.out.println("Test 5: FAIL");
		
		A.getOutOfJail();
		B.goToJail();
		
		if (B.getState() == PlayerInJail.Instance()) {
			System.out.println("Test 6: PASS");
		}
		else
			System.out.print("Test 6: FAIL");
		
		if (A.getState() == PlayerFree.Instance()) {
			System.out.println("Test 7: PASS");
		}
		else
			System.out.print("Test 7: FAIL");
	}

}
