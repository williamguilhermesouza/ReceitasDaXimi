package tk.williamsouza.receitasdaximi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.williamsouza.receitasdaximi.adapters.RecipeRecyclerViewAdapter
import tk.williamsouza.receitasdaximi.models.Recipe
import tk.williamsouza.receitasdaximi.room.AppDatabase

class RecipesListFragment : Fragment() {
    private lateinit var recipeAdapter: RecipeRecyclerViewAdapter
    private var data: List<Recipe> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes_list, container, false)
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

        CoroutineScope(Dispatchers.IO).launch {
            var data = db.recipeDao().getAll()
            withContext(Dispatchers.Main) {
                val search = arguments?.get("search") as String
                if (search != "") {
                    data = data.filter {
                        it.title!!.contains(search)
                    }
                }
                setData(data)
                recipeAdapter.submitList(data)

                recipeAdapter.notifyDataSetChanged()
            }
        }
    }

}