package pe.anthony.sidp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.MyApplication;

/**
 * Created by ANTHONY on 5/04/2018.
 */

public class Market extends RealmObject {

    @PrimaryKey
    private int idMarket;

    @Required
    private String name;

    public Market(String name) {
        this.idMarket = MyApplication.MarketId.incrementAndGet();
        this.name = name;
    }
    public Market() {}

    public int getIdMarket() {
        return idMarket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
