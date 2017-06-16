package com.example.admin.sante;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.admin.sante.database.datasource.DataSource;
import com.example.admin.sante.database.modele.User;
import com.google.gson.Gson;


public class MainActivity extends FragmentActivity {

    FloatingActionButton fab;
    DataSource<User> dataSource;
    private int versionDB = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivityForResult(intent, 2);
            }
        });

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ListeUtilisateurFragment firstFragment = new ListeUtilisateurFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(getApplicationContext(), User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }

        if (resultCode == 2) {

            String flux = data.getStringExtra("utilisateur"); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(

                e.printStackTrace();
            }

        }

    }
}
