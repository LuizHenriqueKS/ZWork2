package br.zul.zwork2.db.selection;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBSelectionCount extends ZDBSelection{

    public ZDBSelectionCount(String... name){
        super(name);
    }
    
    @Override
    public String toString() {
        return String.format("COUNT(%s)",getName());
    }
    
}
