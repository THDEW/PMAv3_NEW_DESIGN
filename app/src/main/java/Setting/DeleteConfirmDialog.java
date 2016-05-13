package Setting;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class DeleteConfirmDialog extends DialogFragment{

    private String typeOfItem;
    private int id;
    private ItemDataModel ownData;

    public DeleteConfirmDialog(String typeOfItem, ItemDataModel ownData){
        this.typeOfItem = typeOfItem;
        this.ownData = ownData;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure to delete " + typeOfItem +" "+ ownData.getId());

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delete();

                Toast.makeText(getContext(), "data deleted", Toast.LENGTH_SHORT).show();

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


    }
}
