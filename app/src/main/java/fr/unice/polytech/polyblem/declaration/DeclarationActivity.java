package fr.unice.polytech.polyblem.declaration;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.unice.polytech.polyblem.MainActivity;
import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Category;
import fr.unice.polytech.polyblem.model.Issue;
import fr.unice.polytech.polyblem.model.Location;
import fr.unice.polytech.polyblem.model.Photo;
import fr.unice.polytech.polyblem.model.Urgency;

/**
 * Created by Florian on 16/04/2018
 */

public class DeclarationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_TAKE_PHOTO = 1;

    private List<Photo> photoList = new ArrayList<>();
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);

        Button addPicture = findViewById(R.id.add_image);
        addPicture.setOnClickListener(this);

        findViewById(R.id.title_horizontalScrollView).setVisibility(View.INVISIBLE);
        ImageView noImage = findViewById(R.id.noImageAdded);
        noImage.setImageResource(R.drawable.noimage);

        setSendButton();
        setCategoriesSpinner();
        setLocationSpinner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_image:
                dispatchTakePictureIntent();
                break;
            default:
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i("DeclarationActivity", "Cannot create file");
            }

            if (photoFile != null) {
                photoList.add(new Photo(photoFile.getAbsolutePath()));
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //Image created !
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setAdapter();
        }
    }

    private void setAdapter() {
        HorizontalScrollView horizontalScrollView = findViewById(R.id.title_horizontalScrollView);
        if (horizontalScrollView.getVisibility() == View.INVISIBLE) {
            findViewById(R.id.title_horizontalScrollView).setVisibility(View.VISIBLE);
            findViewById(R.id.noImageAdded).setVisibility(View.INVISIBLE);
        }

        final ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getApplicationContext(), photoList);
        final GridView gridView = findViewById(R.id.grid_picture);

        int size = photoList.size();
        // Calculated single Item Layout Width for each grid element ....
        int width = 80;

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int totalWidth = (int) (width * size * density);
        int singleItemWidth = (int) (width * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        gridView.setLayoutParams(params);
        gridView.setColumnWidth(singleItemWidth);
        gridView.setHorizontalSpacing(50);
        gridView.setStretchMode(GridView.STRETCH_SPACING);
        gridView.setNumColumns(photoList.size());

        gridView.setAdapter(imageGridAdapter);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH).format(Calendar.getInstance().getTime());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private Issue createIssue() {
        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        Spinner categorySpinner = findViewById(R.id.category);
        Spinner locationSpinner = findViewById(R.id.location);
        EditText locationDetail = findViewById(R.id.location_detail);
        SeekBar urgencyValue = findViewById(R.id.urgencyValue);

        String issueTitle = title.getText() != null ? title.getText().toString() : null;
        String issueDescription = description.getText() != null ? description.getText().toString() : "";
        String issueCategory = categorySpinner.getSelectedItem().equals("Catégorie") ? null : categorySpinner.getSelectedItem().toString();
        String issueLocation = locationSpinner.getSelectedItem().equals("Lieu") ? null : locationSpinner.getSelectedItem().toString();
        String issueLocationDetail = locationDetail.getText() != null ? locationDetail.getText().toString() : "";
        String issueUrgencyValue = Urgency.getFromId(urgencyValue.getProgress());
        String date = new SimpleDateFormat("dd/MM/yy", Locale.FRENCH).format(Calendar.getInstance().getTime());


        if (title.getText().length() == 0) {
            if (title.getText().length() == 0) title.setError("Titre nécessaire");
            return null;
        }
        return new Issue(issueTitle,
                issueCategory,
                issueDescription,
                issueLocation,
                issueLocationDetail,
                issueUrgencyValue,
                MainActivity.email,
                date);
    }

    private void setSendButton() {
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Issue issue = createIssue();
                if (issue != null) {
                    System.out.println(issue.toString());
                    Database database = new Database(getApplicationContext());
                    long id = database.addIssue(issue);
                    Log.i("DeclarationActivity", issue.toString());
                    for (int i = 0; i < photoList.size(); i++) {
                        database.addPicture(id, photoList.get(i).getUrl());
                    }
                    finish();
                }
            }
        });
    }

    private void setCategoriesSpinner() {
        List<String> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.getName());
        }

        Spinner categorySpinner = findViewById(R.id.category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeclarationActivity.this, android.R.layout.simple_spinner_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText(getApplicationContext(), "Sélectionné : " + selectedCategory, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setLocationSpinner() {
        List<String> locations = new ArrayList<>();
        for (Location location : Location.values()) {
            locations.add(location.getName());
        }

        Spinner locationSpinner = findViewById(R.id.location);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeclarationActivity.this, android.R.layout.simple_spinner_item, locations) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText(getApplicationContext(), "Sélectionné : " + selectedLocation, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
