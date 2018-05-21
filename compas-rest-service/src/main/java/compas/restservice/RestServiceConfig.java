package compas.restservice;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by CLLSDJACKT013 on 07/05/2018.
 */
@Configuration
public class RestServiceConfig {
    @Value("http://192.168.13.170:2000")
    public String SERVICE_IP_PORT;

    private Logger logger= LoggerFactory.getLogger(RestServiceConfig.class);

    public String RestServiceConfiguration(String endpoint,String requestMethod,String input){
        String fullURL = String.format(SERVICE_IP_PORT+""+endpoint);
        String responseString = "";
        String current;
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


                if(requestMethod == "GET"){
                    logger.info("-----------------HATHAWAY-----------------");

                    if(httpsURLConnection.getResponseCode() <200 && httpsURLConnection.getResponseCode() >299){
                        throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;

                }
                else if(requestMethod == "POST"){
                    logger.info("--------------HATHAWAY POST---------------");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if(httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                        throw new RuntimeException("Failed::Http Error code::" +httpURLConnection.getResponseCode());
                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL >>>>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;
                }
                httpURLConnection.disconnect();
                return responseString;
            }
            else if(connection instanceof  HttpURLConnection){
                httpURLConnection =(HttpURLConnection)connection;
                httpURLConnection.setRequestMethod(requestMethod);

                if(requestMethod == "GET"){
                    logger.info("----------------HATHAWAY----------------");

                    if(httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() >299){
                        throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;

                }
                else if(requestMethod == "POST"){
                    logger.info("-------------HATHAWAY POST--------------");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if(httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                        throw new RuntimeException("Failed::Http Error code::" +httpURLConnection.getResponseCode());
                    }
                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        responseString += current;
                    }
                    logger.info("Response from URL >>>>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;
                }
                httpURLConnection.disconnect();
                return responseString;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String RestServiceConfiguration(String endpoint,String requestMethod){
        String fullURL = String.format(SERVICE_IP_PORT+""+endpoint);
        String responseString = "";
        String current;
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

                if(requestMethod == "GET"){
                    logger.info("---------------HATHAWAY----------------");

                    if(httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() >299){
                        throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;

                }
                httpURLConnection.disconnect();
                return responseString;
            }
            else if(connection instanceof  HttpsURLConnection){
                httpsURLConnection = (HttpsURLConnection) connection;
                httpsURLConnection.setRequestMethod(requestMethod);

                if(requestMethod == "GET"){
                    logger.info("------------------HATHAWAY------------------");

                    if(httpsURLConnection.getResponseCode() < 200 && httpsURLConnection.getResponseCode() >299){
                        throw new RuntimeException("Failed: Http Error code::"+httpURLConnection.getResponseCode());
                    }

                    BufferedReader dataIn = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while((current = dataIn.readLine()) != null){
                        System.out.println(current);
                        responseString += current;
                    }
                    logger.info("Response from URL >>>" +responseString);
                    httpURLConnection.disconnect();
                    return responseString;

                }

                httpURLConnection.disconnect();
                return responseString;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
