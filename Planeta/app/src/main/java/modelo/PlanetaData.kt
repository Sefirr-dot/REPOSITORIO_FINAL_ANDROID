package modelo

object PlanetaData {
    private val planetaList = mutableListOf(
        Planeta("Mercurio", 4879, 0.39, "mercurio"),
        Planeta("Venus", 12104, 0.72, "venus"),
        Planeta("Tierra", 12756, 1.00, "tierra"),
        Planeta("Marte", 6792, 1.52, "marte"),
        Planeta("Jupiter", 142984, 5.20, "jupiter"),
        Planeta("Saturno", 120536, 9.58, "saturno"),
        Planeta("Urano", 51118, 19.22, "urano"),
        Planeta("Neptuno", 49528, 30.05, "neptuno")
    )
    fun getPlanetas(): MutableList<Planeta> {
        return planetaList

    }
}