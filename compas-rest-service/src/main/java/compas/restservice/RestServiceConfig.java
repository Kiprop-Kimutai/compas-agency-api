package compas.restservice;

import com.google.gson.Gson;
import compas.security.GenerateSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Configuration
public class RestServiceConfig {
/*    @Value("http://192.168.13.170:2000")
    public String SERVICE_IP_PORT;*/
    @Value("${SERVICE.IP}")
    public String SERVICE_IP;
    @Value("${SERVICE.PORT}")
    public String SERVICE_PORT;
    @Value("${api.protocol}")
    private String protocol;
    @Value("${api.username}")
    private String api_username;
    @Value("${api.password}")
    private String api_password;
    @Value("${api.key}")
    private String api_key;
    @Value("${api.security.key}")
    private String security_key;

    ServerResponse serverResponse = new ServerResponse();
    private Logger logger= LoggerFactory.getLogger(RestServiceConfig.class);
    private Gson gson = new Gson();
    private GenerateSignature  generateSignature = new GenerateSignature();

    public String RestServiceConfiguration(String endpoint,String requestMethod,String input){
        String fullURL = String.format(protocol+"://"+SERVICE_IP.concat(":").concat(SERVICE_PORT)+""+endpoint);
        logger.info("REST SERVICE URL:::"+fullURL);
        String responseString = "";
        String current;
        if(pingHost(SERVICE_IP,Integer.parseInt(SERVICE_PORT),30)){
            logger.info("SERVER UP AND RUNNING");
        }
        else{
            logger.info("SERVER IS DOWN");
            serverResponse.setCode(500);
            serverResponse.setMessage("Internal Server error: Server not reachable");
            return gson.toJson(serverResponse);
        }
        try{
            URL url = new URL(fullURL);
            /*****Get some basic info of the supplied url********/
            System.out.println("URL is::::" +url.toString());
            System.out.println("Protocol is ::::::"+url.getProtocol());

            URLConnection connection = url.openConnection();
            if(requestMethod == "POST"){
                connection.setDoOutput(true);
            }
            HttpsURLConnection httpsURLConnection = null;
            HttpURLConnection httpURLConnection = null;

            if(connection instanceof HttpsURLConnection){
                httpsURLConnection = (HttpsURLConnection) connection;
                httpsURLConnection.setRequestMethod(requestMethod);
                    //SET REQUEST HEADERS HERE
                httpsURLConnection.setRequestProperty("owner","kimutai");
                httpsURLConnection.setRequestProperty("security_key",""+security_key);
                httpsURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));

                if(requestMethod == "GET"){
                    logger.info("-----------------HATHAWAY-----------------");
                    if(httpsURLConnection.getResponseCode() <200 && httpsURLConnection.getResponseCode() >299){
                        //throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                        logger.info("HTTP CALL FAILED:::CODE"+httpsURLConnection.getResponseCode());
                        serverResponse.setCode(httpsURLConnection.getResponseCode());
                        serverResponse.setMessage(httpsURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpsURLConnection.disconnect();
                    return responseString;

                }
                else if(requestMethod == "POST"){
                    logger.info("--------------HATHAWAY POST---------------");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if(httpsURLConnection.getResponseCode() <200 ||  httpsURLConnection.getResponseCode() >299){
                        //throw new RuntimeException("Failed::Http Error code::" +httpURLConnection.getResponseCode());
                        logger.info("HTTPS CALL FAILED:::"+httpsURLConnection.getResponseCode());
                        serverResponse.setCode(httpsURLConnection.getResponseCode());
                        serverResponse.setMessage(httpsURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL >>>>>" +responseString);
                    httpsURLConnection.disconnect();
                    serverResponse.setCode(201);
                    serverResponse.setMessage(responseString);
                    return gson.toJson(serverResponse);
                    //return responseString;
                }
                httpsURLConnection.disconnect();
                //return responseString;
            }
            else if(connection instanceof  HttpURLConnection){
                httpURLConnection =(HttpURLConnection)connection;
                httpURLConnection.setRequestMethod(requestMethod);
                httpURLConnection.setRequestProperty("owner","kimutai");
                httpURLConnection.setRequestProperty("security_key",""+security_key);
                httpURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));

                if(requestMethod == "GET"){
                    logger.info("----------------HATHAWAY----------------");

                    if(httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() >299){
                        logger.info("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                        serverResponse.setCode(httpURLConnection.getResponseCode());
                        serverResponse.setMessage(httpURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
                        //return ""+httpsURLConnection.getResponseCode()+"".concat(httpsURLConnection.getResponseMessage());
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    serverResponse.setCode(201);
                    serverResponse.setMessage(responseString);
                    return  gson.toJson(serverResponse);
                    //return responseString;

                }
                else if(requestMethod == "POST"){
                    logger.info("-------------HATHAWAY POST--------------");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if(httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                       // throw new RuntimeException("Failed::Http Error code::" +httpURLConnection.getResponseCode());
                        logger.info("HTTP CALL FAILED::CODE"+httpURLConnection.getResponseCode());
                        serverResponse.setCode(httpURLConnection.getResponseCode());;
                        serverResponse.setMessage(httpURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL >>>>>" +responseString);
                    httpURLConnection.disconnect();
                    serverResponse.setCode(201);
                    serverResponse.setMessage(responseString);
                    return gson.toJson(serverResponse);
                    //return responseString;
                }
                httpURLConnection.disconnect();
                //return responseString;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String RestServiceConfiguration(String endpoint,String requestMethod){
        String fullURL = String.format(SERVICE_IP.concat(SERVICE_PORT)+""+endpoint);
        String responseString = "";
        String current;
        //ping server to establish connection
        if(pingHost(SERVICE_IP,Integer.parseInt(SERVICE_PORT),30)){
            logger.info("SERVER UP AND RUNNING");
        }
        else{
            logger.info("SERVER DOWN");
            serverResponse.setCode(500);
            serverResponse.setMessage("Internal Server error: Server not reachable");
            return gson.toJson(serverResponse);
        }
        try{
            URL url = new URL(fullURL);
            /*****Get some basic info of the supplied url********/
            System.out.println("URL ::::" +url.toString());
            System.out.println("Protocol  ::::::"+url.getProtocol());

            URLConnection connection = url.openConnection();
            if(requestMethod == "POST"){
                connection.setDoOutput(true);
            }
            HttpURLConnection httpURLConnection = null;
            HttpsURLConnection httpsURLConnection = null;

            if(connection instanceof HttpURLConnection){
                httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.setRequestMethod(requestMethod);
                httpURLConnection.setRequestProperty("owner","kimutai");
                httpURLConnection.setRequestProperty("security_key",security_key);
                httpURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));

                if(requestMethod == "GET"){
                    logger.info("---------------HATHAWAY----------------");

                    if(httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() >299){
                        //throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                        logger.info("FAILED::Http Error code::"+httpURLConnection.getResponseCode());
                        serverResponse.setMessage(httpURLConnection.getResponseMessage());
                        serverResponse.setCode(httpURLConnection.getResponseCode());
                        return gson.toJson(serverResponse);
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    //return responseString;
                    serverResponse.setCode(201);
                    serverResponse.setMessage(responseString);
                    return gson.toJson(serverResponse);

                }
                httpURLConnection.disconnect();
                //return responseString;
            }
            else if(connection instanceof  HttpsURLConnection){
                httpsURLConnection = (HttpsURLConnection) connection;
                httpsURLConnection.setRequestMethod(requestMethod);
                httpURLConnection.setRequestProperty("owner","kimutai");
                httpsURLConnection.setRequestProperty("security_key",security_key);
                httpsURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));

                if(requestMethod == "GET"){
                    logger.info("------------------HATHAWAY------------------");

                    if(httpsURLConnection.getResponseCode() < 200 && httpsURLConnection.getResponseCode() >299){
                        //throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                        logger.info("HTTP CALL FAILED::"+httpsURLConnection.getResponseCode());
                        serverResponse.setCode(httpsURLConnection.getResponseCode());
                        serverResponse.setMessage(httpsURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    //return responseString;
                    serverResponse.setCode(201);
                    serverResponse.setMessage(responseString);
                    return gson.toJson(serverResponse);

                }

                httpURLConnection.disconnect();
                //return responseString;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    //boolean method to ping host
    public  boolean pingHost(String service_ip_port,int port,int timeout){
        try{
            Socket socket = new Socket();
            logger.info("<<<<<<<<<<<<<<<About to ping Host>>>>>>>>>>>>>");
            logger.info(service_ip_port);
            socket.connect(new InetSocketAddress(service_ip_port,port),timeout);
            return true;
/*            InetAddress address = InetAddress.getByName(service_ip_port);
            boolean reachable = address.isReachable(timeout);
            logger.info(""+reachable);
            return reachable;*/
        }
        catch (IOException e){
            //e.printStackTrace();
            return false;
        }
        //return false;
    }
}
