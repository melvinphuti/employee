/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import tangent.client.employee.EmployeeServiceClient;
import tangent.model.employee.domain.Employee;
import tangent.model.employee.domain.Position;
import tangent.model.employee.enums.DateRange;
import tangent.model.employee.enums.Gender;
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
    
    @Test
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
            String testErrorMessage = "Service call failure due to: ResponseCode=[" + ex.getResponseCode() + "], "
                + "ErrorMessage=[" + ex.getErrorMessage() + "]";
            Assert.fail(testErrorMessage);
        }
    }

    @Test
    public void testRequestEmployeeListFilter() {

        EmployeeServiceClient client = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            PositionEnum position = PositionEnum.BACK_END_DEVELOPER;
            String userId = "";
            Race race = Race.BLACK_AFRICAN;
            DateRange startDateRange = DateRange.THIS_YEAR;
            Gender gender = Gender.FEMALE;
            DateRange birthDateRange = DateRange.THIS_YEAR;
            String emailContains = "";
            List<Employee> employeeList = client.requestEmployeeList(race, position, startDateRange, userId, gender, birthDateRange, emailContains);
            if(employeeList != null && !employeeList.isEmpty() ){
                Employee employee = employeeList.get(0);
                Assert.assertTrue("Employee is underage", employee.getAge() > 18); 
                Assert.assertTrue("Employee birthdate is null", employee.getBirthDate() != null);
                Assert.assertTrue("Employee position is null", employee.getPosition() != null);
                Assert.assertTrue("Employee user profile is null", employee.getUser() != null);
            }
        } catch (EmployeeServiceException ex) {
            String testErrorMessage = "Service call failure due to: ResponseCode=[" + ex.getResponseCode() + "], "
                + "ErrorMessage=[" + ex.getErrorMessage() + "]";
            Assert.fail(testErrorMessage);
        }
    }
}
