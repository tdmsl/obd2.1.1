package es.tdmsl.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import es.tdmsl.utils.Utilidades;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Manu on 11/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //private static String DB_PATH = "/data/data/es.tdmsl.obd2_1_1/databases/";
    //private static String DB_NAME = "OBD";
    public SQLiteDatabase myDataBase;
    private final Context myContext ;
    private  final String path;
    private final String name;

    //http://www.aprendeandroid.com/l5/sql4.htm
    Utilidades util = new Utilidades();
    public DatabaseHelper(Context context,String path ,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.myContext=context;
        this.path=path;
        this.name=name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No hacemos nada aqui
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // Si existe, no hacemos nada!
            //util.alert("Base deDatos ya rxiste",myContext);
        } else {
            // Llamando a este método se crea la base de datos vacía en la ruta
            // por defecto del sistema de nuestra aplicación por lo que
            // podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();

            try {
                util.alert("Copiando Base de Datos",myContext);
                copyDataBase();

            } catch (IOException e) {
                util.alert("Error copiando database",myContext);
                throw new Error("Error copiando database");

            }
        }
    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = path + getDatabaseName();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDataBase() throws IOException {
        OutputStream databaseOutputStream = new FileOutputStream("" + path + name);
        InputStream databaseInputStream;

        byte[] buffer = new byte[1024];
        int length;

        databaseInputStream = myContext.getAssets().open("OBD.sqlite");
        while ((length = databaseInputStream.read(buffer)) > 0) {
            databaseOutputStream.write(buffer);
        }

        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = path + name;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // Base de datos no creada todavia
        }

        if (checkDB != null) {

            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    public Cursor listar(String marca) {

        return myDataBase.rawQuery("SELECT * FROM "+marca+";", null);
        //String[] front = new String[]{"codigo","obd","descripcion","observaciones"};
        //return myDataBase.rawQuery(true, marca, front, null, null, null, null, null, null);
    }
}
