package fr.unice.polytech.polyblem.declaration;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.unice.polytech.polyblem.R;

/**
 * Created by Florian on 16/04/2018
 */

public class DeclarationFragment extends Fragment {

    public DeclarationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_declaration, container, false);

    }
}
