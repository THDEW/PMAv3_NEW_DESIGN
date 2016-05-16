package com.example.senoir.newpmatry1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import activity.Home;
import activity.MqttServiceHolder;
import billcalculate.BillCalculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.ActivityConstants;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.android.service.sample.MqttCallbackHandler;
import org.eclipse.paho.android.service.sample.MqttTraceCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.security.Provider;
import android.app.Service;

public class IntroductionPage extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private Connection connection = null;

    private String clientHandle = null;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_introduction_page);

        if(connection == null) connectAction();


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
            new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                Intent mainIntent = new Intent();
                mainIntent.setClassName(IntroductionPage.this.getApplicationContext().getPackageName(), "activity.Home");
                mainIntent.putExtra("handle", clientHandle);
                IntroductionPage.this.startActivity(mainIntent);
                //IntroductionPage.this.finish();



            }
        }, SPLASH_DISPLAY_LENGTH);
    }



    /**
     * Process data from the connect action
     *
     *
     */
    private void connectAction() {
        MqttConnectOptions conOpt = new MqttConnectOptions();

        // The basic client information
        String server = "192.168.0.100";
        String clientId = "Android42";
        int port = 1883;
        boolean cleanSession = false;


        String uri = null;
        uri = "tcp://";


        uri = uri + server + ":" + port;

        MqttAndroidClient client;
        client = new MqttAndroidClient(this, uri, clientId);

        // create a client handle
        clientHandle = uri + clientId;

        // connection options


        int timeout = 1000;
        int keepalive = 10;
        boolean ssl = false;
        connection = new Connection(clientHandle, clientId, server, port,
                this, client, ssl);

        conOpt.setCleanSession(cleanSession);
        conOpt.setConnectionTimeout(timeout);
        conOpt.setKeepAliveInterval(keepalive);

        // connect client

        String[] actionArgs = new String[1];
        actionArgs[0] = clientId;

        final ActionListener callback = new ActionListener(this,
                ActionListener.Action.CONNECT, clientHandle, actionArgs);

        boolean doConnect = true;

        client.setCallback(new MqttCallbackHandler(this, clientHandle));

        //set traceCallback
        client.setTraceCallback(new MqttTraceCallback());


        connection.addConnectionOptions(conOpt);
        Connections.getInstance(this).addConnection(connection);

        //Toast.makeText(this, connection.handle(), Toast.LENGTH_LONG).show();
        if (doConnect) {
            try {
                client.connect(conOpt, null, callback);

                //Toast.makeText(this,"Connected to server",Toast.LENGTH_LONG).show();

            }
            catch (MqttException e) {
                Log.e(this.getClass().getCanonicalName(),
                        "MqttException Occured", e);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();


        //Register receivers again
        if(connection!= null)
        {
            connection.getClient().registerResources(this);
            connection.getClient().setCallback(new MqttCallbackHandler
                    (this, connection.getClient().getServerURI() + connection.getClient().getClientId()));
        }


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(connection!= null)
        {
            connection.getClient().unregisterResources();
            connection.getClient().close();
        }
    }

}