package TDLBackend.tdl.Item;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.listener.DataListener;

import TDLBackend.tdl.Store.StoreController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    private static String ITEM_EVENT = "item/";
    private static String ITEM_ADD_EVENT = ITEM_EVENT + "add";
    private static String ITEM_DELETE_EVENT = ITEM_EVENT + "delete";
    private static String ITEM_UPDATE_CHECKED_EVENT = ITEM_EVENT + "updateCheck";

    @Autowired
    private StoreController storeController;

	ItemController(StoreController storeController){
        this.storeController = storeController;
		SocketIONamespace socketNamespaceStoreController = this.storeController.getStoreNamespace();

        // Add event listeners
        socketNamespaceStoreController.addEventListener(ITEM_ADD_EVENT, Item.class, addItem);
        socketNamespaceStoreController.addEventListener(ITEM_DELETE_EVENT, (Class) List.class, deleteItems);
        socketNamespaceStoreController.addEventListener(ITEM_UPDATE_CHECKED_EVENT, (Class) HashMap.class, updatecheckedValue);
    }

    private DataListener<Item> addItem = new DataListener<Item>() {
        @Override
        public void onData(SocketIOClient client, Item item, AckRequest acknowledge) throws Exception 
        {

            try 
            {
                itemRepository.addItem(item);
                storeController.broadcastStoreFetchEvent();
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to add item");
            }
        }
    };

    private DataListener<List<Integer>> deleteItems = new DataListener<List<Integer>>() {
        @Override
        public void onData(SocketIOClient client, List<Integer> itemIDs, AckRequest acknowledge) throws Exception 
        {
            try {
                itemRepository.deleteitemswithids(itemIDs);
                storeController.broadcastStoreFetchEvent();
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to delete items");
            }
        }
    };

    private DataListener<HashMap<String, Boolean>> updatecheckedValue = new DataListener<HashMap<String, Boolean>>() {
        @Override
        public void onData(SocketIOClient client, HashMap<String, Boolean> id_checked_map, AckRequest acknowledge) throws Exception 
        {
            try {
                for (Map.Entry<String, Boolean> entry : id_checked_map.entrySet()) {
                    itemRepository.updatecheckedValue(Integer.valueOf(entry.getKey()), entry.getValue());
                }
                storeController.broadcastStoreFetchEvent();
            }
            catch(Exception e) 
            {
                throw new Exception("Failed to update checks");
            }
        }
    };

}
