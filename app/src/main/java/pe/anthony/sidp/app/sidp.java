package pe.anthony.sidp.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import pe.anthony.sidp.data.entities.MarketEntity;
import pe.anthony.sidp.data.entities.ProductEntity;
import pe.anthony.sidp.data.entities.UserEntity;

/**
 * Created by ANTHONY on 4/04/2018.
 */
public class sidp extends Application{

    public  static AtomicInteger UserId = new AtomicInteger();
    public  static AtomicInteger MarketId = new AtomicInteger();
    public  static AtomicInteger ProductId = new AtomicInteger();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //Voy a configurar cosas global de la app y esto se va configurar cuando se inice la app
    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        UserId = getIdByTable(realm, UserEntity.class);
        MarketId = getIdByTable(realm, MarketEntity.class);
        ProductId = getIdByTable(realm, ProductEntity.class);
        realm.close();
    }

    private void setUpRealmConfig(){
        // The default Realm file is "default.realm" in Context.getFilesDir();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();
        //si hay mas de un registro lo que signnifica que hay un valor de ID asignado
        if(result.size()> 0) {
            return new AtomicInteger(result.max("id").intValue());
        }else {
            return new AtomicInteger();
        }
    }
}
