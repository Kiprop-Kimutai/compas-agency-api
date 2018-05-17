package hello.test;

import hello.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 04/05/2018.
 */
@RestController
@RequestMapping(path="/test")
public class TestController {
    private MyService myService;
    public TestController(MyService service){
        this.myService = service;
    }
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping(method= RequestMethod.GET,path="/api",produces = "application/json")
    @ResponseBody
    public ResponseEntity testAPI(){
        return ResponseEntity.ok("scuffing success");
    }

    @RequestMapping(method = RequestMethod.GET,path="/module",produces = "application/json")
    @ResponseBody
    public String testModule(){
        return (myService.message());
        //return "ok...";
    }

    /*public static void main(String [] args) throws Exception{
        System.out.println("Application init...");
        SpringApplication.run(TestController.class,args);
    }*/
}
