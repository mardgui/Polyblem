package fr.unice.polytech.polyblem.issueList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;

public class IssueGridFragment extends Fragment {
    public IssueGridFragment() {
    }

    public static IssueGridFragment newInstance() {
        return new IssueGridFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Issue> issueList = new ArrayList<>();

        Database database = new Database(getActivity());
        issueList.addAll(database.getAllIssues());
        database.close();

        IssueCustomAdapter issueAdapter = new IssueCustomAdapter(this.getContext(), issueList);
        GridView gridView = (GridView) getActivity().findViewById(R.id.issue_grid);
        gridView.setAdapter(issueAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.issue_container, container, false);
        return rootView;
    }
}
