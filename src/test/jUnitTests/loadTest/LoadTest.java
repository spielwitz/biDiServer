/**	BiDiServer - a library that provides bi-directional communication between
	a server and clients.
	
    Copyright (C) 2022 Michael Schweitzer, spielwitz@icloud.com
	https://github.com/spielwitz/biDiServer
	
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>. **/

package test.jUnitTests.loadTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import spielwitz.biDiServer.ClientConfiguration;
import spielwitz.biDiServer.DataSet;
import spielwitz.biDiServer.DataSetInfo;
import spielwitz.biDiServer.LogLevel;
import spielwitz.biDiServer.PayloadRequestMessageChangeUser;
import spielwitz.biDiServer.PayloadResponseGetDataSetInfosOfUser;
import spielwitz.biDiServer.PayloadResponseMessageChangeUser;
import spielwitz.biDiServer.PayloadResponseMessageGetUsers;
import spielwitz.biDiServer.Response;
import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.ServerConfiguration;
import spielwitz.biDiServer.Tuple;
import spielwitz.biDiServer.User;

class LoadTest
{
	private static final DataSetPayload dataSetPayload = new DataSetPayload();
	private static Random rand = new Random();
	private Object lockObjectStackTrace = new Object();
	
	private static final int USERS_COUNT = 10;
	private static final int ACTIONS_PER_USER_COUNT = 10;
	
	private Hashtable<UUID,Boolean> notifsStatistics = new Hashtable<UUID,Boolean>();
	private Object notifsStatisticLock = new Object();
	
	
	@Test
	void start()
	{
		LoadTestServer server = this.prepareServer();
		new ServerThread(server).start();
		
		System.out.println("Waiting for 2 seconds to make sure that the server starts...");
		
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		LoadTestClient clientAdmin = this.getAdminClient();
		
		ArrayList<LoadTestClient> clientsUser = this.createUserClients(clientAdmin, USERS_COUNT);
		assertTrue(clientsUser.size() == USERS_COUNT);
		
		this.performClientActions(clientsUser);
		
		System.out.println("All tests are finished.");
		
		int notificationsSent = this.notifsStatistics.size();
		int notificationsReceived = 0;
		
		for (Boolean received: this.notifsStatistics.values())
		{
			if (received)
			{
				notificationsReceived++;
			}
		}
		
		System.out.println(notificationsReceived + "/" + notificationsSent+" notifications received.");
	}
	
