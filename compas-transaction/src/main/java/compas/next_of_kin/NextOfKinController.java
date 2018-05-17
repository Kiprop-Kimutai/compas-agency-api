package compas.next_of_kin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 09/05/2018.
 */
@RestController
@RequestMapping(path="/api/next_of_kin")
public class NextOfKinController {

    @RequestMapping(path="/add",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addNextOfKin(@RequestBody String next_of_kin){
        return ResponseEntity.ok("");

    }

}
