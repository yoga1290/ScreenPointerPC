package yoga1290;

import java.awt.Robot;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.InputEvent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class RobotSvc {

    private Robot robot;

    public RobotSvc() throws Exception {
        robot = new Robot();
    }

    public void mouseMove(int dx, int dy) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(p.x + dx, p.y + dy);
    }

    public void mousePress() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    public void mouseRelease() {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void keyPress(int keyCode) {
        robot.keyPress(keyCode);
    }

    public void keyRelease(int keyCode) {
        robot.keyRelease(keyCode);
    }
}
