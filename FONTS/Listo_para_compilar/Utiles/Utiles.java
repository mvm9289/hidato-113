/*  Class Utiles:
    Descripcion: Proporciona operaciones utiles para el tratamiento de Objetos.
    Autor: alex.catarineu
    Colaboradores: miguel.angel.vico
    Revisado: 20/12/2009 15:02 */

package Utiles;

import Tiempo.Fecha;
import Tiempo.Hora;

public class Utiles {

    /* Retorna un array de enteros que es copia de 'array' */
    public static int[] copiarArray(int array[]) {

        int res[] = new int[array.length];
        
        for (int i = 0; i < array.length; ++i)
            res[i] = array[i];

        return res;
    }

    /* Retorna una matriz de enteros que es copia de matriz */
    public static int[][] copiarMatriz(int matriz[][]) {

        int res[][] = new int[matriz.length][];
        
        for (int i = 0; i < matriz.length; ++i)
            res[i] = copiarArray(matriz[i]);
        
        return res;
    }

    /* Retorna cierto si el array array1 tien el mismo contenido que array2 */
    public static boolean igualArray(int array1[], int array2[]) {

        int n1 = array1.length;
        int n2 = array2.length;

        if (n1 != n2) return false;
        for (int i = 0; i < n1; ++i)
            if (array1[i] != array2[i])
                return false;
        
        return true;
    }

    /* Retorna cierto si la matriz matriz1 tien el mismo contenido que matriz2 */
    public static boolean igualMatriz(int matriz1[][], int matriz2[][]) {

        int n1 = matriz1.length;
        int n2 = matriz2.length;
        
        if (n1 != n2) return false;
        for (int i = 0; i < n1; ++i)
            if (!igualArray(matriz1[i], matriz2[i]))
                return false;

        return true;
    }

    /* Realiza la conversion de un array de Bytes a un String */
    public static String byteArrayToString(byte[] byteArray) {

        String string;
        char charArray[] = new char[byteArray.length];
        
        for (int i = 0; i < byteArray.length; ++i) {
            charArray[i] = (char)byteArray[i];
        }
        string = new String(charArray);
        if (string.charAt(string.length() - 1) != '\0') return string;
        
        return string.substring(0, string.indexOf("\0"));
    }

    /* Realiza la conversion de un String a un array de Bytes */
    public static byte[] stringToByteArray(String string, int size) {

        char charArray[] = string.toCharArray();
        byte byteArray[] = new byte[size];

        for (int i = 0; i < charArray.length && i < size; ++i) {
            byteArray[i] = (byte)charArray[i];
        }
        for (int i = charArray.length; i < size; ++i) byteArray[i] = (byte)'\0';

        return byteArray;
    }

    /* Realiza la conversion de un array de Bytes a un entero */
    public static int byteArrayToInt(byte[] byteArray) {

        int value = 0;
        int shift;

        for (int i = 0; i < 4; ++i) {
            shift = (3 - i) * 8;
            value += (byteArray[i] & 0x000000FF) << shift;
        }
        
        return value;
    }

    /* Realiza la conversion de un entero a un array de Bytes */
    public static byte[] intToByteArray(int int_) {

        byte byteArray[] = new byte[4];
        int shift;

        for (int i = 0; i < 4; ++i) {
            shift = (3 - i) * 8;
            byteArray[i] = (byte)((int_ >> shift) & 0x000000FF);
        }

        return byteArray;
    }

    /* Realiza la conversion de un array de Bytes a un double */
    public static double byteArrayToDouble(byte[] byteArray) {

        long value = 0;
        int shift;

        for (int i = 0; i < 8; ++i) {
            shift = (7 - i) * 8;
            value += (long)(byteArray[i] & 0x000000FF) << shift;
        }

        return Double.longBitsToDouble(value);
    }

    /* Realiza la conversion de un double a un array de Bytes */
    public static byte[] doubleToByteArray(double decimal) {

        byte[] byteArray = new byte[8];
        long value = Double.doubleToLongBits(decimal);
        int shift;

        for (int i = 0; i < 8; ++i) {
            shift = (7 - i) * 8;
            byteArray[i] = (byte)((value >> shift) & 0x000000FF);
        }

        return byteArray;
    }

    /* Realiza la conversion de un array de Bytes a un boolean */
    public static boolean byteArrayToBoolean(byte[] byteArray) {

        return (byteArray[0] == (byte)1);
    }

    /* Realiza la conversion de un boolean a un array de Bytes */
    public static byte[] booleanToByteArray(boolean b) {
        
        byte byteArray[] = new byte[1];
        
        if (b) byteArray[0] = (byte)1;
        else byteArray[0] = (byte)0;
        
        return byteArray;
    }

