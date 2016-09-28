package br.zul.zwork2.test.basic.html;

import br.zul.zwork2.test.ZSimpleTest;
import br.zul.zwork2.html.ZHtml;

/**
 *
 * @author Luiz Henrique
 */
public class ZHtmlTest1 extends ZSimpleTest<String> {

   public String getSource(){
       StringBuilder xml = new StringBuilder();
       xml.append("Luiz ");
       xml.append("<b>Henrique</b> <font name=\"Arial\">Kafer</font>");
       return xml.toString();
   }
    
    @Override
    public String convertResultToString(String result) {
        return result.replace("\r\n","").replace(" ","");
    }

    @Override
    public String getResult() {
        ZHtml xml = new ZHtml(getSource());
        return xml.toString();
    }

    @Override
    public String getExpectedResult() {
        return getSource();
    }

    @Override
    public String getTestName() {
        return "Montando um xml";
    }
    
}
