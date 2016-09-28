package br.zul.zwork2.iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luiz.silva
 * @param <Key>
 * @param <Value>
 */
public abstract class ZIterator<Key,Value> implements Cloneable{
    //==========================================================================
    //ENUMS PÚBLICOS
    //==========================================================================
    public enum IteratorState{
        BEFORE_FIRST,AFTER_LAST,FIRST,LAST,MIDDLE,DONT_FOUND;
    }
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZIterator(){
        init();
    }
    
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    private void init(){
        beforeFirst();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract Key getKey();
    public abstract void setKey(Key key);
    
    public abstract boolean isKeyValid();
    
    public abstract Value getValue();
    
    public abstract boolean next();
    public abstract boolean before();
    
    public abstract void beforeFirst();
    public abstract void afterLast();
    
    public abstract boolean hasNext();
    
    public abstract boolean hasBefore();
    
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBSCRITOS
    //==========================================================================
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public List<Value> listValues(){
        return new ArrayList<>(map().values());
    }
    
    public List<Key> listKeys(){
        return new ArrayList<>(map().keySet());
    }
    
    public Map<Key,Value> map(){
        Map<Key,Value> result = new HashMap<>();
        beforeFirst();
        while (next()){
            result.put(getKey(),getValue());
        }
        return result;
    }
    
    
    public boolean first(){
        beforeFirst();
        return next();
    }
    
    public boolean last(){
        afterLast();
        return before();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public abstract IteratorState getState();
    
}
