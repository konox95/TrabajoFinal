package konox.actividad3;

import android.app.Activity;

import com.quickblox.users.model.QBUser;

import konox.libreria1.QBAdmin;

/**
 * Created by asus on 16/02/2017.
 */

public class DataHolder {
    public static DataHolder instance=new DataHolder();
    public QBAdmin qbAdmin;
    public boolean blIntentarLogearse =false;
    public String email="";
    public String name="";

    public DataHolder(){
    }

    public void initQBAdmin(Activity a){
        qbAdmin=new QBAdmin(a);
    }


}
