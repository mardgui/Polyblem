package fr.unice.polytech.polyblem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Florian on 24/05/2018.
 */

public class ConnectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        Button connect = findViewById(R.id.connection_button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateConnection()) {
                    Intent mainActivity = new Intent(ConnectionActivity.this,
                            MainActivity.class);
                    startActivity(mainActivity);
                }
            }
        });
    }

    private boolean validateConnection() {
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        if (email.getText().length() == 0 || password.getText().length() == 0) {
            email.setError("Mauvaise combinaison d'email et mot de passe");
            return false;
        }
        if (email.getText().toString().equals("jose.malard@unice.fr")) {
            MainActivity.admin = 1;
            return true;
        }
        if (email.getText().toString().equals("julie.jolie@etu.unice.fr")) {
            MainActivity.admin = 0;
            return true;
        }
        email.setError("Mauvaise combinaison d'email et mot de passe");
        return false;
    }
}
