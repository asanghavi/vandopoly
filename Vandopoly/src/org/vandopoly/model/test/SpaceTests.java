/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.model.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.vandopoly.model.CardSpace;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.Space;
import org.vandopoly.model.TaxSpace;
import org.vandopoly.model.UpgradeablePropertySpace;

/*
 * SpaceTests is a JUnit testing class that is meant to test the space class
 * and all children of the space class.
 * 
 * @author James Kasten
 */
public class SpaceTests extends TestCase {
	Space normalSpace;
	PropertySpace property;
	
	Player samplePlayer;
	
	Space board[] = new Space[4];
	
	// Sets up "shared" objects among tests.
	// Any changes to objects during a test is confined to that test
	protected void setUp() throws Exception {
		super.setUp();
		normalSpace = new Space();
		
		property = new PropertySpace();
		property.setPurchasePrice(100);
		
		board[0] = new UpgradeablePropertySpace();
		board[1] = new PropertySpace();
		board[2] = new CardSpace();
		board[3] = new TaxSpace();
		
		samplePlayer = new Player();
		samplePlayer.setCash(200);
		samplePlayer.setName("James");
		samplePlayer.setPosition(0);
	}
	
	// Must be used to cleanup after a .setUp()
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Takes all functions that have .testXXX...() and turns them into
	// separate tests
	public static Test suite(){
		return new TestSuite(SpaceTests.class);
	}
	
	public void testSetName() {
		normalSpace.setName("Space1");
		assertTrue(normalSpace.getName() == "Space1");
	}
	public void testPropertyValue() {
		assertTrue(property.getPurchasePrice() == 100);
	}
}
