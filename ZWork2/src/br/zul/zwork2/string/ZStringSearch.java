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
    private String string;
    private boolean caseSensitive;
    private String patterns[];
    private String patternsToAvoid[];
    private List<Integer> indexes;
    
    ////////////////////////////////////////////////////////////////////////////
    //CONSTRUTORES PARA STRINGS
    ////////////////////////////////////////////////////////////////////////////
    private ZStringSearch(String string,boolean caseSensitive,String patterns[],String patternsToAvoid[]){
        this.string = string;
        this.caseSensitive = caseSensitive;
        this.patterns = patterns;
        this.patternsToAvoid = patternsToAvoid;
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern,String patternToAvoid){
        this(string,caseSensitive,new String[]{pattern},new String[]{patternToAvoid});
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern){
        this(string,caseSensitive,new String[]{pattern},new String[]{});
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private void search(){
        List<Integer> possible = new ArrayList<>();
        do {
            for (String pattern:patterns){
                int index;
                int offset = 0;
                do {
                    index = pattern.indexOf(pattern,offset);
                    valid()
                } while ();
            }
        } while (!possible.isEmpty());
        
    }
    
    private void search(String source,String pattern){
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    
    
}
