package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	private Speedometer speedometer;
	private CruiseControl cruiseControl;

	@BeforeEach
	void setUp() {
		speedometer = mock(Speedometer.class);
		cruiseControl = new CruiseControl(speedometer);
	}

	@Test
	void initialValuesAreNullAndDisabled() {
		assertNull(cruiseControl.getSpeedSet());
		assertNull(cruiseControl.getSpeedLimit());
		assertFalse(cruiseControl.isEnabled());
	}

	@Test
	void setSpeedSetWithPositiveValueEnablesCruiseControl()
			throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		cruiseControl.setSpeedSet(100);

		assertEquals(100, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	void setSpeedSetWithZeroOrNegativeThrowsIncorrectSpeedSetException() {
		assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(0));
		assertThrows(IncorrectSpeedSetException.class, () -> cruiseControl.setSpeedSet(-20));
		assertNull(cruiseControl.getSpeedSet());
		assertFalse(cruiseControl.isEnabled());
	}

	@Test
	void setSpeedSetAboveSpeedLimitThrowsSpeedSetAboveSpeedLimitException()
			throws CannotSetSpeedLimitException, IncorrectSpeedLimitException {
		cruiseControl.setSpeedLimit(120);

		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> cruiseControl.setSpeedSet(121));
		assertNull(cruiseControl.getSpeedSet());
		assertFalse(cruiseControl.isEnabled());
	}

	@Test
	void setSpeedSetEqualToSpeedLimitSucceeds()
			throws CannotSetSpeedLimitException, IncorrectSpeedLimitException,
			SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		cruiseControl.setSpeedLimit(120);
		cruiseControl.setSpeedSet(120);

		assertEquals(120, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	void setSpeedLimitWithPositiveValueSetsLimit()
			throws CannotSetSpeedLimitException, IncorrectSpeedLimitException {
		cruiseControl.setSpeedLimit(90);

		assertEquals(90, cruiseControl.getSpeedLimit());
	}

	@Test
	void setSpeedLimitWithZeroOrNegativeThrowsIncorrectSpeedLimitException() {
		assertThrows(IncorrectSpeedLimitException.class, () -> cruiseControl.setSpeedLimit(0));
		assertThrows(IncorrectSpeedLimitException.class, () -> cruiseControl.setSpeedLimit(-50));
		assertNull(cruiseControl.getSpeedLimit());
	}

	@Test
	void setSpeedLimitWhenSpeedSetAlreadySetThrowsCannotSetSpeedLimitException()
			throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		cruiseControl.setSpeedSet(80);

		assertThrows(CannotSetSpeedLimitException.class, () -> cruiseControl.setSpeedLimit(120));
		assertNull(cruiseControl.getSpeedLimit());
	}

	@Test
	void disableDeactivatesCruiseControlAndResetsSpeedSet()
			throws CannotSetSpeedLimitException, IncorrectSpeedLimitException,
			SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		cruiseControl.setSpeedLimit(120);
		cruiseControl.setSpeedSet(100);

		cruiseControl.disable();

		assertFalse(cruiseControl.isEnabled());
		assertNull(cruiseControl.getSpeedSet());
		assertEquals(120, cruiseControl.getSpeedLimit());
	}

	@Test
	void setSpeedLimitSucceedsAfterDisable()
			throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException,
			CannotSetSpeedLimitException, IncorrectSpeedLimitException {
		cruiseControl.setSpeedSet(100);
		cruiseControl.disable();

		cruiseControl.setSpeedLimit(110);

		assertEquals(110, cruiseControl.getSpeedLimit());
	}

}
