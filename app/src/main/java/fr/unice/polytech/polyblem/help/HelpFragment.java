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
        textEmail.setText(R.string.help_email);

        TextView textAgenda = getView().findViewById(R.id.agenda_text);
        textAgenda.setText(R.string.help_agenda);

        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help, container, false);
    }

}
