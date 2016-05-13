package Setting;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class DeleteConfirmDialog extends DialogFragment{

    private String typeOfItem;
    private int id;
    private ItemDataModel ownData;

    private Connection connection;


    public DeleteConfirmDialog(String typeOfItem, ItemDataModel ownData, Connection connection){
        this.typeOfItem = typeOfItem;
        this.ownData = ownData;
        this.connection = connection;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure to delete " + typeOfItem + " " + ownData.getId());

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delete();
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    public void delete(){
        String clientHandle = connection.handle();
        String topic = null;
        String message = null;
        int qos = 0;
        boolean retained = false;

        String[] args = new String[2];

        topic = "android/settings/deleteData/"+typeOfItem;
        message = ""+ownData.getId();

        args[0] = message;
        args[1] = topic+";qos:"+qos+";retained:"+retained;

        try {
            connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
