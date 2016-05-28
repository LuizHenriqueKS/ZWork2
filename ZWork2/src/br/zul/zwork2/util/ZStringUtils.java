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
