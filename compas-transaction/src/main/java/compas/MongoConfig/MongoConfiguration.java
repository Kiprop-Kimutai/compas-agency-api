package compas.MongoConfig;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Configuration
public class MongoConfiguration {
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"),"agency_pbu");
        return mongoTemplate;
    }
}
