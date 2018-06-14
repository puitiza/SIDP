package pe.anthony.sidp.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.Product;

/**
 * Created by ANTHONY on 6/04/2018.
 */

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> list;
    private int layout;

    public ProductAdapter(Context context, List<Product> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Product getItem(int position) {
        return  list.get(position);
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
            vh.nro = convertView.findViewById(R.id.textViewNumero);
            vh.precio = convertView.findViewById(R.id.textViewPrecio);
            vh.stock = convertView.findViewById(R.id.textViewStock);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        Product product = list.get(position);
        vh.nro.setText(Integer.toString(product.getNro()));
        vh.precio.setText(Float.toString(product.getPrecio()));
        vh.stock.setText(Integer.toString(product.getStock()));
        return convertView;
    }

    public class ViewHolder{
        TextView nro;
        TextView precio;
        TextView stock;
    }
}
