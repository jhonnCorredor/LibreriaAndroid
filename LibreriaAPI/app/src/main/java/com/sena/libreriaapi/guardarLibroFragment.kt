package com.sena.libreriaapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sena.libreriaapi.config.urls
import com.sena.libreriaapi.entity.book
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class guardarLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guardar_libro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        val txtTitulo = view.findViewById<EditText>(R.id.txtTitulo)
        val txtAutor = view.findViewById<EditText>(R.id.txtAutor)
        val txtIsbn = view.findViewById<EditText>(R.id.txtIsbn)
        val txtGenero = view.findViewById<EditText>(R.id.txtGenero)
        val txtEjemDisponible = view.findViewById<EditText>(R.id.txtEjemDisponible)
        val txtEjemOcupados = view.findViewById<EditText>(R.id.txtEjemOcupados)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardar)

        // Configurar el OnClickListener
        btnGuardar.setOnClickListener {
            val titulo = txtTitulo.text.toString()
            val autor = txtAutor.text.toString()
            val isbn = txtIsbn.text.toString()
            val genero = txtGenero.text.toString()
            val num_ejem_disponible = txtEjemDisponible.text.toString().toIntOrNull() ?: 0
            val num_ejem_ocupados = txtEjemOcupados.text.toString().toIntOrNull() ?: 0

            val libro = book(0, titulo, autor, isbn, genero, num_ejem_disponible, num_ejem_ocupados)
            guardarLibro(libro)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //tarea para los aprendices crear el formulario para el registro de libros
    fun guardarLibro(libro : book){
        try{
            // se crean los parametros
            var parametros = JSONObject()
            //parametros.put("nombre-variable", "valor-variable")
            parametros.put("titulo", libro.titulo)
            parametros.put("autor", libro.autor)
            parametros.put("isbn", libro.isbn)
            parametros.put("genero", libro.genero)
            parametros.put("num_ejem_disponible", libro.num_ejem_disponible)
            parametros.put("num_ejem_ocupados", libro.num_ejem_ocupados)

            var request = JsonObjectRequest(
                Request.Method.POST,
                urls.urlLibro,
                parametros,
                {response->
                    Toast.makeText(context, "Libro guardado correctamente", Toast.LENGTH_SHORT).show()
                },
                {error->
                    Toast.makeText(context, "Error al guardar el libro", Toast.LENGTH_SHORT).show()
                }
            )
            val queue = Volley.newRequestQueue(this.context)
            queue.add(request)
        }catch(error : Exception){

        }
    }
}