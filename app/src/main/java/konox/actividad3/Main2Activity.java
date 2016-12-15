package konox.actividad3;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    public Button btnEspanol;
    public Button btnIngles;
    public TextView hola;
    public TextView mundo;
    public main2ActivityController main2ActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        main2ActivityController = new main2ActivityController(this);

        btnEspanol = (Button) this.findViewById(R.id.button_Espanol);
        btnIngles = (Button) this.findViewById(R.id.button_Ingles);
        hola = (TextView) this.findViewById(R.id.textView_Hola);
        mundo = (TextView) this.findViewById(R.id.textView_Mundo);

        btnIngles.setOnClickListener(main2ActivityController);
        btnEspanol.setOnClickListener(main2ActivityController);


    }
}
