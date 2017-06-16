package com.example.admin.sante;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.sante.database.modele.User;
import com.google.gson.Gson;

public class Activity2 extends AppCompatActivity {

    Button bouton2;
    String texte;
    TextView textview;
    String texte2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        bouton2 = (Button) findViewById(R.id.button2) ;
        textview = (TextView) findViewById(R.id.textView);

        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edittext = (EditText) findViewById(R.id.editText);
                texte = edittext.getText().toString();
                EditText edittext2 = (EditText) findViewById(R.id.editText2);
                texte2 = edittext2.getText().toString();

                User user = new User();
                user.setNom(texte);
                user.setDétails(texte2);

                // Transformation en JSON :
                String flux = new Gson().toJson(user);
                Log.d("Utilisateur en JSON", flux);

                // On dépose notre utilisateur jsonné dans l'intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("utilisateur", flux);
                setResult(2, resultIntent);

                // Bye l'activité
                finish();

            }
        });
    }
}
