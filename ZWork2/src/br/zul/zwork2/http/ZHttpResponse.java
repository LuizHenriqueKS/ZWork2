package br.zul.zwork2.http;

import br.zul.zwork2.io.ZGzip;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.string.ZString;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttpResponse {

    //==========================================================================
    //CONSTANTES
    //==========================================================================
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_NOT_AUTHORITATIVE = 203;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_RESET = 205;
    public static final int HTTP_PARTIAL = 206;
    public static final int HTTP_MULT_CHOICE = 300;
    public static final int HTTP_MOVED_PERM = 301;
    public static final int HTTP_MOVED_TEMP = 302;
    public static final int HTTP_SEE_OTHER = 303;
    public static final int HTTP_NOT_MODIFIED = 304;
    public static final int HTTP_USE_PROXY = 305;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_PAYMENT_REQUIRED = 402;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_BAD_METHOD = 405;
    public static final int HTTP_NOT_ACCEPTABLE = 406;
    public static final int HTTP_PROXY_AUTH = 407;
    public static final int HTTP_CLIENT_TIMEOUT = 408;
    public static final int HTTP_CONFLICT = 409;
    public static final int HTTP_GONE = 410;
    public static final int HTTP_LENGTH_REQUIRED = 411;
    public static final int HTTP_PRECON_FAILED = 412;
    public static final int HTTP_ENTITY_TOO_LARGE = 413;
    public static final int HTTP_REQ_TOO_LONG = 414;
    public static final int HTTP_UNSUPPORTED_TYPE = 415;
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;
    public static final int HTTP_INTERNAL_ERROR = 500;
    public static final int HTTP_NOT_IMPLEMENTED = 501;
    public static final int HTTP_BAD_GATEWAY = 502;
    public static final int HTTP_UNAVAILABLE = 503;
    public static final int HTTP_GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION = 505;

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private InputStream inputStream;
    private Map<String, List<String>> responsePropertyMap;
    private Integer responseCode;
    private String responseMessage;
    private String url;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHttpResponse() {
        responsePropertyMap = new HashMap<>();
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getResponseText() {
        ZLogger logger = new ZLogger(getClass(), "getResponseText()");
        try {

            if (responsePropertyMap().containsKey("Content-Encoding") && responsePropertyMap().get("Content-Encoding").get(0).equalsIgnoreCase("gzip")) {
                InputStream bodyStream = new GZIPInputStream(getInputStream());
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int length;
                while ((length = bodyStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
                return new String(outStream.toByteArray(), StandardCharsets.UTF_8);
            } else {
                StringBuilder response = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                    response.append("\r\n");
                }
                return response.toString();
            }
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }

    public ZString getResponseZText(boolean caseSensitive) {
        return new ZString(getResponseText(), caseSensitive);
    }
    
    public String getLocationProperty(){
        if (!responsePropertyMap.containsKey("Location")){
            return null;
        }
        return responsePropertyMap.get("Location").get(0);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS PARA COOKIES
    //==========================================================================
    public List<ZCookie> listCookies() {
        return null;
    }

    public Map<String, List<String>> responsePropertyMap() {
        return responsePropertyMap;
    }

    //==========================================================================
    //GETTERS MODIFICADOS
    //==========================================================================
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setResponsePropertyMap(Map<String, List<String>> responsePropertyMap) {
        this.responsePropertyMap = responsePropertyMap;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
