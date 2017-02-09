package konox.libreria1;

import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.users.model.QBUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by konox on 29/11/2016.
 */

public interface QBAdminListiner {
    void logeado(boolean blLogeado, QBUser user);
    void registrado (boolean blRegistrado, QBUser user);
    public void descargaPinesFinalizado(ArrayList<MiPin> pines);
}
