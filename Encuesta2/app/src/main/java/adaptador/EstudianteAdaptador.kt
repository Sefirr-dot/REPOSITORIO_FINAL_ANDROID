package adaptador

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.encuesta.R
import modelo.Estudiante

class EstudianteAdaptador(private val listaEstudiantes: ArrayList<Estudiante>):
        RecyclerView.Adapter<EstudianteAdaptador.EstudianteViewHolder>() {
            private val selectedItems = mutableListOf<Int>()
        inner class EstudianteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.txtNombre1)
            val sistemaTextView: TextView = itemView.findViewById(R.id.txtSistemaOperativo1)
            val especialidadTextView: TextView = itemView.findViewById(R.id.txtEspecialidad1)
            val horasTextView: TextView = itemView.findViewById(R.id.txtHorasEstudio1)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_personas, parent, false)
        return EstudianteViewHolder(itemView)
    }
    //Este método se llama para actualizar el contenido de un ViewHolder existente
    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {



        val estu = listaEstudiantes[position]
        holder.nameTextView.text = estu.nombre
        holder.sistemaTextView.text = "Sistema Operativo: ${estu.sistemaOperativo} "
        holder.especialidadTextView.text = "Especialidad en: ${estu.especialidad}"
        holder.horasTextView.text = "Horas de Estudio: ${estu.horasEstudio}"

        //accedo al imageView


        //definimos el evento click para cada elemento de la lista
        holder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.add(position)
            }
            Toast.makeText(holder.itemView.context, "Clicked: ${estu.nombre}", Toast.LENGTH_SHORT).show()
            Log.d("ACSCO", "Clicked: ${selectedItems.joinToString(", ")}")
            notifyItemChanged(position) // Avisas que ha habido un cambio en la posición y así pasa por el onBindViewHolder de nuevo y en el último if repinta.
        }

        //definimos el evento long click para cada elemento de la lista
        holder.itemView.setOnLongClickListener {
            val removedPosition = holder.adapterPosition
            Toast.makeText(holder.itemView.context, "Clicked: ${estu.nombre}", Toast.LENGTH_SHORT).show()

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Borrado")
                .setMessage("¿Estás seguro de que deseas eliminar el planeta?")
                .setPositiveButton("Borrar") { dialog, _ ->
                    val removedItem = listaEstudiantes.removeAt(removedPosition) // Remove from data list
                    notifyItemRemoved(removedPosition) // Notify adapter
                    // Update selected items positions
                    // Update indices from removed position to the end
                    notifyItemRangeChanged(removedPosition, listaEstudiantes.size - removedPosition)
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
        return listaEstudiantes.size
    }
    fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }
        }
