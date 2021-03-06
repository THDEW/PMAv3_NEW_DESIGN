package History_OnOff.adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.client.mqttv3.MqttException;

import History_OnOff.dialogs.EachDeviceDialog;
import History_OnOff.model.GraphSeriesModel;
import History_OnOff.fragments.LocationFragment;
import History_OnOff.model.SingleItemModel;
import Setting.ItemDataModel;
import activity.Home;
import billcalculate.BillCalculate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.paho.android.service.sample.Connection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    private FragmentManager fm;
    private String location;
    private boolean onOff;

    private String clientHandle;
    private Connection connection;




    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList, FragmentManager fm, String location, Connection connection) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.fm = fm;
        this.location = location;
        this.connection = connection;


    }

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList, FragmentManager fm, String location, boolean onOff,Connection connection) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.fm = fm;
        this.location = location;
        this.onOff = onOff;
        this.connection = connection;
    }



    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.connection = connection;
        holder.connection.registerChangeListener(holder.changeListener);

        holder.id = i;

        holder.item = itemsList.get(i);

        holder.deviceId = holder.item.getId();

        holder.powerNodeId = holder.item.getPowernodeId();

        holder.locationId = holder.item.getLocationId();

        holder.tvTitle.setText(singleItem.getName());

        //if(singleItem.getUrl().equals("a2")){
        holder.itemImage.setBackgroundResource(R.drawable.ic_brightness_7_black_36dp);

        if(LocationFragment.data.size() != 0) {
            Log.d("aaa", LocationFragment.data.get(0).getDevice() + "\n" +
                    LocationFragment.data.get(0).getLocation() + "\n" +
                    holder.tvTitle.getText().toString() + "\n" +
                    location + "\n");
        }
        for (int j = 0; j < LocationFragment.data.size(); j++) {

            if (LocationFragment.data.get(j).getDevice().equals(holder.tvTitle.getText().toString()) &&
                    LocationFragment.data.get(j).getLocation().equals(location)) {

                if(Home.page == 1) {
                    holder.itemImage.setAlpha(0.2f);
                }
                holder.selected = true;
                break;
            }
        }
        Log.v("rowholder","holding");


        /*}
        else{
            holder.itemImage.setBackgroundResource(R.drawable.ic_action_search);
        }*/
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        private Connection connection;
        protected TextView tvTitle;

        protected ImageView itemImage;

        boolean selected = false;

        protected SingleItemModel item;

        protected int deviceId;

        protected int powerNodeId;

        protected int locationId;

        protected int id;

        protected ChangeListener changeListener = new ChangeListener();

        public SingleItemRowHolder(View view) {
            super(view);

            //connection.removeChangeListener(changeListener);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Home.page == 0) {
                        String unit = " Ws ";

                        // หน้า On และ Off parameter (ชื่อ device, location of device, power consumption of device (unit), cost, timing usage, status)

                        DecimalFormat df = new DecimalFormat("00.0000");

                        EachDeviceDialog dialogFragment = new EachDeviceDialog (item.getId(),item.getName() , df.format(item.getSumPower()) + unit ,
                                item.getLastTime(),item.getLastRecord()+"" ,connection);
                        dialogFragment.show(fm, tvTitle.getText().toString() );

                        String topic = "android/currentStatus/group_of_device";
                        String message = item.getId()+"";
                        int qos = 0;
                        boolean retained = false;

                        String[]args = new String[2];
                        args[0] = message;
                        args[1] = topic+";qos:"+qos+";retained:"+retained;

                        try {
                            connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(mContext, ActionListener.Action.PUBLISH, clientHandle, args));
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    } else if (Home.page == 1) {
                        if (LocationFragment.canSelectData) {
                            if (selected) {
//                            LocationFragment.data.get(index).setValue(-1d);
                                for (int j = 0; j < LocationFragment.data.size(); j++) {

                                    if (LocationFragment.data.get(j).getDevice().equals(tvTitle.getText().toString()) &&
                                            LocationFragment.data.get(j).getLocation().equals(location)) {
                                        LocationFragment.data.remove(j);

                                        selected = false;
                                        itemImage.setAlpha(1f);
                                        LocationFragment.addNew = true;
                                        break;
                                    }
                                }
                            } else {

                                //publish here to get each data of group_of_device
                                String[] split = LocationFragment.dateTime.split(" to ");
                                String date = "";
                                if(split.length == 2)
                                {
                                    date = split[0]+"-"+split[1];
                                }
                                else
                                {
                                    date = split[0];
                                }
                                String topic = "android/history/group_of_device";
                                String message = locationId+","+powerNodeId+","+deviceId+","+date;


                                int qos = 0;
                                boolean retained = false;

                                String[]args = new String[2];
                                args[0] = message;
                                args[1] = topic+";qos:"+qos+";retained:"+retained;

                                try {
                                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(mContext, ActionListener.Action.PUBLISH, clientHandle, args));
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }

                                /*
                                LocationFragment.data.add(new GraphSeriesModel(deviceId, powerNodeId, locationId, tvTitle.getText().toString(), location
                                        , 5d, false));
                                */

                                itemImage.setAlpha(0.2f);
                                selected = true;
                                LocationFragment.addNew = true;

                                //itemImage.setBackgroundResource(R.drawable.ic_action_search);
                            }
                        }
                    }
                }
            });


        }

        public void addSeries(Bundle bundle){

            String jall = bundle.getString("history/group_of_device");

            JSONArray jsonArray = null;
            JSONObject jsonObject= null;
            double value = 0; //bundle unit มา

            try {
                jsonArray = new JSONArray(jall);
                jsonObject = (JSONObject) jsonArray.get(0);
                value = (Double.parseDouble(jsonObject.getString("sum_energy")))/3600;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            LocationFragment.data.add(new GraphSeriesModel(deviceId, powerNodeId, locationId,tvTitle.getText().toString(),location
                    ,value,false));
            connection.removeChangeListener(changeListener);
            changeListener = new ChangeListener();
            connection.registerChangeListener(changeListener);

        }

        private class ChangeListener implements PropertyChangeListener {

            @Override
            public void propertyChange(PropertyChangeEvent event) {


                if(event.getPropertyName().equals("history/group_of_device/"+locationId+"/"+powerNodeId+"/"+deviceId))
                {

                    Log.d("problem", locationId+"");
                    Log.d("problem", powerNodeId+"");
                    Log.d("problem", deviceId+"");
                    Bundle bundle;
                    bundle = connection.getBundle();
                    Log.v("history/group_of_device", bundle.toString());
                    addSeries(bundle);

                }
            }
        }


    }

}
