package br.zul.zwork2.string.test;

import br.zul.zwork2.string.ZStringSearch;
import br.zul.zwork2.string.ZStringSearch.ZStringSearchType;
import br.zul.zwork2.string.ZStringSearchResult;
import java.util.List;

/**
 *
 * @author skynet
 */
public class ZStringSearchTest {
    
    public static void testCountResults(){
        System.out.print("Testando ZStringSearch.countResults()...");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..",ZStringSearchType.CUMULATIVE);
        int result = instance.countResults();
        
        if (result==2){
            System.out.println("OK!");
        } else {
            System.out.println("FALHOU!");
            System.out.println("Resultado esperado 2 e foi obtido: "+result);
        }
        
    }
    
    public static void testListResults(){
        System.out.print("Testando ZStringSearch.listResults()...");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..",ZStringSearchType.CUMULATIVE);
        List<ZStringSearchResult> result = instance.listResults();
        
        if (result.get(0).getIndex()==1&&result.get(1).getIndex()==6){
            System.out.println("OK!");
        } else {
            System.out.println("FALHOU!");
            System.out.println("Resultado recebido: ");
            for (ZStringSearchResult r:result){
                System.out.print(r.getIndex()+",");
            }
        }
    }
    public static void testListResultsRight(){
        System.out.print("Testando ZStringSearch.testListResultsRight()...");
        testListResultsLeft(0, "1.3.,6.89", new String[]{".",".,"}, null, 1,3,6);
    }
    
    public static boolean testListResultsRight(int index,String source,String[] patterns,String[] patternsToVoid,int... indexes){
        boolean result = true;
        ZStringSearch instance = new ZStringSearch(source, true, patterns,patternsToVoid,ZStringSearchType.LEFT);
        List<ZStringSearchResult> response = instance.listResults();
        
        if (response.size()!=indexes.length){
            result = false;
        } else {
            for (int i=0;i<response.size();i++){
                if (response.get(i).getIndex()!=indexes[i]){
                    printIndexes(response);
                    printIndexes(indexes);
                }
            }
        }
        
        System.out.printf("[%d]%s",index,result?"OK":"FALHOU!");
        return result;
    }
      
    public static void testListResultsLeft(){
        System.out.print("Testando ZStringSearch.testListResultsLeft()...");
        testListResultsLeft(0, "1.3.,6.89", new String[]{".",".,"}, null, 1,3,6);
    }
    
    public static boolean testListResultsLeft(int index,String source,String[] patterns,String[] patternsToVoid,int... indexes){
        boolean result = true;
        ZStringSearch instance = new ZStringSearch(source, true, patterns,patternsToVoid,ZStringSearchType.LEFT);
        List<ZStringSearchResult> response = instance.listResults();
        
        if (response.size()!=indexes.length){
            result = false;
        } else {
            for (int i=0;i<response.size();i++){
                if (response.get(i).getIndex()!=indexes[i]){
                    printIndexes(response);
                    printIndexes(indexes);
                }
            }
        }
        
        System.out.printf("[%d]%s",index,result?"OK":"FALHOU!");
        return result;
    }
    
    public static void printIndexes(List<ZStringSearchResult> list){
        StringBuilder sb = new StringBuilder();
        for (ZStringSearchResult r:list){
            if (sb.length()>0){
                sb.append(",");
            }
            sb.append(r.getIndex());
        }
        System.out.println(sb.toString());
    }
     
    public static void printIndexes(int... indexes){
        StringBuilder sb = new StringBuilder();
        for (int i:indexes){
            if (sb.length()>0){
                sb.append(",");
            }
            sb.append(i);
        }
        System.out.println(sb.toString());
    }
    
}
