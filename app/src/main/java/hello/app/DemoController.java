package hello.app;

import hello.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 05/05/2018.
 */
//@SpringBootApplication(scanBasePackages="hello")
@RestController
@RequestMapping(path="/api")
public class DemoController {
   private final MyService myService;
    public DemoController(MyService myService){
        this.myService = myService;
    }

    @RequestMapping(path="/kim",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity getMessage(){
        return ResponseEntity.ok(myService.message());
    }
/*    public static void main (String [] args) throws Exception{
        SpringApplication.run(DemoController.class,args);
    }*/
}
