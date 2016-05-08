package Setting;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import activity.Home;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class LoginDialog extends DialogFragment {
    public static String cancelLogin;
    //  public static String LoginSuccess = null;
    private EditText user;
    private View view;

    private EditText pass;

    private String clientHandle = null;
    private Connection connection = null;

    public LoginDialog(View view, String clientHandle) {
        this.view = view;
        this.clientHandle = clientHandle;
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_login, container, false);

        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);

        getDialog().setTitle("Login Section");

        user = (EditText) rootView.findViewById(R.id.userName);

        pass = (EditText) rootView.findViewById(R.id.passWord);

        Button bt = (Button) rootView.findViewById(R.id.loginBt);

        final Button cancelbt = (Button) rootView.findViewById(R.id.cancelBt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String topic = "android/authenticate";
                String message = "getAuthenticate";
                int qos = 0;
                boolean retained = false;

                String[] args = new String[2];
                args[0] = message;
                args[1] = topic + ";qos:" + qos + ";retained:" + retained;

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                dismiss();
            }

        });
        cancelbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelLogin = "Login Cancel";
                RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                my_recycler_view.setVisibility(View.INVISIBLE);
                Button relogin = (Button) view.findViewById(R.id.reloginbt);
                relogin.setVisibility(View.VISIBLE);
                TextView plslogin = (TextView) view.findViewById(R.id.plsLogin);
                plslogin.setVisibility(View.VISIBLE);
                dismiss();
            }
        });

        return rootView;
    }


}
