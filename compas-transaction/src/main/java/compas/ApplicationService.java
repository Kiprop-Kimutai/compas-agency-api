package compas;

import java.util.Properties;
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
    @PropertySource({"file:C:\\Program Files\\apache-tomcat-8.5.20\\conf\\application.properties"})
    @ConfigurationProperties
    public class ApplicationService
    {
        @Autowired
        private Environment environment;
        Properties props = new Properties();
        @Value("${server.port}")
        private String PORT;

        public ApplicationService() {}

        public void setPORT(String port) { PORT = port; }

        public String getPORT() {
            return PORT;
        }
    }

