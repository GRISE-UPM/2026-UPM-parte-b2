package es.upm.grise.cruiseControl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {

	private Speedometer speedometer;
	private CruiseControl cruiseControl;

	@BeforeEach
	public void setUp() {
		speedometer = mock(Speedometer.class);
		cruiseControl = new CruiseControl(speedometer);
	}

	@Test
	public void smokeTest() {}

	@Test
	public void testInicializacion() {
		assertNull(cruiseControl.getSpeedSet());
		assertNull(cruiseControl.getSpeedLimit());
		assertFalse(cruiseControl.isEnabled());
	}

	@Test
	public void testSetSpeedSetCorrecto() throws Exception {
		int velocidad = 60;
		cruiseControl.setSpeedSet(velocidad);
		assertEquals(60, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}
	@Test
	public void testSetSpeedSetCero() {
		assertThrows(IncorrectSpeedSetException.class, () -> {
			cruiseControl.setSpeedSet(0);
		});
	}

	@Test
	public void testSetSpeedSetNegativo() {
		assertThrows(IncorrectSpeedSetException.class, () -> {
			cruiseControl.setSpeedSet(-10);
		});
	}

	@Test
	public void testSetSpeedSetSuperaLimte() throws Exception {
		cruiseControl.setSpeedLimit(100);
		assertThrows(SpeedSetAboveSpeedLimitException.class, () -> {
			cruiseControl.setSpeedSet(120);
		});
	}

	@Test
	public void testSetSpeedSetIgualQueLimite() throws Exception {
		cruiseControl.setSpeedLimit(100);
		cruiseControl.setSpeedSet(100);
		assertEquals(100, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	public void testSetSpeedSetMenorQueLimite() throws Exception {
		cruiseControl.setSpeedLimit(100);
		cruiseControl.setSpeedSet(80);
		assertEquals(80, cruiseControl.getSpeedSet());
		assertTrue(cruiseControl.isEnabled());
	}

	@Test
	public void testSetSpeedLimitValido() throws Exception {
		cruiseControl.setSpeedLimit(120);
		assertEquals(120, cruiseControl.getSpeedLimit());
	}

	@Test
	public void testSetSpeedLimitCero() {
		assertThrows(IncorrectSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(0);
		});
	}

	@Test
	public void testSetSpeedLimitNegativo() {
		assertThrows(IncorrectSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(-15);
		});
	}

	@Test
	public void testSetSpeedLimitConSpeedSetFijado() throws Exception {
		cruiseControl.setSpeedSet(50);
		assertThrows(CannotSetSpeedLimitException.class, () -> {
			cruiseControl.setSpeedLimit(90);
		});
	}
	@Test
	public void testDisableDesactivaYBorraSpeedSet() throws Exception {
		cruiseControl.setSpeedLimit(120);
		cruiseControl.setSpeedSet(90);
		assertTrue(cruiseControl.isEnabled());

		cruiseControl.disable();

		assertFalse(cruiseControl.isEnabled());
		assertNull(cruiseControl.getSpeedSet());
		assertEquals(120, cruiseControl.getSpeedLimit());
	}
}
