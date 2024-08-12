    package com.sena.libreriaapi

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.EditText
    import android.widget.Toast
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.android.volley.toolbox.JsonArrayRequest
    import com.android.volley.Request
    import com.android.volley.toolbox.JsonObjectRequest
    import com.android.volley.toolbox.Volley
    import com.sena.libreriaapi.adapter.libroAdapter
    import com.sena.libreriaapi.config.urls
    import com.sena.libreriaapi.entity.book

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: libroAdapter

    /**
     * A simple [Fragment] subclass.
     * Use the [ListaLibrosFragment.newInstance] factory method to
     * create an instance of this fragment.
     */
    class ListaLibrosFragment : Fragment() {
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
            val view = inflater.inflate(R.layout.fragment_lista_libros, container, false)
            // Cargar la lista de libros

            // Aquí es donde se inicializa el RecyclerView y el adaptador
            recyclerView = view.findViewById(R.id.listLibros)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = libroAdapter(mutableListOf()) { id ->
                eliminar_libro(id)
            }
            recyclerView.adapter = adapter
            cargar_lista_libros()



            return view
        }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment ListaLibrosFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                ListaLibrosFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }

        fun cargar_lista_libros(){
            try {
                /*
                se define el tipo de respuesta
                JSONArrayRequest= respuesta en formato JSON varios registros
                JSONObjectRequest= respuesta en formato JSON en unico registro
                StringRequest= respuesta en formato String
                 */
                var request= JsonArrayRequest(
                    Request.Method.GET,//METODO
                    urls.urlLibro,//URL del servicio web
                    null,//parametros
                    {response->//respuesta es correcta
                        val libros = mutableListOf<book>()

                        for (i in 0 until response.length()) {
                            val item = response.getJSONObject(i)
                            val libro = book(
                                id = item.getInt("id"),
                                titulo = item.getString("titulo"),
                                autor = item.getString("autor"),
                                isbn = item.getString("isbn"),
                                genero = item.getString("genero"),
                                num_ejem_disponible = item.getInt("num_ejem_disponible"),
                                num_ejem_ocupados = item.getInt("num_ejem_ocupados")
                            )
                            libros.add(libro)

                        }
                            var adapter = libroAdapter(libros) { id ->
                                eliminar_libro(id)
                            }
                            recyclerView.adapter = adapter
                        //hacer un recycleView que permita mostrar esta informacion
                    },{error->//respuesta es incorrecta o no es la respuesta esperada
                        var error=error
                    }
                )
                val queue = Volley.newRequestQueue(this.context)
                queue.add(request)
            }catch (error:Exception){

            }
        }

        fun eliminar_libro(id:Int){
            try {
                var request= JsonObjectRequest(
                    Request.Method.DELETE,//METODO
                    urls.urlLibro+id+"/",//URL del servicio web
                    null,//parametros
                    {response->
                        cargar_lista_libros()
                        Toast.makeText(this.context, "Eliminar."+id, Toast.LENGTH_SHORT).show()
                    },{error->//respuesta es incorrecta o no es la respuesta esperada
                        var error=error
                    }
                )
                val queue = Volley.newRequestQueue(this.context)
                queue.add(request)
            }catch (error : Exception){
                Toast.makeText(this.context, "Error al Eliminar: "+error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }