package compas;

import compas.models.OTP;
import compas.transaction.passwordpolicy.OTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.*;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
@SpringBootApplication(scanBasePackages = "compas")
@EnableConfigurationProperties(ApplicationService.class)
/*@EnableAutoConfiguration*/
@EntityScan("compas.models")
@EnableJpaRepositories(basePackages = {"compas.agent","compas.device","compas.accounts","compas.user","compas.bank","compas.txn_params","compas.utilities","compas.currency","compas.authentications","compas.transaction","compas.tariffs","compas.transactionschannel","compas.customer","compas.next_of_kin"})
public class Application  extends SpringBootServletInitializer{
    @Autowired
    private ApplicationService applicationService;
    private OTPRepository otpRepository = new OTPRepository();
    private Logger logger = LoggerFactory.getLogger(Application.class);
    private static String FILES_PATH = System.getProperty("catalina.base")+"/conf/";

    public static void main(String [] args){
        System.out.println(FILES_PATH);
        Properties p = System.getProperties();
        //System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, /path/to/config.xml); //TRY THIS PROP
        p.setProperty("logging.level.compas.transaction","INFO");
        //p.setProperty("spring.output.ansi.enabled","ALWAYS");
        //p.setProperty("logging.level.compas.MongoConfig","SEVERE");
        p.setProperty("logging.level.org.springframework.data","ERROR");
        p.setProperty("logging.file","C:\\Program Files\\apache-tomcat-8.5.20\\conf\\testing.log");
        p.setProperty("logging.config","${catalina.home}\\conf\\logback-test.xml");
        SpringApplication.run(Application.class,args);
    }
    public void managePasswordPolicy(){
        //applicationService.printApplicationProperties();
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
                    if((currentTimeLapse-timelapsed)>300000){
                        //otpRepository.updateOTPStatus(activeOTP.getPassword());
                        otpRepository.updateUnusedOTPs(activeOTP);
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
