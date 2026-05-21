package ec.edu.ug.proyectoparcial;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;
import ec.edu.ug.proyectoparcial.data.database.dbHelper;
import ec.edu.ug.proyectoparcial.helper.ArchivoHelper;

import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ReporteActivity extends AppCompatActivity {

    private Button btnReporteInterno;
    private Button btnReporteExterno;
    private Button btnCompartirReporte;
    private dbHelper db;
    private ImageButton ivVolver;
    private SharedPreferences prfs;
    private String NOMBRE ="NOMBRE";
    private String PREFERENCIAS = "MIS_PREFERENCIAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_reporte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciarComponentes();
        db = new dbHelper(this);
        prfs = getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        iniciarEventos();
    }
    private void iniciarComponentes(){
        btnReporteInterno = findViewById(R.id.btnReporteInterno);
        btnReporteExterno = findViewById(R.id.btnReporteExterno);
        btnCompartirReporte = findViewById(R.id.btnCompartirReporte);
        ivVolver = findViewById(R.id.ivVolver);
    }

    private void iniciarEventos(){

        // Reporte interno - Abre Device Explorer
        btnReporteInterno.setOnClickListener(v -> {

            String nombre = prfs.getString(NOMBRE, getString(R.string.nombre_default_estudiante));

            ArrayList<InventarioDao> lista = db.getAllItem();

            String reporte = ArchivoHelper.generarReporte(nombre, lista);

            ArchivoHelper.guardarArchivoInterno(this, reporte);

            // buscalo en data/data/ec.edu.ug.proyectoparcial/files
            Toast.makeText(this,  R.string.toast_reporte_interno_generado, Toast.LENGTH_SHORT
            ).show();
        });

        // Reporte externo - Abre Device Explorer
        btnReporteExterno.setOnClickListener(v -> {

            String nombre = prfs.getString(NOMBRE, getString(R.string.nombre_default_estudiante));

            ArrayList<InventarioDao> lista = db.getAllItem();

            String reporte = ArchivoHelper.generarReporte(nombre, lista);

            String ruta = ArchivoHelper.guardarArchivoExterno(this, reporte);

            // buscalo en data/user/0/ec.edu.ug.proyectoparcial/files
            Toast.makeText(this, getString(R.string.toast_archivo_exportado,ruta), Toast.LENGTH_LONG).show();
        });

        // Comparte el reporte
        btnCompartirReporte.setOnClickListener(v -> {

            String nombre = prfs.getString(NOMBRE, getString(R.string.nombre_default_estudiante));

            ArrayList<InventarioDao> lista = db.getAllItem();

            String reporte = ArchivoHelper.generarReporte(nombre, lista);

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, reporte);

            startActivity(Intent.createChooser(share, getString(R.string.chooser_compartir_reporte)));
        });

        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });
    }
}