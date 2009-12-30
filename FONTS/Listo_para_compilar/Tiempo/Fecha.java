/*  Class Fecha:
    Descripcion: La clase "Fecha" implementa una representacion de una fecha mediante
        el dia de la semana, el dia del mes, el mes y el a(ny)o. Nos permite obtener la
        fecha actual o seleccionar una deseada. 
    Autor: miguel.angel.vico
    Revisado: 18/11/2009 00:24 */

package Tiempo;

import java.util.Calendar;

public class Fecha {

    /* Constantes para meses y dias de la semana */
    public final static int ENERO = 1;
    public final static int FEBRERO = 2;
    public final static int MARZO = 3;
    public final static int ABRIL = 4;
    public final static int MAYO = 5;
    public final static int JUNIO = 6;
    public final static int JULIO = 7;
    public final static int AGOSTO = 8;
    public final static int SEPTIEMBRE = 9;
    public final static int OCTUBRE = 10;
    public final static int NOVIEMBRE = 11;
    public final static int DICIEMBRE = 12;
    private final static String DIAS_DE_LA_SEMANA[] = {null, "Domingo", "Lunes", 
      "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    private final static String MESES[] = {null, "Enero", "Febrero", "Marzo", "Abril",
      "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
      "Diciembre"};
    /* Atributos */
    private int diaDelMes;
    private int diaDeLaSemana;
    private int mes;
    private int anio;

    /* PRE: - */
    public Fecha() {

        diaDelMes = 0;
        diaDeLaSemana = 0;
        mes = 0;
        anio = 0;
    }
    /* POST: Se crea una instancia de Fecha con todos sus atributos inicializados a 0. */

    /* PRE: - */
    public void hoy() {

        diaDelMes = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        diaDeLaSemana = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        anio = Calendar.getInstance().get(Calendar.YEAR);
    }
    /* POST: Todos los atributos de Fecha quedan modificados de tal manera que su valor
        se corresponde con el dia de hoy. */

    /* PRE: 'dia', 'mes' y 'anio' representan una fecha valida segun el calendario
        Gregoriano. */
    public void ponerFecha(int dia, int mes, int anio) {

        this.diaDelMes = dia;
        this.diaDeLaSemana = calcularDiaDeLaSemana(dia, mes, anio);
        this.mes = mes;
        this.anio = anio;
    }
    /* POST: Los atributos diaDelMes, mes y anio quedan modificados con el valor de
        'dia', 'mes' y 'anio' respectivamente. Al atributo diaDeLaSemana se le asigna
        el dia de la semana calculado a partir de 'dia', 'mes' y 'anio'. */

    /* PRE: - */
    public String obtenerFechaFormal() {
        
        String diaDelMesStr = String.valueOf(diaDelMes);
        String mesStr = String.valueOf(mes);
        String anioStr = String.valueOf(anio);

        if(diaDelMes < 10) diaDelMesStr = "0" + diaDelMesStr;
        if(mes < 10) mesStr = "0" + mesStr;
        
        return diaDelMesStr + "/" + mesStr + "/" + anioStr;
    }
    /* POST: Retorna un string de la forma "DD/MM/AAAA" donde DD es el valor del atributo
        diaDelMes, MM es el valor del atributo mes y AAAA es el valor del atributo anio,
        convertidos a string. */

    /* PRE: - */
    public String obtenerFechaCompleta() {

        String diaDeLaSemanaStr = DIAS_DE_LA_SEMANA[diaDeLaSemana];
        String diaDelMesStr = String.valueOf(diaDelMes);
        String mesStr = MESES[mes];
        String anioStr = String.valueOf(anio);
        
        return diaDeLaSemanaStr + ", " + diaDelMesStr + " de " + mesStr + " de " +
          anioStr;
    }
    /* POST: Retorna un string de la forma "DDDDDD, DD de MMMMMM de AAAA" donde DDDDDD es
        el nombre del dia de la semana representado por el atributo diaDeLaSemana,
        MMMMMM es el nombre del mes del a(ny)o representado por el atributo mes y DD
        es el valor del atributo diaDelMes y AAAA es el valor del atributo anio,
        convertidos a string. */

    /* PRE: - */
    public int obtenerDiaDelMes() {

        return diaDelMes;
    }
    /* POST: Retorna el atributo diaDelMes. */

    /* PRE: - */
    public int obtenerDiaDeLaSemanaInt() {

        return diaDeLaSemana;
    }
    /* POST: Retorna el atributo diaDeLaSemana. */

    /* PRE: - */
    public String obtenerDiaDeLaSemanaStr() {

        return DIAS_DE_LA_SEMANA[diaDeLaSemana];
    }
    /* POST: Retorna el nombre del dia de la semana representado por el atributo
        diaDeLaSemana. */

    /* PRE: - */
    public int obtenerMesInt() {

        return mes;
    }
    /* POST: Retorna el atributo mes. */

    /* PRE: - */
    public String obtenerMesStr() {

        return MESES[mes];
    }
    /* POST: Retorna el nombre del mes del a(ny)o representado por el atributo mes. */

    /* PRE: - */
    public int obtenerAnio() {

        return anio;
    }
    /* POST: Retorna el atributo anio. */

    /* PRE: 'dia', 'mes' y 'anio' representan una fecha valida segun el calendario
        Gregoriano. */
    private int calcularDiaDeLaSemana(int dia, int mes, int anio) {

        /*  La tabla t contiene los modulos (mod 7) de los meses del a(ny)o necesarios
            para el calculo del dia de la semana. */
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};

        /* Este trozo de codigo aplica la formula para calcular el dia de la semana. */
        if (mes < 3) --anio;
        
        return (anio + (anio/4) - (anio/100) + (anio/400) + t[mes-1] + dia)%7 + 1;
    }
    /* POST: Retorna el dia de la semana (numero) calculado a partir de 'dia', 'mes' y
        'anio'. */
}
