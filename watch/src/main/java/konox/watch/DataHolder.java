package konox.watch;

import android.app.Activity;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdmin;

/**
 * Created by konox on 07/06/2017.
 */

public class DataHolder {
    public static DataHolder instance=new DataHolder();
    public QBAdmin qbAdmin;
    public ArrayList<MiPin> pi= new ArrayList<MiPin>();
    public MiPin miPin = null;
    public int id = 0;
    QBUser user;
    public double longitud = 0;
    public double latitud= 0;


    public DataHolder() {

    }

    public void initQBAdmin(Activity a) {
        qbAdmin=new QBAdmin(a);
    }

}
