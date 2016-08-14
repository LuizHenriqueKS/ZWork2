package br.zul.zwork2.test.basic.string.search;

import br.zul.zwork2.string.ZStringSearch;
import br.zul.zwork2.string.ZStringSearchResult;
import br.zul.zwork2.string.ZStringSearchType;
import br.zul.zwork2.test.ZSimpleTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZStringSearchTest1 extends ZSimpleTest<List<Integer>> {

    private final static String SOURCE = "/https://www.google.com/pt/";
    
    @Override
    public String convertResultToString(List<Integer> result) {
        StringBuilder sb = new StringBuilder();
        for (Integer i:result){
            if (sb.length()!=0){
                sb.append(",");
            }
            sb.append(i);
        }
        return sb.toString();
    }

    @Override
    public List<Integer> getResult() {
        ZStringSearch search = new ZStringSearch(SOURCE, true, "/", ZStringSearchType.LEFT_UNIQUE);
        List<Integer> result = new ArrayList<>();
        for (ZStringSearchResult r:search.listResults()){
            result.add(r.getIndex());
        }
        return result;
    }

    @Override
    public List<Integer> getExpectedResult() {
        return Arrays.asList(0,7,8,23,26);
    }

    @Override
    public String getTestName() {
        return "Pesquisa básica de texto";
    }
    
}
