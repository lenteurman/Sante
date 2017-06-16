package com.example.admin.sante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.sante.database.datasource.DataSource;
import com.example.admin.sante.database.modele.User;

import java.util.List;

/**
 * Created by admin on 16/06/2017.
 */

public class UtilisateurFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    TextView article;
    DataSource<User> dataSource;
    private int versionDB = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.utilisateur, container, false);
        article = (TextView) view.findViewById(R.id.article);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt(ARG_POSITION));
        }
        else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(getContext(), User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
        if (article != null) {
            List<User> users = dataSource.readAll();
            article.setText(users.get(position).getDétails());//listeutilisateur position
            mCurrentPosition = position;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
