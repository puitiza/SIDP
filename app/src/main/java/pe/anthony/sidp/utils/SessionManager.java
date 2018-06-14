package pe.anthony.sidp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ANTHONY on 5/04/2018.
 */

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "SIDP";
    private static final String KEY_USERNAME = "username";
    private static final String ID_USERNAME = "id";
    private static final String KEY_NAMESHOP = "nameshop";
    private static final String ID_SHOP = "idshop";

    private static final String KEY_ISLOGGEDIN = "isLoggedIn";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLoginCredenetials(String username,int idName){
        editor.putString(KEY_USERNAME,username);
        editor.putInt(ID_USERNAME,idName);
        editor.putBoolean(KEY_ISLOGGEDIN,true);
        editor.commit();
    }

    public void saveShop(String nameShop,int idShop){
        editor.putString(KEY_NAMESHOP,nameShop);
        editor.putInt(ID_SHOP,idShop);
        editor.commit();
    }


    public int getIdUser(){return sharedPreferences.getInt(ID_USERNAME,50);}

    public int getIdShop(){return sharedPreferences.getInt(ID_SHOP,50);}

    public String getNameShop(){return sharedPreferences.getString(KEY_NAMESHOP,"");}

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_ISLOGGEDIN,false);
    }

    public void logout(){
        editor.putBoolean(KEY_ISLOGGEDIN,false);
        editor.clear();
        editor.commit();
    }
}
