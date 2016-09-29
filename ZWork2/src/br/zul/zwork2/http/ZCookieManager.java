package br.zul.zwork2.http;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZCookieManager {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final List<ZCookie> cookieList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZCookieManager(){
        this.cookieList = new ArrayList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA COOKIES
    //==========================================================================
    public void putCookie(ZCookie cookie){
        int index = indexOf(cookie.getName()); 
        if (index>-1){
            cookieList.remove(index);
        }
        cookieList.add(cookie);
    }
    
    public int indexOf(String cookieName){
        for (int i=0;i<cookieList.size();i++){
            try{
                if (cookieName.equals(cookieList.get(i).getName())){
                    return i;
                }
            }catch(Exception e){
            }
        }
        return -1;
    }
    
    public ZCookie getCookie(String cookieName){
        int index = indexOf(cookieName);
        if (index==-1){
            return null;
        } else {
            return cookieList.get(index);
        }
    }
    
    public void removeCookie(int index){
        cookieList.remove(index);
    }
    
    public void removeCookie(ZCookie cookie){
        cookieList.remove(cookie);
    }
    
    public ZCookie getCookie(int index){
        return cookieList.get(index);
    }       
    
    public List<ZCookie> listCookies(){
        return cookieList;
    }
    
    public String getCookiesText(){
        StringBuilder result = new StringBuilder();
        for (ZCookie cookie:cookieList){
            if (result.length()>0){
                result.append("; ");
            }
            result.append(cookie.getName());
            result.append("=");
            result.append(cookie.getValue());
        }
        return result.toString();
    }
    
    public boolean hasCookies(){
        return !cookieList.isEmpty();
    }
    
    public void putCookies(List<ZCookie> cookieList){
       for (ZCookie cookie:cookieList){
           putCookie(cookie);
       }
    }
    
}
