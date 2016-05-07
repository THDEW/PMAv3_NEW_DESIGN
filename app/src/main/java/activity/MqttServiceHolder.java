package activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.ActivityConstants;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.android.service.sample.MqttCallbackHandler;
import org.eclipse.paho.android.service.sample.MqttTraceCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by EZ3 on 07/05/2016.
 */
public class MqttServiceHolder extends Service {

    private String clientHandle = null;
    private Connection connection = null;

    public MqttServiceHolder()
    {
        connectAction();
        Intent homeIntent = new Intent(this, Home.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeIntent.putExtra("handle",clientHandle);
        startActivity(homeIntent);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //Intent intent2 = new Intent(this,MainActivity.class);
        //startActivity(intent2);

        return START_STICKY;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    /**
     * Process data from the connect action
     *
     *
     */
    private void connectAction() {
        MqttConnectOptions conOpt = new MqttConnectOptions();

        // The basic client information
        String server = "192.168.1.35";
        String clientId = "Android";
        int port = 1883;
        boolean cleanSession = false;


        String uri = null;
        uri = "tcp://";


        uri = uri + server + ":" + port;

        MqttAndroidClient client;
        client = new MqttAndroidClient(this, uri, clientId);




        // create a client handle
        clientHandle = uri + clientId;
        //Toast.makeText(this,clientHandle,Toast.LENGTH_LONG).show();


        // connection options

        String username = ActivityConstants.empty;

        String password = ActivityConstants.empty;

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

                //Toast.makeText(this,""+client.isConnected(),Toast.LENGTH_LONG).show();

            }
            catch (MqttException e) {
                Log.e(this.getClass().getCanonicalName(),
                        "MqttException Occured", e);
            }
        }


    }

}
