package br.zul.zwork2.string;

import java.util.Objects;

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
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean isBetween(ZStringSearchResult other){
        return other.getIndex()>=getIndex()&&getEndIndex()<=other.getEndIndex();
    }       
    
    public boolean collidesWith(ZStringSearchResult other){
        if (getIndex()<=other.getIndex()&&other.getIndex()<=getEndIndex()){
            return true;
        } else if (other.getIndex()<=getIndex()&&getIndex()<=other.getEndIndex()){
            return true;
        } else {
             return false;
        }
    }
    
    public int length(){
        return pattern.length();
    }
    
    public int getEndIndex(){
        return getIndex()+length()-1;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.zStringSearch);
        hash = 41 * hash + Objects.hashCode(this.pattern);
        hash = 41 * hash + this.index;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZStringSearchResult other = (ZStringSearchResult) obj;
        if (this.index != other.index) {
            return false;
        }
        if (!Objects.equals(this.pattern, other.pattern)) {
            return false;
        }
        return Objects.equals(this.zStringSearch, other.zStringSearch);
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
