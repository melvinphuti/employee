/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author User1
 */
public class EndPointFactory {
    
    public static Invocation.Builder getServiceEndpoint(String urlTarget, String path, String apiAuthenticationToken){        
        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(urlTarget).path(path);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
                .header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, "Token " + apiAuthenticationToken);
        
        return invocationBuilder;
    }
}
