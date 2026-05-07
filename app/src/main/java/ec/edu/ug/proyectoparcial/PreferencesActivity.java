package ec.edu.ug.proyectoparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PreferencesActivity extends AppCompatActivity {

    ImageButton ivVolver;
    EditText etNombre, etCurso;
    Button btnGuardarPrfs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preferences);

        //Referencias
        ivVolver = findViewById(R.id.ivVolver);
        etNombre = findViewById(R.id.etNombre);
        etCurso = findViewById(R.id.etCurso);
        btnGuardarPrfs = findViewById(R.id.btnGuardarPrfs);
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });

        btnGuardarPrfs.setOnClickListener(v->{
            if (etNombre.getText().toString().isEmpty() || etCurso.getText().toString().isEmpty()){
                etNombre.setError("No puede quedar vacio");
                etCurso.setError("No puede quedar vacio");
            }else{
                guardarPrfs();
            }
        });

        String nombreP = prefs.getString("Nombre", "");
        etNombre.setText(nombreP);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.prefencias), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void guardarPrfs(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Nombre", etNombre.getText().toString().trim());
        editor.putString("Curso", etCurso.getText().toString().trim());
        editor.commit();
        Toast.makeText(this,"Datos Guardados Correctamente", Toast.LENGTH_LONG).show();
    }

}
