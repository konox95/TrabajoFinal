package konox.actividad3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class pinFragment extends Fragment {
    TextView txt_nombre,txt_descripcion,txt_tipo,txt_dificultad;
    ImageButton imgBtnClose;
    ImageButton imgBtnFavorites;
    Button btnEmpezar;
    ImageView imgSpotPinFragment;
    ImageView icBanco, icBarandilla, icBowl, icCajon, icEscalera, icRampa;


    public pinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pin, container, false);
        txt_nombre = (TextView) v.findViewById(R.id.txtName);
        txt_descripcion = (TextView) v.findViewById(R.id.txtDescript);
        txt_tipo = (TextView) v.findViewById(R.id.txtTipo);
        txt_dificultad = (TextView) v.findViewById(R.id.txtDificultad);
        imgSpotPinFragment = (ImageView) v.findViewById(R.id.imgSpot);
        imgBtnClose = (ImageButton)v.findViewById(R.id.imgBtnClose);
        btnEmpezar = (Button) v.findViewById(R.id.btnEmpezar);
        imgBtnFavorites = (ImageButton)v.findViewById(R.id.imgBtnFavorites);
        icBanco = (ImageView) v.findViewById(R.id.icBanco);
        icBarandilla = (ImageView) v.findViewById(R.id.icBarandilla);
        icBowl = (ImageView) v.findViewById(R.id.icBowl);
        icCajon = (ImageView) v.findViewById(R.id.icCajon);
        icEscalera = (ImageView) v.findViewById(R.id.icEscalera);
        icRampa = (ImageView) v.findViewById(R.id.icRampa);

        return v;
    }

}
