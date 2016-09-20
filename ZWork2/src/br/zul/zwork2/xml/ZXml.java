package br.zul.zwork2.xml;

import br.zul.zwork2.string.ZString;
import br.zul.zwork2.string.ZStringSearch;
import br.zul.zwork2.string.ZStringSearchResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiz.silva
 */
public class ZXml {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<ZXmlElement> elementList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXml(){
        this.elementList = new ArrayList<>();
    }
    
    public ZXml(String xml){
        this();
        ZString z = new ZString(xml,true);
        do {
            ZString before = z.toLeft("<");
            if (!before.trim().isEmpty()){
                elementList.add(new ZXmlValue(before.toString()));
            }
            z = z.fromLeft("<");
            ZStringSearch search = z.search("<","</","/>");
            int tagChildren = 1;
            for (ZStringSearchResult result:search.listResults()){
                switch (result.getPattern()){
                    case "<":
                        tagChildren++;
                        break;
                    case "</":
                        tagChildren--;
                        break;
                    case "/>":
                        tagChildren--;
                        break;
                }
                if (tagChildren<=0){
                    ZString content = z.substring(0,result.getIndex()+result.length());
                    z = z.substring(result.getEndIndex()+1);
                    content = content.appendLeft("<");
                    if (result.getPattern().equals("</")){
                        content = content.appendRight(z.toLeft(">")).appendRight(">");
                        z = z.fromLeft(">");
                    }
                    elementList.add(new ZXmlTag(content.toString()));
                    break;
                }
            }
        } while (z.contains("<"));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getContent(){
        StringBuilder xml = new StringBuilder();
        for (ZXmlElement element:elementList){
            xml.append(element);
        }
        return xml.toString();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        return getContent();
    }
    
    //==========================================================================
    //MÉTODOS PARA ELEMENTOS
    //==========================================================================
    public List<ZXmlElement> listElements(){
        return new ArrayList<>(elementList);
    }
    
    public void addElement(ZXmlElement element){
        elementList.add(element);
    }
    
    public void removeElement(ZXmlElement element){
        elementList.remove(element);
    }
    
    public void removeElement(int index){
        elementList.remove(index);
    }
    
    public ZXmlElement getElement(int index){
        return elementList.get(index);
    }
   
    public int countElements(){
        return elementList.size();
    }
    
}
