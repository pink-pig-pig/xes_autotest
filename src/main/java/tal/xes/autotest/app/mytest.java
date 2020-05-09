package tal.xes.autotest.app;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class mytest {

    @RequestMapping("/test")
    public String test(@RequestParam(value="name", defaultValue="baba") String name) {
        return name;
    }

    @RequestMapping("/")
    public String helloworld() {
        return "Hello World!";
    }
}