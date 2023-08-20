package com.example.googlemapsplaces.modelos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lugar {

    public String latitud;
    public String longitud;
    public String icon;
    public String name;
    public String vicinity;
    public String foto;
    public String estado;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Lugar(JSONObject a) throws JSONException {
        JSONObject geometry = a.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        latitud = location.getString("lat").toString();
        longitud = location.getString("lng").toString();
        icon = a.getString("icon").toString();
        name = a.getString("name").toString();
        vicinity = a.getString("vicinity").toString();

        if(!a.isNull("opening_hours")){
            JSONObject horario = a.getJSONObject("opening_hours");
            if(horario.getString("open_now")=="true")
                estado = ("Local abierto");
            else estado = ("Local cerrado");
        }else
        {
            estado = "Sin horarío";
        }


        JSONObject JSONlista = null;
        JSONlista = a;

        if(!JSONlista.isNull("photos")   ){
            JSONArray JSONlistafotos = JSONlista.getJSONArray("photos");
            JSONObject photo = JSONlistafotos.getJSONObject(0);
            foto = photo.getString("photo_reference").toString();
        }else
        {
            foto = "Null";
        }
    }

    public static ArrayList<Lugar> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Lugar> places = new ArrayList<>();

        for (int i = 0; i < datos.length() && i<20; i++) {
            places.add(new Lugar(datos.getJSONObject(i)));
        }
        return places;
    }
}
