package br.zul.zwork2.io;

import br.zul.zwork2.log.ZLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFile {

    private final File file;

    public ZTxtFile(String pathname) {
        this.file = new File(pathname);
    }

    public ZTxtFile(File file) {
        this.file = file;
    }

    public ZTxtFile(ZResource resource){
        
    }
    
    public String readAll() {
        ZLogger logger = new ZLogger(getClass(),"readAll()");
        try {
            return new String(Files.readAllBytes(file.toPath()),"UTF-8");
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }
    
    public void writeAll(String text){
        ZLogger logger = new ZLogger(getClass(),"writeAll(String text)");
        FileWriter fw;
        try {   
            fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        } catch (IOException ex) {
            throw logger.error.prepareException(ex);
        }
    }

    public String getLastLine() {
        ZLogger logger = new ZLogger(getClass(), "getLastLine()");
        RandomAccessFile fileHandler = null;
        try {
            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;

                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }

                sb.append((char) readByte);
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch (java.io.FileNotFoundException e) {
            logger.error.print(e);
            return null;
        } catch (java.io.IOException e) {
            logger.error.print(e);
            return null;
        } finally {
            if (fileHandler != null) {
                try {
                    fileHandler.close();
                } catch (IOException e) {
                    /* ignore */
                }
            }
        }
    }

}
