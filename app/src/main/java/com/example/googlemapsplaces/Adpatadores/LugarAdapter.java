package com.example.googlemapsplaces.Adpatadores;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.googlemapsplaces.R;
import com.example.googlemapsplaces.modelos.Lugar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class LugarAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "Adaptadorplaces";
    private LayoutInflater inflater;
    private Lugar lugares;

    public LugarAdapter(LayoutInflater inflater){
        this.inflater = inflater;

    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View view = inflater.inflate(R.layout.ly_lugarinfo, null);
        lugares = (Lugar) marker.getTag();
        ((TextView)view.findViewById(R.id.nombre)).setText(lugares.name);
        ((TextView)view.findViewById(R.id.direccion)).setText(lugares.vicinity);
        ((TextView)view.findViewById(R.id.lblweb)).setText(lugares.estado);
        ImageView image2 = (ImageView)view.findViewById(R.id.urlimg);
        String url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + lugares.foto + "&key=AIzaSyCZ08ZGZLtgd4ZHQRQqAEuL-RJ10zIybxI";
        Glide.with(view).load( url).into(image2);
        return view;
    }
}
