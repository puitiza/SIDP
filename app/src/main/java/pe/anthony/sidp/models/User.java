package pe.anthony.sidp.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import pe.anthony.sidp.app.MyApplication;

/**
 * Created by ANTHONY on 4/04/2018.
 */

public class User extends RealmObject{

    @PrimaryKey
    private int id;

    @Required
    private String username;

    @Required
    private String password;

    @Required
    private String email;

    private RealmList<Market> markets;

    public User() {
    }

    public User(String username, String password, String email){
        this.id= MyApplication.UserId.incrementAndGet();
        this.markets = new RealmList<Market>();
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

    public RealmList<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(RealmList<Market> markets) {
        this.markets = markets;
    }
}
