package konox.actividad3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Main3ActivityDrawer extends AppCompatActivity {

    main3ActivityDrawerController main3ActivityDrawerController;
    MapaFragment mapa;
    NuevoSpotFragment nuevoSpotFragment;
    PerfilFragment perfil;
    FragmentManager fm;
    NavigationView navigationView;
    MenuItem perfilNav;
    MenuItem spotNav;
    MenuItem mapaNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_drawer);
        fm = getSupportFragmentManager();
        main3ActivityDrawerController = new main3ActivityDrawerController(this);

        //MENU ITEMS
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Log.v("AA", navigationView + "");
        perfilNav = navigationView.getMenu().getItem(2);
        spotNav = navigationView.getMenu().getItem(1);
        mapaNav = navigationView.getMenu().getItem(0);

        //main3ActivityDrawerController.onNavigationItemSelected(perfilNav);
        //main3ActivityDrawerController.onNavigationItemSelected(spotNav);
        //main3ActivityDrawerController.onNavigationItemSelected(mapaNav);


        //FRAGMENTS
        mapa = (MapaFragment) fm.findFragmentById(R.id.frMapa);
        nuevoSpotFragment = (NuevoSpotFragment) fm.findFragmentById(R.id.frSpotNuevo);
        perfil = (PerfilFragment) fm.findFragmentById(R.id.frPerfil);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(main3ActivityDrawerController);

        cambiarFragmentDrawer(1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3_activity_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cambiarFragmentDrawer(int frgm) {
        FragmentTransaction transaction = fm.beginTransaction();

        if (frgm == 1) {
            transaction.show(mapa);
            transaction.hide(nuevoSpotFragment);
            transaction.hide(perfil);

        } else if (frgm == 2) {
            transaction.show(nuevoSpotFragment);
            transaction.hide(mapa);
            transaction.hide(perfil);
        } else if (frgm == 3) {
            transaction.show(perfil);
            transaction.hide(mapa);
            transaction.hide(nuevoSpotFragment);
        }

        transaction.commit();

    }
}
