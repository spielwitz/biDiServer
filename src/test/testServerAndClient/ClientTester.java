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

package test.testServerAndClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.google.gson.Gson;

import spielwitz.biDiServer.ClientConfiguration;
import spielwitz.biDiServer.DataSet;
import spielwitz.biDiServer.LogLevel;
import spielwitz.biDiServer.Notification;
import spielwitz.biDiServer.Payload;
import spielwitz.biDiServer.PayloadRequestMessageChangeUser;
import spielwitz.biDiServer.PayloadResponseGetDataSetInfosOfUser;
import spielwitz.biDiServer.PayloadResponseMessageChangeUser;
import spielwitz.biDiServer.PayloadResponseMessageGetLog;
import spielwitz.biDiServer.PayloadResponseMessageGetServerStatus;
import spielwitz.biDiServer.PayloadResponseMessageGetUsers;
import spielwitz.biDiServer.Response;
import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.Server;
import spielwitz.biDiServer.Tuple;
import spielwitz.biDiServer.User;

@SuppressWarnings("serial")
public class ClientTester extends JFrame implements IClientTesterCallback, ActionListener, WindowListener
{
	private String userId;
	
	private JButton butCreateUser;
	private JButton butUpdateUser;
	private JButton butChangeCredentialsOfUser;
	private JButton butDeleteUser;
	private JButton butShutdown;
	private JButton butPing;
	private JButton butGetUsers;
	private JButton butGetStatus;
	private JButton butGetLog;
	private JButton butSetLogLevel;
	private JButton butActivateUser;
	private JButton butGetUser;
	private JButton butGetMyGames;
	private JButton butCreateGame;
	private JButton butUpdateGame;
	private JButton butGetGame;
	private JButton butDeleteGame;
	private JButton butDisconnect;
	private JButton butReconnect;
	private JButton butIncreaseGameCounter;
	private JButton butMessage;
	private JTextArea taOutput;
	private JCheckBox cbConfigExists;
	private JCheckBox cbConnected;
	
	private ClientConfiguration conf;
	private TestClient client;
	
	private static final String FOLDER_NAME = "TestClientData";
	
	public static void main(String[] args)
	{
		new ClientTester();
	}
	
	private ClientTester()
	{
		this.setLayout(new BorderLayout());
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		Panel panButtons = new Panel(new GridLayout(4, 6));
		
		this.butCreateUser = new JButton("Create User (Admin)");
		this.butCreateUser.addActionListener(this);
		panButtons.add(butCreateUser);
		
		this.butActivateUser = new JButton("Activate User");
		this.butActivateUser.addActionListener(this);
		panButtons.add(butActivateUser);
		
		this.butUpdateUser = new JButton("Update User (Admin)");
		this.butUpdateUser.addActionListener(this);
		panButtons.add(butUpdateUser);
		
		this.butChangeCredentialsOfUser = new JButton("Clear user credentials (Admin)");
		this.butChangeCredentialsOfUser.addActionListener(this);
		panButtons.add(butChangeCredentialsOfUser);
		
		this.butGetUsers = new JButton("Get Users");
		this.butGetUsers.addActionListener(this);
		panButtons.add(butGetUsers);
		
		this.butGetUser = new JButton("Get User");
		this.butGetUser.addActionListener(this);
		panButtons.add(butGetUser);
		
		this.butDeleteUser = new JButton("Delete User (Admin)");
		this.butDeleteUser.addActionListener(this);
		panButtons.add(butDeleteUser);
		
		this.butShutdown = new JButton("Shutdown Server (Admin)");
		this.butShutdown.addActionListener(this);
		panButtons.add(butShutdown);
		
		this.butPing = new JButton("Ping");
		this.butPing.addActionListener(this);
		panButtons.add(butPing);
		
		this.butGetStatus = new JButton("Get Server Status (Admin)");
		this.butGetStatus.addActionListener(this);
		panButtons.add(butGetStatus);
		
		this.butGetLog = new JButton("Get Log (Admin)");
		this.butGetLog.addActionListener(this);
		panButtons.add(butGetLog);
		
		this.butSetLogLevel = new JButton("Set Log Level (Admin)");
		this.butSetLogLevel.addActionListener(this);
		panButtons.add(butSetLogLevel);
		
		this.butGetMyGames = new JButton("Get My Games");
		this.butGetMyGames.addActionListener(this);
		panButtons.add(butGetMyGames);
		
		this.butCreateGame = new JButton("Create Game");
		this.butCreateGame.addActionListener(this);
		panButtons.add(butCreateGame);
		
		this.butUpdateGame = new JButton("Update Game");
		this.butUpdateGame.addActionListener(this);
		panButtons.add(butUpdateGame);
		
		this.butGetGame = new JButton("Get Game");
		this.butGetGame.addActionListener(this);
		panButtons.add(butGetGame);
		
		this.butDeleteGame = new JButton("Delete Game");
		this.butDeleteGame.addActionListener(this);
		panButtons.add(butDeleteGame);
		
		this.butMessage = new JButton("Write Message");
		this.butMessage.addActionListener(this);
		panButtons.add(butMessage);
		
		this.butIncreaseGameCounter = new JButton("Increase Counter");
		this.butIncreaseGameCounter.addActionListener(this);
		panButtons.add(butIncreaseGameCounter);
		
		this.butDisconnect = new JButton("Disconnect");
		this.butDisconnect.addActionListener(this);
		panButtons.add(butDisconnect);
		
		this.butReconnect = new JButton("Reconnect");
		this.butReconnect.addActionListener(this);
		panButtons.add(butReconnect);
		
		this.add(panButtons, BorderLayout.NORTH);
		// -----
		
		this.taOutput = new JTextArea();
		this.taOutput.setLineWrap(true);
		
		this.taOutput.setRows(35);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setViewportView(this.taOutput);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		// ----
		
		Panel panStatus = new Panel(new FlowLayout());
		
		this.cbConfigExists = new JCheckBox("User configuration exists");
		this.cbConfigExists.setEnabled(false);
		this.setConnectionStatus();
		panStatus.add(this.cbConfigExists);
		
		this.cbConnected = new JCheckBox("Notification Socket Established");
		this.cbConnected.setEnabled(false);
		
		panStatus.add(this.cbConnected);
		
		this.add(panStatus, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, TestData.getUserIds());
		dlg.setVisible(true);
		this.userId = StringSelectionDialog.selectedChoice;
		this.setTitle(this.userId);
		
		this.initConfiguration();
	
		this.setConnectionStatus();
	}

