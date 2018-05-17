package compas.accounts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by CLLSDJACKT013 on 11/05/2018.
 */
public class GenerateAccountNumber {
    private Logger logger  = LoggerFactory.getLogger(GenerateAccountNumber.class);
     public GenerateAccountNumber(){}
    public static void main(String [] args){
        GenerateAccountNumber generateAccountNumber = new GenerateAccountNumber();
        generateAccountNumber.generateAccNumber("110","8457","29785761");
    }

    public String generateAccNumber(String BIN,String branch_code,String id_number){
        logger.info("Init...");
        //split id_number string to an Array of id_number chars
        logger.info(id_number);
        //String [] id_number_character = id_number.split("(?!^)");
         String last3=id_number.substring(id_number.length()-3,id_number.length());
         char[] arrl3=last3.toCharArray();
         String str=arrl3[2]+""+arrl3[1]+""+arrl3[0];
         logger.info(str);

        Integer  lastThreeNumbers = generateLastThreeRandomNumbers(1000,100).intValue();
        logger.info("account no::"+BIN.concat(branch_code).concat(str).concat(lastThreeNumbers.toString()));
        return BIN.concat(branch_code).concat(str).concat(lastThreeNumbers.toString());
    }

    public Double generateLastThreeRandomNumbers(Integer max,Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }
}
