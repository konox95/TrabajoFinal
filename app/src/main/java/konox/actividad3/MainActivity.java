package konox.actividad3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;

import java.util.Map;
import java.util.Set;

import konox.libreria1.QBAdmin;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {
    RegistroFragment registroFragment;
    LoginFragment loginFragment;
    CreditosFragment creditosFragment;
    mainActivityContolador controlador;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.instance.initQBAdmin(this);

        FragmentManager fm = getSupportFragmentManager();
        controlador = new mainActivityContolador(this);

        registroFragment = (RegistroFragment) fm.findFragmentById(R.id.Regfragment);
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.LogFragment);
        creditosFragment = (CreditosFragment) fm.findFragmentById(R.id.Credfragment);
        loginFragment.btnIniciar.setOnClickListener(controlador);
        loginFragment.btnRegistrarse.setOnClickListener(controlador);
        registroFragment.btnAceptar.setOnClickListener(controlador);

        cambiarFragment(3);


        }


    public void cambiarFragment(int ifrg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (ifrg == 1) {
            if(controlador.blAutoLoginCorrect){
                Intent inten = new Intent(this, Main3ActivityDrawer.class);
                startActivity(inten);
            }
            else{
                transaction.show(loginFragment);
                transaction.hide(registroFragment);
                transaction.hide(creditosFragment);
            }
        } else if (ifrg == 2) {
            transaction.show(registroFragment);
            transaction.hide(loginFragment);
            transaction.hide(creditosFragment);
        } else if (ifrg == 3) {
            transaction.show(creditosFragment);
            transaction.hide(registroFragment);
            transaction.hide(loginFragment);
        }
        transaction.commit();

    }

}
