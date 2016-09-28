package br.zul.zwork2.filter;

import br.zul.zwork2.iterator.ZCollectionIterator;
import br.zul.zwork2.iterator.ZIterator;
import br.zul.zwork2.iterator.ZIterator.IteratorState;
import br.zul.zwork2.iterator.ZListIterator;
import br.zul.zwork2.iterator.ZMapIterator;
import br.zul.zwork2.log.ZLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luiz.silva
 * @param <Key>
 * @param <Value>
 */
public abstract class ZFilter<Key,Value> {
    
    //==========================================================================
    //ENUMS PÚBLICOS
    //==========================================================================
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Key key;
    private Value value;
    protected ZIterator<Key,Value> iterator;
    private IteratorState state;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZFilter(ZIterator<Key,Value> iterator){
        this.iterator = iterator;
        init();
    }
    
    protected ZFilter(List<Value> list){
        this((ZIterator<Key, Value>) new ZListIterator<>(list));
    }
    
    protected ZFilter(Collection<Value> values){
        this((ZIterator<Key, Value>) new ZCollectionIterator<>(values));
    }
    
    protected ZFilter(Value... values){
        this(Arrays.asList(values));
    }
    
    protected ZFilter(Map<Key,Value> map){
        this(new ZMapIterator<>(map));
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        beforeFirst();
    }
    
    //==========================================================================
    //MÉTOOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract boolean filter(Key key,Value value);
    
    //==========================================================================
    //MÉTOOS PÚBLICOS
    //==========================================================================
    public boolean first(){
        beforeFirst();
        return next();
    }
    
    public boolean next(){
        clear();
        do {
            if (iterator.next()){
                if (filter(iterator.getKey(),iterator.getValue())){
                    key = iterator.getKey();
                    value = iterator.getValue();
                }
            }
        } while (iterator.hasNext());
        state = IteratorState.DONT_FOUND;
        return false;
    }
    
    public boolean before(){
        clear();
        do {
            if (iterator.before()){
                if (filter(iterator.getKey(),iterator.getValue())){
                    key = iterator.getKey();
                    value = iterator.getValue();
                }
            }
        } while (iterator.hasNext());
        state = IteratorState.DONT_FOUND;
        return false;
    }
    
    public void beforeFirst(){
        clear();
        state = IteratorState.BEFORE_FIRST;
        iterator.beforeFirst();
    }
    
    public void afterLast(){
        clear();
        state = IteratorState.AFTER_LAST;
        iterator.afterLast();
    }
    
    public boolean last(){
        afterLast();
        return before();
    }
    
    public List<Value> listFilteredValues(){
        return new ArrayList<>(filteredMap().values());
    }
    
    public Map<Key,Value> filteredMap(){
        ZLogger logger = new ZLogger(getClass(),"filteredMap()");
        try {
            ZIterator<Key,Value> copy = (ZIterator<Key,Value>) iterator.clone();
            Map<Key,Value> result = new HashMap<>();
            while (copy.next()){
                if (filter(copy.getKey(), copy.getValue())){
                    result.put(copy.getKey(),copy.getValue());
                }
            }
            return result;
        } catch (CloneNotSupportedException ex) {
            throw logger.error.prepareException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS  
    //==========================================================================
    private void clear(){
        this.key = null;
        this.value = null;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    private void setKey(Key key){
        iterator.setKey(key);
        if (filter(key, iterator.getValue())){
            this.value = iterator.getValue();
            this.key = key;
            this.state = iterator.getState();
        } else {
            iterator.setKey(key);
        }
    }

    public Value getValue() {
        if (key==null){
            return null;
        }
        return value;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Key getKey() {
        return key;
    }

    public IteratorState getState() {
        return iterator.getState();
    }
    
}
