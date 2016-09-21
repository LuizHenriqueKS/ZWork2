package br.zul.zwork2.http;

import br.zul.zwork2.log.ZLogger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttpPost extends ZHttpRequest {

    @Override
    public ZHttpResponse send() {
        ZLogger logger = new ZLogger(getClass(), "send()");
        try {
            HttpURLConnection connection = prepareConnection(getUrl());
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(getContentLength()));
            connection.getOutputStream().write(getContent());
            ZHttpResponse response = getResponse(connection);
            if (isInstanceFollowRedirects()&&response.getLocationProperty()!=null){
               ZHttpGet httpGet = new ZHttpGet();
               httpGet.setUrl(response.getLocationProperty());
               prepareRequest(httpGet);
               response = httpGet.send();
            }
            return response;
        } catch (ProtocolException ex) {
            throw logger.error.prepareException(ex);
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }

    public int getContentLength() {
        return getContent().length;
    }

    public byte[] getContent() {
        ZLogger logger = new ZLogger(getClass(), "getContent()");
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : parameterMap().entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            try {
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw logger.error.prepareException(ex, "Não foi possível conveter '%s'!", param.getKey());
            }
            postData.append('=');
            try {
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw logger.error.prepareException(ex, "Não foi possível conveter '%s'!", param.getValue());
            }
        }
        return postData.toString().getBytes();
    }

}
