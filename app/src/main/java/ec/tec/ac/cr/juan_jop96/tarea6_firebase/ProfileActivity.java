package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference rootReference;


    private ListView lista;
    public ArrayList<Producto> ArrayItem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference();
        lista = findViewById(R.id.LV_producto);


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                final String idI = ArrayItem.get(position).getID();
                new AlertDialog.Builder(ProfileActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Eliminando "+ ArrayItem.get(position).getNombre())
                        .setMessage("Desea eliminarlo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"Objeto "+ ArrayItem.get(position).getNombre() +" Eliminado",Toast.LENGTH_SHORT).show();
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(idI);
                                myRef.removeValue();
                                refresh();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;

            }

        });



        cargarLista(this);

    }

    public void refresh(){
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }

    public void cargarLista(final Context context){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Producto pr = ds.getValue(Producto.class);
                    ArrayItem.add(pr);
                }
                producto_adapter adapter = new producto_adapter(ArrayItem,context);
                lista.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }



    public void agregarProducto (View v){
        Intent i = new Intent(this,ProductoActivity.class);
        startActivity(i);
    }


    public void logout (View v){
        auth.signOut();
        finish();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
