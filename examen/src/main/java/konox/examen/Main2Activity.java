package konox.examen;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    FragmentCategorias fCategorias;
    FragmentListaNoticias fLista;
    FragmentNoticia fNoticia;
    FragmentTransaction transaction;
    main2ActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FragmentManager fm = getSupportFragmentManager();
        controller = new main2ActivityController(this);


        fCategorias = (FragmentCategorias) fm.findFragmentById(R.id.fragment_categorias);
       //fLista = (FragmentListaNoticias) fm.findFragmentById(R.id.fragment_listanoticias);

        //fNoticia = (FragmentNoticia) fm.findFragmentById(R.id.fragment_noticia);
        transicionFragmentes(1);

    }

    public void transicionFragmentes(int ifrg) {
        FragmentManager fm = getSupportFragmentManager();
         transaction = fm.beginTransaction();

        if (ifrg == 1) {
            transaction.show(fCategorias);
            transaction.hide(fLista);
            transaction.hide(fNoticia);

        } else if (ifrg == 2) {
            transaction.show(fLista);
            transaction.hide(fNoticia);
            transaction.hide(fCategorias);
        } else if (ifrg == 3) {
            transaction.show(fNoticia);
            transaction.hide(fLista);
            transaction.hide(fCategorias);
        }
        transaction.commit();
    }
}
