package pe.anthony.sidp.data.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pe.anthony.sidp.app.sidp;

/**
 * Created by ANTHONY on 6/04/2018.
 */

public class ProductEntity extends RealmObject {
    @PrimaryKey
    private int id;

    private int nro;

    private float precio;

    private int stock;

    public ProductEntity(int nro, float precio, int stock) {
        this.id = sidp.ProductId.incrementAndGet();
        this.nro = nro;
        this.precio = precio;
        this.stock = stock;
    }
    public ProductEntity(){}

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
