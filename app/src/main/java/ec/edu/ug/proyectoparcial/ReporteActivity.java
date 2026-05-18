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
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ReporteActivity extends AppCompatActivity {

    private Button btnReporteInterno;
    private Button btnReporteExterno;
    private Button btnCompartirReporte;
    private dbHelper db;
    private ImageButton ivVolver;
    private SharedPreferences prfs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        iniciarComponentes();

        db = new dbHelper(this);

        prfs = getSharedPreferences(
                "MIS_PREFERENCIAS",
                Context.MODE_PRIVATE
        );

        iniciarEventos();
    }

    private void iniciarComponentes(){

        btnReporteInterno =
                findViewById(R.id.btnReporteInterno);

        btnReporteExterno =
                findViewById(R.id.btnReporteExterno);

        btnCompartirReporte =
                findViewById(R.id.btnCompartirReporte);

        ivVolver = findViewById(R.id.ivVolver);
    }

    private void iniciarEventos(){

        // Reporte interno - Abre Device Explorer
        btnReporteInterno.setOnClickListener(v -> {

            String nombre =
                    prfs.getString("NOMBRE", "Estudiante");

            ArrayList<InventarioDao> lista =
                    db.getAllItem();

            String reporte =
                    ArchivoHelper.generarReporte(
                            nombre,
                            lista
                    );

            ArchivoHelper.guardarArchivoInterno(
                    this,
                    reporte
            );

            // buscalo en data/data/ec.edu.ug.proyectoparcial/files
            Toast.makeText(
                    this,
                    "Reporte interno generado",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // Reporte externo - Abre Device Explorer
        btnReporteExterno.setOnClickListener(v -> {

            String nombre =
                    prfs.getString("NOMBRE", "Estudiante");

            ArrayList<InventarioDao> lista =
                    db.getAllItem();

            String reporte =
                    ArchivoHelper.generarReporte(
                            nombre,
                            lista
                    );

            String ruta =
                    ArchivoHelper.guardarArchivoExterno(
                            this,
                            reporte
                    );

            // buscalo en data/user/0/ec.edu.ug.proyectoparcial/files
            Toast.makeText(
                    this,
                    "Archivo exportado en:\n" + ruta,
                    Toast.LENGTH_LONG
            ).show();
        });

        // Comparte el reporte
        btnCompartirReporte.setOnClickListener(v -> {

            String nombre =
                    prfs.getString("NOMBRE", "");

            ArrayList<InventarioDao> lista =
                    db.getAllItem();

            String reporte =
                    ArchivoHelper.generarReporte(
                            nombre,
                            lista
                    );

            Intent share =
                    new Intent(Intent.ACTION_SEND);

            share.setType("text/plain");

            share.putExtra(
                    Intent.EXTRA_TEXT,
                    reporte
            );

            startActivity(
                    Intent.createChooser(
                            share,
                            "Compartir reporte con..."
                    )
            );
        });

        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });
    }
}