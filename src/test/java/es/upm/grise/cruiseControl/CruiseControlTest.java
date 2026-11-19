package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.cruiseControl.exceptions.*;

class CruiseControlTest {

	private Speedometer sp;
	private CruiseControl cc;

	@BeforeEach
	public void setup(){
		cc = new CruiseControl(sp);
	}

	@Test
	public void setSpeedSetTest_correcto() throws Exception{
		cc.setSpeedSet(10);
		assertEquals(10, cc.getSpeedSet());
		assertTrue(cc.isEnabled());
	}

	@Test
	public void setSpeedSetTest_negativo(){
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(-10));
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(0));
	}

	@Test
	public void setSpeedSetTest_mayorQueSpeedLimit() throws Exception{
		cc.setSpeedLimit(100);
		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> cc.setSpeedSet(101));
	}

	@Test
	public void setSpeedLimitTest_correcto() throws Exception{
		cc.setSpeedLimit(10);
		assertEquals(10, cc.getSpeedLimit());
	}

	@Test
	public void setSpeedLimitTest_negativo(){
		assertThrows(IncorrectSpeedLimitException.class, () -> cc.setSpeedLimit(-10));
		assertThrows(IncorrectSpeedLimitException.class, () -> cc.setSpeedLimit(0));
	}

	@Test
	public void setSpeedLimitTest_conSpeedSet() throws Exception{
		cc.setSpeedSet(100);
		assertThrows(CannotSetSpeedLimitException.class, () -> cc.setSpeedLimit(100));
	}

	@Test 
	public void disableTest() throws Exception{
		cc.setSpeedSet(100);
		cc.disable();
		assertFalse(cc.isEnabled());
		assertEquals(null, cc.getSpeedSet());
	}
}
