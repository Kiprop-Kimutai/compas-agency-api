package hello.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by CLLSDJACKT013 on 04/05/2018.
 */
@SpringBootApplication(scanBasePackages = "hello")
@EnableAutoConfiguration
public class Application/* extends SpringBootServletInitializer*/{
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    private Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main (String [] args) throws Exception{
        System.out.println("Jonah Hexx");
        SpringApplication.run(Application.class,args);
    }
    @Bean
    CommandLineRunner run(){
        return args ->{
            logger.info("Apllication init...");
        };
    }
}
