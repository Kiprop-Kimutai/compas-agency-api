package compas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import compas.agent.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
/**
 * Created by CLLSDJACKT013 on 25/05/2018.
 */

@Service
//@PropertySource({"file:C:\\Program Files\\apache-tomcat-8.5.20\\conf\\application.properties"})
@PropertySource("file:${catalina.home}\\conf\\application.properties")
@ConfigurationProperties
    public class ApplicationService
    {

        @Value("${api.username}")
        private static String api_username;
        @Value("${api.password}")
        private static String api_password;
        @Autowired
        private Environment environment;
        @Autowired
        private AgentRepository agentRepository;
        @Value("${server.port}")
        private static String PORT;
        private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);
/*        private static String FILES_PATH = System.getProperty("catalina.base")+"/conf/";
        File configDir = new File(System.getProperty("catalina.base"), "conf");
        File configFile = new File(configDir, "application.properties");
        InputStream stream = new FileInputStream(configFile);*/



        public ApplicationService() throws FileNotFoundException {

        }


        public void setPORT(String port) { PORT = port; }

        public String getPORT() {
            return PORT;
        }

        public static void logConfigs(){
            logger.info("...."+api_username);
            logger.info("....."+api_password);
        }

    }

