package ec.edu.ug.proyectoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarActivity extends AppCompatActivity {

    ImageButton ivVolver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_registrar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        iniciarViews();
        volverMennu();
    }

    private void iniciarViews(){
        ivVolver = findViewById(R.id.ivVolver);
    }

    private void volverMennu(){
        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });
    }




}
