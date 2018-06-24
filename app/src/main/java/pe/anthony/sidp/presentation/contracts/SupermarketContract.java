package pe.anthony.sidp.presentation.contracts;

import io.realm.RealmList;
import pe.anthony.sidp.data.entities.MarketEntity;

public interface SupermarketContract {

    interface View{
        void showSnackbar(String message);
    }

    interface Presenter{
        RealmList<MarketEntity> init();
        void createNewShop(String tiendaName);
        void editShop(String tiendaName, MarketEntity shop);
    }
}
