package modelo

object Almacen {
    private val estudiantes = ArrayList<Estudiante>()

    fun agregarEstudiante(estudiante: Estudiante) {
        estudiantes.add(estudiante)
    }

    fun getEstudiante(): ArrayList<Estudiante> {
        return estudiantes
    }
    fun eliminarTodosLosEstudiantes() {
        estudiantes.clear()
    }
}
