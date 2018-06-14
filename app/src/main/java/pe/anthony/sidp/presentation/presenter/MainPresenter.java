package pe.anthony.sidp.presentation.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import io.realm.Realm;
import io.realm.RealmResults;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.UserEntity;
import pe.anthony.sidp.presentation.activities.SupermarketActivity;
import pe.anthony.sidp.presentation.contracts.MainContract;
import pe.anthony.sidp.utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {

    Context context;
    MainContract.View view;
    private Realm realm;

    public MainPresenter(Context context, MainContract.View view, Realm realm) {
        this.context = context;
        this.view = view;
        this.realm = realm;
    }

    @Override
    public void showLoginDialog(String user, String pass) {
        if(user.length()==0 || pass.length()==0){
            view.showSnackbar("Ingrese todos los campos");
        }else{
            validatelogin(user,pass);
        }
    }

    @Override
    public void showRegisterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Registrar");
        dialog.setMessage("Porfavor necesitamos tu correo");

        LayoutInflater inflater = LayoutInflater.from(context);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText edtUserName = register_layout.findViewById(R.id.edtUserName);
        final MaterialEditText edtPassword = register_layout.findViewById(R.id.edtPassword);
        final MaterialEditText edtEmail = register_layout.findViewById(R.id.edtEmail);
        dialog.setView(register_layout);
        dialog.setPositiveButton("registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(TextUtils.isEmpty(edtUserName.getText().toString())){
                    view.showSnackbar("Porfavor Ingrese el nombre de usuario");
                    return;
                }
                if(edtPassword.getText().toString().length()<6){
                    view.showSnackbar("Contraseña demasiado corta");
                    return;
                }
                if(TextUtils.isEmpty(edtEmail.getText().toString())){
                    view.showSnackbar("Por favor ingrese un correo electronico");
                    return;
                }
                regiterUser(edtUserName,edtPassword,edtEmail);
                view.showSnackbar("Registro exitoso");
            }
        });
        dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void validatelogin(String user, String pass) {
        //check username
        RealmResults listUsername = realm.where(UserEntity.class)
                                         .equalTo("username",user).findAll();
//        RealmResults<UserEntity> listaUsuarios = realm.where(UserEntity.class).findAll();
        if(listUsername.size()>0){
            //check password
            RealmResults<UserEntity> listPass = realm
                    .where(UserEntity.class)
                    .equalTo("username",user)
                    .equalTo("password",pass)
                    .findAll();
            if(listPass.size()>0){
                SessionManager sessionManager = new SessionManager(context);
                sessionManager.saveLoginCredenetials(user,listPass.get(0).getId());
                Intent intentSuperMarket = new Intent(context, SupermarketActivity.class);
                intentSuperMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentSuperMarket.putExtra("id",listPass.get(0).getId());
                context.startActivity(intentSuperMarket);
                ((AppCompatActivity)context).finish();
            }else{
                view.showSnackbar("Nombre de usuario y contraseña no son iguales, porfavor coloque el usuario y contraseña correcto");
            }
        }else{
            view.showSnackbar("usuario no encontrado");
        }
    }

    @Override
    public void regiterUser(MaterialEditText edtUserName, MaterialEditText edtPassword, MaterialEditText edtEmail) {
        realm.beginTransaction();
        UserEntity user = new UserEntity(edtUserName.getText().toString(),edtPassword.getText().toString(),edtEmail.getText().toString());
        realm.copyToRealm(user);
        realm.commitTransaction();
    }
}
