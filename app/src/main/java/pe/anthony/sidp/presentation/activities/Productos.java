package pe.anthony.sidp.presentation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.data.entities.ProductEntity;
import pe.anthony.sidp.presentation.adapters.ProductAdapter;
import pe.anthony.sidp.utils.SessionManager;

public class Productos extends AppCompatActivity implements RealmChangeListener<RealmList<ProductEntity>> {

    private ListView listView;
    private FloatingActionButton fab;

    private ProductAdapter adapter;
    private RealmList<ProductEntity> products;
    private Realm realm;

    FrameLayout rootLayout;

    private int marketId;
    private MarketEntity market;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        sessionManager = new SessionManager(this);
        realm= Realm.getDefaultInstance();
        marketId = sessionManager.getIdShop();
        market = realm.where(MarketEntity.class).equalTo("id",marketId).findFirst();
        products = market.getProducts();
        products.addChangeListener(this);
        this.setTitle(market.getName());


        fab = findViewById(R.id.fabAddProduc);
        listView = findViewById(R.id.listViewProdutos);
        adapter = new ProductAdapter(this,products,R.layout.list_view_producto_item);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
        rootLayout = findViewById(R.id.productosLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreateProducts();
            }
        });
        
    }

    private void showAlertForCreateProducts() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Registrar informacion de productos");
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.layout_dialog_create_productos, null);
        dialog.setView(viewInflated);

        final EditText cantidad = viewInflated.findViewById(R.id.editNameCantidad);
        final EditText precio = viewInflated.findViewById(R.id.editNamePrecio);
        final EditText stock = viewInflated.findViewById(R.id.editNameStock);

        dialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String cantidadProduct = cantidad.getText().toString().trim();
                String precioProduct = precio.getText().toString().trim();
                String stockProduct = stock.getText().toString().trim();

                if(!precioProduct.isEmpty()){
                    float precio = Float.valueOf(precioProduct);
                    if(!stockProduct.isEmpty()){
                        int stock = Integer.parseInt(stockProduct);
                        if(!cantidadProduct.isEmpty()){
                            int cantidad = Integer.parseInt(cantidadProduct);
                            createNewProducts(cantidad,precio, stock);
                        }else{
                            Snackbar.make(rootLayout,"Ingrese la cantidad del producto",Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        Snackbar.make(rootLayout,"Ingrese un numero de stock de product",Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    Snackbar.make(rootLayout,"Ingrese un precio del producto",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void createNewProducts(int cantidad, float precio, int stock) {
        realm.beginTransaction();
        ProductEntity _producto = new ProductEntity(cantidad,precio,stock);
        realm.copyToRealm(_producto);
        market.getProducts().add(_producto);
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmList<ProductEntity> products) {
        adapter.notifyDataSetChanged();
    }
}
