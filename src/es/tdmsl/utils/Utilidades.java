package es.tdmsl.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Manu on 11/07/2016.
 */
public class Utilidades {
    public void alert(String msg, Context context){
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(context);
        alertDialog.setMessage(msg);
        alertDialog.show();



    }

    public String completeDigits(String binNum) {
        for (int i = binNum.length(); i < 8; i++) {
            binNum = "0" + binNum;
        }
        return binNum;
    }

}
