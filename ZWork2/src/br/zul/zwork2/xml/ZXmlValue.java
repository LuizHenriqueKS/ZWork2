package br.zul.zwork2.xml;

/**
 *
 * @author luiz.silva
 */
public class ZXmlValue extends ZXmlElement {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String content;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlValue(){
        this.content = "";
    }
    
    public ZXmlValue(String content){
        this.content = content;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZXmlElementType getType() {
        return ZXmlElementType.VALUE;
    }
    
    @Override
    public String toString(){
        return content;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
