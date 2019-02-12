package com.example.danyal.bluetoothhc05;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.io.IOException;
import java.util.UUID;

public class ledControl extends AppCompatActivity {

    boolean isBtnLongPressed = false;
    String sendData = null;
    WebView web_view;
    Button btn_forward, btn_back, btn_left, btn_right,
            btn_rise, btn_drop, btn_left_hand, btn_right_hand,
            btn_rise_1, btn_drop_1,
            btn_rise_2, btn_drop_2,
            btn_open, btn_close,
            btn_stand_by,
            btn_dis;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ConnectBT().execute();

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        setContentView(R.layout.activity_led_control);
        web_view = (WebView) findViewById(R.id.monitor);
        btn_forward = (Button) findViewById(R.id.forward);
        btn_back = (Button) findViewById(R.id.back);
        btn_left = (Button) findViewById(R.id.left);
        btn_right = (Button) findViewById(R.id.right);
        btn_rise = (Button) findViewById(R.id.rise);
        btn_drop = (Button) findViewById(R.id.drop);
        btn_left_hand = (Button) findViewById(R.id.leftHanded);
        btn_right_hand = (Button) findViewById(R.id.rightHanded);
        btn_rise_1 = (Button) findViewById(R.id.rise1);
        btn_drop_1 = (Button) findViewById(R.id.drop1);
        btn_rise_2 = (Button) findViewById(R.id.rise2);
        btn_drop_2 = (Button) findViewById(R.id.drop2);
        btn_open = (Button) findViewById(R.id.open);
        btn_close = (Button) findViewById(R.id.close);
        btn_stand_by = (Button) findViewById(R.id.standBy);
        btn_dis = (Button) findViewById(R.id.dissconnect);

        web_view.setWebViewClient(new myWebViewClient());
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setUseWideViewPort(true);
        web_view.getSettings().setLoadWithOverviewMode(true);
        web_view.getSettings().setSupportZoom(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.loadUrl("http://140.128.86.102:81/index.htm");

        new Thread (new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Log.e("hihi",Boolean.toString(isBtnLongPressed));
                        if (isBtnLongPressed) {
                            sendSignal();
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        btn_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("F", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return  false;
            }
        });

        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("B", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("L", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("R", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_rise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("a", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_drop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("b", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_left_hand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("c", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_right_hand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("d", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_rise_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("g", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_drop_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("e", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });;

        btn_rise_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("h", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_drop_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("f", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_open.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("i", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("j", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_stand_by.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setSendData("k", true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setSendData(null, false);
                }
                return false;
            }
        });

        btn_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Disconnect();
            }
        });

    }

    private class touchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            sendData = null;
            isBtnLongPressed = true;
            return false;
        }
    }

    public void setSendData(String signal, boolean isBtConnected){
        isBtnLongPressed = isBtConnected;
        sendData = signal;
    }

    @Override
    public void onBackPressed() {
        Disconnect();
    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void sendSignal () {
        if ( btSocket != null ) {
            try {
                if (sendData != null){
                    btSocket.getOutputStream().write(sendData.toString().getBytes());
                }
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void Disconnect () {
        if ( btSocket!=null ) {
            try {
                msg("斷開連接");
                btSocket.close();
            } catch(IOException e) {
                msg("斷開錯誤");
            }
        }

        finish();
    }

    private void msg (String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute () {
            progress = ProgressDialog.show(ledControl.this, "連接中...", "請燒等");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isBtConnected ) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("連接失敗 請重新連接");
                finish();
            } else {
                msg("連接成功");
                isBtConnected = true;
            }

            progress.dismiss();
        }
    }
}
