package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CruiseControlTest {

    private CruiseControl cruiseControl;

    @BeforeEach
    void setUp() {
        Speedometer speedometerMock = Mockito.mock(Speedometer.class);
        cruiseControl = new CruiseControl(speedometerMock);
    }

    @Test
    public void smokeTest() {
        assertFalse(cruiseControl.isEnabled());
        assertNull(cruiseControl.getSpeedSet());
        assertNull(cruiseControl.getSpeedLimit());
    }

    /* -------------------- setSpeedSet -------------------- */

    @Test
    void setSpeedSet_zero_throwsIncorrectSpeedSetException() {
        assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(0));
    }

    @Test
    void setSpeedSet_negative_throwsIncorrectSpeedSetException() {
        assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(-10));
    }

    @Test
    void setSpeedSet_positive_setsValueAndEnables() throws Exception {
        cruiseControl.setSpeedSet(100);
        assertEquals(100, cruiseControl.getSpeedSet());
        assertTrue(cruiseControl.isEnabled());
    }

    @Test
    void setSpeedSet_belowSpeedLimit_isAccepted() throws Exception {
        cruiseControl.setSpeedLimit(120);
        cruiseControl.setSpeedSet(100);
        assertEquals(100, cruiseControl.getSpeedSet());
        assertEquals(120, cruiseControl.getSpeedLimit());
        assertTrue(cruiseControl.isEnabled());
    }

    @Test
    void setSpeedSet_equalToSpeedLimit_isAccepted() throws Exception {
        cruiseControl.setSpeedLimit(100);
        cruiseControl.setSpeedSet(100);
        assertEquals(100, cruiseControl.getSpeedSet());
        assertTrue(cruiseControl.isEnabled());
    }

    @Test
    void setSpeedSet_aboveSpeedLimit_throwsSpeedSetAboveSpeedLimitException() throws Exception {
        cruiseControl.setSpeedLimit(100);
        assertThrows(SpeedSetAboveSpeedLimitException.class,
                () -> cruiseControl.setSpeedSet(120));
        assertNull(cruiseControl.getSpeedSet());
        assertFalse(cruiseControl.isEnabled());
    }

    /* -------------------- setSpeedLimit -------------------- */

    @Test
    void setSpeedLimit_zero_throwsIncorrectSpeedLimitException() {
        assertThrows(IncorrectSpeedLimitException.class, () -> cruiseControl.setSpeedLimit(0));
    }

    @Test
    void setSpeedLimit_negative_throwsIncorrectSpeedLimitException() {
        assertThrows(IncorrectSpeedLimitException.class, () -> cruiseControl.setSpeedLimit(-10));
    }

    @Test
    void setSpeedLimit_positive_setsValue() throws Exception {
        cruiseControl.setSpeedLimit(120);
        assertEquals(120, cruiseControl.getSpeedLimit());
        assertFalse(cruiseControl.isEnabled());
    }

    @Test
    void setSpeedLimit_whenSpeedSetAlreadySet_throwsCannotSetSpeedLimitException() throws Exception {
        cruiseControl.setSpeedSet(80);
        assertThrows(CannotSetSpeedLimitException.class,
                () -> cruiseControl.setSpeedLimit(120));
        assertNull(cruiseControl.getSpeedLimit());
    }

    /* -------------------- disable -------------------- */

    @Test
    void disable_resetsEnabledAndSpeedSet_butKeepsSpeedLimit() throws Exception {
        cruiseControl.setSpeedLimit(120);
        cruiseControl.setSpeedSet(80);

        cruiseControl.disable();

        assertFalse(cruiseControl.isEnabled());
        assertNull(cruiseControl.getSpeedSet());
        assertEquals(120, cruiseControl.getSpeedLimit());
    }

    @Test
    void disable_onAlreadyDisabled_isIdempotent() {
        cruiseControl.disable();
        cruiseControl.disable();
        assertFalse(cruiseControl.isEnabled());
        assertNull(cruiseControl.getSpeedSet());
    }
}