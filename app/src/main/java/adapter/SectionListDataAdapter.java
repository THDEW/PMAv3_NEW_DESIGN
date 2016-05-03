package adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import activity.Home;
import activity.LocationFragment;
import dialog.EachDeviceDialog;
import model.GraphSeriesModel;
import model.SingleItemModel;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    private FragmentManager fm;
    private String location;
    private boolean onOff;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList, FragmentManager fm, String location) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.fm = fm;
        this.location = location;
    }

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList, FragmentManager fm, String location, boolean onOff) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.fm = fm;
        this.location = location;
        this.onOff = onOff;
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
                    LocationFragment.data.get(j).getLocation().equals(location) &&
                    LocationFragment.data.get(j).getValue(0) != -1d) {

                holder.index = j;
                holder.itemImage.setAlpha(0.2f);
                holder.selected = true;
                break;
            }
        }
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

        protected TextView tvTitle;

        protected ImageView itemImage;

        boolean selected = false;

        int index;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Home.page == 0) {
                        String unit = " Unit    ";
                        String hour = " Hr.    ";
                        String baht = " BAHT.    ";

                        EachDeviceDialog dialogFragment = new EachDeviceDialog (tvTitle.getText().toString() , location,"80" + unit ,
                                "20" + hour,"500" + baht, onOff);
                        dialogFragment.show(fm, tvTitle.getText().toString() );


                    } else if (Home.page == 1) {
                        if (selected) {
                            LocationFragment.data.get(index).setValue(0);
                            selected = false;
                            itemImage.setAlpha(1f);
                            LocationFragment.addNew = true;
                            //itemImage.setBackgroundResource(R.drawable.ic_brightness_7_black_36dp);
                        } else {
                            index = LocationFragment.data.size();

                            ArrayList<Double> dataFromDataBase = new ArrayList<>();
                            dataFromDataBase.add(2d + index);
                            dataFromDataBase.add(5d + index);
                            dataFromDataBase.add(3d + index);
                            dataFromDataBase.add(3d + index);
                            dataFromDataBase.add(6d + index);
                            dataFromDataBase.add(7d + index);
                            dataFromDataBase.add(1d + index);

                            LocationFragment.data.add(new GraphSeriesModel(tvTitle.getText().toString(),location
                                    ,dataFromDataBase,false));
                            LocationFragment.data2.add(0d);
                            itemImage.setAlpha(0.2f);
                            selected = true;
                            LocationFragment.addNew = true;

                            //itemImage.setBackgroundResource(R.drawable.ic_action_search);
                        }
                    }
                }
            });


        }

    }

}
