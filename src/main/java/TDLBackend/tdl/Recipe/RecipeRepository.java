package TDLBackend.tdl.Recipe;

import TDLBackend.tdl.Item.Item;
import TDLBackend.tdl.Meal.Meal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer>  {
	
	@Query(value="SELECT * FROM recipe", nativeQuery = true)
	List<RecipeEntity> fetchRecipes();
	
	
	@Query(value="SELECT * FROM recipe where recipeId = :recipeId", nativeQuery = true)
	RecipeEntity fetchRecipe(@Param("recipeId") int recipeId);
	//then delete all from the recipeingredient as well
	@Modifying
	@Transactional
	@Query(value="delete from recipe r where r.id = :id", nativeQuery = true)
	void deleteRecipe(Integer id);
	
	@Query("SELECT r FROM RecipeEntity r WHERE r.id = :id")
	RecipeEntity findWithDetailsById(@Param("id") int id);
	
	
	//list<ingredients> select * from recipeingredient where recipe_id = :id,
	//list<instructions>: select * from instruction where instruction_id = :id,
	// recipename: select * from recipe where recipe_id = :id
	
	
	
}