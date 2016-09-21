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
    public ZHttpGet requestGet(String url){
        ZHttpGet request = new ZHttpGet();
        request.setUrl(url);
        prepareRequest(request);
        return request;
    }
    
    public ZHttpGet requestGet(){
        return requestGet(getUrl());
    }

    public ZHttpPost requestPost(String url){
        ZHttpPost request = new ZHttpPost();
        request.setUrl(url);
        prepareRequest(request);
        return request;
    }
    
    public ZHttpPost requestPost(){
        return requestPost(getUrl());
    }
    
}
