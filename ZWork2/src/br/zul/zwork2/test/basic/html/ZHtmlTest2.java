package br.zul.zwork2.test.basic.html;

import br.zul.zwork2.html.ZHtml;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author Luiz Henrique
 */
public class ZHtmlTest2 extends ZSimpleTest<String> {

   public String getSource(){
       StringBuilder html = new StringBuilder();
       html.append("<a><b><c></c></b><d></d></a>");
       return html.toString();
   }

    @Override
    public String convertResultToString(String result) {
        return result.replace("\r\n","").replace(" ","");
    }

    @Override
    public String getResult() {
        ZHtml html = new ZHtml(getSource());
        return html.toString();
    }

    @Override
    public String getExpectedResult() {
        return "<a><b><c/></b><d/></a>";
    }

    @Override
    public String getTestName() {
        return "montando um html mais complexo";
    }
    
    
}
