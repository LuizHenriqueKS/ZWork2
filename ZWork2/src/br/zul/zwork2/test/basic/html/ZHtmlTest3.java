package br.zul.zwork2.test.basic.html;

import br.zul.zwork2.html.ZHtml;
import br.zul.zwork2.iterator.ZHtmlIterator;
import br.zul.zwork2.test.ZSimpleTest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZHtmlTest3 extends ZSimpleTest<List<String>> {

   public String getSource(){
       StringBuilder html = new StringBuilder();
       html.append("<a><b><c></c></b><d></d></a>");
       return html.toString();
   }

    @Override
    public String convertResultToString(List<String> result) {
        StringBuilder sb = new StringBuilder();
        for (String str:result){
            sb.append(str);
            sb.append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public List<String> getResult() {
        ZHtml html = new ZHtml(getSource());
        ZHtmlIterator iterator = new ZHtmlIterator(html);
        List<String> result = new ArrayList<>();
        while (iterator.next()){
            if (iterator.getValue().isTag()){
                result.add(iterator.getValue().asTag().getTagName());
            }
        }
        return result;
    }

    @Override
    public List<String> getExpectedResult() {
        List<String> result = new ArrayList<>();
        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        return result;
    }

    @Override
    public String getTestName() {
        return "iterator html";
    }
    
    
}