	@Override
	public void onNotificationReceived(String userId, Notification notification)
	{
		StringBuilder sb = new StringBuilder();
		
		Object obj = notification.getPayloadObject(this.conf.getUserPrivateKeyObject());
		
		sb.append("---------------------------------------------------\n");
		sb.append("Notification received: " +  new Payload(obj).toString() + "\n");
		
		this.taOutput.append(sb.toString());
	}

	@Override
	public void onConnectionStatusChanged(String userId, boolean connected)
	{
		System.out.println("Connection status of user "+userId+" changed to " + connected);
		this.setConnectionStatus();
	}
	
	private void initConfiguration()
	{
		if (this.userId.equals(User.ADMIN_USER_ID))
		{
			// Get admin configuration from server data folder
			this.conf = ClientConfiguration.readFromFile(
							Paths.get(Server.FOLDER_NAME_ROOT, this.getUserFileName(User.ADMIN_USER_ID)).toString());
		}
		else
		{
			// Get user configuration
			this.conf = ClientConfiguration.readFromFile(
					Paths.get(FOLDER_NAME, this.getUserFileName(userId)).toString());
		}

		this.connect();
	}
	
	private String getUserFileName(String userId)
	{
		return userId + "_localhost_56084";
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		long startTime = System.currentTimeMillis();
		
		if (e.getSource() == this.butPing)
		{
			this.writeRequestMessagePayloadToOutput("PING", null);
			ResponseInfo responseInfo = this.client.pingServer();
			this.writeResponseMessageToOutput(responseInfo.toString(), startTime);
		}
		else if (e.getSource() == this.butShutdown)
		{
			this.writeRequestMessagePayloadToOutput("SHUTDOWN", null);
			ResponseInfo responseInfo = this.client.shutdownServer();
			this.writeResponseMessageToOutput(responseInfo.toString(), startTime);
		}
		else if (e.getSource() == this.butGetStatus)
		{
			this.writeRequestMessagePayloadToOutput("GET_SERVER_STATUS", null);
			Response<PayloadResponseMessageGetServerStatus> response = this.client.getServerStatus();
			this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
		}
		else if (e.getSource() == this.butCreateUser)
		{
			this.changeUser(true, false);
		}
		else if (e.getSource() == this.butUpdateUser)
		{
			this.changeUser(false, false);
		}
		else if (e.getSource() == this.butDeleteUser)
		{
			this.deleteUser();
		}
		else if (e.getSource() == this.butChangeCredentialsOfUser)
		{
			this.changeUser(false, true);
		}
		else if (e.getSource() == this.butActivateUser)
		{
			this.activateUser();
		}
		else if (e.getSource() == this.butDisconnect)
		{
			this.client.disconnect();
		}
		else if (e.getSource() == this.butReconnect)
		{
			this.connect();
		}
		else if (e.getSource() == this.butGetLog)
		{
			this.writeRequestMessagePayloadToOutput("GET_LOG", null);
			Response<PayloadResponseMessageGetLog> response = this.client.getServerLog();
			this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
		}
		else if (e.getSource() == this.butSetLogLevel)
		{
			this.setLogLevel();
		}
		else if (e.getSource() == this.butGetMyGames)
		{
			this.writeRequestMessagePayloadToOutput("GET_DATA_SET_INFOS_OF_USER", new Payload(this.userId));
			Response<PayloadResponseGetDataSetInfosOfUser> response = this.client.getDataSetInfosOfUser(this.userId);
			this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
		}
		else if (e.getSource() == this.butCreateGame)
		{
			this.setGame(true);
		}
		else if (e.getSource() == this.butUpdateGame)
		{
			this.setGame(false);
		}
		else if (e.getSource() == this.butGetGame)
		{
			this.getGame();
		}
		else if (e.getSource() == this.butDeleteGame)
		{
			this.deleteGame();
		}
		else if (e.getSource() == this.butIncreaseGameCounter)
		{
			this.increaseGameCounter();
		}
		else if (e.getSource() == this.butGetUser)
		{
			this.getUser();
		}
		else if (e.getSource() == this.butGetUsers)
		{
			this.writeRequestMessagePayloadToOutput("GET_USERS", null);
			Response<PayloadResponseMessageGetUsers> response = this.client.getUsers();
			this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
		}
		else if (e.getSource() == this.butMessage)
		{
			MessageSendDialog dlg = new MessageSendDialog(this);
			dlg.setVisible(true);
		}
	}
	
