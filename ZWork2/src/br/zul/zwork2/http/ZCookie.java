package br.zul.zwork2.http;

import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.string.ZString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZCookie {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String name;
    private String value;
    private Date expires;
    private String path;
    private String domain;
    boolean secure;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZCookie(){}
    
    public ZCookie(String code){
       init(code);
    }
    
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    public void init(String code){
        ZLogger logger = new ZLogger(getClass(),"init(String code)"); 
        ZString zs = new ZString(code,false);
        if (zs.startsWith("Set-Cookie:")){
            zs = zs.fromLeft("Set-Cookie:").trim();
        }
        ZString[] part = zs.split(";");
        ZString[] nameAndValue = part[0].split("=");
        this.name = (nameAndValue[0].toString());
        this.value = (nameAndValue[1].toString());
        this.secure = false;
        for (int i=1;i<nameAndValue.length;i++){
            nameAndValue = part[i].split("=");
            if (nameAndValue[0].equals("Expires")){
                try {
                    expires = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").parse(nameAndValue[1].toString());
                } catch (ParseException ex) {
                    throw logger.error.prepareException(ex);
                }
            } else if (nameAndValue[0].equals("Path")){
                path = nameAndValue[1].toString();
            } else if (nameAndValue[0].equals("Domain")){
                domain = nameAndValue[1].toString();
            } else if (nameAndValue[0].equals("secure")){
                secure = true;
            }
        }
    }
    
    //==========================================================================
    //GETTERS AND SETTERS
    //==========================================================================
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpires() {
        return expires;
    }
    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isSecure() {
        return secure;
    }
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    
}
