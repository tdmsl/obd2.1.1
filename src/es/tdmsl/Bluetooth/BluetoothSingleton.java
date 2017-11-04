package es.tdmsl.Bluetooth;

/**
 * Created by Manu on 11/10/2016.
 */
public class BluetoothSingleton {
    private static BluetoothSingleton ourInstance = new BluetoothSingleton();

    public static BluetoothSingleton getInstance() {
        return ourInstance;
    }

    private BluetoothSingleton() {
    }
}
