package adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
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

import activity.Home;
import activity.LocationFragment;
import model.GraphSeriesModel;
import model.SectionDataModel;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private boolean onOff;
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private FragmentManager fm;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, FragmentManager fm) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
    }

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, FragmentManager fm, boolean onOff) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
        this.onOff = onOff;
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

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter;
        if(Home.page == 0){
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName, onOff);
        }else{
            itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName);
        }

        itemRowHolder.backUp = itemListDataAdapter;

        itemRowHolder.img.setBackgroundResource(R.drawable.down_arrow);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new GridLayoutManager(mContext, 3));
        itemRowHolder.recycler_view_list.setAdapter(null);


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

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected RelativeLayout expandableButton;

        protected ImageView img;

        protected Button allBt;

        boolean open = false;

        boolean selectAll = false;

        int index;

        SectionListDataAdapter backUp;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.img = (ImageView) view.findViewById(R.id.expandSignal);
            this.allBt = (Button) view.findViewById(R.id.allBt);
            expandableButton = (RelativeLayout) view.findViewById(R.id.expandBt_2);

            if(Home.page == 1) {

                allBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!selectAll) {
                            selectAll = true;
                            index = LocationFragment.data.size();

                            for (int i = 0; i < index; i++) {

                                if (!LocationFragment.data.get(i).getIsLocation() &&
                                        LocationFragment.data.get(i).getLocation().equals(itemTitle.getText().toString())) {
                                    LocationFragment.data.get(i).setValue(0);
                                }
                            }

                            ArrayList<Double> dataFromDataBase = new ArrayList<>();
                            dataFromDataBase.add(2d + index);
                            dataFromDataBase.add(5d + index);
                            dataFromDataBase.add(3d + index);
                            dataFromDataBase.add(3d + index);
                            dataFromDataBase.add(6d + index);
                            dataFromDataBase.add(7d + index);
                            dataFromDataBase.add(1d + index);

                            LocationFragment.data.add(new GraphSeriesModel("", itemTitle.getText().toString(), dataFromDataBase, true));
                            LocationFragment.data2.add(0d);
                            allBt.setAlpha(0.5f);
                            LocationFragment.addNew = true;
                        } else {
                            LocationFragment.data.get(index).setValue(0);
                            allBt.setAlpha(1f);
                            selectAll = false;
                            LocationFragment.addNew = true;
                        }
                    }

                });
            } else {
                allBt.setVisibility(View.INVISIBLE);
            }

            expandableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < index; i++) {

                        if (!LocationFragment.data.get(i).getIsLocation() &&
                                LocationFragment.data.get(i).getLocation().equals(itemTitle.getText().toString())) {
                            LocationFragment.data.get(i).setValue(0);
                        }
                    }
                    if(selectAll) {
                        LocationFragment.data.get(index).setValue(0);
                        selectAll = false;
                        allBt.setAlpha(1f);
                    }

                    if (open) {
                        recycler_view_list.setAdapter(null);
                        img.setBackgroundResource(R.drawable.down_arrow);
                        open = false;
                        if(Home.page == 1){
                            allBt.setVisibility(View.VISIBLE);
                        }
                    } else {
                        recycler_view_list.setAdapter(backUp);
                        img.setBackgroundResource(R.drawable.up_arrow);
                        open = true;
                        allBt.setVisibility(View.INVISIBLE);
                    }
                }

            });

        }

    }

}
