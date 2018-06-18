package pe.anthony.sidp.presentation.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.internal.IOException;
import pe.anthony.sidp.R;
import pe.anthony.sidp.presentation.contracts.MainContract;
import pe.anthony.sidp.presentation.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    //button
    @BindView(R.id.btnSignIn)       Button btnSignIn;
    @BindView(R.id.btnRegister)     Button btnRegister;
    //Edittext
    @BindView(R.id.editText)        EditText editText;
    @BindView(R.id.editTextPassword)EditText editTextPassword;
    //RelativeLayout
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;

    MainContract.Presenter presenter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        presenter = new MainPresenter(this,this,realm);
/*      realm.beginTransaction(); Para limpiar la base de datos
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
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(rootLayout,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
