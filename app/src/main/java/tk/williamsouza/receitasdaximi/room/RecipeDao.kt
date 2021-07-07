package tk.williamsouza.receitasdaximi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tk.williamsouza.receitasdaximi.models.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = (:recipeId)")
    suspend fun getById(recipeId: Int): Recipe

    @Insert
    suspend fun insertAll(vararg recipes: Recipe)

    @Query("DELETE FROM recipe WHERE id = (:recipeId)")
    suspend fun delete(recipeId: Int)

    @Query("UPDATE recipe SET title = (:title), ingredients = (:ingredients), instructions = (:instructions), date = (:date) WHERE id = (:id)")
    suspend fun update(id: Int, title: String, ingredients: String, instructions: String, date: String)
}