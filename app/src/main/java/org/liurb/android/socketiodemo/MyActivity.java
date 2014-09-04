package org.liurb.android.socketiodemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class MyActivity extends Activity {

    EditText ipEditText;
    Button connectButton;
    TextView connectTextView;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ipEditText = (EditText)findViewById(R.id.ip_editText);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectTextView = (TextView)findViewById(R.id.connect_textView);

        connectButton.setOnClickListener(connectOnClickListener);
    }

    Handler connectTextViewHandler = new Handler(){

        public void handleMessage(Message msg){

            connectTextView.setText(msg.obj.toString());
        }
    };


    public Button.OnClickListener connectOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //get ipEditText text
                    String ip = ipEditText.getText().toString().trim();
                    String host = "http://" + ip + ":8888";
                    Log.i("socketIO",host);
                    //init connection
                    try {
                        socket = IO.socket(host);

                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... objects) {
                                socket.emit("chat message", "hi");
                            }

                        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... objects) {
                                Log.i("socketIO","Disconnect");
                            }
                        });

                        socket.connect();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
