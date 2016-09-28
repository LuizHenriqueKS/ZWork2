package br.zul.zwork2.iterator;

import br.zul.zwork2.iterator.ZIterator.IteratorState;
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
public class ZMapIterator<Key, Value> extends ZIterator<Key, Value> {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    protected IteratorState state;
    private final List<Key> keyList;
    private final Map<Key, Value> map;
    private Key key;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZMapIterator(Map<Key, Value> map) {
        this.map = map;
        this.keyList = new ArrayList<>(map.keySet());
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public boolean isKeyValid() {
        if (key==null) {
            return false;
        }
        return keyList.contains(key);
    }

    @Override
    public Value getValue() {
        if (!isKeyValid()){
            return null;
        }
        return map.get(key);
    }

    @Override
    public boolean next() {
        return nextKey();
    }

    @Override
    public boolean before(){
        return beforeKey();
    }

    @Override
    public void beforeFirst(){
        state = IteratorState.BEFORE_FIRST;
        key = null;
    }

    @Override
    public void afterLast(){
        state = IteratorState.AFTER_LAST;
        key = null;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private boolean nextKey() {
        Integer index = getKeyIndex();
        if (index == null){
            state = IteratorState.DONT_FOUND;
            return false;
        }
        index++;
        if (index<0||index>=keyList.size()){
            key = null;
            state = index<0?IteratorState.BEFORE_FIRST:IteratorState.AFTER_LAST;
            return false;
        } else {
            key = keyList.get(index);
            if (index==0){
                state = IteratorState.FIRST;
            } else if (index==keyList.size()-1){
                state = IteratorState.LAST;
            } else {
                state = IteratorState.MIDDLE;
            }
            return true;
        }
    }

    private boolean beforeKey() {
        Integer index = getKeyIndex();
        if (index == null){
            return false;
        }
        index--;
        if (index<0||index>=keyList.size()){
            key = null;
            return false;
        } else {
            key = keyList.get(index);
            return true;
        }
    }
    
    public Integer getKeyIndex(){
        Integer index = null;
        switch (state) {
            case BEFORE_FIRST:
                index = -1;
                break;
            case FIRST:
                index = 1;
                break;
            case AFTER_LAST:
                index = keyList.size();
                break;
            case DONT_FOUND:
                index = null;
                break;
            case LAST:
                index = keyList.size()-1;
                break;
            case MIDDLE:
                index = keyList.indexOf(key);
                break;
        }
        return index;
    }
    
    @Override
    public boolean hasNext() {
        return getKeyIndex()<keyList.size();
    }

    @Override
    public boolean hasBefore() {
        return getKeyIndex()>0;
    }
    
    @Override
    public IteratorState getState() {
        return state;
    }
}
