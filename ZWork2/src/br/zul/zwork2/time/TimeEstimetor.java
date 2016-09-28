package br.zul.zwork2.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luiz.silva
 */
public class TimeEstimetor {

    private final long start;
    private final long progressTotal;

    public TimeEstimetor(long progressTotal) {
        this.start = System.currentTimeMillis();
        this.progressTotal = progressTotal;
    }

    /**
     * Tempo decorrido em milisegundos.
     * @return 
     */
    public long getElapsedTimeMillis() {
        return System.currentTimeMillis() - start;
    }

    public long getTimeLeft(long progressActual) {
        double factor = (double) progressActual / (double) getElapsedTimeMillis();
        double timeLeft = (progressTotal - progressActual) / factor;
        return (long) Math.ceil(timeLeft);
    }

    public String formatTimeLeft(long progressActual, String format) {
        return formatMiliseconds(getTimeLeft(progressActual), format);
    }

    public String formatTimeLeft(long progressActual) {
        return formatStandard(getTimeLeft(progressActual));
    }

    
    public String formatElapsedTime(String format) {
        return formatMiliseconds(getElapsedTimeMillis(), format);
    }
    
    /**
     * Formato do simpleDateFormat. Ex: dd HH:mm:ss.SSS
     *
     * @return
     */
    public String formatElapsedTime() {
        return formatStandard(getElapsedTimeMillis());
    }

    public static String formatMiliseconds(long miliseconds, String format) {
        long ms = miliseconds;
        long ss = divideEFloor(ms, 1000);
        long mm = divideEFloor(ss, 60);
        long HH = divideEFloor(mm, 60);
        long dd = divideEFloor(HH, 24);

        ms = ms % 1000;
        ss = ss % 60;
        mm = mm % 60;
        HH = HH % 24;

        format = format.replace("SSS", String.valueOf(ms));
        format = format.replace("ss", String.valueOf(ss));
        format = format.replace("mm", String.valueOf(mm));
        format = format.replace("HH", String.valueOf(HH));
        format = format.replace("dd", String.valueOf(dd));

        return format;
    }

    /**
     * Exemplo de uso: getTempo(1000,"ss"); //retorna 1 segundo
     * getTempo(60000m,"mm"); //retorna 1 minuto getTempo(120000m,"mm");
     * //retorna 2 minuto
     *
     * @param miliseconds
     * @param medidaTempo
     * @return
     */
    public static long formatTime(long miliseconds, String medidaTempo) {
        return Long.valueOf(formatMiliseconds(miliseconds, medidaTempo));
    }

    /**
     *
     * @param miliseconds
     * @return Retorna no formado dd dias, HH horas mm minutos e ss segundos.
     */
    public static String formatStandard(long miliseconds) {
        String medidasTempo[] = {"dd", "HH", "mm", "ss"};
        String unidades[] = {"dia", "hora", "minuto", "segundo"};
        Map<String, Long> valores = new HashMap<>();

        for (int i = 0; i < medidasTempo.length; i++) {
            String medida = medidasTempo[i];
            String unidade = unidades[i];
            long valor = formatTime(miliseconds, medida);
            if (valor > 0) {
                valores.put(unidade, valor);
            }
        }

        StringBuilder result = new StringBuilder();

        List<String> listaKeys = new ArrayList<>();
        for (String unidade : unidades) {
            if (valores.keySet().contains(unidade)) {
                listaKeys.add(unidade);
            }
        }

        for (int i = 0; i < valores.size(); i++) {
            long valor = valores.get(listaKeys.get(i));
            result.append(valor);
            result.append(" ");
            result.append(listaKeys.get(i));

            if (valor > 1) {
                result.append("s");
            }

            if (i == valores.size() - 2) {
                result.append(" e ");
            } else if (i != valores.size() - 1) {
                result.append(", ");
            }
        }

        if (result.length() == 0) {
            return "1 segundo";
        }
        return result.toString();

    }

    private static long divideEFloor(long valor, long divisor) {
        double resultado = valor / divisor;
        return (long) Math.floor(resultado);
    }
}
