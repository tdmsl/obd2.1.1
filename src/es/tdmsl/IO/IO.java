package es.tdmsl.IO;

import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import es.tdmsl.utils.DialogoAlerta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 06/08/2016.
 */
public class IO {
    private BluetoothSocket socket;
    private int numeroDePid;

    public IO(BluetoothSocket socket) {
        this.socket = socket;
    }

    public StringBuffer enviar(String cmd) {

        try {
            for (int i = 0; i < cmd.length(); i++) {
                socket.getOutputStream().write(cmd.codePointAt(i));//Se escribe en el Puerto serie
                if ((i + 1) == cmd.length()) {
                    socket.getOutputStream().write(13);//Solamente necesita el retorno de carro,sin el salto de linea.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recibir(cmd);
    }

    private StringBuffer recibir(String cmd) {
        StringBuffer respuesta = new StringBuffer();
        String trama = ("no data");
        int caracter = 0;
        try {
            while ((char) caracter != (char) '>') {
                caracter = socket.getInputStream().read();//Se lee el Puerto serie
                respuesta.append((char) caracter);
            }
            // Log.i(this.toString(), "respuesta a " + cmd);
            // Log.i(this.toString(), "respuesta es igual a " + respuesta.toString());//esto no funciona por agun motivo
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
        }
        return respuesta;
    }

    public List pidsDisponibles() {
        StringBuffer tramaBinaria = new StringBuffer();
        List<String> listaPidsSoportados = new ArrayList<String>();
        String trama = ("no data");
        Map<String, String> tablaHexBin = new HashMap<String, String>();
        tablaHexBin.put("0", "0000");
        tablaHexBin.put("1", "0001");
        tablaHexBin.put("2", "0010");
        tablaHexBin.put("3", "0011");
        tablaHexBin.put("4", "0100");
        tablaHexBin.put("5", "0101");
        tablaHexBin.put("6", "0110");
        tablaHexBin.put("7", "0111");
        tablaHexBin.put("8", "1000");
        tablaHexBin.put("9", "1001");
        tablaHexBin.put("A", "1010");
        tablaHexBin.put("B", "1011");
        tablaHexBin.put("C", "1100");
        tablaHexBin.put("D", "1101");
        tablaHexBin.put("E", "1110");
        tablaHexBin.put("F", "1111");

        StringBuffer respuesta = enviar("0100");// muestra pid disponibles

        // if (respuesta.)


        //formateamos la respuesta//quitamos pid y cabeceras
        trama = respuesta.toString();
        trama = trama.replaceAll("\\s", "");

        if (trama.contains("UNABLETOCONNECT")){
            Log.i(this.toString(),"respusta recibida : " + trama);
            trama = "";
        }else {


            trama = trama.replace("SEARCHING...", "");
            trama = trama.replace("0100", "");
            trama = trama.replace("41 00 ", "");
            //trama = trama.replace("1:", "");
            //trama = trama.replace("2:", "");
            trama = trama.replace(">", "");
            Log.i("TAG", "TRAMA  =  " + trama);
            /*if (!trama.isEmpty()){

            }*/
            try {
                trama = trama.substring(0, 8);
            }catch (Exception e){
                System.out.println("error "+e);
            }

            //////////////////////////////
            //trama= "BE1FA813";
            //trama= "B";
            //////////////////////////////
            //trama = trama.replace("\n\r","");
            Log.i("TAG", "TRAMA  =  " + trama);
            Log.i("TAG", "longitud TRAMA  =  " + trama.length());
        }

        int x = 0;
        //se pasa de hex a bin
        for (int l = 1; l <= trama.length(); l++) {
            tramaBinaria.append(tablaHexBin.get(trama.substring(x, x + 1)));
            x++;

        }


        Log.i("TAG", "trama binaria  " + tramaBinaria);
        Log.i("TAG", "trama binaria  " + "10111110000111111010100000010011");
        Log.i("TAG", "longitud trama binaria  " + tramaBinaria.length());
        listaPidsSoportados.add("Listado Pids disponibles");
        //primeros 32 digitos
        numeroDePid = 1;
        try {
            for (int i = 1; i <= 32; i++) {

                if (tramaBinaria.charAt(i - 1) == '1') {
                    Log.i("TAG", numeroDePid + " = " + String.format("%02x", numeroDePid).toUpperCase());
                    //se pasa a hexadecimal y se añade al array "listaPidsSoportados"
                    listaPidsSoportados.add(String.format("%02x", numeroDePid).toUpperCase());
                }
                numeroDePid++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            Log.i("TAG", "error en el 1º tramo" + e);


        }
        try {
            if (tramaBinaria.length() > 32) {
                numeroDePid = 1;
                for (int i = 33; i <= 64; i++) {

                    if (tramaBinaria.charAt(i - 1) == '1') {
                        listaPidsSoportados.add(String.format("%02x", numeroDePid).toUpperCase());
                    }
                    numeroDePid++;
                }
            }

        } catch (StringIndexOutOfBoundsException e) {
            Log.i("TAG", "error en el 2º tramo" + e);

        }
        try {
            if (tramaBinaria.length() > 64) {
                numeroDePid = 1;
                for (int i = 65; i < 96; i++) {

                    if (tramaBinaria.charAt(i - 1) == '1') {
                        listaPidsSoportados.add(String.format("%02x", numeroDePid).toUpperCase());
                    }
                    numeroDePid++;
                }
            }

        } catch (StringIndexOutOfBoundsException e) {
            Log.i("TAG", "error en el 3º tramo" + e);

        }


        Log.i("TAG", "pid soportados " + listaPidsSoportados);
        Log.i("TAG", "numero de PID  " + listaPidsSoportados.size());

        Log.i("TAG", "--------------------------------fin IO---------------------------------------------------");

        return listaPidsSoportados;


    }


}

