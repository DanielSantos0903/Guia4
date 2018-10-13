package ejemplo1.com.guia4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.Toast;



import java.io.FileDescriptor;
import java.io.IOException;


@SuppressWarnings("ALL")
public class Principal extends AppCompatActivity {

    private int PICK_PHOTO1=2;
    private int PICK_PHOTO2=3;
    ImageView imgFondo1;
    ImageView imgFondo2;

    Boolean estado=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        imgFondo1 = findViewById(R.id.ImgFondo1);
        imgFondo2 = findViewById(R.id.ImgFondo2);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //INFO: menu_activity es el archivo que he creado dentro la carpeta menu
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //cuando seleccione una opcion pasa por un switch
        //para saber cual es
        switch (item.getItemId()) {
            case R.id.item1:
                agregarIMG();//llamo a mi funcion
                return true;
            case R.id.item2:
                agregarIMG2();//llamo a mi funcion
                return true;
            case R.id.item3://llamo a mi funcion
                eliminarIMG();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Funciones del Menu
    public void agregarIMG() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO1);
    }
    public void agregarIMG2() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO2);
    }
    private void eliminarIMG() {
        if(imgFondo1!= null && imgFondo2!= null){

            imgFondo1.setImageDrawable(null);
            imgFondo2.setImageDrawable(null);
        }

    }

    //Manejador de resultados
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imgFondo1.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this, "Error Cargando imagen", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }else if(requestCode == PICK_PHOTO2 && resultCode == Activity.RESULT_OK){
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imgFondo2.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this, "Error Cargando imagen", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}