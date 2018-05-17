package compas.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@SpringBootApplication(scanBasePackages = "compas")
public class Application extends SpringBootServletInitializer {

 /*   public static  void main(String [] args){
        Timer timer = new Timer();
        Date timenow = new Date();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Init");
                Date newDate = new Date();
                System.out.println(timenow);
                System.out.println(timenow.getTime());
                System.out.println(newDate.getTime());
                System.out.println("Time difference::" +(timenow.getTime()-newDate.getTime()));
            }
        },10000,10000);
    }*/

    public void updateExpiredPassword(){

    }

}
