package pe.anthony.sidp.data.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.sidp;

/**
 * Created by ANTHONY on 4/04/2018.
 */

public class UserEntity extends RealmObject{

    @PrimaryKey
    private int id;

    @Required
    private String username;

    @Required
    private String password;

    @Required
    private String email;

    private RealmList<MarketEntity> markets;

    public UserEntity() {
    }

    public UserEntity(String username, String password, String email){
        this.id= sidp.UserId.incrementAndGet();
        this.markets = new RealmList<MarketEntity>();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<MarketEntity> getMarkets() {
        return markets;
    }

    public void setMarkets(RealmList<MarketEntity> markets) {
        this.markets = markets;
    }
}
