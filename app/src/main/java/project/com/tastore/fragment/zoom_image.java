package project.com.tastore.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import project.com.tastore.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link zoom_image#newInstance} factory method to
 * create an instance of this fragment.
 */
public class zoom_image extends DialogFragment {
private ImageView img;
private Detail_Barang context;
    public zoom_image() {
        // Required empty public constructor
    }


    public static zoom_image newInstance() {
        return new zoom_image();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_zoom_image, container, false);
        img=(ImageView) view.findViewById(R.id.image);
        String imgs=getArguments().getString("image");
        //Toast.makeText(getActivity(), imgs, Toast.LENGTH_SHORT).show();
        RequestOptions options=new RequestOptions().error(R.drawable.ic_account_circle);
        Glide.with(getActivity()).load(imgs).apply(options).into(img);
        return view;
    }

}
