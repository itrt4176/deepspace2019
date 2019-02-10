/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import java.time.Duration;

import com.irontigers.PeriodicExecutor;
import com.irontigers.RobotMap;
import com.irontigers.RobotMap.XBoxController;
import com.irontigers.commands.AutoAlign;
import com.irontigers.commands.ElevatorLevel1Dropoff;
import com.irontigers.commands.ElevatorLevel1Pickup;
import com.irontigers.commands.ElevatorLevel2;
import com.irontigers.commands.ElevatorLevel3;
import com.irontigers.commands.ElevatorUp;
import com.irontigers.commands.ResetElevatorToDefault;
import com.irontigers.commands.SpearIn;
import com.irontigers.commands.SpearOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Basic Joystick for the robot. While technically this is not a Subsystem of
 * the Robot it is a piece of hardware that can be considered a Subsystem. It
 * provides sensor readings that can be used by the robot's command system (eg.
 * desired speed and rotation).
 *
 * This class, along with all Subsystems, uses the Singleton pattern to ensure
 * only a single instance of this Subsystem exists during the lifetime of the
 * Robot program. The instance will be created statically (the first time this
 * class is used) and can be accessed via the static
 * {@link XBoxController#instance()} method from anywhere in the program. The
 * use of this coding pattern is useful in keeping the class self-contained and
 * easy to use.
 */

public class NavigatorController extends Subsystem {

  private static NavigatorController instance = new NavigatorController();
  public static NavigatorController instance(){
    return instance;
  }

  private Joystick controller;
  private JoystickButton spearInButton;
  private JoystickButton spearOutButton;
  private JoystickButton elevatorDownButton;
  private JoystickButton elevatorUpButton;
  private JoystickButton resetElevatorButton;
  private JoystickButton activateAutoAlignmentButton;
  private JoystickButton elevatorLevel1PickupButton;
  private JoystickButton elevatorLevel1DropoffButton;
  private JoystickButton elevatorLevel2Button;
  private JoystickButton elevatorLevel3Button;

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("navigator_controller", Duration.ofMillis(5), () -> {
    readPeriodicControls();
  });
  
  private NavigatorController() {
    controller = new Joystick(RobotMap.XBoxController.NAVIGATOR_ID);

    spearInButton = new JoystickButton(controller, RobotMap.XBoxController.LEFT_TRIGGER);
    spearOutButton = new JoystickButton(controller, RobotMap.XBoxController.RIGHT_TRIGGER);
    elevatorDownButton = new JoystickButton(controller, RobotMap.XBoxController.LEFT_BUMPER);
    elevatorUpButton = new JoystickButton(controller, RobotMap.XBoxController.RIGHT_BUMPER);
    resetElevatorButton = new JoystickButton(controller, RobotMap.XBoxController.START);
    activateAutoAlignmentButton = new JoystickButton(controller, RobotMap.XBoxController.BACK);
    elevatorLevel1PickupButton = new JoystickButton(controller, RobotMap.XBoxController.X_BUTTON);
    elevatorLevel1DropoffButton = new JoystickButton(controller, RobotMap.XBoxController.A_BUTTON);
    elevatorLevel2Button = new JoystickButton(controller, RobotMap.XBoxController.B_BUTTON);
    elevatorLevel3Button = new JoystickButton(controller, RobotMap.XBoxController.Y_BUTTON);

    // While held down
    elevatorDownButton.whenActive(new SpearIn());
    elevatorUpButton.whenActive(new ElevatorUp());

    // Singular press
    spearInButton.whenReleased(new SpearIn());
    spearOutButton.whenReleased(new SpearOut());
    resetElevatorButton.whenReleased(new ResetElevatorToDefault());
    activateAutoAlignmentButton.whenReleased(new AutoAlign());
    elevatorLevel1PickupButton.whenReleased(new ElevatorLevel1Pickup());
    elevatorLevel1DropoffButton.whenReleased(new ElevatorLevel1Dropoff());
    elevatorLevel2Button.whenReleased(new ElevatorLevel2());
    elevatorLevel3Button.whenReleased(new ElevatorLevel3());

    periodicExecutor.start();
  }
  
  protected void readPeriodicControls(){
    // Nothing
  }

  @Override
  protected void initDefaultCommand() {
    // Nothing
  }
}
