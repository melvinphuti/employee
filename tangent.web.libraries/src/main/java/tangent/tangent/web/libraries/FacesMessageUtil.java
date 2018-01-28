/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.tangent.web.libraries;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author User1
 */
public class FacesMessageUtil {
    public static void addMessage(FacesMessage.Severity severity, String message){
            FacesMessage facesMessage = new FacesMessage(severity, message, null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
