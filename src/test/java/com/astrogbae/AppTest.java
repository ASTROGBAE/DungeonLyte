package com.astrogbae;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * World string input and WorldObject output
     * 
     * This test works on the World class, inputting different Strings and assessing if the Room output is correct.
     */
    // base testing file for initialising a world and comparing output room to expected output
    public Boolean worldLoadTest(String _sourceText, Room _expectedFirstRoom)
    {
        World world = new World(_sourceText);
        Room outputRoom = world.getRoom();
        if (outputRoom == null) { // handle if opening room is null or not...
            if (_expectedFirstRoom == null) {
                return true;
            } return false;
        }
        if (world.getRoom().equals(_expectedFirstRoom)) {
            return true;
        } return false;
    }
    @Test
    public void nullSourceTest() {
        assertTrue(worldLoadTest(null, null));
    }
    @Test
    public void emptySourceTest() {
        assertTrue(worldLoadTest("", null));
    }
    @Test
    public void gibberishSourceTest() {
        assertTrue(worldLoadTest("a", null));
        assertTrue(worldLoadTest("\n\n\n\n\n\n\n\n\n\n", null));
        assertTrue(worldLoadTest("7", null));
        assertTrue(worldLoadTest("(&$h4n", null));
        assertTrue(worldLoadTest("[]4", null));
        assertTrue(worldLoadTest("!@#$%^&*()_)+}{:<>?PJHSUVD", null));
        assertTrue(worldLoadTest(")p,&%#)(&@+_)(*&^%$#@!\n\n\n\n\n)*^#)(*&$_($^$%$*($PUIBFP(&*$", null));
    }
    @Test
    public void incorrectDrakeSourceTest() {
        assertTrue(worldLoadTest("Door[)4", null));
        assertTrue(worldLoadTest("Wacky[mamma]iohg", null));
        assertTrue(worldLoadTest("Room(mamma)50y5g", null));
    }
    // TODO check how to find out if two objects are equal but not the same
    // TODO I know! Make a to string method, check by making it go to string?
    @Test
    public void singleRoomSourceTest() {
        String test = "Room[a]: bcdefghijklmnop";
        Room answer = new Room("a", "bcdefghijklmnop");
        assertTrue(worldLoadTest(test, answer));
    }
}
