

package es.tdmsl.obd2_1_1;
        import android.app.*;
        import android.app.Activity;
        import android.app.AlertDialog.Builder;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.opengl.EGLExt;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;
        import es.tdmsl.Bluetooth.Bluetooth;
        import es.tdmsl.Other.CamaraIP;
        import es.tdmsl.Other.VisorHtml;
        import es.tdmsl.utils.DialogoAlerta;

        import java.io.IOException;

public class Main extends Activity
{
    private ArrayAdapter adapterSpinner;
    private Spinner spinner;
    public static TextView info;
    AlertDialog instancia = null;
    private Bluetooth bluetoot;

    private void cargoSpinner()
    {
        //this.adapterSpinner = ArrayAdapter.createFromResource(this, R.array.ListadosDTC, R.layout.spinner_item);
        this.adapterSpinner = ArrayAdapter.createFromResource(this, R.array.ListadosDTC,R.layout.spinner_item);
        this.adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(this.adapterSpinner);
    }



    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        //new DialogoAlerta("mensaje",this);
        setContentView(R.layout.main);

        this.spinner = ((Spinner)findViewById(R.id.spinner));
        info = (TextView) findViewById(R.id.info);
        Button bLecturaDTC = (Button)findViewById(R.id.bLecturaDTC);
        Button bLecturaParametros = (Button)findViewById(R.id.bLecturaParametros);
        Button bTerminalOBD    = (Button)findViewById(R.id.bTerminalOBD);
        cargoSpinner();
        this.spinner.setSelection(8);
        //Intent localIntent = new Intent(Main.this.getApplicationContext(), TerminalOBD2.class);//llamamos directamente al evento
        //Main.this.startActivity(localIntent);//llamamos directamente al evento
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
                if (Main.this.spinner.getSelectedItemPosition() != -1 + Main.this.spinner.getCount())
                {
                    String str = Main.this.spinner.getItemAtPosition(Main.this.spinner.getSelectedItemPosition()).toString().replace(" ","");
                    Intent localIntent = new Intent(Main.this.getApplicationContext(), ListadosDTCs.class);
                    localIntent.putExtra("marca", str);
                    Main.this.startActivity(localIntent);
                }
            }

            public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
            {
            }
        });
        bLecturaDTC.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent(Main.this.getApplicationContext(), LecturaDTC.class);
                Main.this.startActivity(localIntent);
            }
        });
        bLecturaParametros.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent(Main.this.getApplicationContext(), LecturaParametros.class);
                Main.this.startActivity(localIntent);
            }
        });
        bTerminalOBD.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent(Main.this.getApplicationContext(), TerminalOBD2.class);
                Main.this.startActivity(localIntent);
            }
        });

        bluetoot = Bluetooth.getInstancia(this);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.menu,paramMenu);
        //return super.onCreateOptionsMenu(paramMenu);
        return true;

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent;
        switch ((String)item.getTitle()){
            case "camara IP":
                Log.i(this.toString(), (String) item.getTitle());
                intent = new Intent(this, CamaraIP.class);
                startActivity(intent);
                return true;


            case "OBD|| en HTML":
                Log.i(this.toString(), (String) item.getTitle());
                intent = new Intent(this,VisorHtml.class);
                try {
                    intent.putExtra("url",getAssets().openFd("CodigosDTC.html"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                return true;


        }

        return false;
    }

    public void webTDM(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.tdmtallerdemotos.com/"));
        startActivity(intent);
    }
}