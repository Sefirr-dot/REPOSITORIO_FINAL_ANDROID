package com.example.dam2t_t1_proyecto1

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.editTextPhone)
        editText.filters = arrayOf<InputFilter>(InputFilterMinMax("0", "999999999"))


        val email: EditText = findViewById(R.id.edtEmail)

        if (isValidEmail(email.text.toString())) { // Obtener el texto del EditText
            // Email is valid, do something
        } else {
            // Email is invalid, show error message
            email.error = "Correo electronico Invalido" // Asignar el error al EditText correcto
        }
        val etFecha = findViewById<EditText>(R.id.etdFechaNacimiento)
        etFecha.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = android.app.DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                // Formatear la fecha
                val fechaFormateada =
                    String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
                etFecha.setText(fechaFormateada)
            }, year, month, day)

            dpd.show()
        }

        val password1: EditText = findViewById(R.id.etPswd1)
        val password2: EditText = findViewById(R.id.etPswd2)
        password2.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (password1.text.toString() == password2.text.toString()) {
                    // Las contraseñas coinciden
                } else {
                    password2.error = "Las contraseñas no coinciden"
                }
            }
        }
        val boton = findViewById<Button>(R.id.btValidar)

        boton.setOnClickListener {
            if (validarFormulario()) {
                val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
                val email = findViewById<EditText>(R.id.edtEmail).text.toString()
                val password = findViewById<EditText>(R.id.etPswd1).text.toString()
                val apellido1 = findViewById<EditText>(R.id.etApellido1).text.toString()
                val telefono = findViewById<EditText>(R.id.editTextPhone).text.toString()
                val nacimiento = findViewById<EditText>(R.id.etdFechaNacimiento).text.toString()

                val mensaje = "Nombre: $nombre\n Apellido: $apellido1\nTelefono: $telefono Fecha de nacimiento: $nacimiento\n Email: $email\nContraseña: $password"
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Hay errores en el formulario", Toast.LENGTH_SHORT).show()
            }
        }

        val boton2 = findViewById<Button>(R.id.btLimpiar)
        boton2.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre)
            val email = findViewById<EditText>(R.id.edtEmail)
            val password = findViewById<EditText>(R.id.etPswd1)
            val password2 = findViewById<EditText>(R.id.etPswd2)
            val apellido1 = findViewById<EditText>(R.id.etApellido1)
            val apellido2= findViewById<EditText>(R.id.etApellido2)
            val telefono = findViewById<EditText>(R.id.editTextPhone)
            val nacimiento = findViewById<EditText>(R.id.etdFechaNacimiento)
            nombre.text.clear()
            apellido1.text.clear()
            apellido2.text.clear()
            telefono.text.clear()
            nacimiento.text.clear()
            email.text.clear()
            password.text.clear()
            password2.text.clear()
        }

    }
    private fun validarFormulario(): Boolean {
        val nombre = findViewById<EditText>(R.id.etNombre)
        val apellido1 = findViewById<EditText>(R.id.etApellido1)

        val telefono = findViewById<EditText>(R.id.editTextPhone)
        val nacimiento = findViewById<EditText>(R.id.etdFechaNacimiento)
        val email = findViewById<EditText>(R.id.edtEmail)
        val password = findViewById<EditText>(R.id.etPswd1)
        val password2 = findViewById<EditText>(R.id.etPswd2)
        // ... otros campos

        if (nombre.text.toString().isEmpty()) {
            nombre.error = "El nombre es obligatorio"
            nombre.requestFocus()
            return false
        }
        if (apellido1.text.toString().isEmpty()) {
            apellido1.error = "El primer apellido es obligatorio"
            apellido1.requestFocus()
            return false
        }
        if (telefono.text.toString().isEmpty()) {
            telefono.error = "El telefono es obligatorio"
            telefono.requestFocus()
            return false
        }
        if (nacimiento.text.toString().isEmpty()) {
            nacimiento.error = "La fecha de nacimiento es obligatoria"
            nacimiento.requestFocus()
            return false
        }

        if (!isValidEmail(email.text.toString())) {
            email.error = "Email inválido"
            email.requestFocus()
            return false
        }

        if (password.text.toString().length < 6) {
            password.error = "La contraseña debe tener al menos 6 caracteres"
            password.requestFocus()
            return false
        }
        if (!isValidEmail(email.text.toString())) {
            email.error = "Email inválido"
            email.requestFocus()
            return false
        }

        if (password.text.toString().length < 6) {
            password.error = "La contraseña debe tener al menos 6 caracteres"
            password.requestFocus()
            return false
        }

        if (password.text.toString() != password2.text.toString()) {
            password2.error = "Las contraseñas no coinciden"
            password2.requestFocus()
            return false
        }



        return true
    }
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    }
    class InputFilterMinMax : InputFilter {
        private var min: Int
        private var max: Int

        constructor(min: Int, max: Int) {
            this.min = min
            this.max = max
        }

        constructor(min: String, max: String) {
            this.min = min.toInt()
            this.max = max.toInt()
        }

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = (dest.toString() + source.toString()).toInt()
                if (isInRange(min, max, input))
                    return null
            } catch (nfe: NumberFormatException) {
            }
            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
