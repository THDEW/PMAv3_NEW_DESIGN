package adapter;

/**
 * Created by my131 on 28/4/2559.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import activity.Home;
import activity.LocationFragment;
import model.SingleItemModel;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
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
                        Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                    } else if (Home.page == 1) {
                        if (selected) {
                            LocationFragment.data.set(index, -1d);
                            selected = false;
                            itemImage.setAlpha(1f);
                            //itemImage.setBackgroundResource(R.drawable.ic_brightness_7_black_36dp);
                        } else {
                            index = LocationFragment.data.size();
                            LocationFragment.data.add((index + 1) * 2d);
                            LocationFragment.data2.add(0d);
                            itemImage.setAlpha(0.2f);
                            selected = true;
                            //itemImage.setBackgroundResource(R.drawable.ic_action_search);
                        }
                    }
                }
            });


        }

    }

}
