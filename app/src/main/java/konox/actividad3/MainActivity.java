package konox.actividad3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import konox.libreria1.QBAdmin;

public class MainActivity extends AppCompatActivity {
    RegistroFragment registroFragment;
    LoginFragment loginFragment;
    CreditosFragment creditosFragment;
    FragmentTransaction transaction;
    QBAdmin qbAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

        registroFragment = (RegistroFragment) fm.findFragmentById(R.id.Regfragment);
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.LogFragment);
        creditosFragment = (CreditosFragment) fm.findFragmentById(R.id.Credfragment);
        cambiarFragment(3);

        }

    public void cambiarFragment(int ifrg){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if(ifrg==1){
            transaction.show(loginFragment);
            transaction.hide(registroFragment);
            transaction.hide(creditosFragment);
        }else if(ifrg==2){
            transaction.show(registroFragment);
            transaction.hide(loginFragment);
            transaction.hide(creditosFragment);
        }else if(ifrg==3){
            transaction.show(creditosFragment);
            transaction.hide(registroFragment);
            transaction.hide(loginFragment);
        }
        transaction.commit();

    }
}
