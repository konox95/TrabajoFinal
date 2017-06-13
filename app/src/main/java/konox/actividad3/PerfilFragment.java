package konox.actividad3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * Created by konox on 18/01/2017.
 */

public class PerfilFragment extends Fragment {

    public TextView tvNombrePerfil;
    public TextView tvEmailPErfil;
    public ImageButton btnEdit;
    public EditText etContraseñaPerfil;
    public ImageView img;
    public Button btnGuardarPerfil;
    public ImageButton btnGaleria, btnCamara;

    public String mPath;
    private RelativeLayout mRlView;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvNombrePerfil= (TextView) v.findViewById(R.id.tvNombreUsuarioPerfil);
        tvEmailPErfil= (TextView) v.findViewById(R.id.tvEmaileUsuarioPerfil);
        etContraseñaPerfil= (EditText) v.findViewById(R.id.etContraseñaPerfil);
        img = (ImageView) v.findViewById(R.id.ivFotoPerfil);

        btnEdit = (ImageButton)v.findViewById(R.id.ibEditar);
        btnGaleria = (ImageButton) v.findViewById(R.id.btnGaleria);
        btnCamara = (ImageButton) v.findViewById(R.id.btnCamara);
        btnGuardarPerfil = (Button) v.findViewById(R.id.btnGuardarPerfil);

        tvNombrePerfil.setText(DataHolder.instance.perfilNombre);
        Log.v("NOMBRE", tvNombrePerfil.toString());
        tvEmailPErfil.setText(DataHolder.instance.email);


        //Listener para aladir imagen a través de la galeria
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });



        return v;
    }

    //PAra introducir la imagen comporbando si es de galeria o camara ya que hay que hacer diferentes cosas
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // la foto que se hace sale en el imagenview
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(getActivity(), new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);

                                    //pathImgSelec = uri;
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    img.setImageBitmap(bitmap);
                    break;

                // imagen de la galleria
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    img.setImageURI(path);
                    mPath = path.getPath();
                    break;

            }
        }
    }

    //Abrir camara para hacer foto
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    //Para pedir permisos de camara
    private boolean mayRequestStoragePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if ((getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (getActivity().checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }
}
