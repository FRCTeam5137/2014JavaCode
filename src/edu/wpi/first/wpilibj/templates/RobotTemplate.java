/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;



import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
        RobotDrive chassis = new RobotDrive(1, 2);
        Joystick driveStick = new Joystick(1);
        double counter;
        
        int m_autoPeriodicLoops;

    public RobotTemplate() {
        this.counter = 0.0;
    }

        
        
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
chassis.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
chassis.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);

    }
    public void autonomousInit() {
        m_autoPeriodicLoops = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    
    	static int printSec;
	static int startSec;
    
	public void autonomousPeriodic() {
		
		m_autoPeriodicLoops++;


		if (m_autoPeriodicLoops < (5 * /*GetLoopsPerSec()*/50)) {
			// When on the first periodic loop in autonomous mode, start driving forwards at half speed
			chassis.drive(-0.5, 0.0);			// drive forwards at half speed
		}
                else {
			// After 2 seconds, stop the robot
			chassis.drive(0.0, 0.0);			// stop robot
		}
		
	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Counter", counter++);
        chassis.setSafetyEnabled(true);
        chassis.arcadeDrive(driveStick);
        
     
    }
    
        
    
    /**                       
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }

}

