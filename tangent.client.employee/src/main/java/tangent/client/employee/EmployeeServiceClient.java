/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import tangent.model.employee.domain.Employee;
import tangent.model.employee.domain.EmployeeProfile;
import tangent.model.employee.enums.DateRangeEnum;
import tangent.model.employee.enums.GenderEnum;
import tangent.model.employee.enums.PositionEnum;
import tangent.model.employee.enums.Race;
import tangent.model.employee.exception.EmployeeServiceException;
import tangent.model.employee.exception.EmployeeServiceExceptionEnum;

/**
 *
 * @author User1
 */
public class EmployeeServiceClient {
    
    private final String urlTarget;
    private final String apiAuthenticationToken;
    
    private static final String ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST = "employee";
    private static final String ENDPOINT_PATH_GET_LOGGED_IN_USER = ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST + "/me";
    
    public EmployeeServiceClient(String urlTarget, String apiAuthenticationToken){        
        this.urlTarget = urlTarget;
        this.apiAuthenticationToken = apiAuthenticationToken;
    }
    
    public List<Employee> requestEmployeeList() throws EmployeeServiceException{
        
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(
                this.urlTarget, ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST, apiAuthenticationToken);
        
        Response response = endpoint.get();
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus != Response.Status.OK.getStatusCode() ){
                String errorMessage = "requestEmployeeList() failed due to responseStatus=["
                        + responseStatus + "], responseErrorReason=[" + response.getStatusInfo().getReasonPhrase();
                throw new EmployeeServiceException(EmployeeServiceExceptionEnum.SERVICE_ERROR, errorMessage);
            }
        }
        List<Employee> employeeList = extractEmployeeList(response);
        return employeeList;
    }
    
    public List<Employee> requestEmployeeList(Race race, PositionEnum position, DateRangeEnum startDateRange, String userId,
            GenderEnum gender, DateRangeEnum birthDateRange, String emailContains) throws EmployeeServiceException{
        
        Map<String, String> queryParamMap = new HashMap<>();
        if(race != null) queryParamMap.put("race", race.getRaceCode() );
        if(position != null) queryParamMap.put("position", position.positionCode()+"");
        if(startDateRange != null) queryParamMap.put("start_date_range", startDateRange.getDateRangeCode()+"" );
        if(userId != null) queryParamMap.put("user", userId);
        if(gender != null) queryParamMap.put("gender", gender.getGenderCode());
        if(birthDateRange != null) queryParamMap.put("birth_date_range", birthDateRange.getDateRangeCode()+"" );
        if(emailContains != null) queryParamMap.put("email__contains", emailContains);
        
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(urlTarget, 
                ENDPOINT_PATH_GET_ALL_EMPLOYEE_PROFILE_LIST, apiAuthenticationToken, queryParamMap);
        
        Response response = callService(endpoint);
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus != Response.Status.OK.getStatusCode() ){
                String errorMessage = "requestEmployeeList() failed due to responseStatus=["
                        + responseStatus + "], responseErrorReason=[" + response.getStatusInfo().getReasonPhrase();
                throw new EmployeeServiceException(EmployeeServiceExceptionEnum.SERVICE_ERROR, errorMessage);
            }
        }
        List<Employee> employeeList = extractEmployeeList(response);
        return employeeList;
    }
    
    public EmployeeProfile requestLoggedinUsersProfile(String apiAuthenticationToken) throws EmployeeServiceException{
        Invocation.Builder endpoint = EndPointFactory.getServiceEndpoint(
                urlTarget, ENDPOINT_PATH_GET_LOGGED_IN_USER, apiAuthenticationToken);
        Response response = endpoint.get();
        
        EmployeeProfile employeeProfile = null;
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus == Response.Status.OK.getStatusCode() ){                
                try{
                    employeeProfile = response.readEntity(
                            new GenericType<EmployeeProfile>(){});
                }catch(ProcessingException ps){
                    String errorMessage = "errorMessage=[" + ps.getMessage() + "], cause=[" +
                            ps.getCause().getMessage() + "].";
                    throw new EmployeeServiceException(EmployeeServiceExceptionEnum.PROCESSING_ERROR, errorMessage);
                }
            }else{
                    
                String errorMessage = "requestEmployeeList() failed due to responseStatus=["
                        + responseStatus + "], responseErrorReason=[" + response.getStatusInfo().getReasonPhrase();
                throw new EmployeeServiceException(EmployeeServiceExceptionEnum.SERVICE_ERROR, errorMessage);
            }
        }
        return employeeProfile;
    }

    private List<Employee> extractEmployeeList(Response response) throws EmployeeServiceException {
        
        List<Employee> employeeList = null;
        if(response != null){            
            int responseStatus = response.getStatus();
            if(responseStatus == Response.Status.OK.getStatusCode() ){ 
                try{
                    employeeList = response.readEntity(
                               new GenericType<ArrayList<Employee>>(){});                
                }catch(ProcessingException ps){
                    String errorMessage = "errorMessage=[" + ps.getMessage() + "], cause=[" +
                            ps.getCause().getMessage() + "].";
                    throw new EmployeeServiceException(EmployeeServiceExceptionEnum.PROCESSING_ERROR, errorMessage);
                }
            }
        }
        return employeeList;
    }

    private Response callService(Invocation.Builder endpoint) throws EmployeeServiceException {
        
        Response response = null;
        try{
            response = endpoint.get();
        }catch(javax.ws.rs.ProcessingException pe){
            String errorMessage = "errorMessage=[" + pe.getMessage() + "], cause=[" +
                    pe.getCause().getMessage() + "].";
            throw new EmployeeServiceException(EmployeeServiceExceptionEnum.PROCESSING_ERROR, errorMessage);
        }
        return response;
    }
}
