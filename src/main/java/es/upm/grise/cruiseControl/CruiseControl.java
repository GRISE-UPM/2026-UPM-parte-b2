package es.upm.grise.cruiseControl;

import es.upm.grise.cruiseControl.exceptions.CannotSetSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedLimitException;
import es.upm.grise.cruiseControl.exceptions.IncorrectSpeedSetException;
import es.upm.grise.cruiseControl.exceptions.SpeedSetAboveSpeedLimitException;

public class CruiseControl {
	
	@SuppressWarnings("unused")
	private Speedometer speedometer;
	private Integer speedLimit;
	private Integer speedSet;
	private boolean enabled = false;
	
	public CruiseControl(Speedometer speedometer) {
		
		this.speedometer = speedometer;
		
		this.speedSet = null;
		
		this.speedLimit = null;
		
	}
	
	public void setSpeedSet(int speedSet) throws SpeedSetAboveSpeedLimitException, IncorrectSpeedSetException {
		
		if(speedSet <= 0) {
			
			throw new IncorrectSpeedSetException();
			
		}
		
		if(speedLimit != null) {
			
			if(speedSet > speedLimit) {
				
				throw new SpeedSetAboveSpeedLimitException();
				
			}
		}
		
		this.speedSet = speedSet;
		this.enabled = true;
		
	}
	
	public void setSpeedLimit(int speedLimit) throws CannotSetSpeedLimitException, IncorrectSpeedLimitException {
			
		if(speedLimit <= 0) {

			throw new IncorrectSpeedLimitException();

		}

		if(speedSet != null) {

			throw new CannotSetSpeedLimitException();

		}

		this.speedLimit = speedLimit;

	}
	
	public void disable() {
		
		enabled = false;
		
		speedSet = null;
		
	}

	// Helper
	public boolean isEnabled() {
		return enabled;
	}

	// Helper
	public Integer getSpeedLimit() {
		return speedLimit;
	}

	// Helper
	public Integer getSpeedSet() {
		return speedSet;
	}

}
