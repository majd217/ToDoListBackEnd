package TDLBackend.tdl.Recipe;

import TDLBackend.tdl.Item.Item;
import TDLBackend.tdl.Meal.Meal;
import TDLBackend.tdl.Meal.MealDate;
import TDLBackend.tdl.Store.StoreController;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.List;


public class RecipeController {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	private SocketIOServer socketServer;
	
	private SocketIONamespace socketNamespaceRecipeController;
	
	private static String RECIPE_CONTROLLER = "/recipe";
	
	private static String RECIPE_FETCH_RECIPE_EVENT = "fetch";
	private static String RECIPE_ADD_RECIPE_EVENT = "add";
	private static String RECIPE_UPDATE_RECIPE_EVENT = "update";
	private static String RECIPE_DELETE_RECIPE_EVENT = "delete";
	
	RecipeController(SocketIOServer socketServer) {
		this.socketServer = socketServer;
		this.socketNamespaceRecipeController = this.socketServer.addNamespace(RECIPE_CONTROLLER);
		
		// Add event listeners
		socketNamespaceRecipeController.addEventListener(RECIPE_ADD_RECIPE_EVENT, RecipeEntity.class, addRecipe);
		socketNamespaceRecipeController.addEventListener(RECIPE_DELETE_RECIPE_EVENT, (Class) List.class, deleteRecipe);
		socketNamespaceRecipeController.addEventListener(RECIPE_UPDATE_RECIPE_EVENT, (Class) HashMap.class, updateRecipe);
	}
	
	private DataListener<RecipeEntity> addRecipe = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			try {
				recipeRepository.createRecipe(data.getName(), data.getIngredients(), data.getInstructions());
			} catch (Exception e) {
				throw new Exception("Failed to create Recipe");
			}
			
		}
	};
	private DataListener<RecipeEntity> deleteRecipe = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			try {
				recipeRepository.deleteRecipe(data.getId());
			} catch (Exception e) {
				throw new Exception("Failed to delete Recipe");
			}
			
		}
	};
	
	private DataListener<RecipeEntity> updateRecipe = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			try {
				recipeRepository.updateRecipe(data.getId(), data.getName(), data.getIngredients(), data.getInstructions());
			} catch (Exception e) {
				throw new Exception("Failed to update Recipe");
			}
		}
	};
	
	private DataListener<RecipeEntity> fetchRecipes = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			List<RecipeEntity> recipes;
			
			try {
				recipes = recipeRepository.fetchRecipes();
				client.sendEvent(RECIPE_FETCH_RECIPE_EVENT, recipes);
				broadcastRecipes(recipes);
				
			} catch (Exception e) {
				throw new Exception("Failed to fetch Recipes");
			}
		}
	};
	
	private void broadcastRecipes(List<RecipeEntity> recipes) throws Exception {
		this.socketNamespaceRecipeController.getBroadcastOperations().sendEvent(RECIPE_FETCH_RECIPE_EVENT, recipes);
	}
	
	
}
