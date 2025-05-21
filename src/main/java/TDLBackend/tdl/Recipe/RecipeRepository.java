package TDLBackend.tdl.Recipe;

import TDLBackend.tdl.Item.Item;
import TDLBackend.tdl.Meal.Meal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer>  {
	
	@Query(value="SELECT * FROM recipe", nativeQuery = true)
	List<RecipeEntity> fetchRecipes();
	@Modifying
	@Transactional
	@Query(value="INSERT INTO recipe (name,description,ingredients) VALUES (name,description,ingredients)", nativeQuery = true)
	int createRecipe( @Param("name") String recipeName,
	                   @Param("description") ArrayList<String> description,
	                   @Param("ingredients") ArrayList<String> ingredients);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE recipe SET name = :name,description = :description, ingredients = :ingredients WHERE id = :id", nativeQuery = true)
	int updateRecipe( @Param("id") Integer id,
	                   @Param("name") String recipeName,
	                   @Param("description") ArrayList<String> description,
	                   @Param("ingredients") ArrayList<String> ingredients);
	
	@Modifying
	@Transactional
	@Query(value="delete from recipe r where r.id = :id", nativeQuery = true)
	void deleteRecipe(Integer id);
	
}