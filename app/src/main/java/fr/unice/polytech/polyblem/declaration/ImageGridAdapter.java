package fr.unice.polytech.polyblem.declaration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.model.Photo;

/**
 * Created by user on 11/05/2018.
 */

public class ImageGridAdapter extends ArrayAdapter<Photo> {


    private List<Photo> photoList;

    public ImageGridAdapter(Context context, List<Photo> photoList) {
        super(context, 0, photoList);
        this.photoList = photoList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_preview, null);
        }
        final Photo photo = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.image);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap image = BitmapFactory.decodeFile(photo.getUrl(), options);
        image.compress(Bitmap.CompressFormat.JPEG, 20, bos);
        byte[] bitmapdata = bos.toByteArray();
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));

        Button button = convertView.findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoList.remove(photo);
                notifyDataSetChanged();
                new File(photo.getUrl()).delete();
            }
        });

        return convertView;
    }


}
