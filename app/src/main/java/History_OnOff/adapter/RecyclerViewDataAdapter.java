package History_OnOff.adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;

import History_OnOff.model.GraphSeriesModel;
import History_OnOff.fragments.LocationFragment;
import History_OnOff.model.SectionDataModel;
import activity.Home;

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

        itemRowHolder.locationTitle.setText(sectionName);

        itemRowHolder.parentIndex = i;

        itemRowHolder.locationItem = dataList.get(i);

        SectionListDataAdapter itemListDataAdapter;
        if(Home.page == 0){
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName, onOff,connection);
        }else{
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName);
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

                                LocationFragment.data.add(new GraphSeriesModel(locationId,locationTitle.getText().toString()
                                        , 10, true));

                                //publish here to get all data in a location

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

            double value = 0; //bundle unit มา

            LocationFragment.data.add(new GraphSeriesModel(locationId,locationTitle.getText().toString()
                    , 10, true));

        }
    }

}
