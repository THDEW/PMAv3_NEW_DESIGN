package History_OnOff.adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import History_OnOff.model.GraphSeriesModel;
import History_OnOff.fragments.LocationFragment;
import History_OnOff.model.SectionDataModel;
import activity.Home;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private boolean onOff;
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private FragmentManager fm;

    private String clientHandle;
    private Connection connection;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, FragmentManager fm) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
    }

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, FragmentManager fm, boolean onOff, Connection connection) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
        this.onOff = onOff;
        this.connection = connection;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.connection = connection;
        itemRowHolder.connection.registerChangeListener(itemRowHolder.changeListener);

        itemRowHolder.locationTitle.setText(sectionName);

        itemRowHolder.parentIndex = i;

        itemRowHolder.locationItem = dataList.get(i);

        SectionListDataAdapter itemListDataAdapter;
        if(Home.page == 0){
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName, onOff,connection);
        }else{
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName, connection);
        }

        itemRowHolder.backUp = itemListDataAdapter;

        itemRowHolder.img.setBackgroundResource(R.drawable.down_arrow);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new GridLayoutManager(mContext, 3));
        itemRowHolder.recycler_view_list.setAdapter(null);

        itemRowHolder.open = false;

        if(dataList.get(i).getAllItemsInSection().size() != 0) {
            itemRowHolder.locationId = dataList.get(i).getAllItemsInSection().get(0).getLocationId();
        }


        if(Home.page == 1) {
            if (LocationFragment.open[i]) {
                itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
                itemRowHolder.img.setBackgroundResource(R.drawable.up_arrow);
                itemRowHolder.allBt.setVisibility(View.INVISIBLE);
                //LocationFragment.open[i] = false;
            } else {
                itemRowHolder.img.setBackgroundResource(R.drawable.down_arrow);
                if (Home.page == 1) {
                    itemRowHolder.allBt.setVisibility(View.VISIBLE);
                }
                //LocationFragment.open[i] = true;
            }
        }
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected Connection connection;
        protected TextView locationTitle;

        protected RecyclerView recycler_view_list;

        protected RelativeLayout expandableButton;

        protected ImageView img;

        protected Button allBt;

        protected SectionDataModel locationItem;

        int locationId;

        boolean selectAll = false;

        boolean open;

        protected int parentIndex;

        SectionListDataAdapter backUp;

        protected ChangeListener changeListener = new ChangeListener();

        public ItemRowHolder(View view) {
            super(view);

            this.locationTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.img = (ImageView) view.findViewById(R.id.expandSignal);
            this.allBt = (Button) view.findViewById(R.id.allBt);
            expandableButton = (RelativeLayout) view.findViewById(R.id.expandBt_2);

            if (selectAll) {
                allBt.setAlpha(0.5f);
            } else {
                allBt.setAlpha(1f);
            }

            if(Home.page == 1) {

                allBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(LocationFragment.canSelectData){
                            if (!selectAll) {
                                selectAll = true;

                                //LocationFragment.data.add(new GraphSeriesModel(locationId,locationTitle.getText().toString(), 10, true));

                                //publish here to get all data in a location

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
                                String topic = "android/history/location";
                                String message = locationId+","+date;


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

                                for (int i = 0; i < LocationFragment.data.size(); i++) {

                                    if (!LocationFragment.data.get(i).getIsLocation() &&
                                            LocationFragment.data.get(i).getLocation().equals(locationTitle.getText().toString())) {
                                        LocationFragment.data.remove(i);
                                        i--;
                                    }
                                }

                                allBt.setAlpha(0.5f);
                                LocationFragment.addNew = true;
                            } else {
                                for (int j = 0; j < LocationFragment.data.size(); j++) {

                                    if (LocationFragment.data.get(j).getLocation().equals(locationTitle.getText().toString()) &&
                                            LocationFragment.data.get(j).getIsLocation()) {
                                        LocationFragment.data.remove(j);

                                        allBt.setAlpha(1f);
                                        selectAll = false;
                                        LocationFragment.addNew = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                });
            } else {
                allBt.setVisibility(View.INVISIBLE);
            }

            expandableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Home.page == 1) {

                        if(selectAll){
                            for (int j = 0; j < LocationFragment.data.size(); j++) {

                                if (LocationFragment.data.get(j).getLocation().equals(locationTitle.getText().toString()) &&
                                        LocationFragment.data.get(j).getIsLocation()) {
                                    LocationFragment.data.remove(j);

                                    allBt.setAlpha(1f);
                                    selectAll = false;
                                    LocationFragment.addNew = true;
                                    break;
                                }
                            }
                        }

                        if (LocationFragment.open[parentIndex]) {
                            recycler_view_list.setAdapter(null);
                            img.setBackgroundResource(R.drawable.down_arrow);
                            LocationFragment.open[parentIndex] = false;
                            allBt.setVisibility(View.VISIBLE);
                        } else {
                            recycler_view_list.setAdapter(backUp);
                            img.setBackgroundResource(R.drawable.up_arrow);
                            LocationFragment.open[parentIndex] = true;
                            allBt.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        if (open) {
                            recycler_view_list.setAdapter(null);
                            img.setBackgroundResource(R.drawable.down_arrow);
                            open = false;
                        } else {
                            recycler_view_list.setAdapter(backUp);
                            img.setBackgroundResource(R.drawable.up_arrow);
                            open = true;

                        }
                    }
                }

            });

        }

        public void addSeries(Bundle bundle){

            String jall = bundle.getString("history/location");
            double value = 0; //bundle unit มา

            JSONArray jsonArray = null;
            JSONObject jsonObject= null;


            try {
                jsonArray = new JSONArray(jall);
                jsonObject = (JSONObject) jsonArray.get(0);
                value = (Double.parseDouble(jsonObject.getString("sum_energy"))/1000)/3600;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            LocationFragment.data.add(new GraphSeriesModel(locationId,locationTitle.getText().toString()
                    , value, true));
            connection.removeChangeListener(changeListener);
            changeListener = new ChangeListener();
            connection.registerChangeListener(changeListener);

        }

        private class ChangeListener implements PropertyChangeListener {

            @Override
            public void propertyChange(PropertyChangeEvent event) {


                if(event.getPropertyName().equals("history/location/"+locationId))
                {

                    Log.d("problem", locationId + "");
                    Bundle bundle;
                    bundle = connection.getBundle();
                    Log.v("history/location", bundle.toString());
                    addSeries(bundle);

                }
            }
        }
    }

}
