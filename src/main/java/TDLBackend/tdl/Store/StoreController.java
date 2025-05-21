package TDLBackend.tdl.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

import java.util.List;

@Component
public class StoreController {
	
	private static String STORE_CONTROLLER = "/store";
	private static String STORE_FETCH_EVENT = "fetch";
	
	@Autowired
	StoreRepository storeRepository;

	@Autowired
    private SocketIOServer socketServer;

	StoreController(SocketIOServer socketServer){
        this.socketServer = socketServer;
		SocketIONamespace socketNamespaceStoreController = this.socketServer.addNamespace(STORE_CONTROLLER);

        socketNamespaceStoreController.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient client) {
				List<Store> stores;
				try
				{
					stores = storeRepository.getStores();
				}
				catch(Exception e) 
				{
					return;
				}
				client.sendEvent(STORE_FETCH_EVENT, stores);
			}
		});
    }

	public SocketIONamespace getStoreNamespace()
	{
		return this.socketServer.getNamespace(STORE_CONTROLLER);
	}

	public void broadcastStoreFetchEvent() throws Exception
	{
		List<Store> stores;
		try
		{
			stores = storeRepository.getStores();
		}
		catch(Exception e) 
		{
			throw new Exception("Failed to get stores");
		}

		getStoreNamespace().getBroadcastOperations().sendEvent(STORE_FETCH_EVENT, stores);
	}
	
}
