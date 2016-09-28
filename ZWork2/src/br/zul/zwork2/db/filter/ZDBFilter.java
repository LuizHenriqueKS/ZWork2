package br.zul.zwork2.db.filter;

import br.zul.zwork2.db.selection.ZDBSelection;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZDBFilter {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String[] name;
    private ZDBSelection selection;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBFilter(String... name){
        this.name = name;
    }
    
    public ZDBFilter(ZDBSelection selection){
        this.selection = selection;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public String getName(){
        //VERIFICA SE POSSUI UMA SELEÇÃO
        if (selection!=null){
            return selection.toString();
        }
        //MONTA O NOME
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
