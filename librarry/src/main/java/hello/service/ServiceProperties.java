package hello.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by CLLSDJACKT013 on 05/05/2018.
 */
@ConfigurationProperties("service")
public class ServiceProperties {
    //a message for this service
    private String message;

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}

