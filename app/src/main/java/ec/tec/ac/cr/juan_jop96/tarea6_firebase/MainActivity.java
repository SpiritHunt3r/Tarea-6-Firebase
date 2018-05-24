package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccountCreator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.emailText);
        e2 = findViewById(R.id.passwordText);
        auth = FirebaseAuth.getInstance();



    }



    public void openRegister(View v){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void login(View v){


        if (e1.getText().toString().equals("")  || e2.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"No se permite espacios en blanco",Toast.LENGTH_SHORT).show();
        }
        else{
            String email = e1.getText().toString();
            String pass = e2.getText().toString();
            auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                finish();
                                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Credenciales incorrectas\nIntente nuevamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}
