package hello.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by CLLSDJACKT013 on 05/05/2018.
 */
@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {
    private final ServiceProperties serviceProperties;

    public MyService(ServiceProperties serviceProperties){
        this.serviceProperties = serviceProperties;
    }

    public String message(){
        return this.serviceProperties.getMessage();
    }
}
