package modelo

import java.io.Serializable

data class Estudiante(
    val nombre: String,
    val sistemaOperativo: String,
    val especialidad: MutableList<String>,
    val horasEstudio: Int):Serializable {

}
