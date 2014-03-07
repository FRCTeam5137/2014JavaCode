package edu.wpi.first.wpilibj.templates;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;


public class BlockingArm {
    int extendButtonNum;
    int retractButtonNum;
    DigitalInput extendedLimit;
    DigitalInput retractedLimit;
    Victor actuator;
    Joystick stick;
    
    public BlockingArm(int actuatorNum,
                       int extLimDnum,
                       int retLimDnum,
                       Joystick stick,
                       int extendButtonNum,
                       int retractButtonNum) {
        
        actuator = new Victor(actuatorNum);
        extendedLimit = new DigitalInput(extLimDnum);
        retractedLimit = new DigitalInput(retLimDnum);
        this.stick = stick;
        this.retractButtonNum = retractButtonNum;
        this.extendButtonNum = extendButtonNum;
    }
    
    public void actuate() {
        boolean retract = false;
        boolean extend = false;
        
        /* Check if retract button pressed and limit not reached. */
        if (stick.getRawButton(retractButtonNum)&& retractedLimit.get()) {
            retract = true;
        }
        /* Check if extend button pressed and limit not reached. */
        else if (stick.getRawButton(extendButtonNum) && extendedLimit.get()) {
            extend = true;
        }
   
        /* Extend or retract arm */
        if (retract) {
            actuator.set(-1);
        }
        else if (extend) {
            actuator.set(1);
        }
        else {
            /* Stop motor if limit reached or neither button pressed */
            actuator.set(0);
        }
    }
}