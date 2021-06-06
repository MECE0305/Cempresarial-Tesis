/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cempresarial.rest.generic;

/**
 *
 * @author DIGETBI 05
 */
import java.net.MalformedURLException;
import java.util.logging.Logger;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
/**
 * @author raidentrance
 *
 */
public class AbstractClient {
    private String url;
    private String contextPath;
 
    private static final Logger log = Logger.getLogger(AbstractClient.class.getName());
 
    public AbstractClient(String url, String contextPath) {
        this.url = url;
        this.contextPath = contextPath;
    }
 
    protected WebTarget createClient(String path) {
        String assembledPath = assembleEndpoint(path);
        Client client = ClientBuilder.newClient();        
        WebTarget target= null;
        try {
            target = client.target(URI.create(new URL(assembledPath).toExternalForm()));
        } catch (MalformedURLException ex) {            
            Logger.getLogger(AbstractClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return target;
    }
 
    private String assembleEndpoint(String path) {
        String endpoint = url.concat(contextPath).concat(path);
        log.info(String.format("Calling endpoint %s", endpoint));
        return endpoint;
    }    
}