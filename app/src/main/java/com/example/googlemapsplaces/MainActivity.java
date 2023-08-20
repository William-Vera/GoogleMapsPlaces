package com.example.googlemapsplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.googlemapsplaces.Adpatadores.LugarAdapter;
import com.example.googlemapsplaces.modelos.Lugar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    GoogleMap mapa;
    List<LatLng> lstcoordenadas;;
    PolylineOptions lineasg;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lstcoordenadas = new ArrayList<>();
        lineasg = new PolylineOptions();
        queue= Volley.newRequestQueue(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        //mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        // Mover el mapa a una vista 2D
        CameraUpdate camUpd1 =
                CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0124715285981634, -79.46798611077189), 18);
        mapa.moveCamera(camUpd1);

        mapa.setOnMapClickListener(this);

        mapa.setInfoWindowAdapter(new LugarAdapter(LayoutInflater.from(getApplicationContext())));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        this.mapa.clear();
        LatLng punto = new LatLng(latLng.latitude, latLng.longitude);
        MarkerOptions marcador = new MarkerOptions();
        marcador.position(latLng);
        marcador.title("Punto");
        mapa.addMarker(marcador);


        lstcoordenadas.add(latLng);
        lineasg.add(latLng);

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?fields=name&location="+latLng.latitude+","+latLng.longitude+"&radius=1500&type=bar&key=AIzaSyCZ08ZGZLtgd4ZHQRQqAEuL-RJ10zIybxI";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JSONlista = null;

                            JSONlista = new JSONObject(response);
                            JSONArray JSONlistaplaces = JSONlista.getJSONArray("results");


                            ArrayList<Lugar> lstplace = Lugar.JsonObjectsBuild(JSONlistaplaces);

                            for(int i=0;i<lstplace.size();i++){
                                //System.out.println(lstplace.get(i).location_lat+","+lstplace.get(i).location_lng);
                                LatLng punto = new LatLng(Double.valueOf(lstplace.get(i).latitud), Double.valueOf(lstplace.get(i).longitud));
                                Marker marker = mapa.addMarker(new MarkerOptions().position(punto).title("Marca" + i));
                                marker.setTag(lstplace.get(i)); // Asignar el modelo Lugar como etiqueta al marcador

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR EN PARSEO");
                    }
                }
        );
        queue.add(stringRequest);

        /*
        if (lstcoordenadas.size()==6)
        {
            PolylineOptions lineas = new PolylineOptions();

            for (int i = 0; i < lstcoordenadas.size(); i++) {
                LatLng coordenada = new LatLng(lstcoordenadas.get(i).latitude, lstcoordenadas.get(i).longitude);
                lineas.add(coordenada);
            }
            LatLng coordenada = new LatLng(lstcoordenadas.get(0).latitude, lstcoordenadas.get(0).longitude);
            lineas.add(coordenada);
            lineas.width(8);
            lineas.color(Color.RED);
            mapa.addPolyline(lineas);
            lstcoordenadas.clear();
        }*/

        /*if (lineasg.getPoints().size() == 6) {
            lineasg.add(lineasg.getPoints().get(0));
            lineasg.color(Color.RED);
            mapa.addPolyline(lineasg);
            lineasg.getPoints().clear();
        }*/
    }
}