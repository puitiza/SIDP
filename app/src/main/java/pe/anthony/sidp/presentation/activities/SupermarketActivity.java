package pe.anthony.sidp.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.presentation.contracts.SupermarketContract;
import pe.anthony.sidp.presentation.fragments.CreateSuperMarketDialog;
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

        shops = presenter.init(1);//el numero significa para saber cuando se inicializa

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CreateSuperMarketDialog dialog = CreateSuperMarketDialog.newInstance(1,null);//Le paso el numero para saber si es crear Tienda o Editar Tienda
                //CreateSuperMarketDialog dialog = new CreateSuperMarketDialog();
                dialog.show(fm, "shop");

                //presenter.showAlertForCreateSuperMarket();
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
                    FragmentManager fm = getSupportFragmentManager();
                    CreateSuperMarketDialog dialog = CreateSuperMarketDialog.newInstance(2,shops.get(info.position));
                    dialog.show(fm, "shop");
                 //presenter.showAlertForEditSuperMarket(shops.get(info.position));
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
