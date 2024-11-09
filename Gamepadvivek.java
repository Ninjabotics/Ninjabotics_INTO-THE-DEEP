package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "GamepadVivek", group = "TeleOp")
public class Gamepadvivek extends OpMode {

    private DcMotor frontLeft, frontRight, rearLeft, rearRight, rotation, extension;
    private CRServo pivot1, pivot2, intakeServo;

    @Override
    public void init() {
        // Initialize motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        rotation = hardwareMap.get(DcMotor.class, "rotation");
        extension = hardwareMap.get(DcMotor.class, "extension");

        pivot1 = hardwareMap.get(CRServo.class, "pivot1");
        pivot2 = hardwareMap.get(CRServo.class, "pivot2");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        // Set motor directions if needed
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        rearRight.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        
        double leftY = gamepad1.left_stick_y * 0.8; // Forward and backward
        double leftX = -gamepad1.left_stick_x * 0.8; // Left and right
        double rightX = -gamepad1.right_stick_x * 0.5; // Rotation

        // Linear movement with front wheels only
        if (Math.abs(rightX) < 0.1) { // Only move linearly if right joystick is not active
            double frontLeftPower = leftY + leftX;
            double frontRightPower = leftY - leftX;

            frontLeft.setPower(-frontLeftPower);
            frontRight.setPower(-frontRightPower);

            // Stop rear motors for linear movement
            rearLeft.setPower(0);
            rearRight.setPower(0);
        } else {
            // Rotation (turning) with all wheels
            double turnPower = rightX;

            frontLeft.setPower(-turnPower);
            frontRight.setPower(turnPower);
            rearLeft.setPower(-turnPower);
            rearRight.setPower(turnPower);
        }

        // Gamepad 2 (Arm controls)

        // Rotation control with A and B buttons
        if (gamepad2.left_bumper) {
            rotation.setPower(-0.2); // Move arm down
        } else if (gamepad2.right_bumper) {
            rotation.setPower(0.4); // Move arm up
        } else {
            rotation.setPower(0.1); // Apply hold power to prevent falling
        }

        // Extension control with triggers
        double extensionPower = 0;
        if (gamepad2.left_trigger > 0.1) {
            extensionPower = gamepad2.left_trigger/1.5; // Extend
            extension.setPower(extensionPower);
            rotation.setPower(-0.2); // Optional adjustment when extending
        } else if (gamepad2.right_trigger > 0.1) {
            extensionPower = -gamepad2.right_trigger/1.5; // Retract
            extension.setPower(extensionPower);
            rotation.setPower(0.3); // Optional adjustment when retracting
        }

         
        if (gamepad2.x) {
            intakeServo.setPower(1);// Open claw (move up)
        } else if (gamepad2.y) {
            intakeServo.setPower(-1); // Close claw (move down)
        } 
        
        
       if (gamepad2.a) {
           pivot1.setPower(0.7);
           pivot2.setPower(0.7);
       } else if (gamepad2.b) {
           pivot1.setPower(-0.1);
           pivot2.setPower(-0.1);
       } else if(gamepad2.dpad_up) {
           pivot1.setPower(-0.5);
           pivot2.setPower(-0.5);
       }
}}
