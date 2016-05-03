package br.zul.zwork2.string;

/**
 *
 * @author skynet
 */
public class ZStringSearchResult {
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private final ZStringSearch zStringSearch;
    private final String pattern;
    private final int index;
    
    ////////////////////////////////////////////////////////////////////////////
    //CONSTRUTORES PÚBLICOS
    ////////////////////////////////////////////////////////////////////////////
    public ZStringSearchResult(ZStringSearch zStringSearch,String pattern,int index){
        this.zStringSearch = zStringSearch;
        this.pattern = pattern;
        this.index = index;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public ZStringSearch getzStringSearch() {
        return zStringSearch;
    }

    public String getPattern() {
        return pattern;
    }

    public int getIndex() {
        return index;
    }
    
}
