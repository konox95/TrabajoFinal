package konox.actividad3;

import android.util.Log;
import android.view.View;

import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.HashMap;

import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;

import static android.R.attr.key;
import static android.R.attr.logo;
import static android.R.attr.value;

/**
 * Created by konox on 13/12/2016.
 */

public class main2ActivityController implements View.OnClickListener, QBAdminListiner {
    Main2Activity vista2;
    QBAdmin qbAdmin;
    String Lang = null;

    public main2ActivityController(Main2Activity vista2) {
        this.vista2 = vista2;
        qbAdmin = new QBAdmin(this, vista2);
    }

    @Override
    public void onClick(View view) {

        Log.v("main2ActivityController","HOLA");
        if (view.getId() == vista2.btnEspanol.getId()) {
            Lang = "ES";
            qbAdmin.descargDatos(Lang);

        }
        if (view.getId() == vista2.btnIngles.getId()) {
            Lang = "EN";
            qbAdmin.descargDatos(Lang);
        }
    }




    @Override
    public void logeado(boolean blLogeado, QBUser user) {
    }

    @Override
    public void registrado(boolean blRegistrado, QBUser user) {
    }

    @Override
    public void datosdescarg(HashMap<Integer, String> datos) {
        vista2.hola.setText(datos.get(1));
        vista2.mundo.setText(datos.get(2));
        vista2.btnEspanol.setText(datos.get(3));
        vista2.btnIngles.setText(datos.get(4));
    }
}
