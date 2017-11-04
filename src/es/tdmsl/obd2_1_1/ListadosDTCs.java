package es.tdmsl.obd2_1_1;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import es.tdmsl.DAO.DatabaseHelper;
import es.tdmsl.utils.Utilidades;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Manu on 11/07/2016.
 */
public class ListadosDTCs extends Activity {
    Utilidades util = new Utilidades();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listados_dtcs);
        String marca = getIntent().getExtras().getString("marca");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.append(marca);
        //util.alert(marca,this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this,"/data/data/es.tdmsl.obd2_1_1/databases/" ,"OBD", null, 1);
        //SQLiteDatabase db = databaseHelper.getReadableDatabase();
        try {
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(databaseHelper.myDataBase != null){
            //util.alert(marca,this);
            Cursor cursor=databaseHelper.listar(marca);
            String[] front = new String[]{"codigo","obd","descripcion","observaciones"};
            int[] to = new int[]{R.id.lvCodigo,R.id.lvObd,R.id.lvDescripcion,R.id.lvObservaciones};
            ListView listView = (ListView) findViewById(R.id.listView);
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.lista_item,cursor,front,to,0);
            listView.setAdapter(simpleCursorAdapter);

        }


    }

}