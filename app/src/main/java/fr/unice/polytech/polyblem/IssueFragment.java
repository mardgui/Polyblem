package fr.unice.polytech.polyblem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;
import fr.unice.polytech.polyblem.model.Photo;

/**
 * Created by user on 01/05/2018.
 */

public class IssueFragment extends Fragment {

    Issue issue;

    public IssueFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        TextView title = getView().findViewById(R.id.title);
        title.setText(issue.getTitle());

        TextView categorie = getView().findViewById(R.id.categorie);
        categorie.setText(issue.getCategory());

        Database database = new Database(getContext());
        List<Photo> photoList = database.getPictures(issue);

        TextView description = getView().findViewById(R.id.description);
        description.setText(issue.getDescription());

        TextView localisation1 = getView().findViewById(R.id.label_localisation);
        localisation1.setText("Localisation : ");

        TextView localisation = getView().findViewById(R.id.localisation);
        localisation.setText(issue.getLocation());

        TextView email1 = getView().findViewById(R.id.label_email);
        email1.setText("Email : ");

        TextView email = getView().findViewById(R.id.email);
        email.setText(issue.getEmail());

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            issue = bundle.getParcelable("Issue");
        }
        View view = inflater.inflate(R.layout.issue, container, false);
        return view;
    }
}
