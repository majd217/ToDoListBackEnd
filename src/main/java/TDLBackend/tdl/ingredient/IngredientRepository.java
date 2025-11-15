//package TDLBackend.tdl.ingredient;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface IngredientRepository extends JpaRepository<IngredientEntity, Integer> {
//
//	@Query("insert into IngredientE (name) VALUES (:name)")
//	void insertNewIngredient(@Param("name") String name);
//
//	@Query("select * from ingredient where id =:id")
//	IngredientEntity fetchIngredient(@Param ("id") int id);
//
//	@Query("delete from ingredient i where i.id = :id")
//	void deleteIngredient(@Param("name") String name);
//}
