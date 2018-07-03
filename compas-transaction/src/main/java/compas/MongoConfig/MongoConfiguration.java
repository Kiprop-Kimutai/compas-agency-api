package compas.MongoConfig;

import com.mongodb.MongoClient;
import ch.qos.logback.classic.Logger;
import compas.ApplicationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ch.qos.logback.classic.Level;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@PropertySource({"file:C:\\Program Files\\apache-tomcat-8.5.20\\conf\\application.properties"})
//@PropertySource("file:${catalina.home}\\conf\\application.properties")
@Configuration
public class MongoConfiguration {
    //static Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @Value("${mongodb.host}")
    public String mongodb_host;
    @Value("${mongodb.port}")
    public String mongodb_port;
    @Value("${mongodb.datasource}")
    public String mongo_database;
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
         org.slf4j.Logger logger = LoggerFactory.getLogger(MongoConfiguration.class);
       // rootLogger.setLevel(Level.ERROR);
        //MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("192.168.137.89"),"agency_pbu");
        logger.info("MONGO HOST::::"+mongodb_host);
        logger.info("MONGO PORT>>>>>>>"+mongodb_port);
        logger.info("MONGO DATABASE<<<<<>>>>>"+mongo_database);
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(mongodb_host,Integer.parseInt(mongodb_port)),mongo_database);
        return mongoTemplate;
    }
}
