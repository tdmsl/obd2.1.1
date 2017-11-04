package es.tdmsl.obd2_1_1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import es.tdmsl.Bluetooth.Bluetooth;

/**
 * Created by Manu on 11/07/2016.
 */
public class LecturaParametros extends Activity {
    TextView info;
    Bluetooth bluetooth;
    Button button_rpm;
    Button butoon_tmpRefri;
    Button butoon_kmh;
    Button butoon_vadd;
    Button butoon_co2;
    TextView textView_rpm;
    RespuestasPids respuestasPids;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectura_parametros);
        info = (TextView) findViewById(R.id.infoLectura);
        button_rpm = (Button) findViewById(R.id.button_rpm);
        butoon_tmpRefri = (Button) findViewById(R.id.button_tmpRefri);
        butoon_kmh = (Button) findViewById(R.id.button_kmh);
        butoon_vadd = (Button) findViewById(R.id.button_vadd);
        butoon_co2 = (Button) findViewById(R.id.button_co2);

        info.setText(Main.info.getText());
        bluetooth =Bluetooth.getInstancia(null);
        //ponemos en marcga los indicadores

   /* Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
           while (true){
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }




           }

        }
    });*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    actualizaRpm();
                }


            }
        }).start();

         new Thread(new Runnable() {
             @Override
             public void run() {
                 while (true){
                     try {
                         Thread.sleep(6000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     actualizaTemp();

                 }


             }
         }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    actualizaKmh();

                }


            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    actualizaVadd();
                }


            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    actualizaCo2();

                }

            }
        }).start();
    }

    private void actualizaCo2() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String co2;
                co2 = new RespuestasPids().co2();
                butoon_co2.setText(co2);

            }
        });
    }

    private void actualizaVadd() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String vadd;
                vadd = new RespuestasPids().vadd();
                butoon_vadd.setText(vadd);

            }
        });
    }

    private void actualizaKmh() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String kmh;
                kmh = new RespuestasPids().kmh();
                butoon_kmh.setText(kmh);

            }
        });
    }

    private void actualizaTemp() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String tmpRef;
                tmpRef = new RespuestasPids().temperaturaRefrigerante();
                butoon_tmpRefri.setText(tmpRef);

            }
        });


    }

    private void actualizaRpm() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String rpm = "valor";
                rpm = new RespuestasPids().rpm();
                button_rpm.setText(rpm);

            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

//////////////////////////////////////////////////////////////
/* new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (bluetooth.isOBDII()){
                       // Log.i(this.toString(),"verificando si se ha encontrado \n la interface OBD "+bluetooth.isOBDII());
                       ////////////////////////////////////////////
                        button_rpm.post(new Runnable() {
                            @Override
                            public void run() {
                                String rpm ="valor";
                                rpm = new RespuestasPids().rpm();
                                button_rpm.setText(rpm);

                            }
                        });

                        //////////////////////////////////////////

                        butoon_tmpRefri.post(new Runnable() {
                            @Override
                            public void run() {
                                String tmpRef;
                                tmpRef = new RespuestasPids().temperaturaRefrigerante();
                                butoon_tmpRefri.setText(tmpRef);
                            }
                        });

                        butoon_kmh.post(new Runnable() {
                            @Override
                            public void run() {
                                String kmh;
                                kmh = new RespuestasPids().kmh();
                                butoon_kmh.setText(kmh);

                            }
                        });

                        butoon_vadd.post(new Runnable() {
                            @Override
                            public void run() {
                                String vadd;
                                vadd = new RespuestasPids().vadd();
                                butoon_vadd.setText(vadd);

                            }
                        });

                        butoon_co2.post(new Runnable() {
                            @Override
                            public void run() {
                                String co2;
                                co2 = new RespuestasPids().co2();
                                butoon_co2.setText(co2);

                            }
                        });


                    }//fin if

                }

            }
        }).start();*/