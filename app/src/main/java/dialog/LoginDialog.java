package dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class LoginDialog extends DialogFragment {

    private EditText user;

    private EditText pass;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_login, container, false);
        getDialog().setTitle("Login Section");

        user = (EditText) rootView.findViewById(R.id.userName);

        pass = (EditText) rootView.findViewById(R.id.passWord);

        Button bt = (Button) rootView.findViewById(R.id.loginBt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("")){
                    if(pass.getText().toString().equals("")){

                        Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_LONG).show();

                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Username", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
