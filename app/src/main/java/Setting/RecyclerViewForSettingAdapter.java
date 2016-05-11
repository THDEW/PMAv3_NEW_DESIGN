package Setting;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;

import java.util.ArrayList;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class RecyclerViewForSettingAdapter extends RecyclerView.Adapter<RecyclerViewForSettingAdapter.ItemRowHolder> {


    private ArrayList<TableDataModel> dataList;
    private Context mContext;
    private FragmentManager fm;

    private Connection connection;

    public RecyclerViewForSettingAdapter(Context context, ArrayList<TableDataModel> dataList, FragmentManager fm, Connection connection) {
        this.dataList = dataList;
        this.mContext = context;
        this.fm = fm;
        this.connection = connection;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_column, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        SectionListForSettingAdapter itemListDataAdapter = new SectionListForSettingAdapter(mContext, itemRowHolder.groupOfData ,singleSectionItems, fm, sectionName, i, connection);

        itemRowHolder.backUp = itemListDataAdapter;

        itemRowHolder.condition = i;

        itemRowHolder.columnTitle.setText(sectionName);

        itemRowHolder.img.setBackgroundResource(R.drawable.down_arrow);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        itemRowHolder.recycler_view_list.setAdapter(null);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder{

        protected TextView columnTitle;

        protected RecyclerView recycler_view_list;

        protected RelativeLayout expandableButton;

        protected ImageView img;

        protected Button addBt;

        protected int condition;

        boolean open = false;

        ArrayList<ItemDataModel>[] groupOfData;

        SectionListForSettingAdapter backUp;

            public ItemRowHolder(View view){
                super(view);

                this.columnTitle = (TextView) view.findViewById(R.id.columnTitle);
                this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list2);
                this.img = (ImageView) view.findViewById(R.id.expandSignal2);
                this.addBt = (Button) view.findViewById(R.id.addBt);
                expandableButton = (RelativeLayout) view.findViewById(R.id.expandBt);

                groupOfData = new ArrayList[6];
                for(int i = 0; i < dataList.size(); i++){
                    groupOfData[i] = dataList.get(i).getAllItemsInSection();
                }

                expandableButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });

                addBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RowDataDialog dialog = new RowDataDialog(columnTitle.getText().toString(), condition, true, fm, groupOfData, connection);
                        dialog.show(fm, "Tag");
                        dialog.setCancelable(false);
                    }
                });

            }

    }
}
