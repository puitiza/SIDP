package pe.anthony.sidp.presentation.contracts;

import com.rengwuxian.materialedittext.MaterialEditText;

public interface MainContract {

    interface  View{
        void showSnackbar(String message);
        void showToast(String message);
       /* void loginSuccessfully();
        void loginError();*/
    }

    interface Presenter{
        void showLoginDialog (String user, String pass);
        void showRegisterDialog();
        void exportarRealm();
    }
}
