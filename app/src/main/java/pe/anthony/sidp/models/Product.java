package pe.anthony.sidp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.MyApplication;

/**
 * Created by ANTHONY on 6/04/2018.
 */

public class Product extends RealmObject {
    @PrimaryKey
    private int id;

    private int nro;

    private float precio;

    private int stock;

    public Product(int nro, float precio, int stock) {
        this.id = MyApplication.ProductId.incrementAndGet();
        this.nro = nro;
        this.precio = precio;
        this.stock = stock;
    }
    public Product(){}

    public int getId() {
        return id;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
