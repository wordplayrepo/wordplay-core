package org.syphr.wordplay.core.space;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LocationTest
{
    @Test
    public void at_XY()
    {
        assertThat(Location.at(1, 2), equalTo(new Location(1, 2, 0)));
    }

    @Test
    public void at_XYZ()
    {
        assertThat(Location.at(1, 2, 3), equalTo(new Location(1, 2, 3)));
    }

    @Test
    public void move_Positive()
    {
        assertThat(Location.at(1, 1, 1).move(Vector.of(1, 1, 1)), equalTo(Location.at(2, 2, 2)));
    }

    @Test
    public void move_Negative()
    {
        assertThat(Location.at(2, 2, 2).move(Vector.of(-1, -1, -1)), equalTo(Location.at(1, 1, 1)));
    }

    @Test
    public void isWithin()
    {
        assertAll(() -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 3, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(2, 1, 1), Location.at(4, 3, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 2, 1), Location.at(3, 4, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 2), Location.at(3, 3, 4))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(1, 1, 1))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(4, 4, 4))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(4, 3, 3))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 4, 3))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 3, 4))));
    }

    @Test
    public void compareTo()
    {
        assertAll(() -> assertThat(Location.at(1, 1, 1), comparesEqualTo(Location.at(1, 1, 1))),
                  () -> assertThat(Location.at(1, 1, 1), lessThan(Location.at(2, 1, 1))),
                  () -> assertThat(Location.at(1, 1, 1), lessThan(Location.at(1, 2, 1))),
                  () -> assertThat(Location.at(1, 1, 1), lessThan(Location.at(1, 1, 2))),
                  () -> assertThat(Location.at(2, 2, 2), lessThan(Location.at(3, 1, 1))));
    }
}
