package yoga1290;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class RobotSvc {

    private Robot robot;
    private int x;
    private int y;

    public RobotSvc() throws Exception {
        robot = new Robot();
    }

    public void mouseMove(int dx, int dy) {
        x += dx;
        y += dy;
        robot.mouseMove(x, y);
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
