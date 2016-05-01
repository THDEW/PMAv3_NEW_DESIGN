package dialog;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class RowDataDialog extends DialogFragment {

    private String typeOfData = "";
    private int type;
    private boolean isAdd;

    private Button addBt;
    private Button deleteBt;
    private Button cancelBt;

    private FragmentManager fm;


    public RowDataDialog(String typeOfData, int type, boolean isAdd, FragmentManager fm){
        this.typeOfData = typeOfData;
        this.type = type;
        this.isAdd = isAdd;
        this.fm = fm;
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.dialog_row_data_type, container, false);
        getDialog().setTitle(typeOfData);

        addBt = (Button) rootView.findViewById(R.id.addBt_1);
        deleteBt = (Button) rootView.findViewById(R.id.deleteBt_1);
        cancelBt = (Button) rootView.findViewById(R.id.cancelBt_1);

        if(type == 1) {
            rootView = inflater.inflate(R.layout.dialog_row_data_detail, container, false);
            getDialog().setTitle(typeOfData);

            addBt = (Button) rootView.findViewById(R.id.addBt_2);
            deleteBt = (Button) rootView.findViewById(R.id.deleteBt_2);
            cancelBt = (Button) rootView.findViewById(R.id.cancelBt_2);
        } else if (type == 2) {
            rootView = inflater.inflate(R.layout.dialog_row_data_node, container, false);
            getDialog().setTitle(typeOfData);

            addBt = (Button) rootView.findViewById(R.id.addBt_3);
            deleteBt = (Button) rootView.findViewById(R.id.deleteBt_3);
            cancelBt = (Button) rootView.findViewById(R.id.cancelBt_3);
        } else if (type == 3) {
            rootView = inflater.inflate(R.layout.dialog_row_data_location, container, false);
            getDialog().setTitle(typeOfData);

            addBt = (Button) rootView.findViewById(R.id.addBt_4);
            deleteBt = (Button) rootView.findViewById(R.id.deleteBt_4);
            cancelBt = (Button) rootView.findViewById(R.id.cancelBt_4);
        } else if (type == 4) {
            rootView = inflater.inflate(R.layout.dialog_row_data_group_of_device, container, false);
            getDialog().setTitle(typeOfData);

            addBt = (Button) rootView.findViewById(R.id.addBt_5);
            deleteBt = (Button) rootView.findViewById(R.id.deleteBt_5);
            cancelBt = (Button) rootView.findViewById(R.id.cancelBt_5);
        } else if (type == 5) {
            rootView = inflater.inflate(R.layout.dialog_row_data_device, container, false);
            getDialog().setTitle(typeOfData);

            addBt = (Button) rootView.findViewById(R.id.addBt_6);
            deleteBt = (Button) rootView.findViewById(R.id.deleteBt_6);
            cancelBt = (Button) rootView.findViewById(R.id.cancelBt_6);
        }


        if(isAdd){
            addBt.setText("Add");
            deleteBt.setVisibility(View.INVISIBLE);
        }else{
            addBt.setText("Update");
            deleteBt.setVisibility(View.VISIBLE);
        }

        addBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isAdd){
                    Toast.makeText(getContext(), "Add data", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "data updated", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

        });

        deleteBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DeleteConfirmDialog dialog = new DeleteConfirmDialog(typeOfData);
                dialog.show(fm, "deleteItem");

                dismiss();
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

}
