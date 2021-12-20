package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.Nullable;

import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.Component;
import com.amarcolini.joos.hardware.Servo;

public class Bucket implements Component {
    private final Servo servo;
    private boolean isOpen = false;

    public Bucket(Servo servo) {
        this.servo = servo;
        servo.setPosition(1.0);
    }

    public void open() {
        servo.setPosition(0.58);
        isOpen = true;
    }

    public void close() {
        servo.setPosition(1.0);
        isOpen = false;
    }

    public void toggle() {
        if (isOpen) close();
        else open();
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void update() {
        servo.update();
    }
}
