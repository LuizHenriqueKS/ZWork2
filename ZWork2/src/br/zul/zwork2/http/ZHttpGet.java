package br.zul.zwork2.http;

import br.zul.zwork2.log.ZLogger;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttpGet extends ZHttpRequest {

    @Override
    public ZHttpResponse send() {
        ZLogger logger = new ZLogger(getClass(), "send()");
        try {
            String _url = getUrl();
            if (getContentLength()>0){
                if (!_url.contains("?")){
                    _url+="?";
                }
                _url+=getContent();
            }
            HttpURLConnection connection = prepareConnection(_url);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            return getResponse(connection);
        } catch (ProtocolException ex) {
            throw logger.error.prepareException(ex);
        }
    }

    public int getContentLength() {
        return getContent().length();
    }
    
    public String getContent() {
        ZLogger logger = new ZLogger(getClass(), "getContent()");
        StringBuilder getData = new StringBuilder();
        for (Map.Entry<String, String> param : parameterMap().entrySet()) {
            if (getData.length() != 0) {
                getData.append('&');
            }
            try {
                getData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw logger.error.prepareException(ex, "Não foi possível conveter '%s'!", param.getKey());
            }
            getData.append('=');
            try {
                getData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw logger.error.prepareException(ex, "Não foi possível conveter '%s'!", param.getValue());
            }
        }
        return getData.toString();
    }

}
