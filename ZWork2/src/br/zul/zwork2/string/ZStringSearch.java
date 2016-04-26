package br.zul.zwork2.string;

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
    private String patterns[];
    private String patternsToAvoid[];
    
    ////////////////////////////////////////////////////////////////////////////
    //CONSTRUTORES PARA STRINGS
    ////////////////////////////////////////////////////////////////////////////
    private ZStringSearch(String string,String patterns[],String patternsToAvoid[]){
        this.string = string;
        this.patterns = patterns;
        this.patternsToAvoid = patternsToAvoid;
    }
    
    public ZStringSearch(String string,String pattern,String patternToAvoid){
        this(string,new String[]{pattern},new String[]{patternToAvoid});
    }
    
    public ZStringSearch(String string,String pattern){
        this(string,new String[]{pattern},new String[]{});
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    
    
}
