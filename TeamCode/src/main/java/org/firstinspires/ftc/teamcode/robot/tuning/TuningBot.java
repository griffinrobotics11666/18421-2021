package org.firstinspires.ftc.teamcode.robot.tuning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.dashboard.config.Config;
import com.amarcolini.joos.control.FeedforwardCoefficients;
import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.hardware.Imu;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.TankDrive;
import com.amarcolini.joos.localization.TankLocalizer;
import com.amarcolini.joos.trajectory.config.TankConstraints;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;

@Config
public class TuningBot extends TankDrive {
    public static double TRACK_WIDTH = 18.0;
    public static double MAX_VEL = 30.0;
    public static double MAX_ACCEL = 30.0;
    public static double MAX_ANG_VEL = Math.toRadians(180.0);
    public static double MAX_ANG_ACCEL = Math.toRadians(180.0);
    public static double WHEEL_RADIUS = 2.0;
    public static double GEAR_RATIO = 1.0;
    public static PIDCoefficients AXIAL_COEFFICIENTS = new PIDCoefficients(1);
    public static PIDCoefficients HEADING_COEFFICIENTS = new PIDCoefficients(1);
    public static FeedforwardCoefficients FF_COEFFICIENTS = new FeedforwardCoefficients(0.135, 0.02, 0.1);
//    kV = 0.03187, kStatic = 0.08548 kA = 0.0243

    private static MotorGroup getLeft(HardwareMap hMap) {
        MotorGroup left = new MotorGroup(
                new Motor(hMap, "front_left", 312.0, 537.7, WHEEL_RADIUS, GEAR_RATIO),
                new Motor(hMap, "back_left", 312.0, 537.7, WHEEL_RADIUS, GEAR_RATIO)
        );
        left.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        left.setFeedforwardCoefficients(FF_COEFFICIENTS);
        return left;
    }

    private static MotorGroup getRight(HardwareMap hMap) {
        MotorGroup right = new MotorGroup(
                new Motor(hMap, "front_right", 312.0, 537.7, WHEEL_RADIUS, GEAR_RATIO),
                new Motor(hMap, "back_right", 312.0, 537.7, WHEEL_RADIUS, GEAR_RATIO)
        );
        right.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        right.setFeedforwardCoefficients(FF_COEFFICIENTS);
        right.setReversed(true);
        return right;
    }

    private static @Nullable Imu getImu(HardwareMap hMap) {
        Imu imu = new Imu(hMap, "imu");
        imu.setAxis(Imu.Axis.Z);
        //TODO: do imu
        return imu;
    }

    public TuningBot(HardwareMap hMap) {
        super(getLeft(hMap), getRight(hMap), getImu(hMap), new TankConstraints(
                getLeft(hMap).maxRPM,
                TRACK_WIDTH,
                MAX_VEL,
                MAX_ACCEL,
                MAX_ANG_VEL,
                MAX_ANG_ACCEL
        ), AXIAL_COEFFICIENTS, HEADING_COEFFICIENTS);
        MotorGroup left = getLeft(hMap);
        MotorGroup right = getRight(hMap);
        double TPR = new Motor(hMap, "front_left", 312.0, 537.7, WHEEL_RADIUS, GEAR_RATIO).TPR;
        setLocalizer(new TankLocalizer(() -> Arrays.asList(left.getDistance(), right.getDistance()), () -> Arrays.asList(left.getDistanceVelocity() / TPR, right.getDistanceVelocity() / TPR), getConstraints().getTrackWidth(), this, true));
    }
}
