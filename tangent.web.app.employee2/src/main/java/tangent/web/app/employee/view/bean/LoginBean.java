/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.web.app.employee.view.bean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import tangent.client.employee.EmployeeServiceClient;
import tangent.model.employee.exception.EmployeeServiceException;
import tangent.tangent.web.libraries.FacesMessageUtil;

/**
 *
 * @author User1
 */
@ManagedBean(name="webAppEmployeeLogin")
@SessionScoped
public class LoginBean implements Serializable {

    private String userName;
    private String password;
    private String authenticationToken;
    private boolean isAuthenticated;
    
    private final String URL_TARGET = "http://staging.tangent.tngnt.co/";

    public String authenticate(){
        authenticationToken = null;
        isAuthenticated = false;
        String nextPage = "login.xhtml";
        
        try {
            EmployeeServiceClient employeeServiceClient = new EmployeeServiceClient(URL_TARGET);
            authenticationToken = employeeServiceClient.requestAuthenticationToken(userName, password);
            isAuthenticated = true;
            nextPage = "index.xhtml";
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Authentication failed. Please enter the correct username and password.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nextPage;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public boolean isIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    
    
}
