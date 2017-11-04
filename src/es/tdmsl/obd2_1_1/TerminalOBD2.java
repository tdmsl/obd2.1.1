package es.tdmsl.obd2_1_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import es.tdmsl.Bluetooth.Bluetooth;
import es.tdmsl.IO.IO;
import es.tdmsl.utils.DialogoAlerta;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Manu on 11/07/2016.
 */
public class TerminalOBD2 extends Activity {
    //public BluetoothSocket socket;
    private BluetoothAdapter btAdapter;
    private BluetoothDevice dispositivo;
    private ProgressDialog progress;
    private TextView info;
    private TextView tv_respuesta ;
    private TextView et_CMD;
    private Message msg = new Message();
    private Spinner  spinnerCMD;
    private Spinner comboListPids;
    private IO io;
    private Bluetooth bluetooth;

   /* private Handler handlerProgreso = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //info.setText(info.getText()+"\n"+msg.obj.toString());
            info.append("\n" + msg.obj.toString());
            //Toast.makeText(getBaseContext(), "Estoy en el HandleMessage"+msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private Handler handlerRespuesta = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //tv_respuesta.setText(msg.obj.toString());
            tv_respuesta.append("\n" + msg.obj.toString());

        }
    };

    private Handler handlerAlert = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(TerminalOBD2.this);
            alertDialog.setMessage(msg.obj.toString());
            alertDialog.show();
        }
    };


    private Handler handlerProgresBar = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //Toast.makeText(TerminalOBD2.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            progress = new ProgressDialog(TerminalOBD2.this);
            progress.setMessage(msg.obj.toString());
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();


        }
    };*/

   /* Thread conexion = new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            handlerAlert.sendMessage(msg);
            //BluetoothSocket socket = myDispositivo.createInsecureRfcommSocketToServiceRecord(uuid);
            //tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        }
    });*/




