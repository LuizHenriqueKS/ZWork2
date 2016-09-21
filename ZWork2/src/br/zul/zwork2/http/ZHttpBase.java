package br.zul.zwork2.http;

import br.zul.zwork2.log.ZLogger;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttpBase {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private ZCookieManager cookieManager;
    private String url;
    private final Map<String,String> parameterMap;
    private final Map<String,String> requestPropertyMap;
    private ZProxy proxy;
    private boolean instanceFollowRedirects = false;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHttpBase(){
        this.parameterMap = new HashMap<>();
        this.requestPropertyMap = new HashMap<>();
    }
   
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public HttpURLConnection prepareConnection(String url){
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(),"prepareConnection()");
        try{
            URL _url = new URL( url );
            HttpURLConnection conn;
            if (proxy==null){
                conn = (HttpURLConnection) _url.openConnection();
            } else {
                conn = (HttpURLConnection) _url.openConnection(proxy.getProxy());
            }
            conn.setInstanceFollowRedirects(instanceFollowRedirects);
            conn.setUseCaches( false );
            if (cookieManager!=null&&!cookieManager.hasCookies()){
                conn.setRequestProperty("Cookie", cookieManager.getCookiesText());
            }
            return conn;
        }   catch (MalformedURLException | ProtocolException ex) {
            throw logger.error.prepareException(ex,"Erro em conectar com '%s'!",url);
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    protected ZHttpResponse getResponse(HttpURLConnection connection){
        ZLogger logger = new ZLogger(getClass(),"getResponse(HttpURLConnection connection)");
        try {
            ZHttpResponse response = new ZHttpResponse();
            response.setResponseCode(connection.getResponseCode());
            response.setResponseMessage(connection.getResponseMessage());
            response.setResponsePropertyMap(connection.getHeaderFields());
            response.setInputStream(connection.getInputStream());
            response.setUrl(connection.getURL().toString());
            if (cookieManager!=null){
                cookieManager.putCookies(response.listCookies());
            }
            return response;
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }
    
    protected void prepareRequest(ZHttpRequest request){
        request.setCookieManager(getCookieManager());
        request.setProxy(getProxy());
        request.setInstanceFollowRedirects(isInstanceFollowRedirects());
        for (Map.Entry<String,String> requestProperty:requestPropertyMap().entrySet()){
            request.putParameter(requestProperty.getKey(), requestProperty.getValue());
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA PARAMETROS
    //==========================================================================
    public void putParameter(String name,String value){
        this.parameterMap.put(name, value);
    }
    
    public void removeParameter(String name){
        this.parameterMap.remove(name);
    }
    
    public String getParameter(String name){
        return this.parameterMap.get(name);
    }
    
    public Map<String,String> parameterMap(){
        return new HashMap<>(this.parameterMap);
    }
    
    public void clearParameters(){
        this.parameterMap.clear();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA REQUEST PROPERTY
    //==========================================================================
    public void setRequestProperty(String name,String value){
        this.requestPropertyMap.put(name, value);
    }
    
    public String getRequestProperty(String name){
        return this.requestPropertyMap.get(name);
    }
    
    public Map<String,String> requestPropertyMap(){
        return new HashMap<>(this.requestPropertyMap);
    }
    
    public void clearRequestProperties(){
        this.requestPropertyMap.clear();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZCookieManager getCookieManager() {
        return cookieManager;
    }
    public void setCookieManager(ZCookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public ZProxy getProxy() {
        return proxy;
    }
    public void setProxy(ZProxy proxy) {
        this.proxy = proxy;
    }

    public boolean isInstanceFollowRedirects() {
        return instanceFollowRedirects;
    }
    public void setInstanceFollowRedirects(boolean instanceFollowRedirects) {
        this.instanceFollowRedirects = instanceFollowRedirects;
    }
    
}
