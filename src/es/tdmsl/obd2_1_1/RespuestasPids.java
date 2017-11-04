package es.tdmsl.obd2_1_1;

import android.util.Log;
import es.tdmsl.Bluetooth.Bluetooth;
import es.tdmsl.IO.IO;
import es.tdmsl.utils.Utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manu on 20/06/2016.
 */
public class RespuestasPids{
    private String trama;
    private IO io;
    //BluetoothSocket shocket;
    Bluetooth bluetooth;

    public RespuestasPids() {
        bluetooth = Bluetooth.getInstancia(null);
        io = new IO(bluetooth.getSocket());

    }

    public int Ndtcs(){
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0101"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"");
        int nDTCs;
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            //strDatos="\n41 01 84 4B A5 F1 0A\r";
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("0101", "");
            trama = trama.replace("4101", "");
            trama = trama.replace(">", "");
            Log.i(this.toString(),trama);
            trama = trama.substring(0,8);
            Log.i(this.toString(),trama);
            //"se pasan a binario los bytes \n"
            String A,B,C,D;
            A = trama.substring(0,2);
            Log.i(this.toString(),A);
            nDTCs = Integer.parseInt(A,16)-128 ;
            A = Integer.toBinaryString(Integer.parseInt(A,16));
            A = new Utilidades().completeDigits(A);
            A = A+" es el estado MIL y el número de códigos de problemas.";
            Log.i(this.toString(),A);
            // El primer bit es el MIL encendido
            // / apagado bit (1 o ON en este caso),
            // los próximos 7 bits (0000001) son el número de códigos de problemas";
            //dtcs.append(A+"\n");
            //listaDtcs();
            return  nDTCs;
        }
        //listaDtcs();