   /* public final ThreadLocal<BroadcastReceiver> bluetoothReceiver = new ThreadLocal<BroadcastReceiver>() {

        @Override
        protected BroadcastReceiver initialValue() {
            return new BroadcastReceiver() {
                boolean OBDII;


                @Override
                public void onReceive(final Context context, Intent intent) {
                    final String action = intent.getAction();
                    final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

// Filtramos por la accion. Nos interesa detectar BluetoothAdapter.ACTION_STATE_CHANGED
                    if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                        // Solicitamos la informacion extra del intent etiquetada como BluetoothAdapter.EXTRA_STATE
// El segundo parametro indicara el valor por defecto que se obtendra si el dato extra no existe
                        final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                                BluetoothAdapter.ERROR);

                        switch (estado) {
                            // Apagado
                            case BluetoothAdapter.STATE_OFF: {
                                //((Button)findViewById(R.id.btnBluetooth)).setText(R.string.ActivarBluetooth);
                                //TextView info = (TextView) findViewById(R.id.info);
                                *//*Message msg = new Message();
                                msg.obj = "conectando con un Thread y su Hanler case BluetoothAdapter.STATE_OFF:";
                                handlerProgreso.sendMessage(msg);*//*
                                info.append(" \nBluetoothadapter.STATE_OFF:");
                                break;
                            }

                            // Encendido
                            case BluetoothAdapter.STATE_ON: {
                                //((Button)findViewById(R.id.btnBluetooth)).setText(R.string.DesactivarBluetooth);
                                //TextView info = (TextView) findViewById(R.id.info);
                                //info.setText("Bluetooth Activado");

                                *//*Message msg = new Message();
                                msg.obj = "conectando con un Thread y su Hanler case BluetoothAdapter.STATE_ON:";
                                handlerProgresBar.sendMessage(msg);*//*
                                info.append("\n BluetoothAdapter.STATE_ON:");
                                //conexion.start();
                       *//* // Lanzamos un Intent de solicitud de visibilidad Bluetooth, al que añadimos un par
                        // clave-valor que indicara la duracion de este estado, en este caso 120 segundos
                        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                        startActivity(discoverableIntent);*//*

                        *//*if(bAdapter.isEnabled())
                            bAdapter.disable();
                        else
                        {
                            // Lanzamos el Intent que mostrara la interfaz de activacion del
                            // Bluetooth. La respuesta de este Intent se manejara en el metodo
                            // onActivityResult
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        }*//*


                                break;
                            }
                            default:
                                break;
                        }

                    }
                    // Cada vez que se descubra un nuevo dispositivo por Bluetooth, se ejecutara
                    // este fragmento de codigo
                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        // Acciones a realizar al descubrir un nuevo dispositivo
                        OBDII = false;
                        dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        Log.i("TAG","encontrado "+dispositivo.getName());
                        alert("encontrado "+dispositivo.getName());
                        if ("OBDII".equals(dispositivo.getName())) {
                            // Finalizamos la busqueda de dispositivos
                            btAdapter.cancelDiscovery();
                            progress.dismiss();
                            OBDII = true;
                            Toast.makeText(TerminalOBD2.this, "encontrado dispositivo "+dispositivo.getName(), Toast.LENGTH_LONG).show();
                            info.append("\nencontrado dispositivo   " + dispositivo.getName());//OBD||

                            // Iniciamos la conexion
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    conectar();
                                    Handler handler1 = new Handler();
                                    handler1.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            inizializar();
                                            info.append("\nprotocolo "+io.enviar("ATDP").toString().replace("ATDP",""));
                                            tv_respuesta.append("Listado Pids disponibles\n\n"+io.pidsDisponibles().toString().replace("Listado Pids disponibles","")+"\n\n");
                                        }
                                    },1000);

                                }
                            },1000);



                           // progress.dismiss();

                            //conectar.start();
                            //RespuestasPids respuestasPids = new RespuestasPids();
                            //IO io = new IO(socket);

                        }


                    }
                    // Codigo que se ejecutara cuando el Bluetooth finalice la busqueda de dispositivos.
                    else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                        // Acciones a realizar al finalizar el proceso de descubrimiento
                       // progress.dismiss();
                        // BluetoothDevice dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        if (OBDII == false)
                            new AlertDialog.Builder(context)
                                    .setTitle("Alert")
                                    .setMessage("No se ha encontrado ningun interface\n ELM 327")
                                    .setPositiveButton("reintentar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //btAdapter.disable();
                                            //btAdapter.enable();
                                            //btAdapter = BluetoothAdapter.getDefaultAdapter();
                                            btAdapter.startDiscovery();
                                            progress = new ProgressDialog(context);
                                            progress.setMessage("Buscando interface ELM327 ");
                                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progress.setIndeterminate(true);
                                            progress.show();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        //Toast.makeText(Main.this, "ACTION_DISCOVERY_FINISHED", Toast.LENGTH_SHORT).show();

                    }

                }
            };
        }
    };*/

    private void alert(String s) {
        new DialogoAlerta(s,this);
    }
   /* private void alert(String message, Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }*/


   /* private void inizializar()throws IndexOutOfBoundsException {
        alert("Conectando con\n "+io.enviar("ATZ").toString().replace("ATZ",""));
        info.append("\nconectado con " +  io.enviar("ATZ").toString().replace("ATZ",""));
        io.enviar("ATSP0");
        //info.append("\nprotocolo "+io.enviar("ATDP").toString().replace("ATDP",""));
        //cargamos el listener del comboListPids
        ArrayList pidsDisponibles = (ArrayList) io.pidsDisponibles();
        Resources res = this.getResources();
        String descripcionesPids[] = res.getStringArray(R.array.pids);
        Log.i("TAG","pidsDisponibles.size() = "+ String.valueOf(pidsDisponibles.size()));

        //añadimos una descripcion al Nº de pid guardada en (R.array.pids)
        for (int i = 0; i<pidsDisponibles.size() ; i++) {
            try {
                if (pidsDisponibles.get(i).toString().equals(descripcionesPids[i].split(",")[0])){
                    pidsDisponibles.set(i,pidsDisponibles.get(i)+","+descripcionesPids[i].split(",")[1]);
                    Log.i("TAG","modificao pid "+pidsDisponibles.get(i)+" por ");
                }
                Log.i("TAG",String.valueOf(i));
                Log.i("TAG","los dos elementos   "+pidsDisponibles.get(i).toString()+
                        " "+descripcionesPids[i].split(",")[0]+
                        ","+descripcionesPids[i].split(",")[1]);
                Log.i("TAG","descripcion  "+descripcionesPids[i].split(",")[1]);
            }catch (IndexOutOfBoundsException e){
                //alert(e.getMessage());
                alert("Error cargando la descripcion de los Pids\n"+e.getMessage());
            }


        }
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,pidsDisponibles);

        comboListPids.setAdapter(adapter);

    }*/


