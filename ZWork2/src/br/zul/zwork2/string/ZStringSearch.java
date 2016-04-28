package br.zul.zwork2.string;

import java.util.ArrayList;
import java.util.List;

/**
 *  Classe usada para fazer pesquisas em Strings
 * 
 * @author skynet
 */
public class ZStringSearch {
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private String source;
    private boolean caseSensitive;
    private String patterns[];
    private String patternsToAvoid[];
    private List<ZStringSearchResult> results;
    private int resultIndex;
    
    ////////////////////////////////////////////////////////////////////////////
    //CONSTRUTORES PARA STRINGS
    ////////////////////////////////////////////////////////////////////////////
    private ZStringSearch(String source,boolean caseSensitive,String patterns[],String patternsToAvoid[]){
        this.source = source;
        this.caseSensitive = caseSensitive;
        this.patterns = patterns;
        this.patternsToAvoid = patternsToAvoid;
        search();
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern,String patternToAvoid){
        this(string,caseSensitive,new String[]{pattern},new String[]{patternToAvoid});
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern){
        this(string,caseSensitive,new String[]{pattern},new String[]{});
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PÚBLICOS
    ////////////////////////////////////////////////////////////////////////////
    public List<String> listPatternsToVoid(String pattern,String patternsToVoid[]){
        List<String> result = new ArrayList<>();
        for (String toVoid:patternsToVoid){
            
            if (caseSensitive){
                if (toVoid.contains(pattern)){
                    result.add(toVoid);
                }
            } else {
                if (toVoid.toLowerCase().contains(pattern.toLowerCase())){
                    result.add(toVoid);
                }
            }
            
        }
        return result;
    }
    
    public List<ZStringSearchResult> listResults(){
        return results;
    }
    
    public ZStringSearchResult getFirstResult(){
        return results.get(0);
    }
    
    public ZStringSearchResult getLastResult(){
        return results.get(results.size()-1);
    }
    
    public int countResults(){
        return results.size();
    }
    
    public boolean first(){
        resultIndex = 0;
        return resultIndex>-1;
    }
    
    public boolean last(){
        resultIndex = results.size()-1;
        return resultIndex>-1;
    }
    
    public boolean next(){
        if (resultIndex<results.size()-1){
            resultIndex++;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean prev(){
        if (resultIndex>0){
            resultIndex--;
            return true;
        } else {
            return false;
        }
    }
    
    public ZStringSearchResult getResult(){
        if (resultIndex<0){
            resultIndex = 0;
        }
        return results.get(resultIndex);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private void search(){
        List<ZStringSearchResult> possible = new ArrayList<>();
        int oldSize;
        do {
            int index;
            int offset = 0;
            oldSize = possible.size();
            for (String pattern:patterns){
                boolean add = true;
                if (caseSensitive){
                    index = source.indexOf(pattern,offset);
                } else {
                    index = source.toLowerCase().indexOf(pattern.toLowerCase(),offset);
                }

                for (String toVoid:listPatternsToVoid(pattern, patternsToAvoid)){
                    int difference;

                    if (caseSensitive){
                        difference = toVoid.indexOf(pattern);
                    } else {
                        difference = toVoid.toLowerCase().indexOf(pattern.toLowerCase());
                    }

                    if (caseSensitive){
                        add = !(source.startsWith(toVoid, offset+index-difference));
                    } else {
                        add = !(source.toLowerCase().startsWith(toVoid.toLowerCase(), offset+index-difference));
                    }
                    
                    if (!add){
                        break;
                    }

                }
                
                if (add){
                    ZStringSearchResult r = new ZStringSearchResult(this,pattern,index);
                    possible.add(r);
                }
                
                offset += index+1;
                
            }
        } while (oldSize!=possible.size());
        results = possible;
        resultIndex = -1;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    
    
}
