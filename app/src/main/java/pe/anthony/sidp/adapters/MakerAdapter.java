package pe.anthony.sidp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pe.anthony.sidp.R;
import pe.anthony.sidp.models.Market;

/**
 * Created by ANTHONY on 5/04/2018.
 */

public class MakerAdapter extends BaseAdapter {

    private Context context;
    private List<Market> list;
    private int layout;

    public MakerAdapter(Context context, List<Market> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.nameMarket = convertView.findViewById(R.id.textViewMarket);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        Market market = list.get(position);
        vh.nameMarket.setText(market.getName());
        return convertView;
    }

    public class ViewHolder{
        TextView nameMarket;
    }
}
