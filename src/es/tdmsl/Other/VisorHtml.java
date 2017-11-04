package es.tdmsl.Other;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import es.tdmsl.obd2_1_1.R;

/**
 * Created by Manu on 18/10/2016.
 */
public class VisorHtml extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visor_html);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/CodigosDTC.html");
    }
}