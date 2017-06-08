package konox.actividad3;

import android.app.Activity;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdmin;

/**
 * Created by konox on 16/02/2017.
 */

public class DataHolder {
    public static DataHolder instance=new DataHolder();
    public QBAdmin qbAdmin;
    public boolean blIntentarLogearse =false;
    public String email="";
    public String name="";
    public String pwd = "";
    public String urlImage = "";
    public boolean cerraSesion =false;
    public ArrayList<MiPin> pi= new ArrayList<MiPin>();
    public int id = 0;
    public MiPin miPin = null;
    QBUser user;
    public double longitud = 0;
    public double latitud= 0;

    public DataHolder(){

    }

    public void initQBAdmin(Activity a){
        qbAdmin=new QBAdmin(a);
    }


}
