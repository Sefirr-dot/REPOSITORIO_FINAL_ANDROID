package Auxiliar

import Conexion.AdminSQLIteConexion
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import modelo.Estudiante

object Conexion {
    private var DATABASE_NAME = "estudiantes.db3"
    private var DATABASE_VERSION = 1

    fun cambiarBD(nombreBD: String) {
        this.DATABASE_NAME = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, p: Estudiante): Long {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()

        registro.put("nombre", p.nombre)
        registro.put("sistemaOperativo", p.sistemaOperativo)
        registro.put("horasEstudio", p.horasEstudio)
        val especialidadStr = p.especialidad.joinToString(",") // Convertir especialidad a String
        registro.put("especialidad", especialidadStr)

        val codigo = bd.insert("personas", null, registro)
        bd.close()
        return codigo
    }

    fun delPersona(contexto: AppCompatActivity, dni: String): Int {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val cant = bd.delete("personas", "dni=?", arrayOf(dni))
        bd.close()
        return cant
    }

    fun modPersona(contexto: AppCompatActivity, dni: String, p: Estudiante): Int {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()

        registro.put("nombre", p.nombre)
        registro.put("sistemaOperativo", p.sistemaOperativo)
        registro.put("horasEstudio", p.horasEstudio)
        val especialidadStr = p.especialidad.joinToString(",")
        registro.put("especialidad", especialidadStr)

        val cant = bd.update("personas", registro, "dni=?", arrayOf(dni))
        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, dni: String): Estudiante? {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery(
            "SELECT nombre, sistemaOperativo, especialidad, horasEstudio FROM personas WHERE dni=?",
            arrayOf(dni)
        )

        var estudiante: Estudiante? = null
        if (fila.moveToFirst()) {
            val nombre = fila.getString(0)
            val sistemaOperativo = fila.getString(1)
            val especialidadList = fila.getString(2).split(",").toCollection(ArrayList()) // Convertir especialidad de String a ArrayList
            val horasEstudio = fila.getInt(3)
            estudiante = Estudiante(nombre, sistemaOperativo, especialidadList, horasEstudio)
        }

        fila.close()
        bd.close()
        return estudiante
    }

    fun obtenerPersonas(contexto: AppCompatActivity): ArrayList<Estudiante> {
        val estudiantes = ArrayList<Estudiante>()
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("SELECT nombre, sistemaOperativo, especialidad, horasEstudio FROM personas", null)

        while (fila.moveToNext()) {
            val nombre = fila.getString(0)
            val sistemaOperativo = fila.getString(1)
            val especialidadList = fila.getString(2).split(",").toCollection(ArrayList())
            val horasEstudio = fila.getInt(3)
            val estudiante = Estudiante(nombre, sistemaOperativo, especialidadList, horasEstudio)
            estudiantes.add(estudiante)
        }

        fila.close()
        bd.close()
        return estudiantes
    }
}