	private void changeUser(boolean create, boolean renewCredentials)
	{
		StringSelectionDialog dlg = new StringSelectionDialog(this, TestData.getUserIds());
		dlg.setVisible(true);
		String userIdToBeChanged = StringSelectionDialog.selectedChoice;
		
		Hashtable<String,String> customData = new Hashtable<String,String>();
		customData.put("email", UUID.randomUUID() + "@" + userIdToBeChanged + ".xyz");
		
		PayloadRequestMessageChangeUser payload = new PayloadRequestMessageChangeUser(
				userIdToBeChanged,
				customData,
				UUID.randomUUID() + userIdToBeChanged,
				create,
				renewCredentials);
		
		long startTime = System.currentTimeMillis();
		
		this.writeRequestMessagePayloadToOutput("CHANGE_USER", payload);
		Response<PayloadResponseMessageChangeUser> response = this.client.changeUser(payload);
		this.writeResponseMessageToOutput(response.toString(), startTime);
				
		if (response.getResponseInfo().isSuccess())
		{
			File dir = new File(FOLDER_NAME);
			
			if (!dir.exists())
			{
				dir.mkdir();
			}
			
			PayloadResponseMessageChangeUser payloadResponse = response.getPayload();
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Paths.get(FOLDER_NAME, 
					this.getUserFileName(payloadResponse.getUserId() + "_activation")).toString())))
			{
				bw.write(new Gson().toJson(payloadResponse));			
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void activateUser()
	{
		StringSelectionDialog dlg = new StringSelectionDialog(this, TestData.getUserIds());
		dlg.setVisible(true);
		String userIdToBeActivated = StringSelectionDialog.selectedChoice;
		
		PayloadResponseMessageChangeUser userActivationData = null;
		
		try (BufferedReader br = new BufferedReader(
				new FileReader(new File(Paths.get(FOLDER_NAME, 
						this.getUserFileName(userIdToBeActivated + "_activation")).toString()))))
		{
			String json = br.readLine();
			userActivationData = new Gson().fromJson(json, PayloadResponseMessageChangeUser.class);
		} catch (Exception e)
		{
		}
		
		if (userActivationData == null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("---------------------------------------------------\n");
			sb.append("Error: No activation data for user " + userIdToBeActivated + " found.\n");
			
			this.taOutput.append(sb.toString());
			return;
		}
		
		long startTime = System.currentTimeMillis();
		
		this.writeRequestMessagePayloadToOutput("ACTIVATE_USER", userActivationData);
		Tuple<ClientConfiguration, ResponseInfo> tuple = 
				TestClient.activateUser(
						userActivationData,
						TestData.clientLocale, 
						TestData.clientBuild);
		this.writeResponseMessageToOutput(tuple.getE2().toString(), startTime);
		
		if (tuple.getE2().isSuccess())
		{
			File dir = new File(FOLDER_NAME);
			
			if (!dir.exists())
			{
				dir.mkdir();
			}
			
			tuple.getE1().writeToFile(
					Paths.get(FOLDER_NAME, 
							this.getUserFileName(userActivationData.getUserId())).toString());
		}
	}
	
	private void setConnectionStatus()
	{
		this.cbConfigExists.setSelected(this.conf != null);
		
		if (this.client != null)
		{
			this.cbConnected.setSelected(this.client.isConnected());
		}
	}
	
	private void setLogLevel()
	{
		String[] enumValues = getEnumValues(LogLevel.class);
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, enumValues);
		dlg.setVisible(true);
		LogLevel newLogLevel = LogLevel.valueOf(StringSelectionDialog.selectedChoice);
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput("SET_LOG_LEVEL", newLogLevel);
		ResponseInfo responseMessageInfo = this.client.setLogLevel(LogLevel.Verbose);
		this.writeResponseMessageToOutput(responseMessageInfo.toString(), startTime);

	}
	
	public static String[] getEnumValues(Class<? extends Enum<?>> e) {
	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		this.dispose();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	private void writeRequestMessagePayloadToOutput(String type, Object payloadObject)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("---------------------------------------------------\n");
		sb.append("Sending request message: type = ");
		sb.append(type);
		sb.append(", payload = ");
		
		if (payloadObject == null)
			sb.append("null");
		else
		{
			Payload payload = new Payload(payloadObject);
			sb.append("<"+payload.getClassName()+"> " + payload.toString());
		}
		
		sb.append("\n\n");
		
		this.taOutput.append(sb.toString());
	}
	
	private void writeResponseMessageToOutput(String responseOutput, long startTime)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Response message received (" + (System.currentTimeMillis() - startTime) + " ms)\n");
		sb.append(responseOutput);
		sb.append("\n");
		
		this.taOutput.append(sb.toString());
	}
	
	private void setGame(boolean create)
	{
		String[] gameIds = TestData.getGameIds();
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, gameIds);
		dlg.setVisible(true);
		String gameId = StringSelectionDialog.selectedChoice;
		
		DataSet dataSet = TestData.getGames().get(gameId);
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				create ?
						"CREATE_DATA_SET" :
						"UPDATE_DATA_SET",
				new Payload(dataSet));
		
		ResponseInfo response =
				create ?
						this.client.createDataSet(dataSet) :
						this.client.updateDataSet(dataSet);
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void getGame()
	{
		String[] gameIds = TestData.getGameIds();
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, gameIds);
		dlg.setVisible(true);
		String gameId = StringSelectionDialog.selectedChoice;
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"GET_DATA_SET",
				new Payload(gameId));
		
		Response<DataSet> response = this.client.getDataSet(gameId); 
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void deleteGame()
	{
		String[] gameIds = TestData.getGameIds();
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, gameIds);
		dlg.setVisible(true);
		String gameId = StringSelectionDialog.selectedChoice;
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"Delete data set",
				new Payload(gameId));
		
		ResponseInfo response = this.client.deleteDataSet(gameId); 
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void increaseGameCounter()
	{
		String[] gameIds = new String[] {TestData.getGame0().getId(), TestData.getGame1().getId()};
		
		StringSelectionDialog dlg = new StringSelectionDialog(this, gameIds);
		dlg.setVisible(true);
		String gameId = StringSelectionDialog.selectedChoice;
		
		PayloadRequestIncreaseGameCounter payload = new PayloadRequestIncreaseGameCounter(gameId);
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"CUSTOM",
				new Payload(payload));
		
		ResponseInfo response = this.client.increaseGameCounter(gameId); 
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void getUser()
	{
		StringSelectionDialog dlg = new StringSelectionDialog(this, TestData.getUserIds());
		dlg.setVisible(true);
		String userId = StringSelectionDialog.selectedChoice;
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"GET_USER",
				new Payload(userId));
		
		Response<User> response = this.client.getUser(userId); 
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void deleteUser()
	{
		StringSelectionDialog dlg = new StringSelectionDialog(this, TestData.getUserIds());
		dlg.setVisible(true);
		String userId = StringSelectionDialog.selectedChoice;
		
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"DELETE_USER",
				new Payload(userId));
		
		ResponseInfo response = this.client.deleteUser(userId); 
		
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}

	@Override
	public void sendMessage(ArrayList<String> recipients, String message)
	{
		long startTime = System.currentTimeMillis();
		this.writeRequestMessagePayloadToOutput(
				"NOTIFY",
				new Payload(message));
		ResponseInfo response = this.client.pushNotification(recipients, message);
		this.writeResponseMessageToOutput(response != null ? response.toString() : null, startTime);
	}
	
	private void connect()
	{
		if (this.conf != null)
		{
			this.client = new TestClient(this.conf, true, this);
			this.client.start();
		}
	}
}
