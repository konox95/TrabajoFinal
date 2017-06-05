package konox.libreria1;

/**
 * Created by konox on 07/02/2017.
 */

public class MiPin {
    public double dbLatitud;
    public double dbLongitud;
    public String sNombre;
    public String sDescripcion;
    public String sTipo;
    public String sDificultad;

    public MiPin(double dblat,double dblon,String snom){
        dbLatitud=dblat;
        dbLongitud=dblon;
        sNombre=snom;
    }

    public MiPin(double dblat,double dblon,String snom,String sdescripcion,String stipo,String sdificultad){
        dbLatitud=dblat;
        dbLongitud=dblon;
        sNombre=snom;
        sDescripcion=sdescripcion;
        sTipo=stipo;
        sDificultad=sdificultad;
    }

}
