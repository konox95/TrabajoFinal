package konox.actividad3;


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


    public galeriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_galeria, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv);

       ArrayList<galeria>  galerias = new ArrayList<>();

        for (int i = 20; i > 0; i--){
            galeria gal = new galeria("nombre"+i, R.mipmap.ic_launcher);
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


