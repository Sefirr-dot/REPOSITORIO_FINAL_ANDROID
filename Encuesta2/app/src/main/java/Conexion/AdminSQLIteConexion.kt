package Conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.e("ACSCO", "Paso por OnCreate del AdminSQLLite")
        db.execSQL(
            """
        CREATE TABLE personas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT,
            sistemaOperativo TEXT,
            especialidad TEXT,
            horasEstudio INTEGER
        )
        """
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("ACSCO","Paso por OnUpgrade del AdminSQLLite")
        //si la BBDD ya existe, se modificaria con el SQL que aquí pongamos.
        //Si no existe se va al OnCreate, si existe, viene aquí.
        //para venir aquí has tenido que pasar una versión diferente y se detecta automáticamente y pasa por aquí.
        //por ejemplo podríamos borrar una tabla con DROP y luego crearla si interesa que una tabla esté siempre vacía
        //o le hago un truncate y me cargo sus datos directamente. (por ejemplo la típica tabla de registro de bitácora de la sesión)
    }
}