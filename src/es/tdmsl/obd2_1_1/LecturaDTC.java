package es.tdmsl.obd2_1_1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import es.tdmsl.Bluetooth.Bluetooth;
import es.tdmsl.IO.IO;

import java.util.List;

/**
 * Created by Manu on 11/07/2016.
 */
public class LecturaDTC extends Activity {
    private Bluetooth bluetooth;
    private IO io;
    private RespuestasPids respuestasPids;
    private TextView info;
    private TextView dtcs;
    private StringBuffer valorHex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectura_dtc);
        info = (TextView) findViewById(R.id.infoLectura);
        dtcs = (TextView) findViewById(R.id.tv_dtcs);
        info.setText(Main.info.getText());
        valorHex = new StringBuffer();

        bluetooth = Bluetooth.getInstancia(this);
        io = new IO(bluetooth.getSocket());
        respuestasPids = new RespuestasPids();

        int ndtcs = respuestasPids.Ndtcs();
        System.out.println(ndtcs);
        dtcs.append("Numero de DTCs = "+ndtcs+"\n");
        String error = respuestasPids.listaDtcs().toString();
        error = "0: 43 06 01 00 02 00" +
                "2: 00 00 00 00 00 00 00" +
                "43 01 01 01";
        StringBuffer codigoError=new StringBuffer();
        int incremento=4;
        for(int s=0;s<ndtcs;s++){
            System.out.println("incremento = "+incremento+"\n"+error.codePointAt(incremento));
            boolean codigo_encontrado=false;
            if(error.codePointAt(incremento)==48){//if = 0
                valorHex.append("P0");
            }else if(error.codePointAt(incremento)==49){//if =1
                valorHex.append("P1");
            }else if(error.codePointAt(incremento)==50){//if =2
                valorHex.append("P2");
            }else if(error.codePointAt(incremento)==51){//if =3
                valorHex.append("P3");
            }else if(error.codePointAt(incremento)==52){//if =4
                valorHex.append("C0");
            }else if(error.codePointAt(incremento)==53){//if =5
                valorHex.append("C1");
            }else if(error.codePointAt(incremento)==54){//if =6
                valorHex.append("C2");
            }else if(error.codePointAt(incremento)==55){//if =7
                valorHex.append("C3");
            }else if(error.codePointAt(incremento)==56){//if =8
                valorHex.append("B0");
            }else if(error.codePointAt(incremento)==57){//if =9
                valorHex.append("B1");
            }else if(error.codePointAt(incremento)==65){//if =A
                valorHex.append("B2");
            }else if(error.codePointAt(incremento)==66){//if =B
                valorHex.append("B3");
            }else if(error.codePointAt(incremento)==67){//if =C
                valorHex.append("U0");
            }else if(error.codePointAt(incremento)==68){//if =D
                valorHex.append("U1");
            }else if(error.codePointAt(incremento)==69){//if =E
                valorHex.append("U2");
            }else if(error.codePointAt(incremento)==70){//if =F
                valorHex.append("U3");
            }
            for(int p=incremento+1;p<(incremento+5);p++){//Introduce el resto denumeros del error
                if(error.codePointAt(p)!=32){// space
                    valorHex.append(error.charAt(p));
                }

            }
            incremento=incremento+6;//Coloca la posicion en la trama para el siguiente//error

             if(error.codePointAt(incremento+3)==10){//Si detecta salto de linea se
                // prepara la posicion para el siguiente error en la siguiente linea
                incremento=incremento+7;
            }
            String codError=valorHex.toString();
            String sCadena;
            //try {
            //fr = new FileReader("CodigosErrores.txt");
            //bf = new BufferedReader(fr);
                /*while ((sCadena = bf.readLine())!=null) {
                    if(codError.compareTo(sCadena.substring(0, 5))==0){//se compara el error leido con cada linea
                                                                       // del fichero, si lo encuentra se sale del bucle
                        codigoError.append(sCadena+"\n\n");
                        codigo_encontrado=true;
                        break;
                    }
                }*/
            codigoError.append(codError+"\n\n");
                /*if(codigo_encontrado==false){
                    codigoError.append("Unknown "+codError+" error\n\n");
                }*/
            //} catch (IOException e) {
            //    codigoError.append("Database error codes inaccessible");
            //}
            int longitudTrama=valorHex.length();//Se deja vacio el buffer que contiene el
//código procedente de la ECU
            valorHex.delete(0, longitudTrama);
        }


        dtcs.append("dtcs detectados\n"+codigoError.toString());

       /* B = trama.substring(2,4);
        B = Integer.toBinaryString(Integer.parseInt(B,16));
        B = completeDigits(B);
        B = B+" El significado de los tres bytes restantes está enterrado en la especificación SAE J1979.";
        dtcs.append(B+"\n");
        C = trama.substring(4,6);
        C = Integer.toBinaryString(Integer.parseInt(C,16));
        C = completeDigits(C);
        C = C+" El significado de los tres bytes restantes está enterrado en la especificación SAE J1979.";
        dtcs.append(C+"\n");
        D = trama.substring(6,8);
        D = Integer.toBinaryString(Integer.parseInt(D,16));
        D = completeDigits(D);
        D = D+" El significado de los tres bytes restantes está enterrado en la especificación SAE J1979.";
        dtcs.append(D+"\n");*/

    }


}