package com.traffic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TrafficPhaseTest {

    @Test
    void testNorthSouthPhase() {
        TrafficPhase phase = TrafficPhase.NORTH_SOUTH;
        List<Direction> allowed = phase.getAllowedDirections();

        assertEquals(2, allowed.size());
        assertTrue(allowed.contains(Direction.NORTH));
        assertTrue(allowed.contains(Direction.SOUTH));
        assertFalse(allowed.contains(Direction.EAST));
        assertFalse(allowed.contains(Direction.WEST));
    }

    @Test
    void testEastWestPhase() {
        TrafficPhase phase = TrafficPhase.EAST_WEST;
        List<Direction> allowed = phase.getAllowedDirections();

        assertEquals(2, allowed.size());
        assertTrue(allowed.contains(Direction.EAST));
        assertTrue(allowed.contains(Direction.WEST));
        assertFalse(allowed.contains(Direction.NORTH));
        assertFalse(allowed.contains(Direction.SOUTH));
    }

    @Test
    void testIsDirectionAllowed() {
        assertTrue(TrafficPhase.NORTH_SOUTH.isDirectionAllowed(Direction.NORTH));
        assertTrue(TrafficPhase.NORTH_SOUTH.isDirectionAllowed(Direction.SOUTH));
        assertFalse(TrafficPhase.NORTH_SOUTH.isDirectionAllowed(Direction.EAST));
        assertFalse(TrafficPhase.NORTH_SOUTH.isDirectionAllowed(Direction.WEST));

        assertTrue(TrafficPhase.EAST_WEST.isDirectionAllowed(Direction.EAST));
        assertTrue(TrafficPhase.EAST_WEST.isDirectionAllowed(Direction.WEST));
        assertFalse(TrafficPhase.EAST_WEST.isDirectionAllowed(Direction.NORTH));
        assertFalse(TrafficPhase.EAST_WEST.isDirectionAllowed(Direction.SOUTH));
    }

    @Test
    void testGetNext() {
        assertEquals(TrafficPhase.EAST_WEST, TrafficPhase.NORTH_SOUTH.getNext());
        assertEquals(TrafficPhase.NORTH_SOUTH, TrafficPhase.EAST_WEST.getNext());
    }

    @Test
    void testPhaseAlternation() {
        TrafficPhase current = TrafficPhase.NORTH_SOUTH;

        for (int i = 0; i < 10; i++) {
            TrafficPhase next = current.getNext();
            assertNotEquals(current, next);
            current = next;
        }
    }

    @Test
    void testNoConflictingDirections() {
        List<Direction> nsDirections = TrafficPhase.NORTH_SOUTH.getAllowedDirections();
        List<Direction> ewDirections = TrafficPhase.EAST_WEST.getAllowedDirections();

        for (Direction nsDir : nsDirections) {
            for (Direction ewDir : ewDirections) {
                assertTrue(nsDir.isPerpendicularTo(ewDir),
                        "Phases should only allow perpendicular directions");
            }
        }
    }
}