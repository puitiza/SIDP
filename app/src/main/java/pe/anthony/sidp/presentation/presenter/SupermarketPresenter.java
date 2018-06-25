package pe.anthony.sidp.presentation.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.data.entities.UserEntity;
import pe.anthony.sidp.data.local.SessionManager;
import pe.anthony.sidp.presentation.activities.MapsActivity;
import pe.anthony.sidp.presentation.adapters.MakerAdapter;
import pe.anthony.sidp.presentation.contracts.SupermarketContract;

public class SupermarketPresenter implements RealmChangeListener<RealmList<MarketEntity>>,
        AdapterView.OnItemClickListener,SupermarketContract.Presenter {

    Context context;
    SupermarketContract.View view;
    ListView listView;
    private SessionManager sessionManager;
    private RealmList<MarketEntity> shops;
    private MakerAdapter adapter;

    private Realm realm;
    private int userId;
    private UserEntity user;


    public SupermarketPresenter(Context context, SupermarketContract.View view, ListView listView) {
        this.context = context;
        this.view = view;
        this.listView = listView;
    }

    public SupermarketPresenter(Context context, ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    public RealmList<MarketEntity> init(int item) {
        sessionManager = new SessionManager(context);
        realm= Realm.getDefaultInstance();
        if(((AppCompatActivity)context).getIntent().getExtras()!= null){
            userId = ((AppCompatActivity)context).getIntent().getExtras().getInt("id");
        }else{
            userId = sessionManager.getIdUser();
        }
        user = realm.where(UserEntity.class).equalTo("id",userId).findFirst();

        shops = user.getMarkets();
        shops.addChangeListener(this);
        if(item == 1){ //Esto es para mostrar la lista de tiendas que hay
            adapter = new MakerAdapter(context,shops,R.layout.list_view_market_item);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        return shops;
    }

    @Override
    public void createNewShop(String tiendaName) {
        adapter = new MakerAdapter(context,shops,R.layout.list_view_market_item); // como se esta agregando un nuevo item solo inicializo el adapter
        realm.beginTransaction();
        MarketEntity _market = new MarketEntity(tiendaName);
        realm.copyToRealm(_market);
        user.getMarkets().add(_market);
        realm.commitTransaction();
    }

    @Override
    public void editShop(String tiendaName, MarketEntity shop) {
        adapter = new MakerAdapter(context,shops,R.layout.list_view_market_item);
        realm.beginTransaction();
        shop.setName(tiendaName);
        realm.copyToRealmOrUpdate(shop);
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmList<MarketEntity> marketEntities) {
        adapter.notifyDataSetChanged();//si agregas o editar el adapter inicializado lo va a saber
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        sessionManager.saveShop(shops.get(position).getName(),shops.get(position).getId());
        Intent intent = new Intent(context,MapsActivity.class);
        intent.putExtra("id",shops.get(position).getId());
        context.startActivity(intent);
    }
}
