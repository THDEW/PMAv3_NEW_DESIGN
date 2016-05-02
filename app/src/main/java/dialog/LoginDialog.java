package dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

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
    public  LoginDialog(View view){
        this.view = view;
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_login, container, false);
        getDialog().setTitle("Login Section");

        user = (EditText) rootView.findViewById(R.id.userName);

        pass = (EditText) rootView.findViewById(R.id.passWord);

        Button bt = (Button) rootView.findViewById(R.id.loginBt);

        final Button cancelbt = (Button) rootView.findViewById(R.id.cancelBt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("")){
                    if(pass.getText().toString().equals("")){

                        Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_LONG).show();
                        RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                        my_recycler_view.setVisibility(View.VISIBLE);
                        Button logout = (Button) view.findViewById(R.id.logout);
                        logout.setVisibility(View.VISIBLE);


                        Button relogin = (Button) view.findViewById(R.id.reloginbt);
                        relogin.setVisibility(View.INVISIBLE);
                        TextView plslogin = (TextView) view.findViewById(R.id.plsLogin);
                        plslogin.setVisibility(View.INVISIBLE);


                        Home.login = true;

                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelbt.setOnClickListener(new View.OnClickListener(){
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
