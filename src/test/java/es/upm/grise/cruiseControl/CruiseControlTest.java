package es.upm.grise.cruiseControl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	@Mock
	private Speedometer speedometer;

	private CruiseControl cruiseControl;

	@BeforeEach
	public void setUp() {
		cruiseControl = new CruiseControl(speedometer);
	}


	@Test
	public void initialization_DefaultValuesAreCorrect() {
		assertFalse(cruiseControl.isEnabled());
		assertNull(cruiseControl.getSpeedSet());
		assertNull(cruiseControl.getSpeedLimit());
	}

	@Test
	public void setSpeedSet_ZeroOrNegative_ThrowsException() {
		assertThrows(IncorrectSpeedSetException.class, () -> {
			cruiseControl.setSpeedSet(0);
		});
		assertThrows(IncorrectSpeedSetException.class, () -> {
			cruiseControl.setSpeedSet(-10);
		});
	}

	@Test
	public void setSpeedSet_AboveSpeedLimit_ThrowsException() throws Exception {
		cruiseControl.setSpeedLimit(100);

		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> {
			cruiseControl.setSpeedSet(120);
		});
	}

	@Test
	public void setSpeedSet_ValidSpeedWithoutLimit_SetsSpeedAndEnables() throws Exception {
		cruiseControl.setSpeedSet(80);

		assertEquals(80, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	public void setSpeedSet_ValidSpeedEqualToLimit_SetsSpeedAndEnables() throws Exception {
		cruiseControl.setSpeedLimit(100);
		cruiseControl.setSpeedSet(100);

		assertEquals(100, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	public void setSpeedLimit_ZeroOrNegative_ThrowsException() {
		assertThrows(IncorrectSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(0);
		});
		assertThrows(IncorrectSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(-50);
		});
	}

	@Test
	public void setSpeedLimit_WhenSpeedAlreadySet_ThrowsException() throws Exception {
		cruiseControl.setSpeedSet(90);

		assertThrows(CannotSetSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(120);
		});
	}

	@Test
	public void setSpeedLimit_ValidLimit_SetsSpeedLimit() throws Exception {
		cruiseControl.setSpeedLimit(120);

		assertEquals(120, cruiseControl.getSpeedLimit());
	}

	@Test
	public void disable_WhenEnabled_DisablesAndClearsSpeedSet() throws Exception {
		cruiseControl.setSpeedLimit(120);
		cruiseControl.setSpeedSet(100);

		cruiseControl.disable();

		assertFalse(cruiseControl.isEnabled());
		assertNull(cruiseControl.getSpeedSet());
		assertEquals(120, cruiseControl.getSpeedLimit());
	}
}