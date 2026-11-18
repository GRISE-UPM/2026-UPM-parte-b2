package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	@Test
	public void smokeTest() {}

	@Test
	public void StartCruiseControl() {
		CruiseControl cc = new CruiseControl(null);

		assertEquals(null, cc.getSpeedSet());
		assertEquals(null, cc.getSpeedLimit());
		assertEquals(false, cc.isEnabled());
	}

	@Test
	public void setSpeedSet() throws Exception {
		CruiseControl cc = new CruiseControl(null);

		cc.setSpeedSet(100);

		assertEquals(100, cc.getSpeedSet());
		assertEquals(true, cc.isEnabled());
	}

	@Test
	public void setSpeedSetZero() {
		CruiseControl cc = new CruiseControl(null);

		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(0));
		assertEquals(null, cc.getSpeedSet());
		assertEquals(false, cc.isEnabled());
	}

	@Test
	public void setSpeedSetNegative() {
		CruiseControl cc = new CruiseControl(null);

		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(-10));
		assertEquals(null, cc.getSpeedSet());
		assertEquals(false, cc.isEnabled());
	}

	@Test
	public void setSpeedSetBelowLimit() throws Exception {
		CruiseControl cc = new CruiseControl(null);
		cc.setSpeedLimit(120);

		cc.setSpeedSet(100);

		assertEquals(100, cc.getSpeedSet());
		assertEquals(true, cc.isEnabled());
	}

	@Test
	public void setSpeedSetEqualToLimit() throws Exception {
		CruiseControl cc = new CruiseControl(null);
		cc.setSpeedLimit(120);

		cc.setSpeedSet(120);

		assertEquals(120, cc.getSpeedSet());
		assertEquals(true, cc.isEnabled());
	}

	@Test
	public void setSpeedSetAboveLimit() throws Exception {
		CruiseControl cc = new CruiseControl(null);
		cc.setSpeedLimit(120);

		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> cc.setSpeedSet(121));
		assertEquals(null, cc.getSpeedSet());
		assertEquals(false, cc.isEnabled());
	}

	@Test
	public void setSpeedLimit() throws Exception {
		CruiseControl cc = new CruiseControl(null);

		cc.setSpeedLimit(120);

		assertEquals(120, cc.getSpeedLimit());
	}

	@Test
	public void setSpeedLimitZero() {
		CruiseControl cc = new CruiseControl(null);

		assertThrows(IncorrectSpeedLimitException.class, () -> cc.setSpeedLimit(0));
		assertEquals(null, cc.getSpeedLimit());
	}

	@Test
	public void setSpeedLimitNegative() {
		CruiseControl cc = new CruiseControl(null);

		assertThrows(IncorrectSpeedLimitException.class, () -> cc.setSpeedLimit(-10));
		assertEquals(null, cc.getSpeedLimit());
	}

	@Test
	public void setSpeedLimitAfterSpeedSet() throws Exception {
		CruiseControl cc = new CruiseControl(null);
		cc.setSpeedSet(100);

		assertThrows(CannotSetSpeedLimitException.class, () -> cc.setSpeedLimit(120));
		assertEquals(null, cc.getSpeedLimit());
	}

	@Test
	public void disable() throws Exception {
		CruiseControl cc = new CruiseControl(null);
		cc.setSpeedSet(100);

		cc.disable();

		assertEquals(false, cc.isEnabled());
		assertEquals(null, cc.getSpeedSet());
	}
}