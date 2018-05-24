package fr.unice.polytech.polyblem.issue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.cketti.mailto.EmailIntentBuilder;
import fr.unice.polytech.polyblem.MainActivity;
import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;
import fr.unice.polytech.polyblem.model.Photo;
import me.relex.circleindicator.CircleIndicator;


public class IssueFragment extends Fragment {

    private Issue issue;
    private ViewPager mPager;
    private int currentPage = 0;

    public IssueFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        TextView title = getView().findViewById(R.id.title);
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        setText(issue.getTitle(), title);
        setText(issue.getDate(), (TextView) getView().findViewById(R.id.date));
        setText(issue.getCategory(), (TextView) getView().findViewById(R.id.categorie));
        setText(issue.getDescription(), (TextView) getView().findViewById(R.id.description));
        setText(issue.getEmail(), (TextView) getView().findViewById(R.id.email));
        StringBuilder fullLocation = new StringBuilder();

        fullLocation.append(issue.getLocation());

        if (issue.getLocationDetails() != null && !issue.getLocationDetails().equals("")) {
            fullLocation.append(" ").append(issue.getLocationDetails());
        }

        if (fullLocation.toString().equals("null")) {
            fullLocation = new StringBuilder();
            fullLocation.append("Lieu non indiqué");
        }

        setText(fullLocation.toString(), (TextView) getView().findViewById(R.id.location));

        ImageView urgency = getView().findViewById(R.id.urgency);
        urgency.setImageResource(issue.getUrgency().getId());

        Database database = new Database(getContext());
        List<Photo> photoList = database.getPictures(issue);

        if (photoList.size() > 0) {
            getView().findViewById(R.id.noPicture).setVisibility(View.GONE);
            initSlides(photoList);
        } else {
            getView().findViewById(R.id.picture).setVisibility(View.GONE);
            ImageView noPicture = getView().findViewById(R.id.noPicture);
            noPicture.setImageResource(R.drawable.nopicture);
        }

        super.onActivityCreated(savedInstanceState);

        setListeners();

        setHasOptionsMenu(true);

    }

    private void setText(String string, TextView textView) {
        if (string != null) {
            textView.setText(string);
        } else
            textView.setText("Non indiqué");
    }

    private void initSlides(final List<Photo> photos) {
        ViewPager mPager = getView().findViewById(R.id.pager);
        mPager.setAdapter(new ImageAdapter(getContext(), photos));
        CircleIndicator indicator = getView().findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            issue = bundle.getParcelable("Issue");
        }
        return inflater.inflate(R.layout.issue, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_delete);
        if (MainActivity.admin == 1) {
            item.setVisible(true);
        } else item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Database database = new Database(getContext());
            database.deleteIssue(issue);
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        Button sendEmail = getView().findViewById(R.id.send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailIntentBuilder.from(getActivity())
                        .to(issue.getEmail())
                        .subject("Incident : " + issue.getTitle() + " - " + issue.getDate())
                        .body("J'aimerais plus détails concernant cet incident : \n")
                        .start();
            }
        });

        Button addToAgenda = getView().findViewById(R.id.add_to_agenda);
        addToAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent agendaIntent = new Intent(Intent.ACTION_EDIT);
                agendaIntent.setType("vnd.android.cursor.item/event");
                agendaIntent.putExtra("title", "Incident : " + issue.getTitle());
                agendaIntent.putExtra("description", "S'occuper de l'incident " + issue.getTitle() + " déclaré le " + issue.getDate() + " :\n" + issue.getDescription());

                startActivity(Intent.createChooser(agendaIntent, "Ajouter à l'agenda..."));
            }
        });

    }
}
