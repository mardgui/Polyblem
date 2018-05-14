package fr.unice.polytech.polyblem.issueList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.model.Issue;

public class IssueCustomAdapter extends ArrayAdapter<Issue> {

    private List<Issue> issueList = null;
    private ArrayList<Issue> arrayList;

    public IssueCustomAdapter(Context context, List<Issue> issueList) {
        super(context, 0, issueList);
        this.issueList = issueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(issueList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.issue_preview, null);
        }
        Issue issue = getItem(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView date = convertView.findViewById(R.id.date);
        ImageView urgency = convertView.findViewById(R.id.urgency);
        TextView location = convertView.findViewById(R.id.location);

        urgency.setImageResource(issue.getUrgency().getId());

        title.setText(issue.getTitle());
        date.setText(issue.getDate());
        location.setText(issue.getLocation());

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        issueList.clear();
        if (charText.length() == 0) {
            issueList.addAll(arrayList);
        } else {
            for (Issue issue : arrayList) {
                if (issue.getDescription().toLowerCase(Locale.getDefault()).contains(charText) || issue.getLocationDetails().toLowerCase(Locale.getDefault()).contains(charText) || issue.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || issue.getLocation().toLowerCase(Locale.getDefault()).contains(charText) || issue.getEmail().toLowerCase(Locale.getDefault()).contains(charText) || issue.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) {
                    issueList.add(issue);
                }
            }
        }
        notifyDataSetChanged();
    }
}
