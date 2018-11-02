package compas.security;

import compas.MongoConfig.MongoConfiguration;
import compas.authentications.ApiKeyRepository;
import compas.models.ApiSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by CLLSDJACKT013 on 10/25/2018.
 */
//@PropertySource({"file:C:\\Program Files\\apache-tomcat-8.5.20\\conf\\application.properties"})
@PropertySource("file:${catalina.home}\\conf\\application.properties")
@Configuration
@Repository
public class CompasSignatureGenerator {

    @Autowired
    private  ApiKeyRepository apiKeyRepository;

    @Value("${mongodb.host}")
    public   String mongodb_host;
    @Value("${mongodb.port}")
    public String mongodb_port;
    @Value("${mongodb.datasource}")
    public String mongo_database;
    @Value("${api.ITERATIONS}")
    private  String ITERATIONS;

    @Value("${api.KEY_LENGTH}")
    private  String KEY_LENGTH;
    MongoConfiguration mongoConfiguration=new MongoConfiguration();
    private static Logger logger = LoggerFactory.getLogger(CompasSignatureGenerator.class);
    //generate a salt to be used for hashing
    public byte[] generateSalt(String api_key){
        mongoConfiguration.logConfigs();
        char [] dest = new char[api_key.length()];
        //api_key.getChars(7,35,dest,0);
        logger.info("<------>||"+api_key.length());
        api_key.getChars(0,api_key.length(),dest,0);
        return dest.toString().getBytes();
    }

    //return a hashed password with a pinch of salt
    public  byte[] generateApiKey(String apiKey,String username,char [] password,char [] action,byte [] salt){
        String actionedpassword =  password.toString().concat(action.toString());
        PBEKeySpec spec = new PBEKeySpec(actionedpassword.toCharArray(), salt, Integer.parseInt(ITERATIONS), Integer.parseInt(KEY_LENGTH));
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            logger.info("---------------------------");
            logger.info(""+skf.generateSecret(spec).getEncoded());
            ApiSecurity  apiSecurity = new ApiSecurity(apiKey,username,skf.generateSecret(spec).getEncoded(),action.toString());
            apiKeyRepository.save(apiSecurity);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    //return a hashed password with a pinch of salt
    public  ApiSecurity getHashedPassword(String apiKey,String username,char [] password,String action,byte [] salt){
        logger.info(ITERATIONS);
        logger.info(KEY_LENGTH);
        logger.info(mongodb_host);
        logger.info(mongodb_port);
        logger.info(mongo_database);
        String actionedpassword =  password.toString().concat(action);
        PBEKeySpec spec = new PBEKeySpec(actionedpassword.toCharArray(), salt, Integer.parseInt(ITERATIONS), Integer.parseInt(KEY_LENGTH));
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            logger.info("---------------------------");
            logger.info(""+skf.generateSecret(spec).getEncoded());
            ApiSecurity  apiSecurity = new ApiSecurity(apiKey,username,skf.generateSecret(spec).getEncoded(),action.toString());
            //apiKeyRepository.save(apiSecurity);
            return apiSecurity;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
    //retrieve salt for supplied user
    public  boolean didhashmatch(String username,String action,byte [] clienthashedpassword){
/*        byte [] savedHashedPassword = new byte[0];
        ApiSecurity apiSecurity = new ApiSecurity();
        List<ApiSecurity> apiSecurities = apiKeyRepository.findByUsername(username,action);
        savedHashedPassword = apiSecurities.get(0).getHash();*/
        if(apiKeyRepository.findByUsername(username,action).size() == 0){
            return false;
        }
        return isExpectedPassword(clienthashedpassword,apiKeyRepository.findByUsername(username,action).get(0).getHash());
    }
    //return true if the given password and salt match the hashed value, false otherwise
    public static boolean isExpectedPassword(byte [] clientGeneratedHash, byte[] expectedHash) {
        if (clientGeneratedHash.length != expectedHash.length) return false;
        for (int i = 0; i < clientGeneratedHash.length; i++) {
            if (clientGeneratedHash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}