	private LoadTestServer prepareServer()
	{
		if (LoadTestServer.ServerHomeDir.exists())
		{
			try
			{
				Files.walk(LoadTestServer.ServerHomeDir.toPath())
				  .sorted(Comparator.reverseOrder())
				  .map(Path::toFile)
				  .forEach(File::delete);
			} catch (Exception e)
			{
			}
		}
		
		ServerConfiguration serverConfig = new ServerConfiguration(
				"localhost", 
				ServerConfiguration.SERVER_PORT + 1, 
				"", 
				LogLevel.Verbose,
				"en-US");
		
		LoadTestServer server = null;
		
		try
		{
			server = new LoadTestServer(serverConfig);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return server;
	}
	
	private LoadTestClient getAdminClient()
	{
		ClientConfiguration clientConfigAdmin = ClientConfiguration.readFromFile(
				Paths.get(
						LoadTestServer.ServerHomeDir.toString(),
						LoadTestServer.FOLDER_NAME_ROOT.toString(),
						ClientConfiguration.getFileName(
								User.ADMIN_USER_ID, 
								"localhost", 
								ServerConfiguration.SERVER_PORT + 1)).
				toString());
		
		return new LoadTestClient(clientConfigAdmin, false, "de-DE");
	}
	
	private ArrayList<LoadTestClient> createUserClients(LoadTestClient clientAdmin, int usersCount)
	{
		System.out.println("Creating " + usersCount + " user clients ...");
		
		ArrayList<LoadTestClient> usersClient = new ArrayList<LoadTestClient>();
		ArrayList<UserCreationThread> threads = new ArrayList<UserCreationThread>(); 
		
		for (int i = 0; i < usersCount; i++)
		{
			UserCreationThread thread = new UserCreationThread(clientAdmin);
			threads.add(thread);
			thread.start();
		}
		
		for (UserCreationThread thread: threads)
		{
			try
			{
				thread.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			usersClient.add(thread.client);
		}
		
		System.out.println(usersClient.size() + " user clients created.");
		
		return usersClient;
	}
	
	private void performClientActions(ArrayList<LoadTestClient> clientsUser)
	{
		ArrayList<ClientActionsThread> threads = new ArrayList<ClientActionsThread>(); 
		System.out.println("Running client actions...");
		
		for (LoadTestClient clientUser: clientsUser)
		{
			ClientActionsThread thread = new ClientActionsThread(clientUser);
			clientUser.clientActionThread = thread;
			threads.add(thread);
			thread.start();
		}
		
		for (ClientActionsThread thread: threads)
		{
			try
			{
				thread.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void incrementNotifsSent(UUID message)
	{
		synchronized(this.notifsStatisticLock)
		{
			this.notifsStatistics.put(message, false);
		}
	}
	
	private void incrementNotifsReceived(UUID message)
	{
		synchronized(this.notifsStatisticLock)
		{
			if (this.notifsStatistics.containsKey(message))
			{
				this.notifsStatistics.replace(message, true);
			}
		}
	}
	
	private boolean checkExpectedResponseInfo(ResponseInfo info, boolean expectedResult, Thread thread)
	{
		synchronized(this.lockObjectStackTrace)
		{
			if (info.isSuccess() == expectedResult)
			{
				return true;
			}
			else
			{
				System.out.println(">>> Error message: " + info.getMessage());
				StackTraceElement[] stackTrace = thread.getStackTrace();
				for (StackTraceElement stackTraceElement: stackTrace)
					System.out.println(stackTraceElement.toString());
				return false;
			}
		}
	}
	
	private class ServerThread extends Thread
	{
		private LoadTestServer server;
		
		public ServerThread(LoadTestServer server)
		{
			this.server = server;
		}
		
		public void run()
		{
			this.server.start();
		}
	}
	
	private class UserCreationThread extends Thread
	{
		private LoadTestClient clientAdmin;
		public LoadTestClient client;
		
		public UserCreationThread(LoadTestClient clientAdmin)
		{
			this.clientAdmin = clientAdmin;
		}
		
		public void run()
		{
			PayloadRequestMessageChangeUser payloadNewUser =
					new PayloadRequestMessageChangeUser(
							UUID.randomUUID().toString(), 
							new Hashtable<String,String>(), 
							UUID.randomUUID().toString(), 
							true,
							true);
			
			Response<PayloadResponseMessageChangeUser> response = clientAdmin.changeUser(payloadNewUser);
			
			Tuple<ClientConfiguration,ResponseInfo> responseActivateUser = 
					LoadTestClient.activateUser(response.getPayload(), "de-DE", "1234");
			
			checkExpectedResponseInfo(responseActivateUser.getE2(), true, this);
			
			this.client = new LoadTestClient(responseActivateUser.getE1(), true, "de-DE");
			this.client.start();
		}
	}
	
	class ClientActionsThread extends Thread
	{
		private LoadTestClient client;
		
		public ClientActionsThread(LoadTestClient client)
		{
			this.client = client;
		}
		
		public void run()
		{
			for (int i = 0; i < ACTIONS_PER_USER_COUNT; i++)
			{
				double r = rand.nextDouble();
			
				if (r < 0.2)
					this.executeActionsDataSet();
				else if (r < 0.4)
					this.executeActionsUsers();
				else if (r < 0.6)
					this.executeActionsCustomRequest();
				else
					this.executeActionsNotifications();
			}
			
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			this.client.disconnect();
		}
		
		private void executeActionsDataSet()
		{
			HashSet<String> userIds = new HashSet<String>();
			userIds.add(this.client.getUserId());
			
			DataSet dataSet = new DataSet(
					UUID.randomUUID().toString(), 
					userIds, 
					dataSetPayload);
			
			// Create a data set
			ResponseInfo info = this.client.createDataSet(dataSet);
			checkExpectedResponseInfo(info, true, this);
			
			// Get the data set
			Response<DataSet> responseDataSet = this.client.getDataSet(dataSet.getId());
			checkExpectedResponseInfo(responseDataSet.getResponseInfo(), true, this);
			assertTrue(responseDataSet.getPayload().getId().equals(dataSet.getId()));
			assertTrue(responseDataSet.getPayload().getPayloadObject().getClass() == DataSetPayload.class);
			
			DataSetPayload dataSetPayload = (DataSetPayload)responseDataSet.getPayload().getPayloadObject();
			assertTrue(dataSetPayload.aLongString.equals(LoadTest.dataSetPayload.aLongString));
			
			// Get all data sets of the user
			Response<PayloadResponseGetDataSetInfosOfUser> responseGetDataSetInfosOfUser = this.client.getDataSetInfosOfUser(this.client.getUserId());
			checkExpectedResponseInfo(responseGetDataSetInfosOfUser.getResponseInfo(), true, this);
			assertTrue(responseGetDataSetInfosOfUser.getPayload().getDataSetInfos().size() == 1);
			
			DataSetInfo dataSetInfo = responseGetDataSetInfosOfUser.getPayload().getDataSetInfos().get(0);
			assertTrue(dataSetInfo.getUserIds().size() == 1);
			assertTrue(dataSetInfo.getUserIds().contains(this.client.getUserId()));
			assertTrue(dataSetInfo.getId().equals(dataSet.getId()));
			
			// Update the data set
			info = this.client.updateDataSet(dataSet);
			checkExpectedResponseInfo(info, true, this);
			
			// Delete the data set
			info = this.client.deleteDataSet(dataSet.getId());
			checkExpectedResponseInfo(info, true, this);
			
			// Get the data set again
			responseDataSet = this.client.getDataSet(dataSet.getId());
			checkExpectedResponseInfo(responseDataSet.getResponseInfo(), false, this);
		}
		
		private void executeActionsNotifications()
		{
			ArrayList<String> recipients = new ArrayList<String>();
			recipients.add(this.client.getUserId());
			
			UUID message = UUID.randomUUID();
			
			incrementNotifsSent(message);
			this.client.pushNotification(recipients, message);
		}
		
		private void executeActionsUsers()
		{
			Response<PayloadResponseMessageGetUsers> response = this.client.getUsers();
			checkExpectedResponseInfo(response.getResponseInfo(), true, this);
			
			PayloadResponseMessageGetUsers payload = response.getPayload();
			
			for (User user: payload.getUsers())
			{
				Response<User> responseUser = this.client.getUser(user.getId());
				checkExpectedResponseInfo(responseUser.getResponseInfo(), true, this);
				assertTrue(responseUser.getPayload().toString().equals(user.toString()));
			}
		}
		
		private void executeActionsCustomRequest()
		{
			CustomPayloadRequest payloadRequest = new CustomPayloadRequest();
			Response<CustomPayloadResponse> response = this.client.customRequest(payloadRequest);
			checkExpectedResponseInfo(response.getResponseInfo(), true, this);
			assertTrue(response.getPayload().getaString().equals(payloadRequest.getaString()));
		}
		
		void onNotificationReceived(UUID message)
		{
			incrementNotifsReceived(message);
		}
	}
}
