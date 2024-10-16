package modelo

object Almacen {
    var listaEstudiantes = ArrayList<Estudiante>()
    fun addEstudiante(estudiante: Estudiante) {
        listaEstudiantes.add(estudiante)

    }
    fun getEstudiante(): ArrayList<Estudiante> {
        return listaEstudiantes
    }
    fun clearlistaEstudiantes() {
        listaEstudiantes.clear()
    }
}