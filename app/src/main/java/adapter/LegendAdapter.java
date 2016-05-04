package adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

/**
 * Created by Toshiba on 5/4/2016.
 */
public class LegendAdapter extends RecyclerView.Adapter<LegendAdapter.Item>{

    String[] name;
    int[] color;

    public LegendAdapter(String[] name, int[] color){
        this.name = name;
        this.color = color;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_legend, null);
        Item mh = new Item(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {

        holder.itemName.setText(name[position]);

        holder.img.setBackgroundColor(color[position]);

    }

    @Override
    public int getItemCount() {
        return (null != name ? name.length : 0);
    }

    public class Item extends RecyclerView.ViewHolder{

        protected TextView itemName;

        protected ImageView img;

        public Item(View itemView) {
            super(itemView);

            this.itemName = (TextView) itemView.findViewById(R.id.itemName);
            this.img = (ImageView) itemView.findViewById(R.id.item_color);;
        }
    }
}
