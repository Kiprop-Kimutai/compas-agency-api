package compas.transaction.passwordpolicy;

import com.google.gson.Gson;
import compas.models.OTP;
import compas.restservice.RestServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
@RestController
@RequestMapping(path = "/api")
public class OTPController {
    private OTPRepository otpRepository = new OTPRepository();
    @Autowired
    private RestServiceConfig restServiceConfig;
    private Logger logger = LoggerFactory.getLogger(OTPController.class);
    private Gson gson = new Gson();
    @RequestMapping(path="/generateOTP",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    @ResponseBody
    public ResponseEntity generateOTP(@RequestBody String otpRequestString){
        Date request_time = new Date();
        OTP otp = gson.fromJson(otpRequestString,OTP.class);
        otp.setTransaction_type("cash_transaction");
        otp.setOperation("withdrawal");
        otp.setPassword(generatePassword());
        otp.setRequest_time(request_time);
        otp.setActive(true);
        String validOTP = otpRepository.savePassword(otp).getPassword();
        String responseBody = restServiceConfig.RestServiceConfiguration("/api/OTP","POST",gson.toJson(otp));
        return ResponseEntity.status(201).body(responseBody);
    }

    public String generatePassword(){
        Date requestedDate = new Date();
        Long requestedDateLong = requestedDate.getTime();
        System.out.println(requestedDateLong);
        Integer  lastThreeRandomNos = generateLastThreeRandomNumbers(1000,100).intValue();
        String password = requestedDateLong.toString().substring(requestedDateLong.toString().length()-3,requestedDateLong.toString().length()).concat(lastThreeRandomNos.toString());

        logger.info(password);
        return password;
    }
    public Double generateLastThreeRandomNumbers(Integer max,Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }

    public  void ManagePasswordPolicy(){
       Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               logger.info("   WATCH TIMER");
           }
       },1000,1000);
    }

}
