package es.tdmsl.Bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.*;
import android.util.Log;
import android.widget.TextView;
import es.tdmsl.utils.DialogoAlerta;
import es.tdmsl.obd2_1_1.Main;
import es.tdmsl.IO.*;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Manu on 08/08/2016.
 */
public class Bluetooth {

    private ProgressDialog progress;
    public BluetoothSocket socket;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice dispositivo;
    private Activity activity;
    private IO io;
    public boolean OBDII ;
    private TextView info;

    private static Bluetooth instancia;// = new Bluetooth()

    public static Bluetooth getInstancia(Activity activity) {
        if (instancia == null){
            instancia =  new Bluetooth(activity);
        }
        return instancia;
    }


    public Bluetooth(Activity activity) {
        this.activity = activity;
        // Obtenemos el adaptador Bluetooth.
        // Si es NULL, significara que el dispositivo no posee Bluetooth,
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.i("TAG", "Este dispositivo no posee bluetooth");
        }
        if (!bluetoothAdapter.isEnabled()) {
            Log.i("TAG", "Bluetooth desactivado");
            new AlertDialog.Builder(activity)
                    .setTitle("Alert")
                    .setMessage("Bluetooth desactivado")
                    .setPositiveButton("activar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                            activity.startActivity(discoverableIntent);
                        }
                    })
                    .setNegativeButton("activar manualmente", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
           //  progress.dismiss();

        }
        if (bluetoothAdapter.isEnabled()) {
            Log.i("TAG", "Bluetooth activado");
            OBDII = false;
            registrarEventosBluetooth();
            bluetoothAdapter.startDiscovery();
            Log.i("TAG", "bluetoothAdapter iniciando busqueda de dispositivos");
            progress = new ProgressDialog(activity);
            progress.setMessage("Buscando interface ELM327 ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

    }

    public boolean isOBDII() {
        return OBDII;
    }

    // Instanciamos un BroadcastReceiver que se encargara de detectar si el estado
    // del Bluetooth del dispositivo ha cambiado mediante su handler onReceive
    public final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // Filtramos por la accion. Nos interesa detectar BluetoothAdapter.ACTION_STATE_CHANGED
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                // Solicitamos la informacion extra del intent etiquetada como BluetoothAdapter.EXTRA_STATE
                // El segundo parametro indicara el valor por defecto que se obtendra si el dato extra no existe
                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Log.i("TAG", "estoi dentro del broadcastReceiber");
                switch (estado) {
                    // Apagado
                    case BluetoothAdapter.STATE_OFF: {
                        Log.i("TAG", "Bluetoothadapter.STATE_OFF");
                        if (activity != null) {

                            new AlertDialog.Builder(activity)
                                    .setTitle("Alert")
                                    .setMessage("Bluetooth STATE_OFF")
                                    .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        break;
                    }
                    case BluetoothAdapter.STATE_ON: {
                        Log.i("TAG", "Bluetoothadapter.STATE_ON");
                        if (activity != null) {
                            new AlertDialog.Builder(activity)
                                    .setTitle("Alert")
                                    .setMessage("Bluetooth activado")
                                    .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        break;
                    }//final case STATE_ON
                }//FINAL switch
            }//final ESTATE_CHANGED**********************************************************************
            // Cada vez que se descubra un nuevo dispositivo por Bluetooth, se ejecutara
            // este fragmento de codigo
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Acciones a realizar al descubrir un nuevo dispositivo
                dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("TAG", "encontrado " + dispositivo.getName());
                if (activity != null) {
                    new DialogoAlerta("Encontrado dispositivo " + dispositivo.getName(),activity);
                }
                if ("OBDII".equals(dispositivo.getName())) {
                    OBDII=true;
                    new DialogoAlerta("Encontrado dispositivo " + dispositivo.getName(),activity);
                    // Iniciamos la conexion
                    conectar();//Log.i("TAG","conectar");
                    inizializar();//Log.i("TAG","inizializar");
                    Log.i(this.toString(),"OBD = "+OBDII);
                    progress.dismiss();

                }//final si OBD|| se ha encontrado
            }//final ACTION_FOUND*********************************************************************
            // Codigo que se ejecutara cuando el Bluetooth finalice la busqueda de dispositivos.
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (OBDII == false) {

                    if (activity != null) {
                        new AlertDialog.Builder(context)
                                .setTitle("Alert")
                                .setMessage("No se ha encontrado ningun interface\n ELM 327")
                                .setPositiveButton("reintentar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        bluetoothAdapter.startDiscovery();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }//final OBDII == false
            }//final ACTION_DISCOVERY_FINISHED

        }//final onReceive******************************************************************************

        private void conectar() {

            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService
            try {
                socket = dispositivo.createInsecureRfcommSocketToServiceRecord(uuid);
                socket.connect();
                io = new IO(socket);
                //alert("conectando con "+socket.getRemoteDevice().getName());
                Log.i("TAG", "conectando con " + socket.getRemoteDevice().getName());
                Main.info.append("\nEncontrado dispositivo " + dispositivo.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//final metodo interni conectar

    };//final BroadcastReceiver

    private void inizializar() {
        Main.info.append("\nconectado con " +  io.enviar("ATZ").toString().replace("ATZ",""));
        Main.info.append("\nprotocolo "+io.enviar("ATDP").toString().replace("ATDP",""));
        io.enviar("ATSP0");
/*        ArrayList pidsDisponibles = (ArrayList) io.pidsDisponibles();
        Resources res = activity.getResources();
        String descripcionesPids[] = res.getStringArray(R.array.pids);
        //Log.i("TAG","pidsDisponibles.size() = "+ String.valueOf(pidsDisponibles.size()));

        //añadimos una descripcion al Nº de pid guardada en (R.array.pids)
        for (int i = 0; i < pidsDisponibles.size(); i++) {
            try {
                if (pidsDisponibles.get(i).toString().equals(descripcionesPids[i].split(",")[0])) {
                    pidsDisponibles.set(i, pidsDisponibles.get(i) + "," + descripcionesPids[i].split(",")[1]);
                }

                Log.i("TAG", "Pids disponibles   " + pidsDisponibles.get(i).toString());

            } catch (IndexOutOfBoundsException e) {

            }
        }*/
        OBDII = true;


    }

    private void registrarEventosBluetooth() {
        // Registramos los BroadcastReceiver que instanciamos previamente para
        // detectar los distintos eventos que queremos recibir
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filtro.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filtro.addAction(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(bluetoothReceiver, filtro);
    }


    public void cerrar() {
        activity.unregisterReceiver(bluetoothReceiver);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "onDestroy");
    }



    public ProgressDialog getProgress() {
        return progress;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public BluetoothDevice getDispositivo() {
        return dispositivo;
    }

    public Activity getActivity() {
        return activity;
    }


    public IO getIo() {
        return io;
    }

    public static Bluetooth getInstancia() {
        return instancia;
    }

    public BroadcastReceiver getBluetoothReceiver() {
        return bluetoothReceiver;
    }
}




