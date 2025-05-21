package TDLBackend.tdl.Meal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;


@Component
public class MealController {
	
	private static String MEAL_CONTROLLER = "/meal";
	private static String MEAL_GET_MEALS_IN_WEEK_EVENT = "getMealsInWeek";
	private static String MEAL_BROADCAST_MEALS_IN_WEEK_EVENT = MEAL_GET_MEALS_IN_WEEK_EVENT + "/year/{0,number,#}/calenderWeek/{1,number,#}";
	private static String MEAL_ADD_MEAL_EVENT = "add";
    private static String MEAL_DELETE_MEAL_EVENT = "delete";


	@Autowired
	MealRepository mealRepository;

    @Autowired
    private SocketIOServer socketServer;

    private SocketIONamespace socketNamespaceMealController;

    MealController(SocketIOServer socketServer){
        this.socketServer = socketServer;
		this.socketNamespaceMealController = this.socketServer.addNamespace(MEAL_CONTROLLER);

        // Add event listeners
        this.socketNamespaceMealController.addEventListener(MEAL_GET_MEALS_IN_WEEK_EVENT, MealDate.class, fetchMeals);
        this.socketNamespaceMealController.addEventListener(MEAL_ADD_MEAL_EVENT, Meal.class, addMeal);
        this.socketNamespaceMealController.addEventListener(MEAL_DELETE_MEAL_EVENT, (Class)Integer.class, deleteMeal);
    }

    private DataListener<Meal> addMeal = new DataListener<Meal>() {
        @Override
        public void onData(SocketIOClient client, Meal meal, AckRequest acknowledge) throws Exception 
        {
            try 
            {
                mealRepository.addMeal(meal);
                LocalDate date = meal.getMealDate();
                Integer year = date.get(WeekFields.ISO.weekBasedYear());
                Integer weekNumber = date.get(WeekFields.ISO.weekOfWeekBasedYear());
                broadcastMealsInWeekEvent(new MealDate(year, weekNumber));
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to add meal");
            }
        }
    };

    private DataListener<Integer> deleteMeal = new DataListener<Integer>() {
        @Override
        public void onData(SocketIOClient client, Integer id, AckRequest acknowledge) throws Exception 
        {
            try 
            {
                Meal meal = mealRepository.getMealByID(id);
                mealRepository.deleteMeal(id);
                LocalDate date = meal.getMealDate();
                Integer year = date.get(WeekFields.ISO.weekBasedYear());
                Integer weekNumber = date.get(WeekFields.ISO.weekOfWeekBasedYear());
                broadcastMealsInWeekEvent(new MealDate(year, weekNumber));
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to delete meal");
            }
        }
    };

    private DataListener<MealDate> fetchMeals = new DataListener<MealDate>() {
        @Override
        public void onData(SocketIOClient client, MealDate mealDate, AckRequest acknowledge) throws Exception 
        {
            List<Meal> mealsInWeek;

            try 
            {
                mealsInWeek = mealRepository.getMealsInCalenderWeek(mealDate.getYear(), mealDate.getCalenderWeek());
                client.sendEvent(
                    MessageFormat.format(
                        MEAL_BROADCAST_MEALS_IN_WEEK_EVENT, 
                        mealDate.getYear(), 
                        mealDate.getCalenderWeek()
                    ), 
                    mealsInWeek
                );
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to get meals in week");
            }
        }
    };

    private void broadcastMealsInWeekEvent(MealDate mealDate) throws Exception
	{
        List<Meal> mealsInWeek;

        try 
        {
            mealsInWeek = mealRepository.getMealsInCalenderWeek(mealDate.getYear(), mealDate.getCalenderWeek());
        }
        catch(Exception e) 
        {
            throw new Exception("Failed to get meals in week");
        }

		this.socketNamespaceMealController.getBroadcastOperations().sendEvent(
            MessageFormat.format(
                MEAL_BROADCAST_MEALS_IN_WEEK_EVENT, 
                mealDate.getYear(), 
                mealDate.getCalenderWeek()
            ), 
            mealsInWeek
        );
	}
}
