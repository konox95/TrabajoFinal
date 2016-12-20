package konox.examen;



import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    FragmentCategorias fCategorias;
    FragmentListaNoticias fLista;
    FragmentNoticia fNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FragmentManager fm = getSupportFragmentManager();

        fCategorias = (FragmentCategorias) fm.findFragmentById(R.id.fragment_categorias);
        fLista = (FragmentListaNoticias) fm.findFragmentById(R.id.fragment_listanoticias);
        fNoticia = (FragmentNoticia) fm.findFragmentById(R.id.fragment_noticia);
    }
}
