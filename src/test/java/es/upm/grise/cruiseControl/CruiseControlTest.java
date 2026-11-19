package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	@Test
    public void testIniciar() {
        CruiseControl cc = new CruiseControl(null);
        assertNull(cc.getSpeedSet());
        assertNull(cc.getSpeedLimit());
        assertFalse(cc.isEnabled());
    }

	//tests speedset
	@Test
    public void testSpeedSetValido() throws Exception {
        CruiseControl cc = new CruiseControl(null);
        cc.setSpeedSet(100);
        assertEquals(100, cc.getSpeedSet());
        assertTrue(cc.isEnabled());
    }

	@Test
    public void testSpeedSetCero() throws Exception{
        CruiseControl cc = new CruiseControl(null);
        assertThrows(IncorrectSpeedSetException.class, () -> {
            cc.setSpeedSet(0);
        });
    }

	//tests speedlimit
	@Test
    public void testSetSpeedLimitValido() throws Exception {
        CruiseControl cc = new CruiseControl(null);
        cc.setSpeedLimit(120);
        assertEquals(120, cc.getSpeedLimit());
    }

	@Test
    public void testSetSpeedLimitInvalido() throws Exception{
        CruiseControl cc = new CruiseControl(null);
        assertThrows(IncorrectSpeedLimitException.class, () -> {
            cc.setSpeedLimit(0);
        });
    }

	//ambos
	@Test
    public void testSpeedSetMenorQueLimite() throws Exception {
        CruiseControl cc = new CruiseControl(null);
        cc.setSpeedLimit(100);
        cc.setSpeedSet(90);
        assertEquals(90, cc.getSpeedSet());
        assertTrue(cc.isEnabled());
    }

	@Test
    public void testSpeedSetMayorQueLimite() throws Exception {
        CruiseControl cc = new CruiseControl(null);
        cc.setSpeedLimit(120);
        assertThrows(SpeedSetAboveSpeedLimitException.class, () -> {
            cc.setSpeedSet(130);
        });
    }

	//test disable
	@Test
    public void testDisable() throws Exception{
        CruiseControl cc = new CruiseControl(null);
        cc.disable();
        assertFalse(cc.isEnabled());
        assertNull(cc.getSpeedSet());
    }

}
