package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	Speedometer speedometer = Mockito.mock(Speedometer.class);

	@Test
	public void smokeTest() {}

	@Test
	public void speedLimitNull() throws Exception {
		CruiseControl cc = new CruiseControl(speedometer);
		assertNull(cc.getSpeedLimit());
	}

	@Test
	public void speedSetNull() throws Exception {
		CruiseControl cc = new CruiseControl(speedometer);
		assertNull(cc.getSpeedSet());
	}

	@Test 
	public void setSpeedTestCero() {
		CruiseControl cc = new CruiseControl(speedometer);
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(0));
	}

	@Test 
	public void setSpeedTestNegative() {
		CruiseControl cc = new CruiseControl(speedometer);
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(-10));
	}

	@Test
	public void speedSetted() throws Exception {
		CruiseControl cc = new CruiseControl(speedometer);
		cc.setSpeedLimit(100);
		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> cc.setSpeedSet(110));
	}

	@Test
	public void setSpeedLimitTestCero() {
		CruiseControl cc = new CruiseControl(speedometer);
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(0));
	}

	@Test 
	public void setSpeedLimitTestNegative() {
		CruiseControl cc = new CruiseControl(speedometer);
		assertThrows(IncorrectSpeedSetException.class, () -> cc.setSpeedSet(-10));
	
	}

	@Test
	public void speedLimitSetted() throws Exception {
		CruiseControl cc = new CruiseControl(speedometer);
		cc.setSpeedSet(100);
		assertThrows(CannotSetSpeedLimitException.class, () -> cc.setSpeedLimit(110));
	}

	@Test
	public void disableTest() throws Exception {
		CruiseControl cc = new CruiseControl(speedometer);
		cc.setSpeedSet(100);
		cc.disable();
		assertNull(cc.getSpeedSet());
	}

}
