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

class EstudianteAdaptador(private val listaEstudiantes: ArrayList<Estudiante>) :
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

    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        val estu = listaEstudiantes[position]

        Log.d("EstudianteAdaptador", "Vinculando estudiante: ${estu.nombre}")

        holder.nameTextView.text = estu.nombre
        holder.sistemaTextView.text = "Sistema Operativo: ${estu.sistemaOperativo}"
        holder.especialidadTextView.text = "Especialidad en: ${estu.especialidad.joinToString(", ")}"
        holder.horasTextView.text = "Horas de Estudio: ${estu.horasEstudio}"

        // Click para seleccionar el item
        holder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.add(position)
            }
            Toast.makeText(holder.itemView.context, "Clicked: ${estu.nombre}", Toast.LENGTH_SHORT).show()
            Log.d("EstudianteAdaptador", "Selected items: ${selectedItems.joinToString(", ")}")
            notifyItemChanged(position)
        }

        // Long click para eliminar el item
        holder.itemView.setOnLongClickListener {
            val removedPosition = holder.adapterPosition
            Toast.makeText(holder.itemView.context, "Long Clicked: ${estu.nombre}", Toast.LENGTH_SHORT).show()

            Log.d("EstudianteAdaptador", "Eliminando estudiante: ${estu.nombre}")

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Borrado")
                .setMessage("¿Estás seguro de que deseas eliminar el estudiante?")
                .setPositiveButton("Borrar") { dialog, _ ->
                    listaEstudiantes.removeAt(removedPosition)
                    notifyItemRemoved(removedPosition)
                    notifyItemRangeChanged(removedPosition, listaEstudiantes.size - removedPosition)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

            true
        }
    }


    override fun getItemCount(): Int {
        return listaEstudiantes.size
    }

    fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }
}