  /*  private void conectar() {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService
        try {
            socket = dispositivo.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            io = new IO(socket);
            alert("conectando con "+socket.getRemoteDevice().getName());





        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    /*Thread conectar = new Thread(new Runnable() {
        @Override
        public void run() {

            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService
            try {
                socket = dispositivo.createInsecureRfcommSocketToServiceRecord(uuid);
                socket.connect();
                Message msg = new Message();
                msg.obj = "conectado con " + socket.getRemoteDevice().getName();//OBD||
                handlerProgreso.sendMessage(msg);

                //progress.dismiss();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    });*/

    private long timer() {
        Date temps2 = new Date();
        return temps2.getTime();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_obd2);
        bluetooth = Bluetooth.getInstancia(null);
        info = (TextView) findViewById(R.id.infoTerminal);
        tv_respuesta = (TextView) findViewById(R.id.tv_respuesta);
        et_CMD = (EditText) findViewById(R.id.etCMD);
        Button enviarCMD = (Button) findViewById(R.id.btnEnviarCMD);
        io = new IO(bluetooth.getSocket());

        info.setText(Main.info.getText());
        spinnerCMD = (Spinner) findViewById(R.id.comboCMD);
        comboListPids = (Spinner) findViewById(R.id.comboListPids);
        //cargamos el spinerCMD
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,comandos );
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.comandos, android.R.layout.simple_spinner_item);
        spinnerCMD.setAdapter(adapter);

        //implementamos el listener del boton
        //esta implementado desde el fichero XML

        //implementamos el listener del spinnerCMD
        spinnerCMD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                    String[] comando = spinnerCMD.getItemAtPosition(position).toString().split(",");
                    tv_respuesta.append(io.enviar(comando[0])+"\n\n");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        //implementamos el listener del comboListPids
        comboListPids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ( position!=0) {
                    String pid[] = comboListPids.getItemAtPosition(position).toString().split(",");
                    tv_respuesta.append(io.enviar("01"+pid[0])+"\n\n");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            tv_respuesta.append(listarPidsdisponibles().toString());


    }

    private ArrayList listarPidsdisponibles() {
        //cargamos el listener del comboListPids
        ArrayList pidsDisponibles = (ArrayList)io.pidsDisponibles();
        Resources res = this.getResources();
        String descripcionesPids[] = res.getStringArray(R.array.pids);
        Log.i("TAG","pidsDisponibles.size() = "+ String.valueOf(pidsDisponibles.size()));

        //añadimos una descripcion al Nº de pid guardada en (R.array.pids)
        for (int i = 0; i<pidsDisponibles.size() ; i++) {
            try {
                if (pidsDisponibles.get(i).toString().equals(descripcionesPids[i].split(",")[0])){
                    pidsDisponibles.set(i,pidsDisponibles.get(i)+","+descripcionesPids[i].split(",")[1]);
                    pidsDisponibles .set(i,pidsDisponibles.get(i)+"\n");
                    Log.i("TAG","modificao pid "+pidsDisponibles.get(i)+" por ");
                }
                Log.i("TAG",String.valueOf(i));
                Log.i("TAG","los dos elementos   "+pidsDisponibles.get(i).toString()+
                        " "+descripcionesPids[i].split(",")[0]+
                        ","+descripcionesPids[i].split(",")[1]);
                Log.i("TAG","descripcion  "+descripcionesPids[i].split(",")[1]);
            }catch (IndexOutOfBoundsException e){
                //alert(e.getMessage());
                alert("Error cargando la descripcion de los Pids\n"+e.getMessage());
            }


        }
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,pidsDisponibles);

        comboListPids.setAdapter(adapter);

        return pidsDisponibles;
    }


    // Ademas de realizar la destruccion de la actividad, eliminamos el registro del
// BroadcastReceiver y cerramos el socket.
   // @Override
    /*public void onDestroy() {
        super.onDestroy();
        //this.unregisterReceiver(bluetoothReceiver.get());
        try {
           // socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG","onDestroy");

    }*/

    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public void enviarCMD(View view){
        tv_respuesta.append(io.enviar(et_CMD.getText().toString().toUpperCase())+"\n\n");


    }
}