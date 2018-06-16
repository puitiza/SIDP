package pe.anthony.sidp.presentation.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pe.anthony.sidp.R;

public class CreateSuperMarketDialog extends DialogFragment {

    @BindView(R.id.txt_registarTienda)
    TextView txt_registarTienda;
    @BindView(R.id.editNameTienda)
    EditText editNameTienda;

    //SupermarketContract.View view;
    //SupermarketContract.Presenter presenter;


    /*public static CreateSuperMarketDialog newInstance(View view, SupermarketContract.Presenter presenter){
        CreateSuperMarketDialog f = new CreateSuperMarketDialog();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", view);
        f.setArguments(args);

        return f;
    }*/



    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_create_supermarket, container, false);
        ButterKnife.bind(this, v);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        txt_registarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tiendaName = editNameTienda.getText().toString().trim();
                if(tiendaName.length()>0){
                    getDialog().dismiss();
                }else{
                    //
                }
            }
        });
        return v;
    }
}
