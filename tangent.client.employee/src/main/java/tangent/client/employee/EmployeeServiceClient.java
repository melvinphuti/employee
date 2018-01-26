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
import tangent.model.employee.domain.Position;
import tangent.model.employee.enums.DateRange;
import tangent.model.employee.enums.Gender;
import tangent.model.employee.enums.Race;

/**
 *
 * @author User1
 */
public class EmployeeServiceClient {
    
    private String urlTarget;
    private String apiAuthenticationToken;
    
    private static final String ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST = "employee";
    private static final String ENDPOINT_PATH_GET_LOGGED_IN_USER = ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST + "/me";
    
    public EmployeeServiceClient(String urlTarget, String apiAuthenticationToken){        
        this.urlTarget = urlTarget;
        this.apiAuthenticationToken = apiAuthenticationToken;
    }
    
    public List<Employee> requestEmployeeList(){
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(
                urlTarget, ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST, apiAuthenticationToken);
        Response response = endpoint.get();
        
        List<Employee> employeeList = null;
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus == Response.Status.OK.getStatusCode() ){                
                employeeList = response.readEntity(
                        new GenericType<ArrayList<Employee>>(){});
            }
        }
        return employeeList;
    }
    
    public List<Employee> requestEmployeeList(Race race, Position position, DateRange startDateRange, String userId,
            Gender gender, DateRange birthDateRange, String emailContains){
        
        String path = ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST + "/?race=" + race
                + "&position=" + position
                + "&start_date_range=" + startDateRange
                + "&user=" + userId
                + "&gender=" + gender
                + "&birth_date_range=" + birthDateRange
                + "&email__contains=" + emailContains;
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(
                urlTarget, path, apiAuthenticationToken);
        Response response = endpoint.get();
        
        List<Employee> employeeList = null;
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus == Response.Status.OK.getStatusCode() ){                
                employeeList = response.readEntity(
                        new GenericType<ArrayList<Employee>>(){});
            }
        }
        return employeeList;
    }
    
    public List<Employee> requestLoggedinUsersProfile(String apiAuthenticationToken){
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(
                urlTarget, ENDPOINT_PATH_GET_LOGGED_IN_USER, apiAuthenticationToken);
        Response response = endpoint.get();
        
        List<Employee> employeeList = null;
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus == Response.Status.OK.getStatusCode() ){                
                employeeList = response.readEntity(
                        new GenericType<ArrayList<Employee>>(){});
            }
        }
        return employeeList;
    }
    
    public static void main(String args[]){
        /**
         * GET_LOGGED_IN_USER http://staging.tangent.tngnt.co/api/user/me/ 
         * GET_ALL_EMPLOYEE_PROFILE_LIST http://staging.tangent.tngnt.co/api/employee/
         * GET_LOGGED_IN_USER http://staging.tangent.tngnt.co/api/employee/me/
         * GET_FILTERED_EMPLOYEE_PROFILE_LIST /api/employee/?race=C&position=2&start_date_range=4&user=12&gender=M&birth_date_range=4&email__contains=prav
         * 
         * 
         */
        Client client = ClientBuilder.newClient();
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
                
        
                System.out.print("Status 9999: " + response.getStatus() );
            
        } 
    }
}
