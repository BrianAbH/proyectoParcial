package ec.edu.ug.proyectoparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static String NOMBRE;
    //Elementos
    private Button btnUg;
    private CardView cvPreferencias, cvRegistrar, cvInventario,cvReporte;

    private TextView tvSaludo;

    private SharedPreferences prfs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iniciarComponentes();
        obtenerPreferenciaNombre();
        iniciarIntent();
        saludoInicial();
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerPreferenciaNombre();
        saludoInicial();
    }

    private void iniciarComponentes(){
        //Referencias
        btnUg = findViewById(R.id.btnUriUg);
        cvPreferencias = findViewById(R.id.cvPreferencias);
        cvRegistrar = findViewById(R.id.cvRegistrar);
        cvInventario = findViewById(R.id.cvInventario);
        tvSaludo = findViewById(R.id.tvSaludo);
        cvReporte = findViewById(R.id.cvReporte);
    }

    private void iniciarIntent(){
        //Intent implicito
        btnUg.setOnClickListener(v->{
            Uri urlUg = Uri.parse("https://www.ug.edu.ec/");
            Intent urlI = new Intent(Intent.ACTION_VIEW,urlUg);
            startActivity(urlI);

        });

        cvPreferencias.setOnClickListener(v->{
            Intent iPreferencias = new Intent(this, PreferencesActivity.class);
            startActivity(iPreferencias);
        });

        cvRegistrar.setOnClickListener(v->{
            Intent iRegistrar = new Intent(this, RegistrarActivity.class);
            startActivity(iRegistrar);
        });

        cvInventario.setOnClickListener(v->{
            Intent iInventario = new Intent(this, InventarioActivity.class);
            startActivity(iInventario);
        });

        cvReporte.setOnClickListener(v -> {

            Intent iReporte = new Intent(this, ReporteActivity.class);
            startActivity(iReporte);

        });
    }


    private void obtenerPreferenciaNombre(){
        prfs = getSharedPreferences("MIS_PREFERENCIAS", Context.MODE_PRIVATE);
        NOMBRE = prfs.getString("NOMBRE","");

        if (NOMBRE.isEmpty()){
            tvSaludo.setText(getString(R.string.msj_bienvenida_pordefecto));
        }else{
            tvSaludo.setText(getString(R.string.msjBienvenida, NOMBRE));
        }
    }

    private void saludoInicial(){
        boolean estado = prfs.getBoolean("ESTADO",false);
        if (estado){
            Toast.makeText(this, getString(R.string.msjBienvenida, NOMBRE), Toast.LENGTH_SHORT).show();
        }
    }
}