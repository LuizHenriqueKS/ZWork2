package br.zul.zwork2.db;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZDBResult {
    
    //==========================================================================
    //METODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract boolean first();
    public abstract boolean next();
    public abstract List<Object> asList();
    public abstract List<String> listNames();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Object asObject(){
        List<Object> list = asList();
        if (list==null||list.isEmpty()){
            return null;
        } else {
            return list.get(0);
        }
    }
    
    public Long asLong(){
        Object result = asObject();
        if (result==null){
            return null;
        } else {
            return Long.valueOf(String.valueOf(result));
        }
    }
    
    public Object[] asArray(){
        List<Object> list = asList();
        Object[] result = list.toArray(new Object[list.size()]);
        return result;
    }
    
    public List<Object[]> listAll(){
        List<Object[]> result = new ArrayList<>();
        if (first()){
            result.add(asArray());
            while (next()){
                result.add(asArray());
            }
        }
        return result;
    }
    
}
