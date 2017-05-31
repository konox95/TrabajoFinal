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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.io.File;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * Created by konox on 18/01/2017.
 */

public class NuevoSpotFragment extends Fragment {
    public Button btnNewSpot;
    public Button btngaleria, btn;
    public EditText editTextSpot, editTextDesc;
    public RadioButton rdSkatepark, rdStreet;
    public RadioButton rdDificultalBaja, rdDificultalMedia,rdDificultalAlta;
    public CheckBox cbEscalera, cbBarandilla, cbBanco, cbRampa, cbCajon, cbBowl;
    public ImageView img;
    public RadioGroup rdbtipo;
    public RadioGroup rdgDificultad;

    private String mPath;
    private RelativeLayout mRlView;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nuevospot, container, false);
        //Botones para hacer foto o seleccionar imagwen de la galeria
        btngaleria = (Button) v.findViewById(R.id.btngaleria);
        btn = (Button) v.findViewById(R.id.btncamara);

        //Grupo de checkbox para seleccionar el contenido del spot
        cbEscalera = (CheckBox) v.findViewById(R.id.cbEscalera);
        cbBarandilla = (CheckBox) v.findViewById(R.id.cbBarandilla);
        cbBanco = (CheckBox) v.findViewById(R.id.cbBanco);
        cbRampa = (CheckBox) v.findViewById(R.id.cbRampa);
        cbCajon = (CheckBox) v.findViewById(R.id.cbCajon);
        cbBowl = (CheckBox) v.findViewById(R.id.cbBowl);

        //Grupo de radio buttons para seleccionar el tipo de spot
        /*rdSkatepark = (RadioButton) v.findViewById(R.id.rdSkatepark);
        rdStreet = (RadioButton) v.findViewById(R.id.rdStreet);*/

        rdbtipo =(RadioGroup) v.findViewById(R.id.rbgTipo);

        //Grupo de radio buttons para seleccionar la dificultad del spot
       /* rdDificultalBaja = (RadioButton) v.findViewById(R.id.rdDificultalBaja);
        rdDificultalMedia = (RadioButton) v.findViewById(R.id.rdDificultalMedia);
        rdDificultalAlta = (RadioButton) v.findViewById(R.id.rdDificultalAlta);*/

        rdgDificultad = (RadioGroup) v.findViewById(R.id.rbgDificultad);

        //Edit text del nombre del spot
        editTextSpot = (EditText)v.findViewById(R.id.editTextSpot);

        //Edit text de descripcion del spot que es opcional
        editTextDesc = (EditText) v.findViewById(R.id.editTextDesc);

        //Imagen que introduce el usuario para agregar el spot
        img = (ImageView) v.findViewById(R.id.set_picture);

        //Boton de agregar spot
        btnNewSpot=(Button)v.findViewById(R.id.btnOkSpot);

        // aceptar permisos
        if (mayRequestStoragePermission())
            btn.setEnabled(true);
        else
            btn.setEnabled(false);


        //Listener para aladir imagen a través de la galeria
        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }
        });




        // Listener de añadir imagen a traves de hacer una foto
        btn.setOnClickListener(new View.OnClickListener() {
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
                                    }
                                });

                        Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                        img.setImageBitmap(bitmap);
                        break;

                    // imagen de la galleria
                    case SELECT_PICTURE:
                        Uri path = data.getData();
                        img.setImageURI(path);
                        break;

                }
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


}

