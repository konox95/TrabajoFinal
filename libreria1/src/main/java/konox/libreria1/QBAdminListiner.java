package konox.libreria1;

import android.widget.ImageView;

import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by konox on 29/11/2016.
 */

public interface QBAdminListiner {
    void sesionCreada(boolean creada);
    void logeado(boolean blLogeado, QBUser user);
    void registrado (boolean blRegistrado, QBUser user);
    void descargaPinesFinalizado(ArrayList<MiPin> pines);
    void insertarSpot(boolean blInsertado, QBCustomObject object);
    void fotosubida(boolean blUpload, QBFile qbFile);
    void cambiarContraseñas(boolean cambiada, QBUser user);


}
