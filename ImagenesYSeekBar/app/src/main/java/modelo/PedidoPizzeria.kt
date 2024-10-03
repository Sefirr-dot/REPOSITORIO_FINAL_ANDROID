package modelo

data class PedidoPizzeria(
    val nombre: String,
    val queso: Boolean,
    val bacon: Boolean,
    val cebolla: Boolean,
    val borde: Boolean,
    val bordePan: Boolean,
)
