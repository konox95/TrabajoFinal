package konox.actividad3;

import android.media.Image;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditosFragment extends Fragment {

    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    TimerTask timertask;
    Timer timer;
    public MainActivity mainActivity;

    public CreditosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.fragment_creditos, container, false);
        img1 = (ImageView) v.findViewById(R.id.imgPrimeraCargando);
        img2 = (ImageView) v.findViewById(R.id.imgSegundaAndroid);
        img3 = (ImageView) v.findViewById(R.id.imgTerceraUtad);
        timer = new Timer();
        timertask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (img1.getVisibility() == View.VISIBLE) {
                            img2.setVisibility(View.VISIBLE);
                            img1.setVisibility(View.INVISIBLE);
                        } else if (img2.getVisibility() == View.VISIBLE) {
                            img3.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                if (img3.getVisibility() == View.VISIBLE) {
                    timer.cancel();
                    mainActivity.cambiarFragment(1);
                }

            }
        };
        timer.schedule(timertask, 2000, 2000);



        return v;
    }

}
