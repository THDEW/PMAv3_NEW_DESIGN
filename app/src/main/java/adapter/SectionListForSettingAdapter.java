package adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;

import dialog.RowDataDialog;
import model.ItemDataModel;
import model.SingleItemModel;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class SectionListForSettingAdapter extends RecyclerView.Adapter<SectionListForSettingAdapter.SingleItemRowHolder> {

    private ArrayList<ItemDataModel> itemsList;
    private ArrayList<ItemDataModel>[] itemsListSupport;
    private Context mContext;
    private FragmentManager fm;
    private String rowName;
    private int condition;

    public SectionListForSettingAdapter(Context context,ArrayList<ItemDataModel>[] itemsListSupport, ArrayList<ItemDataModel> itemsList, FragmentManager fm, String rowName, int i) {
        this.itemsList = itemsList;
        this.itemsListSupport = itemsListSupport;
        this.mContext = context;
        this.fm = fm;
        this.rowName = rowName;
        condition = i;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_column_row, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        ItemDataModel singleItem = itemsList.get(i);

        holder.ownData = singleItem;

        holder.rowNameTv.setText(singleItem.getName());


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView rowNameTv;

        protected RelativeLayout rl;

        protected ItemDataModel ownData;

        public SingleItemRowHolder(View view) {
            super(view);

            rowNameTv = (TextView) view.findViewById(R.id.rowName);

            rl = (RelativeLayout) view.findViewById(R.id.rowItem);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RowDataDialog dialog = new RowDataDialog(rowName, condition, false, fm, itemsListSupport, ownData);
                    dialog.show(fm, "Tag");
                    dialog.setCancelable(false);
                }
            });

        }

    }

}
