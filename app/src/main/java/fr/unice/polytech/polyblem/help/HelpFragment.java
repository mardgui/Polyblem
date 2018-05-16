package fr.unice.polytech.polyblem.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.unice.polytech.polyblem.R;

/**
 * Created by user on 14/05/2018.
 */

public class HelpFragment extends Fragment {

    public HelpFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        TextView textEmail = getView().findViewById(R.id.email_text);
        textEmail.setText("Ce bouton permet d'envoyer un mail au déclarateur de l'incident.\n " +
                "Ce mail sera près remplis avec les informations concernant l'incident.\n" +
                "Vous pourrez choisir l'application que vous souhaitez pour effectuer cette action.");

        TextView textAgenda = getView().findViewById(R.id.agenda_text);
        textAgenda.setText("Ce bouton permet d'ajouter un incident à l'agenda de votre choix.");

        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help, container, false);
    }

}
