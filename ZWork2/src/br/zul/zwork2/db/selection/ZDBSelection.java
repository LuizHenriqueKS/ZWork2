package br.zul.zwork2.db.selection;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZDBSelection {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final String name[];
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBSelection(String... name){
        this.name = name;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    @Override
    public abstract String toString();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(toString());
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
        final ZDBSelection other = (ZDBSelection) obj;
        return Objects.equals(other.toString(), this.toString());
    }
   
    
    //==========================================================================
    //MÉTODOS PARA NOME
    //==========================================================================
    public String getName(){
        StringBuilder result = new StringBuilder();
        for (String n:name){
            if (result.length()>0){
                result.append(".");
            }
            result.append(n);
        }
        return result.toString();
    }
    
    public List<String> listNames(){
        return Arrays.asList(name);
    }
    
}
