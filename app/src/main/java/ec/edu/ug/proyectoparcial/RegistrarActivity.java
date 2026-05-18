package ec.edu.ug.proyectoparcial;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;
import ec.edu.ug.proyectoparcial.data.database.dbHelper;

public class RegistrarActivity extends AppCompatActivity {

    private ImageButton ivVolver;
    private Button btnGuardar;

    private DatePicker fecha;

    private EditText etNombre, etCantidad, etUbicacion, etObservacion;

    private ArrayList<String> categorias;
    private Spinner spCategoria;
    private int posicion;
    private String fecha_registro;

    private dbHelper db;
    private Calendar today = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        EdgeToEdge.enable(this);
        db = new dbHelper(RegistrarActivity.this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_registrar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        iniciarViews();
        volverMennu();
        obtenerFecha();
        obtenerPosicionCategoria();
        iniciarSpinner();
        guardar();

    }

    private void iniciarViews(){
        ivVolver = findViewById(R.id.ivVolver);
        btnGuardar = findViewById(R.id.btnGuardar);
        etNombre = findViewById(R.id.etNombre);
        spCategoria = findViewById(R.id.spCategoria);
        etCantidad = findViewById(R.id.etCantidad);
        etUbicacion = findViewById(R.id.etUbicacion);
        etObservacion = findViewById(R.id.etObservacion);
        fecha = findViewById(R.id.dpFecha);
    }

    private void guardar(){

        btnGuardar.setOnClickListener(v->{
            String nombre = etNombre.getText().toString().trim();
            String categoria = categorias.get(posicion);
            String ubicacion = etUbicacion.getText().toString().trim();
            String observacion = etObservacion.getText().toString().trim();
            boolean hayError = false;
            if (nombre.isEmpty() ){
                etNombre.setError(getString(R.string.error_nombreR));
                hayError = true;
            }
            if (etCantidad.getText().toString().isEmpty()){
                etCantidad.setError(getString(R.string.error_cantidad));
                hayError = true;
            }

            if (ubicacion.isEmpty()){
                etUbicacion.setError(getString(R.string.error_ubicacion));
                hayError = true;
            }

            if (observacion.isEmpty()){
                etObservacion.setError(getString(R.string.error_observacion));
                hayError = true;
            }
            if(hayError){
                return;
            }
            int cantidad = Integer.parseInt(etCantidad.getText().toString());

            boolean estado = db.add(new InventarioDao(nombre, categoria, cantidad,ubicacion, observacion, fecha_registro));
            if (estado){
                Toast.makeText(this, R.string.msj_toast_correcto, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, R.string.msj_toast_falla, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciarSpinner(){
        categorias = new ArrayList<>();
        categorias.add("Microscopios");
        categorias.add("Instrumental");
        categorias.add("Cristaleria");
        categorias.add("Reactivos");
        categorias.add("Otros");

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                R.layout.spinner_dropdown_item,
                categorias
        );

        spCategoria.setAdapter(adaptador);

    }

    private void obtenerFecha(){

        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear();
        fecha_registro = dia + "/" + mes + "/" + anio;
    }

    private void obtenerPosicionCategoria(){
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void volverMennu(){
        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });
    }





}
