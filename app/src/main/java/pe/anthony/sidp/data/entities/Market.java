package pe.anthony.sidp.data.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.MyApplication;

/**
 * Created by ANTHONY on 5/04/2018.
 */

public class Market extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String name;

    private RealmList<Product> products;

    public Market(String name) {
        this.id = MyApplication.MarketId.incrementAndGet();
        this.products = new RealmList<Product>();
        this.name = name;
    }
    public Market() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }
}
