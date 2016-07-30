package br.zul.zwork2.test.basic.http;

import br.zul.zwork2.http.ZHttp;
import br.zul.zwork2.http.ZHttpGet;
import br.zul.zwork2.http.ZHttpResponse;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author Luiz Henrique
 */
public class ZHttpTest1 extends ZSimpleTest<Boolean> {

    @Override
    public String convertResultToString(Boolean result) {
        return result.toString();
    }

    @Override
    public Boolean getResult() {
        ZHttp http = new ZHttp();
        ZHttpGet request = http.requestGet();
        request.setUrl("http://www.google.com.br/");
        ZHttpResponse response = request.send();
        return !response.getResponseText().isEmpty();
    }

    @Override
    public Boolean getExpectedResult() {
        return true;
    }

    @Override
    public String getTestName() {
        return "GET";
    }
    
}
