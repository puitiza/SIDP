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
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.presentation.contracts.SupermarketContract;
import pe.anthony.sidp.presentation.presenter.SupermarketPresenter;

public class CreateSuperMarketDialog extends DialogFragment {

    @BindView(R.id.btn_registarTienda)    Button btn_registarTienda;
    @BindView(R.id.btn_editarTienda)      Button btn_editarTienda;
    @BindView(R.id.editNameTienda)        EditText editNameTienda;
    @BindView(R.id.txt_Editar)            TextView txt_Editar;
    @BindView(R.id.txt_Registrar)         TextView txt_Registrar;

    ListView listView;
    SupermarketContract.Presenter presenter;

//  Esto es para cuando quieres pasarle datos al dialogFragment
    public static CreateSuperMarketDialog newInstance(int item, MarketEntity shop){
        CreateSuperMarketDialog frag = new CreateSuperMarketDialog();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("item", item);
        args.putSerializable("shop",shop);
        frag.setArguments(args);
        return frag;
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
        btn_editarTienda.setBackgroundResource(outValue.resourceId);
//      -----------------------------------------------------------------------------------------------------------------------------------------
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
        int item = getArguments().getInt("item", 0);
        presenter.init(2);//Como no es el 1 no va a volver a llamar al setAdapter en el listView, solo inicializa los otros valores
        switch (item){
            case 1 : // Si va a registrar una tienda
                        setVisibility(item);
                        btn_registarTienda.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String tiendaName = editNameTienda.getText().toString().trim();
                                if(tiendaName.length()>0){
                                    //Si quiero usar el presenter tengo que volver a instancearlo
                                    presenter.createNewShop(tiendaName);
                                }else{
                                    Toast.makeText(getContext(), "Ingrese un nombre de Tienda", Toast.LENGTH_SHORT).show();
                                }
                                getDialog().dismiss();
                            }
                        });
                        break;
            case 2: //Si va a editar una tienda
                        setVisibility(item);
                        final MarketEntity tienda = (MarketEntity) getArguments().getSerializable("shop");
//                      ------------------------------------Pone el cursor en la ultima posicion de la palabra-------------------------------
                        editNameTienda.requestFocus();
                        editNameTienda.setText(tienda.getName());
                        editNameTienda.setSelection(editNameTienda.length());
//                      ---------------------------------------------------------------------------------------------------------------------
                        btn_editarTienda.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String tiendaName = editNameTienda.getText().toString().trim();
                                if(tiendaName.isEmpty()){
                                    Toast.makeText(getContext(), "Ingrese un nombre de Tienda para editar", Toast.LENGTH_SHORT).show();
                                }else if(tiendaName.equals(tienda.getName())){
                                    Toast.makeText(getContext(), "El nombre ingresado es el mismo", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    presenter.editShop(tiendaName,tienda);
                                }
                                getDialog().dismiss();
                            }
                        });
                        break;
            default:    break;
        }

    }

    public void setVisibility(int item){
       switch (item){
           case 1 :
               txt_Registrar.setVisibility(View.VISIBLE);
               txt_Editar.setVisibility(View.GONE);
               btn_registarTienda.setVisibility(View.VISIBLE);
               btn_editarTienda.setVisibility(View.GONE);
               break;
           case 2 :
               txt_Registrar.setVisibility(View.GONE);
               txt_Editar.setVisibility(View.VISIBLE);
               btn_registarTienda.setVisibility(View.GONE);
               btn_editarTienda.setVisibility(View.VISIBLE);
               break;
           default:
               break;
       }
    }

}
