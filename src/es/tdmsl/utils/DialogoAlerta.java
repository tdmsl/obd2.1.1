package es.tdmsl.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import es.tdmsl.obd2_1_1.R;

/**
 * Created by Manu on 01/10/2016.
 */
public class DialogoAlerta  {
    String mesage;
    Activity activity;
    AlertDialog instancia ;

    public DialogoAlerta(String mesage, Activity activity) {
        /*this.mesage = mesage;
        this.activity = activity;*/

        if (instancia!=null){
            instancia.dismiss();
            instancia.cancel();

        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage(mesage);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                instancia.dismiss();
            }
        });
        instancia= alertDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                instancia.dismiss();
            }
        },6000);

    }






}
