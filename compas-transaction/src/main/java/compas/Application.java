package compas;

import com.google.gson.Gson;
import compas.models.OTP;
import compas.models.utilities.DSTV;
import compas.models.utilities.GOTV;
import compas.models.utilities.UMEME;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String [] args) throws Exception{
        Properties p = System.getProperties();
        //System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, /path/to/config.xml); //TRY THIS PROP
        p.setProperty("logging.level.compas.transaction","INFO");
        //p.setProperty("spring.output.ansi.enabled","ALWAYS");
        //p.setProperty("logging.level.compas.MongoConfig","SEVERE");
        p.setProperty("logging.level.org.springframework.data","ERROR");
        p.setProperty("logging.level.org.springframework.data","INFO");
        p.setProperty("logging.file","C:\\Program Files\\apache-tomcat-8.5.20\\conf\\testing.log");
        p.setProperty("logging.config","${catalina.home}\\conf\\logback-test.xml");
        //SpringApplication.run(Application.class,args);


/*        Date localDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-12 16:15:32.000");
        //Date localDate = new SimpleDateFormat("yyyyMMdd").parse("2018-08-03 19:00:12".replace("\\","").replace("/","-"));
        logger.info(""+localDate.getDate());
        logger.info("month"+localDate.getMonth());
        logger.info("date"+localDate.toString());
        logger.info(""+localDate.toLocaleString());
        logger.info("Today::"+new Date());
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("<<>>"+localDateTime);
        LocalDateTime fromDate = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime toDate = localDateTime.withDayOfMonth(7).withHour(23).withMinute(59).withSecond(59);
        logger.info("from:"+fromDate.toString().replace("T"," "));
        logger.info("to:"+toDate.toString().replace("T"," "));
        LocalDateTime localDateTime1 = LocalDateTime.now();
        logger.info("Day::"+localDateTime1.getDayOfMonth());
        LocalDateTime fastforward = localDateTime1.withMonth(4).withDayOfMonth(30);
        logger.info("Fast forward::"+fastforward.toString());
        LocalDateTime now  =  LocalDateTime.now();
        logger.info("MonthValue::"+now.getMonthValue());
        LocalDateTime lastMonth = now.minusMonths(8);
        logger.info(">>"+lastMonth);
        logger.info("BBB"+lastMonth.getMonthValue());
        logger.info("NN"+lastMonth.getMonth());*/

        logger.info(new Gson().toJson(new GOTV()));
        logger.info(new Gson().toJson(new DSTV()));
        logger.info(new Gson().toJson(new UMEME()));

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
