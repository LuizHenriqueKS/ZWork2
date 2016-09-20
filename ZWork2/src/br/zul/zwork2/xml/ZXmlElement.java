package br.zul.zwork2.xml;

import br.zul.zwork2.string.ZString;

/**
 *
 * @author luiz.silva
 */
public abstract class ZXmlElement {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private ZXmlTag parent;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTÁTICOS
    //==========================================================================
    public static ZXmlElement parseElement(String xml){
        ZString z = new ZString(xml,true);
        ZString before = z.toLeft("<");
        //SE POSSUI UM CONTEÚDO ANTES DO "<" ENTÃO É UM VALOR
        if (!before.trim().isEmpty()){
            return new ZXmlValue(before.toString());
        } else {
            ZString after = z.fromLeft("<");
            if (!after.trim().isEmpty()){
                //SE POSSUI CONTEÚDO DEPOIS ENTÃO É UMA TAG
                return new ZXmlTag(after.toString());
            } else {
                //SE NÃO ACHOU NADA, RETORNA UM VALOR EM BRANCO
                return new ZXmlValue();
            }
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZXmlElementType getType();
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public boolean isType(ZXmlElementType type){
        return getType().equals(type);
    }
    
    public boolean isValue(){
        return isType(ZXmlElementType.VALUE);
    }
    
    public boolean isTag(){
        return isType(ZXmlElementType.TAG);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZXmlTag getParent() {
        return parent;
    }
    public void setParent(ZXmlTag parent) {
        this.parent = parent;
    }

}
