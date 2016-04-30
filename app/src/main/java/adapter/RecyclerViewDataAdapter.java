package adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import activity.Home;
import activity.LocationFragment;
import model.GraphSeriesModel;
import model.SectionDataModel;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private FragmentManager fm;
    private int indexLocation;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, FragmentManager fm) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        indexLocation = i;

        String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, fm, sectionName);

        itemRowHolder.backUp = itemListDataAdapter;

        itemRowHolder.img.setBackgroundResource(R.drawable.down_arrow);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new GridLayoutManager(mContext, 3));
        itemRowHolder.recycler_view_list.setAdapter(null);


        /*itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();



            }
        });*/


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
            //this.btnMore= (Button) view.findViewById(R.id.btnMore);

            allBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!selectAll) {
                        selectAll = true;
                        index = LocationFragment.data.size();
                        
                        for (int i = 0; i < index; i++){

                            if(!LocationFragment.data.get(i).getIsLocation() &&
                                    LocationFragment.data.get(i).getLocation().equals(itemTitle.getText().toString())){
                                LocationFragment.data.get(i).setValue(-1d);
                            }
                        }

                        LocationFragment.data.add(new GraphSeriesModel("", itemTitle.getText().toString(),50d,true));
                        LocationFragment.data2.add(0d);
                    } else {
                        LocationFragment.data.get(index).setValue(-1d);
                        selectAll = false;
                    }
                }

            });

            itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(selectAll) {
                        LocationFragment.data.get(index).setValue(-1d);
                        selectAll = false;
                    }

                    if (open) {
                        recycler_view_list.setAdapter(null);
                        img.setBackgroundResource(R.drawable.down_arrow);
                        open = false;
                        allBt.setVisibility(View.VISIBLE);
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
