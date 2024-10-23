package modelo

import java.io.Serializable

data class Estudiante(val nombre: String,val sistemaOperativo: String ,val especialidad: ArrayList<String>,val horasEstudio: Int):Serializable {

}
