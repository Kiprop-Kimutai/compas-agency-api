package compas;

import compas.models.OTP;
import compas.transaction.passwordpolicy.OTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
@SpringBootApplication(scanBasePackages = "compas")
public class Application extends SpringBootServletInitializer{
    private OTPRepository otpRepository = new OTPRepository();
    private Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String [] args){
        SpringApplication.run(Application.class,args);
    }
    public void managePasswordPolicy(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //logger.info("   WATCH TIMER");
                Date currentDate = new Date();
                //get time in milliseconds
                Long currentTimeLapse = currentDate.getTime();
                //fetch times from oTP collection where with active status
                List<OTP> activeOTPS = otpRepository.fetchActiveOTPs();
                activeOTPS.forEach((activeOTP)->{
                    Long timelapsed = activeOTP.getRequest_time().getTime();
                    if((currentTimeLapse-timelapsed)>60000){
                        otpRepository.updateOTPStatus(activeOTP.getPassword());
                    }
                });
            }
        },1000,1000);
    }
    @Bean
    CommandLineRunner run(){
        return (String... args) ->{
            Application app = new Application();
            app.managePasswordPolicy();
        };
    }

}
