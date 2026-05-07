package ec.edu.ug.proyectoparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Elementos
    Button btnUg;
    CardView cvPreferencias;

    TextView tvSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Referencias
        btnUg = findViewById(R.id.btnUriUg);
        cvPreferencias = findViewById(R.id.cvPreferencias);
        tvSaludo = findViewById(R.id.tvSaludo);
        SharedPreferences prfs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        String nombre = prfs.getString("Nombre","");

        tvSaludo.setText("Hola " + nombre);



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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}