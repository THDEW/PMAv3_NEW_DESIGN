package Statistic;

/**
 * Created by my131 on 29/4/2559.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.senoir.newpmatry1.R;

import java.util.List;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.MyViewHolder> {

    private List<ApplianceModel> applianceList;


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApplianceModel appliancemodel = applianceList.get(position);
        holder.appliname.setText(appliancemodel.getTitle());
        holder.genre.setText(Double.toString(appliancemodel.getElect()));
        //holder.date.setText(appliancemodel.getDate());
        holder.frontelect.setText(appliancemodel.getFrontElect());


    }
    /*
    @Override
    public int getItemId(int position) {
        if (position < applianceList.size()) {
            return applianceList.get(position).getElect();
        }
        return RecyclerView.NO_ID;
    }
    */
    @Override
    public int getItemCount() {
        return applianceList.size();
    }
    public ApplianceAdapter(List<ApplianceModel> applianceList) {
        this.applianceList = applianceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appliances_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView appliname, date, genre, frontelect;

        public MyViewHolder(View view) {
            super(view);
            appliname = (TextView) view.findViewById(R.id.appliance_name);
            genre = (TextView) view.findViewById(R.id.elect__usage);
            //date = (TextView) view.findViewById(R.id.date);
            frontelect = (TextView) view.findViewById(R.id.front_elect_usage);
        }
    }


}
