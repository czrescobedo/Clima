package com.example.cesar.clima.controllers;

import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cesar.clima.R;
import com.example.cesar.clima.models.ClimaActual;
import com.example.cesar.clima.models.VentanaAlerta;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ClimaActual climaActual;

    /*ImageView ivGrado;
    TextView tvGrados;
    TextView tvTiempo;
    TextView tvLocalizacion;
    TextView tvHumedad;
    TextView tvProbabilidad;
    TextView tvResumen;*/
    @Bind(R.id.tvGrados) TextView tvGrados;
    @Bind(R.id.tvHumedadValor) TextView tvHumedadValor;
    @Bind(R.id.tvLocalizacion) TextView tvLocalizacion;
    @Bind(R.id.tvTiempo) TextView tvTiempo;
    @Bind(R.id.tvProbabilidadValor) TextView tvProbabilidadValor;
    @Bind(R.id.ivGrado) ImageView ivGrado;
    @Bind(R.id.tvResumen) TextView tvResumen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*ivGrado = (ImageView) findViewById(R.id.ivGrado);
        tvGrados = (TextView) findViewById(R.id.tvGrados);
        tvTiempo = (TextView) findViewById(R.id.tvTiempo);
        tvLocalizacion = (TextView) findViewById(R.id.tvLocalizacion);
        tvHumedad = (TextView) findViewById(R.id.tvHumedadValor);
        tvProbabilidad = (TextView) findViewById(R.id.tvProbabilidadValor);
        tvResumen = (TextView) findViewById(R.id.tvResumen);*/

        String apKey ="6dd7bba8054ec36394a5f7e65295be94";
        double latitud = 37.8267;
        double longitud = -122.423;
        String forecast = "https://api.forecast.io/forecast/" + apKey + "/" + latitud + "," + longitud;


        if (redDisponible()){
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecast).build();


            final com.squareup.okhttp.Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        if (response.isSuccessful()){
                            String jsonInformacion = response.body().string();
                            climaActual = obtenerClimaActual(jsonInformacion);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    tvLocalizacion.setText(climaActual.getLocalizacion());
                                    tvResumen.setText(climaActual.getResumen());
                                    tvTiempo.setText(climaActual.obtenerTiempoFormateado());
                                    tvHumedadValor.setText(String.valueOf(climaActual.getHumedad()));
                                    tvGrados.setText(String.valueOf(climaActual.getTemperatura()));
                                    tvProbabilidadValor.setText(String.valueOf(climaActual.getProbabilidad()));
                                    ivGrado.setImageResource(climaActual.getImagenID());

                                }
                            });
                        }else {
                            alertaErrorUsuario();
                        }
                    }catch (IOException e){
                        Log.e(TAG,e.getMessage());
                    }catch (JSONException e){
                        Log.e(TAG,e.getMessage());
                    }


                }
            });
        } else {
            Toast.makeText(this,"La red no esta disponble",Toast.LENGTH_LONG).show();
        }

    }

    private ClimaActual obtenerClimaActual(String informacion) throws JSONException{
        JSONObject forecast = new JSONObject(informacion);

        JSONObject currently = forecast.getJSONObject("currently");
        climaActual = new ClimaActual();
        climaActual.setLocalizacion(forecast.getString("timezone"));
        climaActual.setTiempo(currently.getLong("time"));
        climaActual.setResumen(currently.getString("summary"));
        climaActual.setHumedad(currently.getDouble("humidity"));
        climaActual.setProbabilidad(currently.getDouble("precipProbability"));
        climaActual.setTemperatura(currently.getDouble("temperature"));
        climaActual.setIcono(currently.getString("icon"));




        //String zona = forecast.getString("timezone");
        //Log.d(TAG,zona);
        return climaActual;

    }

    private boolean redDisponible() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean estaDisponible = false;
        if (networkInfo != null && networkInfo.isConnected()){
            estaDisponible = true;
        }
        return estaDisponible;
    }

    private void alertaErrorUsuario() {
        VentanaAlerta ventanaAlerta = new VentanaAlerta();
        ventanaAlerta.show(getFragmentManager(),"error_ventana");
    }


}
