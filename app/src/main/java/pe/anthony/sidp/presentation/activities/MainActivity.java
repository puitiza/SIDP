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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import pe.anthony.sidp.R;
import pe.anthony.sidp.data.entities.UserEntity;
import pe.anthony.sidp.presentation.contracts.MainContract;
import pe.anthony.sidp.presentation.presenter.MainPresenter;
import pe.anthony.sidp.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    //button
    @BindView(R.id.btnSignIn)       Button btnSignIn;
    @BindView(R.id.btnRegister)     Button btnRegister;
    //Edittext
    @BindView(R.id.editText)        EditText editText;
    @BindView(R.id.editTextPassword)EditText editTextPassword;
    //RelativeLayout
    @BindView(R.id.rootLayout)RelativeLayout rootLayout;

    MainContract.Presenter presenter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        presenter = new MainPresenter(this,this,realm);
/*      realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        //Eventos de click del login y el registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showRegisterDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showLoginDialog(editText.getText().toString(),editTextPassword.getText().toString());
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


    @Override
    public void showSnackbar(String message) {
        Snackbar.make(rootLayout,message,Snackbar.LENGTH_SHORT).show();
    }
}
