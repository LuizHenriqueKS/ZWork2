package br.zul.zwork2.http;

import java.util.Map.Entry;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttp extends ZHttpBase {
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZHttpGet requestGet(){
        return requestGet(getUrl());
    }
    
    public ZHttpGet requestGet(String url){
        ZHttpGet request = new ZHttpGet();
        request.setCookieManager(getCookieManager());
        request.setProxy(getProxy());
        request.setUrl(url);
        for (Entry<String,String> requestProperty:requestPropertyMap().entrySet()){
            request.putParameter(requestProperty.getKey(), requestProperty.getValue());
        }
        return request;
    }

    public ZHttpPost requestPost(){
       return requestPost(getUrl());
    }
    
    public ZHttpPost requestPost(String url){
        ZHttpPost request = new ZHttpPost();
        request.setCookieManager(getCookieManager());
        request.setProxy(getProxy());
        request.setUrl(url);
        for (Entry<String,String> requestProperty:requestPropertyMap().entrySet()){
            request.putParameter(requestProperty.getKey(), requestProperty.getValue());
        }
        return request;
    }
    
}
