package tk.williamsouza.receitasdaximi

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import tk.williamsouza.receitasdaximi.models.Recipe
import tk.williamsouza.receitasdaximi.room.AppDatabase
import java.time.LocalDateTime

class NewRecipeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val recipeTitle = view.findViewById<EditText>(R.id.titleInput)
        val recipeIngredients = view.findViewById<EditText>(R.id.ingredientsInput)
        val recipePreparation = view.findViewById<EditText>(R.id.preparationInput)
        saveButton.setOnClickListener {
            val title = recipeTitle.text.toString()
            val ingredients = recipeIngredients.text.toString()
            val preparation = recipePreparation.text.toString()

            val db = Room.databaseBuilder(
                requireContext().applicationContext,
                AppDatabase::class.java, "recipe"
            ).build()

            CoroutineScope(IO).launch {
                db.recipeDao().insertAll(Recipe(title = title, ingredients = ingredients, instructions = preparation, date = LocalDateTime.now().toString()))
            }
            findNavController().navigate(R.id.action_newRecipeFragment_to_mainFragment)
        }
        return view
    }

}