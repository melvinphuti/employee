/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.client.employee;

import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author User1
 */
public class EndPointFactory {
    
    public static Invocation getServiceEndpoint(String urlTarget, String path, Map<String, String> queryParams){        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(urlTarget).path(path);
        if(queryParams != null && !queryParams.isEmpty() ){
            for(Map.Entry<String, String> entry :queryParams.entrySet()){
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue() );
            }
        } 
        Invocation.Builder invocationBuilder = webTarget.request().header(HttpHeaders.ACCEPT_ENCODING, MediaType.APPLICATION_JSON_TYPE);
        Invocation invocation = invocationBuilder.buildPost(Entity.entity(new String(), MediaType.APPLICATION_JSON_TYPE));
        return invocation;
    }
    public static Invocation.Builder getServiceEndpoint(String urlTarget, String path, String apiAuthenticationToken){        
        return getServiceEndpoint(urlTarget, path, apiAuthenticationToken, null);
    }
    public static Invocation.Builder getServiceEndpoint(String urlTarget, String path, String apiAuthenticationToken,
            Map<String, String> queryParams){        
        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(urlTarget).path(path);
        
        if(queryParams != null && !queryParams.isEmpty() ){
            for(Map.Entry<String, String> entry :queryParams.entrySet()){
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue() );
            }
        }        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
                
        if(apiAuthenticationToken != null && !apiAuthenticationToken.isEmpty() ){
            invocationBuilder = invocationBuilder.header(javax.ws.rs.core.HttpHeaders.AUTHORIZATION, "Token " + apiAuthenticationToken);
        }
        
        return invocationBuilder;
    }
}
