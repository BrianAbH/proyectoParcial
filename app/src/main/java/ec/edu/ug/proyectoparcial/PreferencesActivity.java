package ec.edu.ug.proyectoparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PreferencesActivity extends AppCompatActivity {

    private static String MIS_PREFERENCIAS = "MIS_PREFERENCIAS";
    private static String NOMBRE = "NOMBRE";
    private static String CURSO = "CURSO";
    private static String ESTADO = "ESTADO";

    private ImageButton ivVolver;
    private EditText etNombre, etCurso;
    private Button btnGuardarPrfs, btnLimpiar;
    private Switch swSaludo;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preferences);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.prefencias), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        preferences = getSharedPreferences(MIS_PREFERENCIAS, Context.MODE_PRIVATE);

        iniciarComponenetes();
        validarCampos();
        volverMenu();
        llenarCampos();
        guardarEstado();
    }

    private void iniciarComponenetes(){
        ivVolver = findViewById(R.id.ivVolver);
        etNombre = findViewById(R.id.etNombre);
        etCurso = findViewById(R.id.etCurso);
        btnGuardarPrfs = findViewById(R.id.btnGuardarPrfs);
        swSaludo = findViewById(R.id.swSaludo);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        btnLimpiar.setOnClickListener(v->eliminarPreferencias());
    }

    private void validarCampos(){
        btnGuardarPrfs.setOnClickListener(v->{
            if (etNombre.getText().toString().isEmpty() || etCurso.getText().toString().isEmpty()){
                etNombre.setError(getString(R.string.error_nombre));
                etCurso.setError(getString(R.string.error_curso));
            }else{
                guardarPrfs();
            }
        });
    }

    private void guardarPrfs(){
        preferences.edit()
        .putString(NOMBRE, etNombre.getText().toString().trim())
        .putString(CURSO, etCurso.getText().toString().trim())
        .apply();
        Toast.makeText(this,R.string.msj_toast, Toast.LENGTH_LONG).show();
    }

    private void guardarEstado(){
        swSaludo.setOnCheckedChangeListener((Switch, isChecked) -> {
            if (isChecked){
                preferences.edit()
                        .putBoolean(ESTADO, true).apply();
            }else{
                preferences.edit()
                        .putBoolean(ESTADO, false).apply();
            }
        });
    }

    private void volverMenu(){
        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });
    }


    private void llenarCampos(){
        String nombre = preferences.getString("NOMBRE","");
        String curso = preferences.getString("CURSO","");
        boolean estado = preferences.getBoolean("ESTADO", false);
        etNombre.setText(nombre);
        etCurso.setText(curso);
        swSaludo.setChecked(estado);
    }

    private void eliminarPreferencias(){
        preferences.edit().clear().apply();
        etNombre.setText("");
        etCurso.setText("");
        Toast.makeText(this, R.string.btn_limpiar_texto, Toast.LENGTH_SHORT).show();
    }


}
