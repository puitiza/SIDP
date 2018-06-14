package pe.anthony.sidp.presentation.contracts;

import com.rengwuxian.materialedittext.MaterialEditText;

public interface MainContract {

    interface  View{
        void showSnackbar(String message);
       /* void loginSuccessfully();
        void loginError();*/
    }

    interface Presenter{
        void showLoginDialog (String user, String pass);
        void showRegisterDialog();
        void validatelogin(String user, String pass);
        void regiterUser(MaterialEditText edtUserName, MaterialEditText edtPassword, MaterialEditText edtEmail);
    }
}
