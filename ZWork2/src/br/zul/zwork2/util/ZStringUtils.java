package br.zul.zwork2.util;

import br.zul.zwork2.string.ZString;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author skynet
 */
public class ZStringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(ZString str) {
        return str == null || str.isEmpty();
    }

    public static boolean isLong(String str) {

        //SE ESTIVER VAZIO ENTÃO NÃO É LONG
        if (isEmpty(str)) {
            return false;
        }

        //TENTA CONVERTER PARA LONG
        try {
            Long.parseLong(str);
            //SE CONSEGUIR ENTÃO É LONG
            return true;
        } catch (Exception e) {
            //SE NÃO CONSEGUIR, É PQ NÃO É LONG
            return false;
        }

    }

    public static String removeEmptyChar(String str) {

        String result = "";

        for (char c : str.toCharArray()) {
            if (c != 65279) {
                result += c;
            }
        }

        return result;

    }

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String[] convert(ZString[] array) {
        List<String> list = new ArrayList<>();
        for (ZString z : array) {
            list.add(z.toString());
        }
        return list.toArray(new String[list.size()]);
    }

    public static ZString[] convert(String[] array, boolean caseSensitive) {
        List<ZString> list = new ArrayList<>();
        for (String s : array) {
            list.add(new ZString(s, caseSensitive));
        }
        return list.toArray(new ZString[list.size()]);
    }

    public static void toClipboard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }

    public static String fromClipboard() {
        try {
            String data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
            return data;
        } catch (UnsupportedFlavorException | IOException ex) {
            return null;
        }
    }
    
    public static ZString fromClipboard(boolean caseSensitive){
        String string = fromClipboard();
        return new ZString(string,caseSensitive);
    }

}
