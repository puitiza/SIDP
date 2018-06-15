package pe.anthony.sidp.presentation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.data.entities.UserEntity;
import pe.anthony.sidp.presentation.adapters.MakerAdapter;
import pe.anthony.sidp.data.local.SessionManager;
import pe.anthony.sidp.presentation.contracts.MainContract;
import pe.anthony.sidp.presentation.contracts.SupermarketContract;
import pe.anthony.sidp.presentation.presenter.SupermarketPresenter;

public class SupermarketActivity extends AppCompatActivity implements SupermarketContract.View {

    @BindView(R.id.listViewSupermarket)     ListView listView;
    @BindView(R.id.fabAddSuperM)     FloatingActionButton fab;
    @BindView(R.id.superMarterLayout) FrameLayout rootLayout;

    private RealmList<MarketEntity> shops;
    SupermarketContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket);

        ButterKnife.bind(this);
        presenter = new SupermarketPresenter(this,this,listView);

        shops = presenter.init();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showAlertForCreateSuperMarket();
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(shops.get(info.position).getName());
        getMenuInflater().inflate(R.menu.context_menu_supermarket_activity,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.edit_superMarket :
                 presenter.showAlertForEditSuperMarket(shops.get(info.position));
                 return true;
            default:
                 return  super.onContextItemSelected(item);
        }
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(rootLayout,message,Snackbar.LENGTH_SHORT).show();
    }
}
