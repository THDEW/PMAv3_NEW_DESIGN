package About;

import java.util.ArrayList;
import java.util.List;
import com.example.senoir.newpmatry1.R;


/**
 * Created by my131 on 10/5/2559.
 */
public class HowtoModel {

    public String descrption;

    public int photoId;

    public HowtoModel(){
    }
    public HowtoModel(String descrption, int photoId) {
        this.descrption = descrption;
        this.photoId = photoId;
    }


    public List<HowtoModel> howtouse;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    public void initializeData(){
        howtouse = new ArrayList<>();
        howtouse.add(new HowtoModel("Current Status.\n"+"This page tell the user current status of each device whether it's on or off\n" +
                "It  can show the real time graph of energy usage of each device by taping on device you would like to see",  R.drawable.icon_1_current_status));
        howtouse.add(new HowtoModel("History\n"+"This page show each devices and locations historical usage from the past until present in comparison's the form of graph." +
                " You can click on button \"All\" to create graph for location and in each location can be expanded into devices, so tap on each devices to create devices graph",  R.drawable.icon_2_history));
        howtouse.add(new HowtoModel("Statistic\n" +
                "This page show information of the most power's usage devices of each day, month and year. It can show in two different way which are location and devices\n" +
                "It can sort by the max and min power's usage of each devices and location.",  R.drawable.icon_3_statistic));
        howtouse.add(new HowtoModel("Electricity Bill\n" +
                "This page calculate the bill of each month.\n" +
                "Showing the cost of each location and devices separately.\n" +
                "There are two type of customer which are: \n" +
                "- Customer use the power less than 150 Units\n" +
                "- Customer use the power more than 150 Units\n" +
                "Condition for first customer type: \n" +
                "   Customer have to use power less than 150 Units per month for 3 month to reach the requirement of this user type",  R.drawable.icon_4_electricity_bill));
        howtouse.add(new HowtoModel("Setting",  R.drawable.icon_5_setting));

    }
}