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
        ZHttpGet request = new ZHttpGet();
        request.setCookieManager(getCookieManager());
        request.setProxy(getProxy());
        request.setUrl(getUrl());
        for (Entry<String,String> requestProperty:requestPropertyMap().entrySet()){
            request.putParameter(requestProperty.getKey(), requestProperty.getValue());
        }
        return request;
    }

    public ZHttpPost requestPost(){
        ZHttpPost request = new ZHttpPost();
        request.setCookieManager(getCookieManager());
        request.setProxy(getProxy());
        request.setUrl(getUrl());
        for (Entry<String,String> requestProperty:requestPropertyMap().entrySet()){
            request.putParameter(requestProperty.getKey(), requestProperty.getValue());
        }
        return request;
    }
    
}
