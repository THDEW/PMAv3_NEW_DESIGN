package billcalculate;

import android.text.Editable;

import java.text.DecimalFormat;

/**
 * Created by Toshiba on 5/4/2016.
 */
public class BillCalculate {

    private double unit;

    public BillCalculate(){
        unit = 0d;
    }

    public double getBillOfType1_1(double unit){


        double cost = 0;
        this.unit = unit;

        double caseBill[] = new double[]{
                2.3488d,
                2.9882d,
                3.2405d,
                3.6237d,
                3.7171d,
                4.2218d,
                4.4217d
        };

        int unitLoop = (int) Math.round(unit);

        int count = 0;

        for(int i = 1; i <= unitLoop; i++){
            if(i <= 15){
                count = 0;
            } else if(i <= 25){
                count = 1;
            } else if(i <= 35){
                count = 2;
            } else if(i <= 100){
                count = 3;
            } else if(i <= 150){
                count = 4;
            } else if(i <= 400){
                count = 5;
            } else {
                count = 6;
            }

            cost += caseBill[count];

            this.unit--;
        }
        cost += this.unit * caseBill[count];
        // service cost
        cost += 8.19d;
        // ft
        cost += (unit * -0.048d);
        // Vat
        cost += (cost*7)/100;


        return cost;
    }

    public double getBillOfType1_2(double unit){
        double cost = 0;
        this.unit = unit;
        double caseBill[] = new double[]{
                3.2484d,
                4.2218d,
                4.4217d
        };

        int unitLoop = (int) Math.round(unit);

        int count = 0;

        for(int i = 1; i <= unitLoop; i++){
            if(i <= 150){
                count = 0;
            } else if(i <= 400){
                count = 1;
            } else {
                count = 2;
            }

            cost += caseBill[count];

            this.unit--;
        }
        cost += this.unit * caseBill[count];
        // service cost
        cost += 38.22d;
        // ft
        cost += (unit * -0.048d);
        // Vat
        cost += (cost*7)/100;
        return cost;
    }

    public double getBillOfType1_3(double unit_1, double unit_2, int type){
        double cost = 0;
        this.unit = unit_1;
        double caseBill[] = new double[]{
                5.1135d,// on peak 1
                5.7982d,// on peak 2
                2.6037d,// off peak 1
                2.6369d,// off peak 2


        };

        int count;

        if(type == 0){
            count = 0;
        } else {
            count = 1;
        }

        cost += unit_1 * caseBill[count];

        if(type == 0){
            count = 2;
        } else {
            count = 3;
        }

        cost += unit_2 * caseBill[count];

        // service cost
        if(type == 0){ cost += 312.24d;}
        else{ cost += 38.22d; }
        // ft
        cost += ((unit_1 + unit_2) * -0.048d);
        // Vat
        cost += (cost*7)/100;

        return cost;
    }


}
