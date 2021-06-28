package tk.williamsouza.receitasdaximi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.williamsouza.receitasdaximi.adapters.RecipeRecyclerViewAdapter
import tk.williamsouza.receitasdaximi.models.Recipe
import tk.williamsouza.receitasdaximi.room.AppDatabase

class MainFragment : Fragment() {
    private lateinit var recipeAdapter: RecipeRecyclerViewAdapter
    private var data: List<Recipe> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment
        val recipeButton = view.findViewById<FloatingActionButton>(R.id.addRecipeButton)

        recipeButton?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newRecipeFragment)
        }

        val recipeSearch = view.findViewById<EditText>(R.id.recipeSearch)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun initRecyclerView() {
        val recipeView = this.requireView().findViewById<RecyclerView>(R.id.recipeRecyclerView)
        recipeAdapter = RecipeRecyclerViewAdapter()

        recipeView.layoutManager = LinearLayoutManager(this.context)

        recipeView.adapter = recipeAdapter
    }

    private fun setData(_data: List<Recipe>) {
        data = _data
    }

    private fun addDataSet() {
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "recipe"
        ).build()

        CoroutineScope(IO).launch {
            val data = db.recipeDao().getAll()
            withContext(Main) {
                setData(data)
                recipeAdapter.submitList(data)

                recipeAdapter.notifyDataSetChanged()
            }
        }
    }


}