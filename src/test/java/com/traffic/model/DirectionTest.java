package com.traffic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testFromStringValidValues() {
        assertEquals(Direction.NORTH, Direction.fromString("north"));
        assertEquals(Direction.SOUTH, Direction.fromString("south"));
        assertEquals(Direction.EAST, Direction.fromString("east"));
        assertEquals(Direction.WEST, Direction.fromString("west"));
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(Direction.NORTH, Direction.fromString("NORTH"));
        assertEquals(Direction.SOUTH, Direction.fromString("South"));
        assertEquals(Direction.EAST, Direction.fromString("EaSt"));
        assertEquals(Direction.WEST, Direction.fromString("WeSt"));
    }

    @Test
    void testFromStringInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString(null));
    }

    @Test
    void testGetOpposite() {
        assertEquals(Direction.SOUTH, Direction.NORTH.getOpposite());
        assertEquals(Direction.NORTH, Direction.SOUTH.getOpposite());
        assertEquals(Direction.WEST, Direction.EAST.getOpposite());
        assertEquals(Direction.EAST, Direction.WEST.getOpposite());
    }

    @Test
    void testIsPerpendicularTo() {
        assertTrue(Direction.NORTH.isPerpendicularTo(Direction.EAST));
        assertTrue(Direction.NORTH.isPerpendicularTo(Direction.WEST));
        assertTrue(Direction.SOUTH.isPerpendicularTo(Direction.EAST));
        assertTrue(Direction.SOUTH.isPerpendicularTo(Direction.WEST));

        assertFalse(Direction.NORTH.isPerpendicularTo(Direction.NORTH));
        assertFalse(Direction.NORTH.isPerpendicularTo(Direction.SOUTH));
        assertFalse(Direction.EAST.isPerpendicularTo(Direction.EAST));
        assertFalse(Direction.EAST.isPerpendicularTo(Direction.WEST));
    }

    @Test
    void testJsonName() {
        assertEquals("north", Direction.NORTH.getJsonName());
        assertEquals("south", Direction.SOUTH.getJsonName());
        assertEquals("east", Direction.EAST.getJsonName());
        assertEquals("west", Direction.WEST.getJsonName());
    }
}