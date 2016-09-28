package br.zul.zwork2.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author skynet
 */
public class CalcularTempo {

    private final long inicio;
    private final long total;
    
    public CalcularTempo(long total){
        this.inicio = System.currentTimeMillis();
        this.total = total;
    }
    
    public long getTempoDecorridoEmMilisegundos(){
        return System.currentTimeMillis()-inicio;
    }
    
    public long getTempoRestante(long progresso){
        double fator = (double)progresso/(double)getTempoDecorridoEmMilisegundos();
        double restante = (total-progresso)/fator;
        return (long)Math.ceil(restante);
    }
    
    public String formatTempoRestante(long progresso,String formato){
        return formatMiliseconds(getTempoRestante(progresso), formato);
    }
    
    public String formatoPadraoTempoRestante(long progresso){
        return formatoPadrao(getTempoRestante(progresso));
    }
    
    /**
     * Formato padrao do simpleDateFormat.
     * Ex: 
     *  dd HH:mm:ss.SSS
     * @param formato
     * @return 
     */
    public String formatTempoDecorrido(String formato){
        return formatMiliseconds(getTempoDecorridoEmMilisegundos(),formato);
    }
    
    public String formatoPadraoTempoDecorrido(){
        return formatoPadrao(getTempoDecorridoEmMilisegundos());
    }
    
    public static String formatMiliseconds(long miliseconds,String formato){
       long ms = miliseconds;
       long ss = divideEFloor(ms,1000);
       long mm = divideEFloor(ss,60);
       long HH = divideEFloor(mm,60);
       long dd = divideEFloor(HH,24);
       
       ms = ms%1000;
       ss = ss%60;
       mm = mm%60;
       HH = HH%24;
       
       formato = formato.replace("SSS", String.valueOf(ms));
       formato = formato.replace("ss", String.valueOf(ss));
       formato = formato.replace("mm", String.valueOf(mm));
       formato = formato.replace("HH", String.valueOf(HH));
       formato = formato.replace("dd", String.valueOf(dd));
       
       return formato;
    }
    
    /**
     * Exemplo de uso: 
     *  getTempo(1000,"ss"); //retorna 1 segundo
     *  getTempo(60000m,"mm"); //retorna 1 minuto
     *  getTempo(120000m,"mm"); //retorna 2 minuto
     * @param miliseconds
     * @param medidaTempo
     * @return 
     */
    public static long getTempo(long miliseconds,String medidaTempo){
        return Long.valueOf(formatMiliseconds(miliseconds, medidaTempo));
    }
    
    /**
     * 
     * @param miliseconds
     * @return Retorna no formado dd dias, HH horas mm minutos e ss segundos.
     */
    public static String formatoPadrao(long miliseconds){
        String medidasTempo[] = {"dd","HH","mm","ss"};
        String unidades[] = {"dia","hora","minuto","segundo"};
        Map<String,Long> valores = new HashMap<>();
        
        for (int i=0;i<medidasTempo.length;i++){
            String medida = medidasTempo[i];
            String unidade = unidades[i];
            long valor = getTempo(miliseconds,medida);
            if (valor>0){
                valores.put(unidade, valor);
            }
        }
        
        StringBuilder result = new StringBuilder();
        
        List<String> listaKeys = new ArrayList<>();
        for (String unidade:unidades){
            if (valores.keySet().contains(unidade)){
                listaKeys.add(unidade);
            }
        }
        
        
        for (int i=0;i<valores.size();i++){
            long valor = valores.get(listaKeys.get(i));
            result.append(valor);
            result.append(" ");
            result.append(listaKeys.get(i));
            
            if (valor>1){
                result.append("s");
            }
            
            if (i==valores.size()-2){
                result.append(" e ");
            } else if (i!=valores.size()-1) {
                result.append(", ");
            }
        }
        
        if (result.length()==0){
            return "1 segundo";
        }
        return result.toString();
        
    }
    
    private static long divideEFloor(long valor,long divisor){
        double resultado = valor/divisor;
        return (long)Math.floor(resultado);
    }
    
}
