package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText email, pass;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailText);
        pass = findViewById(R.id.passwordText);

        auth = FirebaseAuth.getInstance();

    }

    public void createUser(View v){
        String ActualEmail = email.getText().toString();
        String ActualPass = pass.getText().toString();

        if (ActualEmail.equals("") || ActualPass.equals("")){
            Toast.makeText(getApplicationContext(),"No se permite espacios en blanco",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(ActualEmail,ActualPass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Usuario creado correctamente",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"No es posible crear el usuario",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }

    }

    public void backMain(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
