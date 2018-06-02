package compas.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CLLSDJACKT013 on 29/05/2018.
 */
public class Hash256Encode {
    //class instance
    public Hash256Encode(){

    }
    private Logger logger = LoggerFactory.getLogger(Hash256Encode.class);

    public String encode(String stringToEncode){
        String sha256hex = DigestUtils.sha256Hex(stringToEncode);

        return sha256hex;
    }
}
