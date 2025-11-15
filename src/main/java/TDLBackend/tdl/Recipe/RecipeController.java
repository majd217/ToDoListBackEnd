package TDLBackend.tdl.Recipe;

import TDLBackend.tdl.Instruction.Instruction;
import TDLBackend.tdl.RecipeIngredient.RecipeIngredient;
//import TDLBackend.tdl.RecipeIngredient.RecipeIngredientRepository;
import TDLBackend.tdl.Store.Store;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import jakarta.transaction.Transactional;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


//TODO:
//Refactor tables to use model normailzation 1. iNGREDIENTS SHOULD BE IN THEIR OWN TABLE.
//Create a table for relation between recipe and ingredients
//So each ingredient can be reused

@Component
public class RecipeController {
	
	@Autowired
	RecipeRepository recipeRepository;
//
	private final SocketIOServer socketServer;
	private final SocketIONamespace socketNamespaceRecipeController;
	
	private static final String RECIPE_CONTROLLER = "/recipe";
	private static final String RECIPE_FETCH_RECIPE_EVENT = "fetch";
	private static final String RECIPE_ADD_RECIPE_EVENT = "add";
	private static final String RECIPE_UPDATE_RECIPE_EVENT = "update";
	private static final String RECIPE_DELETE_RECIPE_EVENT = "delete";
	
	@Autowired
	RecipeController(SocketIOServer socketServer) {
		this.socketServer = socketServer;
		this.socketNamespaceRecipeController = this.socketServer.addNamespace(RECIPE_CONTROLLER);
		
		socketNamespaceRecipeController.addEventListener(RECIPE_ADD_RECIPE_EVENT, RecipeEntity.class, createNewRecipe);
		socketNamespaceRecipeController.addEventListener(RECIPE_DELETE_RECIPE_EVENT, RecipeEntity.class, deleteRecipe);
		socketNamespaceRecipeController.addEventListener(RECIPE_UPDATE_RECIPE_EVENT, RecipeEntity.class, updateRecipe);
		
		socketNamespaceRecipeController.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				List<RecipeEntity> recipes;
				try
				{
					recipes = recipeRepository.fetchRecipes();
				}
				catch(Exception e)
				{
					return;
				}
				client.sendEvent(RECIPE_FETCH_RECIPE_EVENT, recipes);
			}
		});
	}
	private DataListener<RecipeEntity> findRecipeWithDetails = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			try {
				recipeRepository.findWithDetailsById(data.getId());
				broadcastRecipes();
			} catch (Exception e) {
				throw new Exception("Failed to create Recipe");
			}
		}
	};
	//Creates template for recipe with name and id
	//Instructions and ingredients should be initially empty
	private DataListener<RecipeEntity> createNewRecipe = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			try {
				RecipeEntity recipe = new RecipeEntity(data.getName(), new ArrayList<>(), new ArrayList<>());
				recipeRepository.save(recipe);
				broadcastRecipes();
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
				broadcastRecipes();
			} catch (Exception e) {
				throw new Exception("Failed to delete Recipe");
			}
		}
	};
	
	//update recipe (add instructions and ingredients)
	private DataListener<RecipeEntity> updateRecipe = new DataListener<RecipeEntity>() {
		@Override
		public void onData(SocketIOClient client, RecipeEntity data, AckRequest ackSender) throws Exception {
			
			try {
				RecipeEntity recipe = recipeRepository.fetchRecipe(data.getId());
				recipe.setInstructions(data.getInstructions());
				recipe.setIngredients(data.getIngredients());
				recipe.setName(data.getName());
				recipeRepository.save(recipe);
				broadcastRecipes();
			} catch (Exception e) {
				throw new Exception("Failed to update Recipe");
			}
		}
	};
	
	private void broadcastRecipes() throws Exception {
		List<RecipeEntity> recipes;
		try {
			recipes = recipeRepository.fetchRecipes();
		} catch (Exception e) {
			throw new Exception("Failed to fetch Recipes");
		}
		this.socketNamespaceRecipeController.getBroadcastOperations().sendEvent(RECIPE_FETCH_RECIPE_EVENT, recipes);
	}
}
