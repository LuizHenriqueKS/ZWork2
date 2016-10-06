package br.zul.zwork2.xml;

import br.zul.zwork2.string.ZString;
import br.zul.zwork2.string.ZStringSearch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author luiz.silva
 */
public class ZXmlTag extends ZXmlElement {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String tagName;
    private Map<String, String> attributesMap;
    private List<ZXmlElement> elementList;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlTag() {
        this.tagName = "undefined";
        this.attributesMap = new HashMap<>();
        this.elementList = new ArrayList<>();
    }

    public ZXmlTag(String xml) {
        this();
        
        //OBTEM O NOME DA TAG
        ZString z = new ZString(xml,true);
        z = z.fromLeft("<");
        tagName = z.toLeft(" ",">").toString();
        z = z.substring(tagName.length());

        //OBTEM OS ATTRIBUTOS
        ZString attributes = z.toLeft(">");
        while (!attributes.trim().isEmpty()){
            attributes = attributes.trim();
            String attrName = attributes.toLeft("=").trim().toString();
            attributes = attributes.fromLeft("=").trim();
            String quote = attributes.substring(0,1).toString();
            attributes = attributes.substring(1);
            String attrValue = attributes.toLeft(new String[]{quote},new String[]{"\\'"+quote}).toString();
            attributes = attributes.fromLeft(new String[]{quote},new String[]{"\\'"+quote});
            attributesMap.put(attrName, attrValue);
        }
        
        //VERIFICA SE TEM CONTEÚDO
        ZStringSearch search = z.search(">","/>");
        search.first();
        if (search.getResult().getPattern().equals("/>")){
            return;
        }
        z = z.fromLeft(">");
        
        //REMOVE TUDO QUE ESTÁ ALÉM DO CONTEÚDO DA TAG
        int tag = 0;
        search = z.search("<","/>","</");
        search.first();
        do{
            switch (search.getResult().getPattern()){
                case "<":
                    tag++;
                    break;
                case "/>":
                    tag--;
                    break;
                case "</":
                    tag--;
                    break;
            }
            if (tag<0){
                z = z.substring(0,search.getResult().getEndIndex()-1);
                break;
            }
        } while (search.next());
        
        //OBTEM OS ELEMENTOS
        ZXml zxml = new ZXml(z.toString());
        elementList.addAll(zxml.listElements());
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    @Override
    public String toString(){
        StringBuilder xml = new StringBuilder();
        
        //INICIA
        xml.append("<");
        xml.append(tagName);
        
        //PASSA OS ATRIBUTOS
        for (Entry<String,String> attribute:attributesMap.entrySet()){
            xml.append(" ");
            xml.append(attribute.getKey());
            xml.append("=\"");
            xml.append(attribute.getValue().replace("\"","\\\""));
            xml.append("\"");
        }
        
        //FINALIZA
        if (elementList.isEmpty()){
            xml.append("/>");
        } else {
            xml.append(">");
            xml.append("\r\n");
            xml.append(getContent());
            xml.append("</");
            xml.append(tagName);
            xml.append(">");
        }
        xml.append("\r\n");
        
        return xml.toString();
    }
    
    public String getContent(){
        StringBuilder xml = new StringBuilder();
        for (ZXmlElement element:elementList){
            xml.append(element);
        }
        if (!xml.toString().endsWith("\r\n")&&!xml.toString().trim().isEmpty()){
            xml.append("\r\n");
        }
        return xml.toString().trim();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZXmlElementType getType() {
        return ZXmlElementType.TAG;
    }

    //==========================================================================
    //MÉTODOS PARA ATRIBUTOS
    //==========================================================================
    public String getAttribute(String name) {
        return attributesMap.get(name);
    }

    public void putAttribute(String name, String value) {
        attributesMap.put(name, value);
    }

    public void putAttribute(String name) {
        attributesMap.put(name, "");
    }

    public void removeAttribute(String name) {
        attributesMap.remove(name);
    }

    public boolean hasAttribute(String name) {
        return attributesMap.containsKey(name);
    }

    public Map<String, String> attributeMap() {
        return attributesMap;
    }

    public int countAttributes() {
        return attributesMap.size();
    }

    //==========================================================================
    //MÉTODOS PARA ELEMENTOS
    //==========================================================================
    public List<ZXmlElement> listElements() {
        return new ArrayList<>(elementList);
    }

    public void addElement(ZXmlElement element) {
        elementList.add(element);
        element.setParent(this);
    }

    public void removeElement(ZXmlElement element) {
        elementList.remove(element);
    }

    public void removeElement(int index) {
        elementList.remove(index);
    }

    public ZXmlElement getElement(int index) {
        return elementList.get(index);
    }

    public int countElements() {
        return elementList.size();
    }

    //==========================================================================
    //GETTERES E SETTERS
    //==========================================================================
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
