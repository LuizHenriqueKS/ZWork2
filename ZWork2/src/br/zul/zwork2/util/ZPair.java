package br.zul.zwork2.util;

import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 * @param <A>
 * @param <B>
 */
public class ZPair<A,B> {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private A a;
    private B b;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPair(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public ZPair(){
        
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
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
        final ZPair<?, ?> other = (ZPair<?, ?>) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        return Objects.equals(this.b, other.b);
    }
    
    @Override    
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.a);
        hash = 83 * hash + Objects.hashCode(this.b);
        return hash;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }
    public void setB(B b) {
        this.b = b;
    }
    
}
