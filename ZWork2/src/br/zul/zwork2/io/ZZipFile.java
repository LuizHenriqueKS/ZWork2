package br.zul.zwork2.io;

import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.others.WinZipInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZZipFile {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final File file;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipFile(File zipFile) {
        this.file = zipFile;
    }

    public ZZipFile(String zipFile) {
        this.file = new File(zipFile);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public List<ZFile> listFiles() {
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"listFiles()");
        
        //PREPARA A LISTA RESULTADO
        List<ZFile> result = new ArrayList<>();
        try {
            InputStream is;
            
            //VERIFICA SE É UM ARQUIVO .EXE
            if (file.getAbsolutePath().toLowerCase().endsWith(".exe")) {
                //SE SIM PRECISA FAZER UM TRATAMENTO ESPECIAL
                is = new WinZipInputStream(new FileInputStream(file));
            } else {
                //SE NÃO, É SÓ LER O ARQUIVO NORMALMENTE
                is = new FileInputStream(file);
            }
            
            ZipInputStream jis = new ZipInputStream(is);

            ZipEntry je;

            //PERCORRE TODOS OS ARQUIVOS DENTRO DO ZIP
            while ((je = (ZipEntry) jis.getNextEntry()) != null) {
                String entryName = je.getName();
                //ADICIONA OS ARQUIVOS NO ZFile
                result.add(new ZFile(entryName));
            }

            return result;

        } catch (FileNotFoundException ex) {
            log.error.println(ex,"Arquivo não encontrado: %s", file.getAbsolutePath());
        } catch (IOException ex) {
            log.error.println(ex,  "Não foi possível ler o arquivo: %s",file.getAbsoluteFile());
        }
        return null;
    }

}
