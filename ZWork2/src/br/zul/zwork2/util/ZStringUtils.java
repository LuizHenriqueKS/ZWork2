package br.zul.zwork2.util;

import br.zul.zwork2.string.ZString;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skynet
 */
public class ZStringUtils {
    
    public static boolean isEmpty(String str){
        return str==null||str.isEmpty();
    }
    
    public static boolean isEmpty(ZString str){
        return str==null||str.isEmpty();
    }
    
    public static boolean isLong(String str){
        
        //SE ESTIVER VAZIO ENTÃO NÃO É LONG
        if (isEmpty(str)){
            return false;
        }
        
        //TENTA CONVERTER PARA LONG
        try{
            Long.parseLong(str);
            //SE CONSEGUIR ENTÃO É LONG
            return true;
        }catch(Exception e){
            //SE NÃO CONSEGUIR, É PQ NÃO É LONG
            return false;
        }
        
    }
    
    public static String removeEmptyChar(String str){
        
        String result="";
        
        for (char c:str.toCharArray()){
            if (c!=65279){
                result+=c;
            }
        }
        
        return result;
        
    }
    
    public static String[] convert(ZString[] array){
        List<String> list = new ArrayList<>();
        for (ZString z:array){
            list.add(z.toString());
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static ZString[] convert(String[] array,boolean caseSensitive){
        List<ZString> list = new ArrayList<>();
        for (String s:array){
            list.add(new ZString(s,caseSensitive));
        }
        return list.toArray(new ZString[list.size()]);
    }
}
