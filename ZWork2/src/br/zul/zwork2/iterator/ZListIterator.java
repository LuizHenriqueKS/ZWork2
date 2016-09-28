package br.zul.zwork2.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiz.silva
 * @param <Value>
 */
public class ZListIterator<Value> extends ZIterator<Integer, Value> {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Integer key;
    private final List<Value> list;
    private IteratorState state;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZListIterator(List<Value> list) {
        this.list = list;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public Value getValue() {
        if (isKeyValid()){
            return list.get(getKey());
        } else {
            return null;
        }
    }

    @Override
    public boolean next() {
        do {
            key++;
        } while (key < 0);
        if (key >= list.size()) {
            state = IteratorState.AFTER_LAST;
            return false;
        }
        if (isKeyValid()) {
            if (key == 0) {
                state = IteratorState.FIRST;
            } else if (key == list.size() - 1) {
                state = IteratorState.LAST;
            } else {
                state = IteratorState.MIDDLE;
            }
            return true;
        } else {
            state = IteratorState.DONT_FOUND;
            return false;
        }
    }

    @Override
    public boolean before() {
        do {
            key--;
        } while (key >= list.size());
        if (key < 0) {
            state = IteratorState.BEFORE_FIRST;
            return false;
        }
        if (isKeyValid()) {
            if (key == 0) {
                state = IteratorState.FIRST;
            } else if (key == list.size() - 1) {
                state = IteratorState.LAST;
            } else {
                state = IteratorState.MIDDLE;
            }
            return true;
        } else {
            state = IteratorState.DONT_FOUND;
            return false;
        }
    }

    @Override
    public void beforeFirst() {
        key = -1;
        state = IteratorState.BEFORE_FIRST;
    }

    @Override
    public void afterLast() {
        key = list.size();
        state = IteratorState.AFTER_LAST;
    }

    @Override
    public boolean isKeyValid() {
        if (key==null){
            return false;
        }
        return key >= 0 && key < list.size();
    }

    @Override
    public IteratorState getState() {
        return state;
    }

    @Override
    public boolean hasNext() {
        return key<list.size();
    }

    @Override
    public boolean hasBefore() {
        return key>0;
    }

}