       return  0;
        //listaDtcs();
        //List<String> listaDtcs= new RespuestasPids().listaDtcs();
    }

    public List<String> listaDtcs() {
        //descifraTramaHexDescripcionErrores
        //43 01 33 02 45 61 21 0A
        //43 01 33 02 45 61 21 0A
        ///////////////////////////
        //0: 43 06 01 00 02 00
        //1: 03 00 43 00 82 00 C1
        //2: 00 00 00 00 00 00 00
        //43 01 01 01


        String cp = String.valueOf(io.enviar("03"));
        Log.i(this.toString(),"trama recibida "+cp);
        cp = cp.replace("03","");
        Log.i(this.toString(),"respuesta a 03 <"+cp+"");
        String s1 = cp.substring(0,22);
        Log.i(this.toString(),"1º tramo "+s1);
        String s2 = cp.substring(22,44);
        Log.i(this.toString(),"2ª tramo "+s2);
        String s3 = cp.substring(45,70);
        Log.i(this.toString(),"3ª tramo "+s3);
        String s4 = cp.substring(70,82);
        Log.i(this.toString(),"4ª tramo "+s4);

        //Log.i(this.toString(),"respuesta a 03 <"+cp+"");
        Log.i(this.toString(),"deveria ser "+"0: 43 06 01 00 02 00\n" +
                "1: 03 00 43 00 82 00 C1\n" +
                "2: 00 00 00 00 00 00 00\n" +
                "43 01 01 01");
        cp = "0: 43 06 01 00 02 00\n1: 03 00 43 00 82 00 C1\n2: 00 00 00 00 00 00 00\n43 01 01 01";
        Log.i(this.toString(),"siendo "+cp);
        List<String> list = new ArrayList<>();
        list.add(cp);

        /*for(int s=0;s<Ndtcs();s++){

        }*/
       // cp = cp.replaceAll("\\s", "");



        return list;
    }

    public String rpm(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("010C"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"");
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("010C", "");
            trama = trama.replace("410C", "");
            trama = trama.replace(">", "");
            trama = trama.substring(0, 4);
            //((A*256) + B) / 4
            String A = trama.substring(0, 2);
            String B = trama.substring(2, 4);
            trama = String.valueOf(((Integer.parseInt(A, 16) * 256) + Integer.parseInt(B, 16)) / 4) + " rpm";
        }
        return trama;
    }

    public String temperaturaRefrigerante() {
        String trama = ("no data");
        //formateamos la respuesta//quitamos pid y cabeceras
        trama = String.valueOf(io.enviar("0105"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"tempRef");
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("0105", "");
            trama = trama.replace("4105", "");
            trama = trama.replace(">", "");
            trama = trama.substring(0, 2);
            int i = Integer.parseInt(trama, 16);
            i = i - 40;
            trama = String.valueOf(i) + " grados";
        }
        return trama;

    }

    public String kmh(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("010D"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"");
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("010D", "");
            trama = trama.replace("410D", "");
            trama = trama.replace(">", "");
            trama = trama.substring(0, 2);
            int i = Integer.parseInt(trama, 16);
            trama = String.valueOf(i);
            trama = trama + " KMh";
        }
        Log.i(this.toString(),trama+"");
        return trama;
    }

    public String vadd(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0110"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"");
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("0110", "");
            trama = trama.replace("4110", "");
            trama = trama.replace(">", "");

            trama = trama.substring(0, 4);
            Log.i(this.toString(), trama + "");
            //(256A+B)/100
            int A = Integer.parseInt(trama.substring(0, 2), 16);
            int B = Integer.parseInt(trama.substring(2, 4), 16);
            trama = String.valueOf((256 * A + B) / 100);
            trama = trama + " Volumen air add";
        }

        return trama;
    }

    public String co2(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0114"));
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        Log.i(this.toString(),trama+"");
        if (trama.contains("NODATA")){
            trama =  trama.substring(4);

        }else {
            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("0114", "");
            trama = trama.replace("4114", "");
            trama = trama.replace(">", "");
            trama = trama.substring(0,4);
            //A: A/200
            //B: B/1.28-100
            //(Si B==FF, entonces el sensor no se usa en el cálculo del ajuste)
            int A = Integer.parseInt(trama.substring(0,2),16);
            trama = String.valueOf(A/200);
            trama = trama+" CO2";

        }

        Log.i(this.toString(),trama+"");
        return trama;
    }

    public String estadoSistemaCombustible(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0103"));
        //trama = trama.replace("SEARCHING...", "");
        trama = trama.replace("0103", "");
        trama = trama.replace("41 03 ", "");
        trama = trama.replace(">", "");
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        //trama = trama
        Log.i("TAG", "TRAMA  =  " + trama);
        Log.i("TAG", "longitud TRAMA  =  " + trama.length());

        return trama;
    }

    public double cargaMotor(){
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0104"));
        trama = trama.replace("SEARCHING...", "");
        trama = trama.replace("0104", "");
        trama = trama.replace("41 04 ", "");
        trama = trama.replace(">", "");
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        double carga = Integer.parseInt(trama,16);
        carga = carga/2.55;
        carga =  Math.round(carga);
        Log.i("TAG", "TRAMA  =  " + trama);
        Log.i("TAG", "Porcentaje de carga  =  " + carga);
        Log.i("TAG", "longitud TRAMA  =  " + trama.length());

        return carga;
    }

    public double relacionEstequiometrica() {
        //formateamos la respuesta//quitamos pid y cabeceras
        String trama = ("no data");
        trama = String.valueOf(io.enviar("0106"));
        trama = trama.replace("SEARCHING...", "");
        trama = trama.replace("0106", "");
        trama = trama.replace("4109", "");
        trama = trama.replace(">", "");
        trama = trama.replaceAll("\\s", "");
        trama = trama.replace("\n\r","");
        double mezcla= Integer.parseInt(trama,16);
        mezcla = (mezcla/1.28)-100;
        Log.i("TAG", "TRAMA  =  " + trama);
        Log.i("TAG", "relacionEstequiometrica  =  " + trama);
        Log.i("TAG", "longitud TRAMA  =  " + trama.length());
        return mezcla;
    }


}
