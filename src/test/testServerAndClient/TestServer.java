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

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;

import com.google.gson.JsonElement;

import spielwitz.biDiServer.DataSet;
import spielwitz.biDiServer.DataSetInfo;
import spielwitz.biDiServer.LogLevel;
import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.Server;
import spielwitz.biDiServer.ServerClientBuildCheckResult;
import spielwitz.biDiServer.ServerConfiguration;
import spielwitz.biDiServer.ServerException;
import spielwitz.biDiServer.Tuple;

public class TestServer extends Server
{
	private static final File fileServerConf = Paths.get(Server.FOLDER_NAME_ROOT, "ServerConfig.json").toFile();
	
	public static void main(String[] args)
	{
		ServerConfiguration config = null;
		if (fileServerConf.exists())
		{
			config = ServerConfiguration.readFromFile(fileServerConf.getAbsolutePath());
		}
		else
		{
			config = new ServerConfiguration(
					"localhost", 
					ServerConfiguration.SERVER_PORT, 
					"spielwitz@me.com", 
					ServerConfiguration.SERVER_DEFAULT_LOGLEVEL,
					"en-US");
			
			config.writeToFile(fileServerConf.getAbsolutePath());
		}

		TestServer testServer = null;

		try
		{
			testServer = new TestServer(config);
		}
		catch (Exception x)
		{
			System.out.println("Server initialization error: " + x.getMessage());
			return;
		}

		testServer.start();
	}

	public TestServer(ServerConfiguration config) throws ServerException
	{
		super (config, System.getProperty("user.dir"));
	}
	
	@Override
	public String getBuild()
	{
		return TestData.serverBuild;
	}

	@Override
	protected void onConfigurationUpdated(ServerConfiguration config)
	{
		config.writeToFile(fileServerConf.getAbsolutePath());
	}

	@Override
	protected Tuple<ResponseInfo,Object> onCustomRequestMessageReceived(String userId, Object payloadRequest)
	{
		if (payloadRequest.getClass() == PayloadRequestIncreaseGameCounter.class)
		{
			Tuple<ResponseInfo,Object> response = null;
			
			PayloadRequestIncreaseGameCounter p = (PayloadRequestIncreaseGameCounter)payloadRequest;
			
			DataSetInfo dataSetInfo = this.getDataSetInfo(p.getGameId());
			
			if (dataSetInfo == null)
			{
				response = new Tuple<ResponseInfo,Object>(
						new ResponseInfo(false, "Game not found"),
						null);
			}
			else if (!dataSetInfo.getUserIds().contains(userId))
			{
				response = new Tuple<ResponseInfo,Object>(
						new ResponseInfo(false, "User is not authorized to read the game"),
						null);
			}
			else
			{
				this.logCustomMessage(LogLevel.Information, "Increasing game counter for game "+ dataSetInfo.getId());
				
				DataSet dataSet = this.getDataSet(dataSetInfo.getId());
				
				Game game = (Game) dataSet.getPayloadObject();
				
				game.dateUpdate = System.currentTimeMillis();
				game.counter++;
				
				dataSet.setPayloadObject(game);
				this.setDataSet(dataSet);
				
				response = new Tuple<ResponseInfo,Object>(new ResponseInfo(true), null);
			}
			
			return response;
		}
		
		return null;
	}

	@Override
	protected Object setDataSetInfoPayloadObject(
			String dataId, 
			HashSet<String> userIds, 
			Object dataPayloadObject,
			Object currentDataInfoPayloadObject)
	{
		GameInfo gameInfo = new GameInfo();
		gameInfo.dateUpdate = ((Game)dataPayloadObject).dateUpdate;
		
		return gameInfo;
	}

	@Override
	public ServerClientBuildCheckResult checkServerClientBuild(String clientBuild)
	{
		String buildServer = this.getBuild();
		String minimumCompatibleBuild = TestData.serverComptabileBuild;
		
		if (buildServer != null && 
			clientBuild != null &&
			clientBuild.compareTo(minimumCompatibleBuild) < 0)
			
		{
			return new ServerClientBuildCheckResult(false, minimumCompatibleBuild);
		}
		else
		{
			return new ServerClientBuildCheckResult(true, minimumCompatibleBuild);
		}
	}

	@Override
	protected JsonElement migrateDataSet(String className, JsonElement jsonElementBeforeMigration)
	{
		return null;
	}
}
