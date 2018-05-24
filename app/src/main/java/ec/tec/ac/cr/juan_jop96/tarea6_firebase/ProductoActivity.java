package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ProductoActivity extends AppCompatActivity {
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private String ImgDir;
    private Uri filePath;
    private EditText d1,d2,d3;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference rootRefence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.imgView);
        d1 = (EditText) findViewById(R.id.nombreTxt);
        d2 = (EditText) findViewById(R.id.precioTxt);
        d3 = (EditText) findViewById(R.id.descripcionTxt);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        rootRefence = FirebaseDatabase.getInstance().getReference();

    }


    public void selectImg(View v){
        chooseImage();
    }

    public void uploadImg (View v){
        uploadImage();
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        final String dir = "productos/"+ UUID.randomUUID().toString();
        if (d1.getText().toString().equals("") || d2.getText().toString().equals("") || d3.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "No se permiten espacios en blanco", Toast.LENGTH_SHORT).show();
            return;
        }
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Picture...");
            progressDialog.show();
            StorageReference ref = storageReference.child(dir);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ImgDir = storageReference.child(dir).getPath();
                            Producto newProduct = new Producto(d1.getText().toString(),d2.getText().toString(),ImgDir,d3.getText().toString());
                            rootRefence.child(newProduct.getID()).setValue(newProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "Articulo agregado", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                                            startActivity(i);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(), "No se permiten productos sin imagen", Toast.LENGTH_SHORT).show();
        }
    }


}
