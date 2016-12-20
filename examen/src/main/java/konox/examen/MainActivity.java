package konox.examen;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import konox.libreria1.QBAdmin;

public class MainActivity extends AppCompatActivity {

    LogFragment FragLogin;
    RegFragment FragRegistro;
    mainActivityController controller;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        controller = new mainActivityController(this);

        FragLogin = (LogFragment) fm.findFragmentById(R.id.fragment_log);
        FragRegistro = (RegFragment) fm.findFragmentById(R.id.fragment_reg);
        FragLogin.btnLog.setOnClickListener(controller);
        FragLogin.btnReg.setOnClickListener(controller);
        FragLogin.btnEsp.setOnClickListener(controller);
        FragLogin.btnIng.setOnClickListener(controller);
        FragRegistro.btnAceptar.setOnClickListener(controller);
        FragRegistro.btnCancelar.setOnClickListener(controller);


        cambiarFragment(1);


    }

    public  void cambiarFragment(int ifrg){
        FragmentManager fm = getSupportFragmentManager();
       transaction = fm.beginTransaction();

        if (ifrg == 1) {
            transaction.show(FragLogin);
            transaction.hide(FragRegistro);

        } else if (ifrg == 2) {
            transaction.show(FragRegistro);
            transaction.hide(FragLogin);
        }
        transaction.commit();
    }
}
