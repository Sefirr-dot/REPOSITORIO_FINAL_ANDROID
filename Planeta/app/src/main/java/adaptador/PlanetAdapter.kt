package adaptador

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.planeta.R
import modelo.Planeta

class PlanetAdapter(private val planets: MutableList<Planeta>) :
    RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {
    private val selectedItems = mutableSetOf<Int>()
    inner class PlanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.txtNombrePlaneta)
        val sizeTextView: TextView = itemView.findViewById(R.id.txtLongitudPlaneta)
        val distanceTextView: TextView = itemView.findViewById(R.id.txtDistancia)
        val planetaImageView: ImageView = itemView.findViewById(R.id.imgPlaneta)

    }
    //Este método se llama cuando se necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_planetas, parent, false)
        return PlanetViewHolder(itemView)
    }
    //Este método se llama para actualizar el contenido de un ViewHolder existente
    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {


        holder.planetaImageView.setImageResource( R.drawable.saturno)
        val planet = planets[position]
        holder.nameTextView.text = planet.nombre
        holder.sizeTextView.text = "Size: ${planet.longitud} km"
        holder.distanceTextView.text = "Distance: ${planet.distancia} AU"

        //accedo al imageView


        val imageResourceId = holder.itemView.context.resources.getIdentifier( // Use holder.itemView.context
            planet.nomImagen,"drawable",holder.itemView.context.packageName)

        holder.planetaImageView.setImageResource(imageResourceId)

        //definimos el evento click para cada elemento de la lista
        holder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.add(position)
            }
            Toast.makeText(holder.itemView.context, "Clicked: ${planet.nombre}", Toast.LENGTH_SHORT).show()
            Log.d("ACSCO", "Clicked: ${selectedItems.joinToString(", ")}")
            notifyItemChanged(position) // Avisas que ha habido un cambio en la posición y así pasa por el onBindViewHolder de nuevo y en el último if repinta.
        }

        //definimos el evento long click para cada elemento de la lista
        holder.itemView.setOnLongClickListener {
            val removedPosition = holder.adapterPosition
            Toast.makeText(holder.itemView.context, "Clicked: ${planet.nombre}", Toast.LENGTH_SHORT).show()

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Borrado")
                .setMessage("¿Estás seguro de que deseas eliminar el planeta?")
                .setPositiveButton("Borrar") { dialog, _ ->
                    val removedItem = planets.removeAt(removedPosition) // Remove from data list
                    notifyItemRemoved(removedPosition) // Notify adapter
                    // Update selected items positions
                    // Update indices from removed position to the end
                    notifyItemRangeChanged(removedPosition, planets.size - removedPosition)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

            true // Indicar que el evento ha sido consumido
        }

    }

    override fun getItemCount(): Int {
        return planets.size
    }
    fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }

}