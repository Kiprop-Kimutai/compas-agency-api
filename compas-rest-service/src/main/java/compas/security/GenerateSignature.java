package compas.security;

/**
 * Created by CLLSDJACKT013 on 29/05/2018.
 */
public class GenerateSignature {
    String signature = null;
    private Hash256Encode hash256Encode = new Hash256Encode();
    private Sha256HmacEncode sha256HmacEncode = new Sha256HmacEncode();
    //default constructor
    public GenerateSignature(){

    }

    public String generateTransactionSignature(String apiKey,String username,String password,String transactionId,String action){
        signature = sha256HmacEncode.encode(apiKey,(username.concat(hash256Encode.encode(password)).concat(transactionId).concat(action)));
        return signature;
    }
}
