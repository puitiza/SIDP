package pe.anthony.sidp.data.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.MyApplication;

/**
 * Created by ANTHONY on 5/04/2018.
 */

public class MarketEntity extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String name;

    private RealmList<ProductEntity> products;

    public MarketEntity(String name) {
        this.id = MyApplication.MarketId.incrementAndGet();
        this.products = new RealmList<ProductEntity>();
        this.name = name;
    }
    public MarketEntity() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(RealmList<ProductEntity> products) {
        this.products = products;
    }
}
