package com.example.admin.sante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.sante.database.datasource.DataSource;
import com.example.admin.sante.database.modele.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/06/2017.
 */

public class ListeUtilisateurFragment extends Fragment {

    private RecyclerView recyclerView;
    DataSource<User> dataSource;
    List<String> noms = new ArrayList<>();
    private int versionDB = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_utilisateurs, container, false);
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(new RecyclerView.Adapter<UtilisateurViewHolder>() {

            @Override
            public UtilisateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilisateur_list_content, parent, false);
                return new UtilisateurViewHolder(view);
            }

            @Override
            public void onBindViewHolder(UtilisateurViewHolder holder, final int position) {
                List<User> users = dataSource.readAll();
                ((UtilisateurViewHolder) holder).mContentView.setText(users.get(position).getNom());//liste utilisateur.Titre(position)
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilisateurFragment articleFrag = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.article_fragment);

                        if (articleFrag != null) {
                            articleFrag.updateArticleView(position);
                        }
                        else {
                            UtilisateurFragment newFragment = new UtilisateurFragment();
                            Bundle args = new Bundle();
                            args.putInt(UtilisateurFragment.ARG_POSITION, position);
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                List<User> users = dataSource.readAll();
                return users.size();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Utilisateurs chargés depuis json
        //refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        //closeDB();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(getContext(), User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }

    public class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mContentView;

        public UtilisateurViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }


    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("COOKIE", "opopopop");
        if (resultCode == 2) {

            String flux = data.getStringExtra("utilisateur"); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            Log.d("UTILISATEUR", utilisateur.getNom());
            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(
                Log.d("Erreur", "souci à l'insertion");
                e.printStackTrace();
            }

            // Indiquer un changement au RecycleView
            refresh();
        }

    }*/

    public void refresh() {
        List<User> users = dataSource.readAll();
        noms.clear();
        int count = 0;
        for(User user : users) {
            noms.add(count, user.getNom());
            count++;
            //Toast.makeText(getContext(), user.getNom() + " added", Toast.LENGTH_LONG).show();
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }
}

