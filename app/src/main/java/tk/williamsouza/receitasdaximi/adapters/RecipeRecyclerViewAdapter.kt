package tk.williamsouza.receitasdaximi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import tk.williamsouza.receitasdaximi.R
import tk.williamsouza.receitasdaximi.models.Recipe

class RecipeRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Recipe> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RecipeViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(recipeList: List<Recipe>) {
        items = recipeList
    }

    class RecipeViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val recipeTitleButton = itemView.findViewById<Button>(R.id.recipeTitleButton)

        fun bind(recipe: Recipe) {
            recipeTitleButton.text = recipe.title

            recipeTitleButton.setOnClickListener {
                val bundle = bundleOf("recipe" to recipe)
                itemView.findNavController().navigate(R.id.action_recipesListFragment_to_editRecipeFragment, bundle)
            }
        }
    }
}