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
public class ZStringSearchTest2 extends ZSimpleTest<List<Integer>> {

    private final static String SOURCE = "ab";
    
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
        String[] patterns = {"a","ab"};
        String[] patternsToVoid = {};
        ZStringSearch search = new ZStringSearch(SOURCE, true,patterns,patternsToVoid, ZStringSearchType.LEFT_UNIQUE);
        List<Integer> result = new ArrayList<>();
        for (ZStringSearchResult r:search.listResults()){
            result.add(r.getIndex());
        }
        return result;
    }

    @Override
    public List<Integer> getExpectedResult() {
        return Arrays.asList(0);
    }

    @Override
    public String getTestName() {
        return "Pesquisa básica de texto";
    }
    
}
