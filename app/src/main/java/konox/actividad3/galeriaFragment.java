package konox.actividad3;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class galeriaFragment extends Fragment {


    private ArrayList<Integer> seriesOfNumbers;



    public galeriaFragment() {
        seriesOfNumbers = new ArrayList<Integer>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_galeria, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv);

       ArrayList<galeria>  galerias = new ArrayList<>();

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
        for (int i = 0; i < textoSpot.length; i++){
                galeria gal = new galeria(textoSpot[i], seriesOfNumbers.get(i));
                galerias.add(gal);
        }
        AdapterA adapter = new AdapterA(getContext(), galerias);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);  //displays number of cards per row
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return v;
    }

}


