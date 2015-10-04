package com.example.cesar.clima.models;

import android.widget.Switch;

import com.example.cesar.clima.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by cesar on 9/26/2015.
 */
public class ClimaActual  {
    private double humedad;
    private double temperatura;
    private String localizacion;
    private long tiempo;
    private String icono;
    private double probabilidad;
    private String resumen;


    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    //Metodos para configurar mis imagenes

    public int getImagenID(){
        int idImagen = R.drawable.clear_day;
        switch (icono){
            case "clear-day":idImagen = R.drawable.clear_day;break;
            case "clear-night": idImagen = R.drawable.clear_night;break;
            case "rain": idImagen = R.drawable.rain;break;
            case "snow": idImagen = R.drawable.snow;break;
            case "sleet": idImagen = R.drawable.sleet;break;
            case "wind":idImagen = R.drawable.wind;break;
            case "fog": idImagen = R.drawable.fog;break;
            case "cloudy": idImagen = R.drawable.cloudy;break;
            case "partly-cloudy-day": idImagen = R.drawable.partly_cloudy;break;
            case "partly-cloudy-night": idImagen = R.drawable.cloudy_night;break;
        }
        return idImagen;
    }

    public String obtenerTiempoFormateado(){
        SimpleDateFormat format = new SimpleDateFormat("H:mm a");
        format.setTimeZone(TimeZone.getTimeZone(localizacion));

        Date dateTime = new Date(getTiempo() * 1000);
        String timepo = format.format(dateTime);
        return timepo;

    }

}
