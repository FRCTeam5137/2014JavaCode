/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;



import java.util.Random;
import java.util.Date;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
        RobotDrive chassis = new RobotDrive(1, 2);
        AnalogModule exampleAnalog;
        Joystick driveStick = new Joystick(1);
        Joystick camStick = new Joystick(2);
        Victor armLeft = new Victor(5);
        Victor armRight = new Victor(6);
        Relay armVertical = new Relay(1);
        DigitalInput extendedLimit = new DigitalInput(1);
        DigitalInput retractedLimit = new DigitalInput(2);
        DigitalInput leftExtendedLimit = new DigitalInput(3);
        DigitalInput leftRetractedLimit = new DigitalInput(4);
        DigitalInput rightExtendedLimit = new DigitalInput(6);
        DigitalInput rightRetractedLimit = new DigitalInput(5);
        boolean vertArmLower = false;
        boolean vertArmRaise = false;
        boolean leftArmRetract = false;
        boolean leftArmExtend = false;
        boolean rightArmRetract = false;
        boolean rightArmExtend = false; 
        double counter;
        Servo camPan = new Servo(3);
        Servo camTilt = new Servo(4);
        double tiltIn;
        double panIn;
        Random rn;
        int rand;
        long ms;
        
        int m_autoPeriodicLoops;
        int relayCounter;
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
        exampleAnalog = AnalogModule.getInstance(1);
        vertArmLower = false;
        vertArmRaise = false;

    }
    public void autonomousInit() {
        m_autoPeriodicLoops = 0;
        ms = (new Date()).getTime();
        rn = new Random(ms);
        rand = rn.nextInt();
    }

    /**
     * This function is called periodically during autonomous
     */
    
    	static int printSec;
	static int startSec;
    
	public void autonomousPeriodic() {

            m_autoPeriodicLoops++;

            chassis.drive(0.0, 0.0);
            if (m_autoPeriodicLoops < (3 * 50) && extendedLimit.get()) {
                armVertical.set(Relay.Value.kForward);
            }
            else {
                armVertical.set(Relay.Value.kOff);
            }
            if (m_autoPeriodicLoops < (1 * /*GetLoopsPerSec()*/50)) {
            // When on the first periodic loop in autonomous mode, start driving forwards at half speed

                if ((rand & 1) == 1){
                    chassis.drive(0.5, 0.0);			// drive forwards at half speed
                }
                else {
                    chassis.drive(-0.5, 0.0);
                }
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
        SmartDashboard.putNumber("Ultrasonic Value", exampleAnalog.getValue(1));
        SmartDashboard.putNumber("Voltage", exampleAnalog.getVoltage(1));
        
        chassis.setSafetyEnabled(true);
        chassis.arcadeDrive(driveStick);
        
        tiltIn = (camStick.getY() +0.5);
        camTilt.set(tiltIn);
        panIn = (camStick.getX() +0.5 );
        camPan.set(panIn);
        
        /* 
           Vertical arm
        */
        vertArmLower = false;
        vertArmRaise = false;
        
        /* Button 2 lowers arm */
        if (camStick.getRawButton(2) && retractedLimit.get()) {
            vertArmLower = true;
        }
        /* Button 3 Raises arm */
        else if (camStick.getRawButton(3) && extendedLimit.get()) {
            vertArmRaise = true;
        }
        
        /* Raise or lower arm */
        if (vertArmLower) {
            armVertical.set(Relay.Value.kReverse);
        }
        else if (vertArmRaise) {
            armVertical.set(Relay.Value.kForward);
        }
        else {
            /* Stop motor if limit reached or neither button pressed */
            armVertical.set(Relay.Value.kOff);
        }

        /* 
           Left Arm
        */
        leftArmRetract = false;
        leftArmExtend = false;
        
        /* Button 7 retracts arm */
        if (camStick.getRawButton(7)&& leftRetractedLimit.get()) {
            leftArmRetract = true;
        }
        /* Button 10 Raises arm */
        else if (camStick.getRawButton(10) && leftExtendedLimit.get()) {
            leftArmExtend = true;
        }
   
        /* Extend or retract arm */
        if (leftArmRetract) {
            armLeft.set(-1);
        }
        else if (leftArmExtend) {
            armLeft.set(1);
        }
        else {
            /* Stop motor if limit reached or neither button pressed */
            armLeft.set(0);
        }
        
         /* 
           Right Arm
        */
        rightArmRetract = false;
        rightArmExtend = false;
        
        /* Button 6 retracts arm */
        if (camStick.getRawButton(6) && rightRetractedLimit.get()) {
            rightArmRetract = true;
        }
        /* Button 11 Raises arm */
        else if (camStick.getRawButton(11) && rightExtendedLimit.get()) {
            rightArmExtend = true;
        }
        
        /* Extend or retract arm */
        if (rightArmRetract) {
            armRight.set(-1);
        }
        else if (rightArmExtend) {
            armRight.set(1);
        }
        else {
            /* Stop motor if limit reached or neither button pressed */
            armRight.set(0);
        }
            
    }
     
    /**                       
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }

}