    /* Realiza la conversion de un array de Bytes a un objeto de tipo Fecha */
    public static Fecha byteArrayToFecha(byte[] byteArray) {

        Fecha fecha = new Fecha();
        int dia;
        int mes;
        int anio;

        dia = byteArrayToInt(getSubByte(byteArray, 0, 3));
        mes = byteArrayToInt(getSubByte(byteArray, 4, 7));
        anio = byteArrayToInt(getSubByte(byteArray, 8, 11));
        fecha.ponerFecha(dia, mes, anio);

        return fecha;
    }

    /* Realiza la conversion de un objeto de tipo Fecha a un array de Bytes */
    public static byte[] fechaToByteArray(Fecha fecha) {

        byte[] byteArray = new byte[12];

        setSubByte(byteArray, intToByteArray(fecha.obtenerDiaDelMes()), 0, 3);
        setSubByte(byteArray, intToByteArray(fecha.obtenerMesInt()), 4, 7);
        setSubByte(byteArray, intToByteArray(fecha.obtenerAnio()), 8, 11);

        return byteArray;
    }

    /* Realiza la conversion de un array de Bytes a un objeto de tipo Hora */
    public static Hora byteArrayToHora(byte[] byteArray) {

        Hora hora = new Hora();
        int horas;
        int minutos;
        int segundos;

        horas = byteArrayToInt(getSubByte(byteArray, 0, 3));
        minutos = byteArrayToInt(getSubByte(byteArray, 4, 7));
        segundos = byteArrayToInt(getSubByte(byteArray, 8, 11));
        hora.ponerHora(horas, minutos, segundos);

        return hora;
    }

    /* Realiza la conversion de un objeto de tipo Hora a un array de Bytes */
    public static byte[] horaToByteArray(Hora hora) {

        byte[] byteArray = new byte[12];

        setSubByte(byteArray, intToByteArray(hora.obtenerHora()), 0, 3);
        setSubByte(byteArray, intToByteArray(hora.obtenerMinuto()), 4, 7);
        setSubByte(byteArray, intToByteArray(hora.obtenerSegundo()), 8, 11);

        return byteArray;
    }

    /* Retorna el array de Bytes contenido por 'byteArray' que va desde la posicion
        'from' hasta la posicion 'to' */
    public static byte[] getSubByte(byte[] byteArray, int from, int to) {

        byte[] subyte = new byte[to - from + 1];

        for (int i = from; i <= to; ++i) {
            subyte[i - from] = byteArray[i];
        }

        return subyte;
    }

    /* Inserta el array de Bytes 'subyte' dentro de 'byteArray' empezando en la posicion
        'from' y acabando en la posicion 'to' */
    public static void setSubByte(byte[] byteArray, byte[] subyte, int from, int to) {

        for (int i = from; i <= to; ++i) {
            byteArray[i] = subyte[i - from];
        }
    }

    /* Retorna cierto si el String 'string' unicamente contiene caracteres
        alfanumericos */
    public static boolean alfanumericos(String string) {

        char charAux;

        for (int i = 0; i < string.length(); ++i) {
            charAux = string.charAt(i);
            if ((charAux < 'A' || charAux > 'Z') && (charAux < 'a' || charAux > 'z') &&
              (charAux < '0' || charAux > '9'))
                return false;
        }

        return true;
    }

    /* Retorna cierto si el String 'string' representa un valor entero */
    public static boolean esInt(String string) {

        int num;

        try {
            num = Integer.parseInt(string);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    /* Retorna el double resultado de hacer el redondeo a 'decimales' cifras decimales
        del numero 'numero' */
    public static double redondearDouble(double numero, int decimales) {
        
        return Math.round(numero * Math.pow(10,decimales)) / Math.pow(10,decimales);
    }

    /* Retorna un String que expresa el valor de 'segundos' en el formato "HH:MM:SS" */
    public static String segundosToTiempo(int segundos) {

        int minutos;
        int horas;
        String segundosStr;
        String minutosStr;
        String horasStr;

        minutos = segundos / 60;
        segundos = segundos % 60;
        horas = minutos / 60;
        minutos = minutos % 60;

        segundosStr = Integer.toString(segundos);
        if (segundos < 10) segundosStr = "0" + segundosStr;
        minutosStr = Integer.toString(minutos);
        if (minutos < 10) minutosStr = "0" + minutosStr;
        horasStr = Integer.toString(horas);
        if (horas < 10) horasStr = "0" + horasStr;

        return horasStr + ":" + minutosStr + ":" + segundosStr;
    }
}
