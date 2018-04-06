package pe.anthony.sidp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import pe.anthony.sidp.R;
import pe.anthony.sidp.adapters.MakerAdapter;
import pe.anthony.sidp.models.Market;
import pe.anthony.sidp.models.User;

public class supermarket extends AppCompatActivity implements RealmChangeListener<RealmList<Market>>,AdapterView.OnItemClickListener {

    private ListView listView;
    private FloatingActionButton fab;

    private MakerAdapter adapter;
    private RealmList<Market> shops;
    private Realm realm;

    FrameLayout rootLayout;

    private int userId;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket);

        realm= Realm.getDefaultInstance();

        if(getIntent().getExtras()!= null){
            userId = getIntent().getExtras().getInt("id");}
        user = realm.where(User.class).equalTo("id",userId).findFirst();

        shops = user.getMarkets();
        shops.addChangeListener(this);

        fab = findViewById(R.id.fabAddSuperM);
        listView = findViewById(R.id.listViewSupermarket);
        adapter = new MakerAdapter(this,shops,R.layout.list_view_market_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        rootLayout = findViewById(R.id.superMarterLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreateSuperMarket();
            }
        });

    }

    private void showAlertForCreateSuperMarket() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Registrar Tienda");
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.layout_dialog_create_supermarket, null);
        dialog.setView(viewInflated);

        final EditText tienda = viewInflated.findViewById(R.id.editNameTienda);

        dialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tiendaName = tienda.getText().toString().trim();
                if(tiendaName.length()>0){
                    createNewShop(tiendaName);
                }else{
                    Snackbar.make(rootLayout,"Ingrese un nombre de Tienda",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void createNewShop(String tiendaName) {
        realm.beginTransaction();
        Market _market = new Market(tiendaName);
        realm.copyToRealm(_market);
        user.getMarkets().add(_market);
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmList<Market> markets) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this,MapsActivity.class);
//        intent.putExtra("id",tiendas.get(position).getIdMarket());
        startActivity(intent);
        supermarket.this.finish();
    }
}
