package ec.edu.ug.proyectoparcial;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;
import ec.edu.ug.proyectoparcial.data.database.dbHelper;

public class DetalleActivity extends AppCompatActivity {
    private ArrayList<String> categorias;
    private EditText eNombre, eCantidad, eUbicacion, eObservacion;
    private Spinner eCategoria;
    private DatePicker Fecha;
    private  dbHelper db;
    private InventarioDao item;
    private Button btnModificar,btnEliminar;
    private int idC, posicion,idIntent;
    private String fecha_registro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_detalle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Referencias del intent
        idIntent = getIntent().getIntExtra("ID",0);
        db = new dbHelper(this);
        item =  db.getById(idIntent);

        iniciarView();
        iniciarSpinner();
        setearCampos();
        setearFecha();
        obtenerCategoria();
        modificar();
        eliminar();

    }

    private void iniciarView(){
        eNombre = findViewById(R.id.eNombre);
        eCategoria = findViewById(R.id.eCategoria);
        eCantidad = findViewById(R.id.eCantidad);
        eUbicacion = findViewById(R.id.eUbicacion);
        eObservacion = findViewById(R.id.eObservacion);
        Fecha = findViewById(R.id.Fecha);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
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

        eCategoria.setAdapter(adaptador);

    }

    private void setearCampos(){
        eNombre.setText(item.getNombre());
        eCantidad.setText(String.valueOf(item.getCantidad()));
        idC = categorias.indexOf(item.getCategoria());
        eCategoria.setSelection(idC);
        eUbicacion.setText(item.getUbicacion());
        eObservacion.setText(item.getObservacion());
    }

    private  void setearFecha(){
        String fechaRegistro = item.getFecha_registro();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = format.parse(fechaRegistro);
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                Fecha.updateDate(year, month, day);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void modificar(){
        btnModificar.setOnClickListener(v->{
            obtenerFecha();
            String nombre = eNombre.getText().toString().trim();
            String categoria = categorias.get(posicion);
            String ubicacion = eUbicacion.getText().toString().trim();
            String observacion = eObservacion.getText().toString().trim();

            if (nombre.isEmpty()) {
                eNombre.setError(getString(R.string.error_nombreR));
                return;
            }
            if (ubicacion.isEmpty()) {
                eNombre.setError(getString(R.string.error_ubicacion));
                return;
            }
            if (observacion.isEmpty()) {
                eNombre.setError(getString(R.string.error_observacion));
                return;
            }
            if (eCantidad.getText().toString().isEmpty()){
                eCantidad.setError(getString(R.string.error_cantidad));
                return;
            }
            int cantidad = Integer.parseInt(eCantidad.getText().toString());
            if (db.update(new InventarioDao(nombre,categoria,cantidad,ubicacion,observacion,fecha_registro), idIntent) ==1){
                Toast.makeText(this,R.string.msg_toast_correcto, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.msg_toast_fallo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminar(){
        btnEliminar.setOnClickListener(v->{
            new AlertDialog.Builder(this).setTitle(R.string.confirmar_eliminacion)
                    .setMessage(R.string.mensaje_eliminar_item)
                    .setPositiveButton(R.string.btn_si, (dialog, which) -> {
                        if (db.delete(idIntent) == 1) {
                            Toast.makeText(this, R.string.item_eliminado_correctamente, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, R.string.error_eliminar_item, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.btn_no, null)
                    .show();
        });
    }

    private void obtenerCategoria(){
        eCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void obtenerFecha(){
        int dia = Fecha.getDayOfMonth();
        int month = Fecha.getMonth();
        int year = Fecha.getYear();
        fecha_registro = dia + "/" + month + "/" + year;
    }


}
