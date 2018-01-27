/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee.test;

import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tangent.client.employee.EmployeeServiceClient;
import tangent.model.employee.domain.Employee;
import tangent.model.employee.domain.EmployeeProfile;
import tangent.model.employee.enums.DateRangeEnum;
import tangent.model.employee.enums.GenderEnum;
import tangent.model.employee.enums.PositionEnum;
import tangent.model.employee.enums.Race;
import tangent.model.employee.exception.EmployeeServiceException;

/**
 *
 * @author User1
 */
public class EmployeeServiceClientTest {
    
    private static final String URL_TARGET = "http://staging.tangent.tngnt.co/api";
    private static final String API_AUTHENTICATION_TOKEN = "2a3d1af2f3f6d1cddaa3012c1c465fcbdffa3678";
    
    
    @Test()
    public void testRequestEmployeeListWrongAuthenticationToken() {

        String wrongAuthenticationToken = "trdtstrsdxfdsersrs";
        EmployeeServiceClient client = new EmployeeServiceClient(URL_TARGET, wrongAuthenticationToken);
        try {
            client.requestEmployeeList();
            Assert.fail("Service call should have failed due to invalid token"); 
             
        } catch (EmployeeServiceException ex) {
            
        }
    }
    
    @Ignore
    @Test()
    public void testRequestEmployeeList() {

        EmployeeServiceClient client = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            List<Employee> employeeList = client.requestEmployeeList();
            if(employeeList != null && !employeeList.isEmpty() ){
                Employee employee = employeeList.get(0);
                Assert.assertTrue("Employee is underage", employee.getAge() > 18); 
                Assert.assertTrue("Employee birthdate is null", employee.getBirthDate() != null);
                Assert.assertTrue("Employee position is null", employee.getPosition() != null);
                Assert.assertTrue("Employee user profile is null", employee.getUser() != null);
            }
        } catch (EmployeeServiceException ex) {
            String testErrorMessage = "Service call failure due to: ResponseCode=[" + ex.getErrorCode() 
                    + "], ResponseErrorMessage=[" + ex.getErrorMessage() + "]";
            Assert.fail(testErrorMessage);
        }
    }
    @Ignore
    @Test
    public void testRequestEmployeeListFilter() {

        EmployeeServiceClient client = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            PositionEnum position = PositionEnum.FRONT_END_DEVELOPER; ;
            String userId = ""; userId = null;
            Race race = Race.WHITE; //race = null;
            DateRangeEnum startDateRange = DateRangeEnum.THIS_YEAR; startDateRange = null;
            GenderEnum gender = GenderEnum.FEMALE; gender = null;
            DateRangeEnum birthDateRange = DateRangeEnum.THIS_YEAR; birthDateRange = null;
            String emailContains = ""; emailContains = null;
            List<Employee> employeeList = client.requestEmployeeList(race, position, startDateRange, userId, gender, birthDateRange, emailContains);
            
            if(employeeList != null && !employeeList.isEmpty() ){
                for(Employee employee: employeeList){
                    System.out.println("GithubUser=[" + employee.getGithubUser() + "],"
                            + "Email=[" + employee.getEmail() + "]");
                }
                
                Employee employee = employeeList.get(0);
                Assert.assertTrue("Employee is underage", employee.getAge() > 18); 
                Assert.assertTrue("Employee birthdate is null", employee.getBirthDate() != null);
                Assert.assertTrue("Employee position is null", employee.getPosition() != null);
                Assert.assertTrue("Employee user profile is null", employee.getUser() != null);
            }
        } catch (EmployeeServiceException ex) {
            String testErrorMessage = "Service call failure due to: ResponseCode=[" + ex.getErrorCode() 
                    + "], ResponseErrorMessage=[" + ex.getErrorMessage() + "]";
            Assert.fail(testErrorMessage);
        }
    }
    
    @Ignore
    @Test()
    public void testRequestLoggedinUsersProfile() {

        EmployeeServiceClient client = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            String userApiAuthenticationToken = API_AUTHENTICATION_TOKEN;
            EmployeeProfile userProfile = client.requestLoggedinUsersProfile(userApiAuthenticationToken);
            if(userProfile != null){
                System.out.println("Employee Profile: id=[" + userProfile.getId() + "], "
                    + "Email=[" + userProfile.getEmail() + "]");
                Assert.assertTrue("Employee is underage", userProfile.getAge() > 18); 
                Assert.assertTrue("Employee birthdate is null", userProfile.getBirthDate() != null);
                Assert.assertTrue("Employee position is null", userProfile.getPosition() != null);
                Assert.assertTrue("Employee user profile is null", userProfile.getUser() != null);
            }
        } catch (EmployeeServiceException ex) {
            String testErrorMessage = "Service call failure due to: ResponseCode=[" + ex.getErrorCode() 
                    + "], ResponseErrorMessage=[" + ex.getErrorMessage() + "]";
            Assert.fail(testErrorMessage);
        }
    }
}
