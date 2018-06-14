package pe.anthony.sidp.presentation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.UserEntity;
import pe.anthony.sidp.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn,btnRegister;
    private EditText editText, editTextPassword;
    RelativeLayout rootLayout;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        editText = findViewById(R.id.editText);
        editTextPassword =findViewById(R.id.editTextPassword);
/*        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        rootLayout = findViewById(R.id.rootLayout);

        //Eventos de click del login y el registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
//        exportarRealm();
    }

    private void exportarRealm() {
        try {
            final File file = new File(Environment.getExternalStorageDirectory().getPath().concat("/default.realm"));
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }

            realm.writeCopyTo(file);
            Toast.makeText(MainActivity.this, "Success export realm file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            realm.close();
            e.printStackTrace();
        }
    }

    private void showLoginDialog() {
        if(editText.getText().length()==0 || editTextPassword.getText().length()==0){
            Snackbar.make(rootLayout,"Ingrese todos los campos",Snackbar.LENGTH_SHORT).show();
        }else{
            validatelogin(editText.getText().toString(),editTextPassword.getText().toString());
        }
    }

    private void validatelogin(String user, String pass) {
        //check username
        RealmResults listUsername = realm
                .where(UserEntity.class)
                .equalTo("username",user).findAll();
        RealmResults<UserEntity> listaUsuarios = realm.where(UserEntity.class).findAll();
        if(listUsername.size()>0){
            //check password
            RealmResults<UserEntity> listPass = realm
                    .where(UserEntity.class)
                    .equalTo("username",user)
                    .equalTo("password",pass)
                    .findAll();
            if(listPass.size()>0){
                SessionManager sessionManager = new SessionManager(MainActivity.this);
                sessionManager.saveLoginCredenetials(user,listPass.get(0).getId());
                Intent intentSuperMarket = new Intent(this, supermarket.class);
                intentSuperMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentSuperMarket.putExtra("id",listPass.get(0).getId());
                startActivity(intentSuperMarket);
                MainActivity.this.finish();
            }else{
                Snackbar.make(rootLayout,"Nombre de usuario y contraseña no son iguales, porfavor coloque el usuario y contraseña correcto",Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(rootLayout,"usuario no encontrado",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showRegisterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Registrar");
        dialog.setMessage("Porfavor necesitamos tu correo");

        LayoutInflater inflater = LayoutInflater.from(this);
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
                    Snackbar.make(rootLayout,"Porfavor Ingrese el nombre de usuario",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(edtPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"Contraseña demasiado corta",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(edtEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Por favor ingrese un correo electronico",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                regiterUser(edtUserName,edtPassword,edtEmail);
                Snackbar.make(rootLayout,"Registro exitoso",Snackbar.LENGTH_SHORT).show();
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

    private void regiterUser(MaterialEditText edtUserName, MaterialEditText edtPassword, MaterialEditText edtEmail) {
        realm.beginTransaction();
        UserEntity user = new UserEntity(edtUserName.getText().toString(),edtPassword.getText().toString(),edtEmail.getText().toString());
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

}
