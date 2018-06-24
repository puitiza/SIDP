package pe.anthony.sidp.presentation.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.anthony.sidp.R;
import pe.anthony.sidp.presentation.contracts.SupermarketContract;
import pe.anthony.sidp.presentation.presenter.SupermarketPresenter;

public class CreateSuperMarketDialog extends DialogFragment {

    @BindView(R.id.btn_registarTienda)
    Button btn_registarTienda;
    @BindView(R.id.editNameTienda)
    EditText editNameTienda;
    ListView listView;
    SupermarketContract.Presenter presenter;

//  Esto es para cuando quieres pasarle datos al dialogFragment
    public static CreateSuperMarketDialog newInstance(String tienda){
        CreateSuperMarketDialog f = new CreateSuperMarketDialog();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("tienda", tienda);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog3);
        setCancelable(true);
        listView = getActivity().findViewById(R.id.listViewSupermarket);
        presenter = new SupermarketPresenter(getContext(),listView);
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_create_supermarket, container, false);
        ButterKnife.bind(this, v);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //Utiliza mas este metodo paa ocultar el teclado que el del XML
//      ------------------------------Esto es para agregar el efecto Ripple --------------------------------------------------------------------------
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        btn_registarTienda.setBackgroundResource(outValue.resourceId);
//      -----------------------------------------------------------------------------------------------------------------------------------------
        btn_registarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tiendaName = editNameTienda.getText().toString().trim();
                if(tiendaName.length()>0){
                    presenter.init();//Si quiero usar el presenter tengo que volver a instancearlo
                    presenter.createNewShop(tiendaName);
                }else{
                    Toast.makeText(getContext(), "Ingrese un nombre de Tienda", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
    }

}
