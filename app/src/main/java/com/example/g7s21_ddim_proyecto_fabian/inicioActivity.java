package com.example.g7s21_ddim_proyecto_fabian;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class inicioActivity extends AppCompatActivity {

    private Spinner spinnernombres;
    private ImageView lblmostrar;
    private Button btnrecargar;
    private List<String> nombres = new ArrayList<>();
    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        spinnernombres = findViewById(R.id.spinnernombres);
        lblmostrar = findViewById(R.id.lblmostrar);
        btnrecargar = findViewById(R.id.btnrecargar);

        // URL del formato json de Google Sheets mas la api
        String url = "https://sheets.googleapis.com/v4/spreadsheets/15xS7MboBBLjS_ut5vcpwfeksan2HNfnqL_th5UZPZ1Q/values/'Hoja 1'!A2:B?key=AIzaSyAvyIu7QK5px4v0YGzuaGV1mkldsrv9LU0";

        //va a  Cargar los datos desde Google Sheets cada vez que iniciamos la aplicacion
        loadSheetData(url);

        //botón para recargar los datos
        btnrecargar.setOnClickListener(v -> loadSheetData(url));
    }

    private void loadSheetData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    nombres.clear(); // Limpiar la lista de nombres
                    urls.clear();    // Limpiar la lista de URLs

                    try {
                        JSONArray rows = response.getJSONArray("values");
                        for (int i = 0; i < rows.length(); i++) {
                            JSONArray row = rows.getJSONArray(i);
                            nombres.add(row.getString(0)); // Nombre
                            urls.add(row.getString(1));    // URL de la imagen
                        }
                        populateSpinner();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(inicioActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(inicioActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonObjectRequest);
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnernombres.setAdapter(adapter);

        spinnernombres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUrl = urls.get(position);
                loadImage(selectedUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });
    }

    private void loadImage(String url) {
        Glide.with(this).load(url).into(lblmostrar);
    }
}