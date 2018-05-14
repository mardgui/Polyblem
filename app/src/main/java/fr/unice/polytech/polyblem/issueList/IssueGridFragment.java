package fr.unice.polytech.polyblem.issueList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.issue.IssueFragment;
import fr.unice.polytech.polyblem.model.Issue;
import fr.unice.polytech.polyblem.model.Urgency;

public class IssueGridFragment extends Fragment {

    private IssueCustomAdapter issueCustomAdapter;
    private Database database;
    private List<Issue> issueList;

    public IssueGridFragment() {
    }

    private static final String BACK_STACK_ISSUE_TAG = "issue_fragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        database = new Database(getActivity());
        issueList = database.getAllIssues();
        sortIssueList();
        database.close();

        issueCustomAdapter = new IssueCustomAdapter(this.getContext(), issueList);
        GridView gridView = (GridView) getActivity().findViewById(R.id.issue_grid);
        gridView.setAdapter(issueCustomAdapter);

        final SearchView searchView = (SearchView) getActivity().findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Issue issue = issueList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Issue", issue);

                IssueFragment issueFragment = new IssueFragment();
                issueFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, issueFragment)
                        .addToBackStack(BACK_STACK_ISSUE_TAG)
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.issue_container, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        database = new Database(getActivity());
        issueList = database.getAllIssues();
        sortIssueList();
        database.close();

        issueCustomAdapter = new IssueCustomAdapter(this.getContext(), issueList);
        GridView gridView = (GridView) getActivity().findViewById(R.id.issue_grid);
        gridView.setAdapter(issueCustomAdapter);
        super.onResume();
    }

    private void sortIssueList() {
        List<Issue> sortedList = new ArrayList<>();
        Urgency urgencies[] = {Urgency.LOW, Urgency.MEDIUM, Urgency.HIGH};

        while (!issueList.isEmpty()) {
            Issue issueToBeRemoved = null;
            for (Urgency urgency : urgencies) {
                List<Issue> mostUrgentIssues = new ArrayList<>();
                for (Issue issue : issueList) {
                    if (issue.getUrgency().equals(urgency)) mostUrgentIssues.add(issue);
                }
                if (mostUrgentIssues.isEmpty()) continue;
                Issue newestIssue = mostUrgentIssues.get(0);
                for (Issue issue : mostUrgentIssues) {
                    if (dateComparator(issue.getDate(), newestIssue.getDate())) newestIssue = issue;
                }
                issueToBeRemoved = newestIssue;
            }
            if (issueToBeRemoved != null) {
                sortedList.add(issueToBeRemoved);
                issueList.remove(issueToBeRemoved);
            }
        }

        issueList = sortedList;
    }

    /**
     * @param date1 : first date to compare
     * @param date2 : second date to compare
     * @return true if date1 is newer than date2
     */
    private boolean dateComparator(String date1, String date2) {
        if (date1.length() != 8) return false;
        if (date2.length() != 8) return true;

        double specialDate1 = 0;
        double specialDate2 = 0;

        double i = .0001;
        for (String string : date1.split("/")) {
            specialDate1 += i * Integer.parseInt(string);
            i *= 100;
        }

        i = .0001;
        for (String string : date2.split("/")) {
            specialDate2 += i * Integer.parseInt(string);
            i *= 100;
        }

        return specialDate1 > specialDate2;
    }
}
