/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import tangent.model.employee.domain.Employee;

/**
 *
 * @author User1
 */
public class EmployeeServiceClient {
    
    public static void main(String args[]){
        /**
         * GET_LOGGED_IN_USER http://staging.tangent.tngnt.co/api/user/me/ 
         * GET_ALL_EMPLOYEE_PROFILE_LIST http://staging.tangent.tngnt.co/api/employee/
         * GET_FILTERED_EMPLOYEE_PROFILE_LIST /api/employee/?race=C&position=2&start_date_range=4&user=12&gender=M&birth_date_range=4&email__contains=prav
         * 
         * 
         */
//        Client client = ClientBuilder.newClient();        
//        
//        Invocation.Builder invocationBuilder = client.target("http://staging.tangent.tngnt.co/api")
//                .path("/employee")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "pravin.gordhan")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pravin.gordhan")
//                .request(MediaType.APPLICATION_JSON);
//        Collection<COrder> readValues = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<COrder>>() { });
//        ClientConfig clientConfig = new ClientConfig();
//
//        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("pravin.gordhan", "pravin.gordhan");
//        clientConfig.register( feature) ;
//
//        clientConfig.register(JacksonFeature.class);
//
//        Client client = ClientBuilder.newClient( clientConfig );
//        WebTarget webTarget = client.target("http://staging.tangent.tngnt.co/api").path("employee");
//
//        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
//        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
//                                    .nonPreemptive()
//                                    .credentials("pravin.gordhan", "pravin.gordhan")
//                                    .build();
// 
//        final Client client = ClientBuilder.newClient();
//        client.register(feature);
//        WebTarget webTarget = client.target("http://staging.tangent.tngnt.co")
//                .path("api/employee")
//                .property("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
//        System.out.println("host3: " + webTarget.getUri().getHost() + ", path: " + webTarget.getUri().getPath());
//        Response response = null;
//        try{
//            response = invocationBuilder.get();
//            
////            Employee[] employeeArray = invocationBuilder.get(Employee[].class);
//            if(response != null){
//                int status = response.getStatus();
//                System.out.println("Status: " + status);
//                if(response.getStatus() == Response.Status.OK.getStatusCode() ){                    
//                    List<Employee> employeeList = response.readEntity(
//                            new GenericType<List<Employee>>(){});
//                }
//            }  
//
//        int i = 0;
//        }catch(Throwable e){
//            e.printStackTrace();
//        }
        Client client = ClientBuilder.newClient();
        
//        Invocation.Builder invocationBuilder = client.target("http://staging.tangent.tngnt.co/api/employee/")
//                .path("me")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "pravin.gordhan")
//                .property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pravin.gordhan")
//                .request(MediaType.APPLICATION_JSON);
        WebTarget webTarget = client.target("http://staging.tangent.tngnt.co/api")
                .path("employee");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
                .header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, "Token " + "2a3d1af2f3f6d1cddaa3012c1c465fcbdffa3678");
        Response response = invocationBuilder.get();
        List<Employee> employeeList = null;
        if(response != null){
            int status = response.getStatus();
            Object entity = response.getEntity();
            String entStr = entity.toString();
            System.out.println("Status: " + status);
            
            
                employeeList = response.readEntity(
                        new GenericType<ArrayList<Employee>>(){});
            
            if(response.getStatus() == Response.Status.OK.getStatusCode() ){ 
                String jsonStr = response.readEntity(String.class);

                //
                
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = null;
                try {
                    rootNode = mapper.readTree(jsonStr);
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (rootNode instanceof ArrayNode) {
                    try {
                        Employee[] objects = mapper.readValue(rootNode.toString(), Employee[].class);
                    } catch (IOException ex) {
                        Logger.getLogger(EmployeeServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (rootNode instanceof JsonNode) {
                    try {
                        Employee object = mapper.readValue(rootNode.toString(), Employee.class);
                    } catch (IOException ex) {
                        Logger.getLogger(EmployeeServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //
                }
            }
        } 
        
        System.out.print("Status 33333: " + response.getStatus() );
    }
}
