package br.zul.zwork2.test.basic.xml;

import br.zul.zwork2.test.ZSimpleTest;
import br.zul.zwork2.xml.ZXml;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlTest1 extends ZSimpleTest<String> {

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
        ZXml xml = new ZXml(getSource());
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
