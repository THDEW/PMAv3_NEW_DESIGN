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
        howtouse.add(new HowtoModel("This page tell the user current status of each device whether it's on or off\n" +
                "It  can show the real time graph of energy usage of each device by taping on device you would like to see",  R.drawable.icon_1_current_status));
        howtouse.add(new HowtoModel("History",  R.drawable.icon_2_history));
        howtouse.add(new HowtoModel("Statistic",  R.drawable.icon_3_statistic));
        howtouse.add(new HowtoModel("Electricity Bill",  R.drawable.icon_4_electricity_bill));
        howtouse.add(new HowtoModel("Setting",  R.drawable.icon_5_setting));

    }
}