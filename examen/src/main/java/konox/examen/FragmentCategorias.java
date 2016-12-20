package konox.examen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by konox on 20/12/2016.
 */

public class FragmentCategorias extends Fragment {
    public Button btnGeneral;
    public Button btnPoliticas;
    public Button btnEconomia;
    public Button btnDeportes;
    public Button btnOcio;
    public Button btnTecnologia;
public FragmentCategorias(){

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.categorias_fragment, container, false);
        btnGeneral = (Button) v.findViewById(R.id.btnGeneral);
        btnPoliticas = (Button) v.findViewById(R.id.btnPolitica);
        btnEconomia = (Button) v.findViewById(R.id.btnEconomia);
        btnDeportes = (Button) v.findViewById(R.id.btnDeportes);
        btnOcio = (Button) v.findViewById(R.id.btnOcio);
        btnTecnologia = (Button) v.findViewById(R.id.btnTecnologia);
        return v;
    }
}
