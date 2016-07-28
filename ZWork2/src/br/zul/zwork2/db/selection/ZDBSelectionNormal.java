package br.zul.zwork2.db.selection;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBSelectionNormal extends ZDBSelection{

    public ZDBSelectionNormal(String... name){
        super(name);
    }
    
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public ZDBSelectionType getType() {
        return ZDBSelectionType.NORMAL;
    }
    
}
