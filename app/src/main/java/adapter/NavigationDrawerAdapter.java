package adapter;

/**
 * Created by my131 on 19/4/2559.
 */
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.senoir.newpmatry1.R;
import java.util.Collections;
import java.util.List;

import activity.Home;
import model.NavDrawerItem;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        switch (position) {
            case 0:
                holder.img.setBackgroundResource(R.drawable.icon_1_current_status);
                break;
            case 1:
                    holder.img.setBackgroundResource(R.drawable.icon_2_history);
                break;
            case 2:
                    holder.img.setBackgroundResource(R.drawable.icon_3_statistic);
                break;
            case 3:
                    holder.img.setBackgroundResource(R.drawable.icon_4_electricity_bill);
                break;
            case 4:
                    holder.img.setBackgroundResource(R.drawable.icon_5_setting);
                break;
            case 5:
                    holder.img.setBackgroundResource(R.drawable.icon_6_about);
                break;
        }

        Log.d("aaa", position+"");

        if(position == Home.page) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2F2"));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ImageView img;

        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            img = (ImageView) itemView.findViewById(R.id.icon_drawer);

            this.itemView = itemView;
        }
    }
}
