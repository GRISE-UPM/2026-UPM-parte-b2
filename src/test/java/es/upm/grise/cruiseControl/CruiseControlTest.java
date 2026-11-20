package es.upm.grise.cruiseControl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import es.upm.grise.cruiseControl.exceptions.*;

import org.junit.jupiter.api.BeforeEach;


class CruiseControlTest {

	private Speedometer speedometer;
	private CruiseControl service;

	@BeforeEach
	void setUp() {
		speedometer = mock(Speedometer.class);
		service = new CruiseControl(speedometer);
	}

	@Test
	void testInicializacion() {
		assertTrue(service.getSpeedSet() == null && service.getSpeedLimit() == null );
	}

	@Test
	public void smokeTest() {}
	void testSpeedSet() {
		
		try {
			service.setSpeedSet(1);
		} catch (Exception IncorrectSpeedSetException) {
			
		}
		assertTrue(service.getSpeedSet()==1);
	}

	void testSpeedSetc0() {
		assertThrows(IncorrectSpeedSetException.class, () -> service.setSpeedSet(0));
	}

	void testSpeedSetneg() {
		assertThrows(IncorrectSpeedSetException.class, () -> service.setSpeedSet(-1));
	}

	void testSpeedSetLim() throws CannotSetSpeedLimitException, IncorrectSpeedLimitException{

		service.setSpeedLimit(1);

		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> service.setSpeedSet(2));
	}

	void testSpeedLim() {
		try {
			service.setSpeedLimit(1);
		} catch (Exception IncorrectSpeedLimitException) {
			
		}
		assertTrue(service.getSpeedLimit()==1);
	}

	void testSpeedLimneg() {

		assertThrows(IncorrectSpeedLimitException.class, () -> service.setSpeedLimit(-1));
	}

	void testSpeedLim0() {
		assertThrows(IncorrectSpeedLimitException.class, () -> service.setSpeedLimit(0));
	}

	void testSpeedLimSet() throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		service.setSpeedSet(1);
		assertThrows(CannotSetSpeedLimitException.class, () -> service.setSpeedLimit(1));
	}

	void testDisable() throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		service.setSpeedSet(1);
		service.disable();
		assertTrue(service.getSpeedSet()==null);
	}
}