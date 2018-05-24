package fr.unice.polytech.polyblem.issue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.model.Photo;

public class ImageAdapter extends PagerAdapter {

    private List<Photo> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageAdapter(Context context, List<Photo> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);

        ImageView imageView = myImageLayout.findViewById(R.id.image);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap image = BitmapFactory.decodeFile(images.get(position).getUrl(), options);
        image.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
