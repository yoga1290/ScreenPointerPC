package yoga1290;

import java.awt.Robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {

    @Autowired
    RobotSvc robotSvc;

    @RequestMapping("/mm")
    private @ResponseBody String mm(@RequestParam int dx, @RequestParam int dy) throws Exception {
        robotSvc.mouseMove(dx, dy);
        return "";
    }

    @RequestMapping("/mp")
    private @ResponseBody String mp() throws Exception {
        System.out.println("\n/mouse press\n");
        robotSvc.mousePress();
        return "";
    }

    @RequestMapping("/mr")
    private @ResponseBody String mr() throws Exception {
        System.out.println("\n/mouse release\n");
        robotSvc.mouseRelease();
        return "";
    }

    @RequestMapping("/kp")
    private @ResponseBody String kp(@RequestParam int keyCode) throws Exception {
        System.out.println("\n/key press\n");
        robotSvc.keyPress(keyCode);
        return "";
    }

    @RequestMapping("/kr")
    private @ResponseBody String kr(@RequestParam int keyCode) throws Exception {
        System.out.println("\n/key release\n");
        robotSvc.keyRelease(keyCode);
        return "";
    }
}
