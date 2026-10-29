package com.example.facturabanck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsuario, etContrasena;
    Button btnIngresar;
    TextView tvMensaje;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvMensaje = findViewById(R.id.tvMensaje);

        dbHelper = new DBHelper(this);

        btnIngresar.setOnClickListener(v -> validarIngreso());
    }

    private void validarIngreso() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            tvMensaje.setText("Debe ingresar usuario y contraseña.");
            return;
        }

        boolean valido = dbHelper.validarUsuario(usuario, contrasena);

        if (valido) {
            Toast.makeText(this, "Bienvenido a Factura Banck", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            tvMensaje.setText("Usuario o contraseña incorrectos.");
        }
    }
}