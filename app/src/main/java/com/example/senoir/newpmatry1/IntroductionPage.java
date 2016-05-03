package com.example.senoir.newpmatry1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import activity.Home;
import billcalculate.BillCalculate;

public class IntroductionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_page);
        Button gogo = (Button) findViewById(R.id.Go);
        gogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);

                startActivity(intent);

            }
        });


    }

    public void checkBill(View view) {
        EditText temp = (EditText) findViewById(R.id.editText);
        BillCalculate bill = new BillCalculate();
        String str = (String) temp.getText().toString();
        Toast.makeText(this, bill.getBillOfType1_3(389, 457, 1) + "", Toast.LENGTH_LONG).show();
    }
}
