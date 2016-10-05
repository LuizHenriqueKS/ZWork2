package br.zul.zwork2.io;

import br.zul.zwork2.string.ZString;
import br.zul.zwork2.util.ZStringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZPath {
    
    //==========================================================================
    //CONSTANTES PÚBLICAS
    //==========================================================================
    public static boolean CASE_SENSITIVE = true;
    public static ZPathPattern DEFAULT_PATTERN = ZPathPattern.LINUX;
    //==========================================================================
    //ENUMS
    //==========================================================================
    public enum ZPathPattern{
        /**
         * c:\\br\\com\\package\\example
         */
        WINDOWS,
        /**
         * /br/com/package/example
         */
        LINUX,
        /**
         * br.com.package.main
         */
        PACKAGE;
    }
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String path;
    private ZString parts[];
    private ZPathPattern pattern;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPath(String path,String separator){
        this.path = path;
        this.parts = new ZString(path,CASE_SENSITIVE).split(separator);
        switch (separator){
            case "\\":
                pattern = ZPathPattern.WINDOWS;
                break;
            case "/":
                pattern = ZPathPattern.LINUX;
                break;
            case ".":
                pattern = ZPathPattern.PACKAGE;
                break;
            default:
                pattern = DEFAULT_PATTERN;
        }
        
    }
    
    public ZPath(String path,ZPathPattern pattern){
        init(path,pattern);
    }
    
    public ZPath(String path){
        if (path.contains("/")){
            init(path,ZPathPattern.LINUX);
        } else if (path.contains("\\")) {
            init(path,ZPathPattern.WINDOWS);
        } else if (path.contains(".")){
            init(path,ZPathPattern.PACKAGE);
        }
    }
    
    public ZPath(ZString path){
        this(path.asString());
    }
    
    public ZPath(Package pack){
        String packPath = pack.toString().substring("package ".length());
        init(packPath,ZPathPattern.PACKAGE);
    }
    
    public ZPath(String pathParts[],ZPathPattern pattern){
        this.parts = ZStringUtils.convert(pathParts, CASE_SENSITIVE);
        init(pattern);
    }
    
    public ZPath(List<String> partList,ZPathPattern pattern){
        this(partList.toArray(new String[partList.size()]),pattern);
    }
    
    //==========================================================================
    //MÉTODOS PARA CONSTRUÇÃO
    //==========================================================================
    private void init(ZPathPattern pattern){
        this.path = format(pattern);
        this.pattern = pattern;
    }
    
    private void init(String path,ZPathPattern pattern){
        this.path = path;
        this.pattern = pattern;
        switch (pattern){
            case WINDOWS:
                parts = new ZString(path,CASE_SENSITIVE).split("\\");
                break;
            case LINUX:
                parts = new ZString(path,CASE_SENSITIVE).split("/");
                break;
            case PACKAGE:
                parts = new ZString(path,CASE_SENSITIVE).split(".");
                break;
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String format(ZPathPattern pattern){
        StringBuilder result = new StringBuilder();
        
        switch (pattern){
            
            case LINUX:
                //MONTA O PATH. Ex: /ROOT/DIRECTORY/SUBDIRECTORY
                for (ZString z:parts){
                    if (!z.isEmpty()){
                        result.append("/");
                        result.append(z.toString());    
                    }
                }
                break;
                
            case PACKAGE:
                //MONTA O PATH. Ex: ROOT.DIRECTORY.SUBDIRECTORY
                for (int i=0;i<parts.length;i++){
                    if (i>0){
                        result.append(".");
                    }
                    result.append(parts[i].toString());
                }
                break;
                
            case WINDOWS:
                //Ex: C:\\DIRECTORY\\SUBDIRECTORY
                for (int i=0;i<parts.length;i++){
                    if (i>0){
                        result.append("\\");
                    }
                    result.append(parts[i].toString());
                }
        }
        
        return result.toString();
    }
    
    @Override
    public String toString(){
        return path;
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof ZPath){
            
            ZPath zp = (ZPath)other;
            //SE O TAMANHO É DIFERENTE QUER DIZER Q NÃO É IGUAL
            if (zp.listParts().size()!=parts.length){
                return false;
            } else {
                //VERIFICA SE AS PARTES SÃO IGUAIS
                for (int i=0;i<parts.length;i++){
                    if (!parts[i].equals(zp.listParts().get(i))){
                        return false; //SE UM NÃO FOR RETORNA FALSE
                    }
                }
                //SE TODAS FOREM IGUAIS, RETORNA TRUE
                return true;
            }
            
            
        } else {
            return super.equals(other);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Arrays.deepHashCode(this.parts);
        return hash;
    }
    
    public List<String> listParts(){
        return Arrays.asList(ZStringUtils.convert(parts));
    }
    
    public String getLastPart(){
        return parts[parts.length-1].toString();
    }
    
    public ZString getLastPart(boolean caseSensitive){
        return new ZString(getLastPart(),caseSensitive);
    }
    
    public String getFirstPart(){
        return parts[0].toString();
    }
    
    public ZString getFirstPartAsZString(boolean caseSensitive){
        return new ZString(getFirstPart(),caseSensitive);
    }
    
    public ZPath getParent(){
        try{
            String[] parentParts = Arrays.copyOfRange(ZStringUtils.convert(parts), 0, parts.length-1);
            return new ZPath(parentParts,pattern);
        }catch(Exception e){
            return null;
        }
    }
    
}
