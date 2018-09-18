package compas.restservice;

import com.google.gson.Gson;
import compas.models.apiresponse.ApiResponse;
import compas.security.GenerateSignature;
import compas.security.SignatureGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Configuration
public class RestServiceConfig {
/*    @Value("http://192.168.13.170:2000")
    public String SERVICE_IP_PORT;*/

    @Value("${api.username}")
    private String api_username;
    @Value("${api.password}")
    private String api_password;
    @Value("${api.key}")
    private String api_key;
    @Value("${api.security.key}")
    private String security_key;
    @Value("${api.server.timeout}")
    private String timeout;
    ServerResponse serverResponse = new ServerResponse();
    ApiResponse apiResponse = new ApiResponse();
    private Logger logger= LoggerFactory.getLogger(RestServiceConfig.class);
    private Gson gson = new Gson();
    private GenerateSignature  generateSignature = new GenerateSignature();
    private SignatureGenerator signatureGenerator = new SignatureGenerator();


    public String RestServiceConfiguration(String protocol,String IP,String PORT,String endpoint,String requestMethod,String input,String transId,String action){

        disableSslVerification();
        logger.info("POST REQUEST PAYLOAD::::"+input);
        String fullURL = String.format(protocol+"://"+IP.concat(":").concat(PORT)+""+endpoint);
        logger.info("REST SERVICE URL:::"+fullURL);
        String responseString = "";
        String current;
        /**********DISABLE THIS PIECE, SOME FIREWALLS CAN BLOCK PING************************/
/*        if(pingHost(IP,Integer.parseInt(PORT),30)){
            logger.info("SERVER UP AND RUNNING");
        }
        else{
            logger.info("SERVER IS DOWN");
            serverResponse.setCode(100);
            serverResponse.setMessage("Internal Server error: Server not reachable");
            return gson.toJson(serverResponse);
        }*/
/**************************************************************/
        try{
            URL url = new URL(fullURL);
            /*****Get some basic info of the supplied url********/
            System.out.println("URL is::::" +url.toString());
            System.out.println("Protocol is ::::::"+url.getProtocol());

            URLConnection connection = url.openConnection();
            //HttpsURLConnection httpsURLConnection = url.openConnection();
            if(requestMethod == "POST"){
                connection.setDoOutput(true);
            }
            HttpsURLConnection httpsURLConnection = null;
            HttpURLConnection httpURLConnection = null;

            if(connection instanceof HttpsURLConnection){
                httpsURLConnection = (HttpsURLConnection) connection;
                //set timeout
                httpsURLConnection.setConnectTimeout(Integer.parseInt(timeout));
                httpsURLConnection.setRequestMethod(requestMethod);
                //SET REQUEST HEADERS HERE
                httpsURLConnection.setRequestProperty("security_key",""+security_key);
                //httpsURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,transId,action));
                httpsURLConnection.setRequestProperty("signature",signatureGenerator.generateSignature(api_username.trim(),api_password.trim(),transId.trim(),action.trim(),api_key.trim()));
                if(requestMethod == "GET"){
                    logger.info("-----------------HATHAWAY GET-----------------");
                    if(httpsURLConnection.getResponseCode() <200 && httpsURLConnection.getResponseCode() >299){
                        logger.info("HTTPS CALL FAILED:::CODE"+httpsURLConnection.getResponseCode());
                        apiResponse.setCode(httpsURLConnection.getResponseCode());
                        apiResponse.setMessage(httpsURLConnection.getResponseMessage());
                        return gson.toJson(apiResponse);
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL:::" +responseString);
                    httpsURLConnection.disconnect();
                    return responseString;

                }
                else if(requestMethod == "POST"){
                    logger.info("--------------HATHAWAY POST---------------");
                    OutputStream os = httpsURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if(httpsURLConnection.getResponseCode() <200 ||  httpsURLConnection.getResponseCode() >299){
                        logger.info("HTTPS CALL FAILED:::"+httpsURLConnection.getResponseCode());
                        apiResponse.setCode(httpsURLConnection.getResponseCode());
                        serverResponse.setMessage(httpsURLConnection.getResponseMessage());
                        return gson.toJson(apiResponse);

                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL:::" +responseString);
                    httpsURLConnection.disconnect();
                    apiResponse = gson.fromJson(responseString,ApiResponse.class);
                    apiResponse.setCode(201);
                    return gson.toJson(apiResponse);
                }
                httpsURLConnection.disconnect();
            }
            else if(connection instanceof  HttpURLConnection){
                httpURLConnection =(HttpURLConnection)connection;
                httpURLConnection.setConnectTimeout(Integer.parseInt(timeout));
                httpURLConnection.setRequestMethod(requestMethod);
                httpURLConnection.setRequestProperty("security_key",""+security_key);
                //httpURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,transId,action));
                httpURLConnection.setRequestProperty("signature",signatureGenerator.generateSignature(api_username,api_password,transId,action,api_key));

                if(requestMethod == "GET"){
                    logger.info("----------------HATHAWAY----------------");

                    if(httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() >299){
                        logger.info("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                        serverResponse.setCode(httpURLConnection.getResponseCode());
                        serverResponse.setMessage(httpURLConnection.getResponseMessage());
                        return gson.toJson(serverResponse);
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

                }
                else if(requestMethod == "POST"){
                    logger.info("-------------HATHAWAY POST--------------");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();
                    if(httpURLConnection.getResponseCode() <200 ||  httpURLConnection.getResponseCode() >299){
                        logger.info("HTTP CALL FAILED::CODE"+httpURLConnection.getResponseCode());
                        apiResponse.setCode(httpURLConnection.getResponseCode());
                        apiResponse.setMessage(httpURLConnection.getResponseMessage());
                        return gson.toJson(apiResponse);
                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL >>>>>" +responseString);
                    httpURLConnection.disconnect();
                    apiResponse = gson.fromJson(responseString,ApiResponse.class);
                    apiResponse.setCode(201);
                    return gson.toJson(apiResponse);
                }
                httpURLConnection.disconnect();
                //return responseString;
            }
        }
        catch (java.net.SocketTimeoutException e){
            logger.info("Server Timeout");
            apiResponse.setCode(151);
            apiResponse.setMessage("Transaction Timeout");
            return gson.toJson(apiResponse);
        }
        catch (Exception e){
            //e.printStackTrace();
            logger.info("SERVER IS DOWN");
            apiResponse.setCode(151);
            apiResponse.setMessage("Internal Server error: Server not reachable");
            return gson.toJson(apiResponse);
        }
        return "";
    }
    public String RestServiceConfiguration(String protocol,String IP,String PORT,String endpoint,String requestMethod,String transId,String action){
        String fullURL = String.format(protocol+"://"+IP.concat(":").concat(PORT)+""+endpoint);
        String responseString = "";
        String current;
        //ping server to establish connection
        if(pingHost(IP,Integer.parseInt(PORT),30)){
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
                //httpURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));
                httpURLConnection.setRequestProperty("signature",signatureGenerator.generateSignature(api_username,api_password,transId,action,api_key));

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
                //httpsURLConnection.setRequestProperty("signature",generateSignature.generateTransactionSignature(api_key,api_username,api_password,"TRANSSID","XXX"));
                httpURLConnection.setRequestProperty("signature",signatureGenerator.generateSignature(api_username,api_password,transId,action,api_key));

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
            logger.info("SERVER IS DOWN");
            serverResponse.setCode(100);
            serverResponse.setMessage(" Internal Server error: Server not reachable");
            return gson.toJson(serverResponse);
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

    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
