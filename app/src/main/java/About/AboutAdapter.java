package About;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import java.util.List;

import About.HowtoModel;


/**
 * Created by my131 on 10/5/2559.
 */
public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.HowtoModelViewHolder>{

    List<HowtoModel> howtouse;

    public AboutAdapter(List<HowtoModel> howtouse){
        this.howtouse = howtouse;
    }
    @Override
    public HowtoModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.about_card_item, viewGroup, false);
        HowtoModelViewHolder pvh = new HowtoModelViewHolder(v);
        return pvh;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(HowtoModelViewHolder holder, int position) {
        holder.description.setText(howtouse.get(position).descrption);
        holder.Photo.setImageResource(howtouse.get(position).photoId);
    }

    @Override
    public int getItemCount() {
        return howtouse.size();
    }

    public static class HowtoModelViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView description;
        ImageView Photo;

        HowtoModelViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_about);
            description = (TextView)itemView.findViewById(R.id.how_to);
            Photo = (ImageView)itemView.findViewById(R.id.icon_page);
        }
    }

}