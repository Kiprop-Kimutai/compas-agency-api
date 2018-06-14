package compas.bank;

import com.google.gson.Gson;
import compas.models.Bank_Branch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CLLSDJACKT013 on 10/05/2018.
 */
@RestController
@RequestMapping(path="/branch")
public class BranchController {
    private Logger logger = LoggerFactory.getLogger(BranchController.class);
    private Gson gson = new Gson();
    @Autowired(required = false)
    private BranchRepository branchRepository;

    @RequestMapping(path="/addBranch",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    @ResponseBody
    ResponseEntity saveBranch(@RequestBody String branchString){
        Bank_Branch branch = branchRepository.save(gson.fromJson(branchString,Bank_Branch.class));
        return ResponseEntity.status(201).body(branch);
    }
}
