package TDLBackend.tdl.Meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal,Integer> {

     @Modifying
     @Transactional
     @Query(value="insert into meal (label,mealdate) values(:#{#meal.label}, :#{#meal.mealDate})", nativeQuery = true)
     void addMeal(@Param("meal") Meal meal);

     @Query(value="select * from (select *,date_part('week', mealdate) as cw, date_part('isoyear', mealdate) as year from meal) where year = :year and cw = :weeknumber", nativeQuery = true)
     List<Meal> getMealsInCalenderWeek(@Param("year") int year, @Param("weeknumber") int weekNumber);

     @Modifying
     @Transactional
     @Query(value="delete from meal m where m.id = :id", nativeQuery = true)
     void deleteMeal(Integer id);

     @Query(value="select * from meal m where m.id = :id", nativeQuery = true)
     Meal getMealByID(Integer id);
}
