package konox.actividad3;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import konox.libreria1.MiPin;


/**
 * A simple {@link Fragment} subclass.
 */
public class galeriaFragment extends Fragment {

    private ArrayList<Integer> seriesOfNumbers;
    private ArrayList<galeria> galerias;
    private AdapterA adapter;
    private  RecyclerView recyclerView;


    public galeriaFragment() {
        seriesOfNumbers = new ArrayList<Integer>();
        galerias = new ArrayList<>();
        adapter = new AdapterA(getContext(), galerias);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_galeria, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);


        //para recoger el array de textos de spots
        Resources res = getResources();
        String[] textoSpot = res.getStringArray(R.array.textoSpot);
        //para recoger el array de imagenes spots
        TypedArray imgs = getResources().obtainTypedArray(R.array.fotos);
        int count = imgs.length();
        int[] ids = new int[count];

        //para recorrer el array de ids de imagenes y insertarlas en otro arrayList
        for (int z = 0; z < ids.length; z++) {
            ids[z] = imgs.getResourceId(z, 0);
            int num = ids[z];
            seriesOfNumbers.add(num);
        }
        //parar recorrer el array de textos e ir insertando texto con foto en el array de galeria
        for (int i = 0; i < textoSpot.length; i++) {
            galeria gal = new galeria(textoSpot[i], seriesOfNumbers.get(i));
            galerias.add(gal);
        }
        adapter = new AdapterA(getContext(), galerias);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);  //displays number of cards per row
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return v;

    }

    public void data() {
        if(DataHolder.instance.contadorFav==1){
            galerias.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        String nombre = DataHolder.instance.miPin.sDificultad;
        galeria gal = new galeria(nombre);
        galerias.add(gal);
        adapter = new AdapterA(getContext(), galerias);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);  //displays number of cards per row
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}


