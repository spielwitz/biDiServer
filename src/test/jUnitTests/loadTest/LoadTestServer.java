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

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;

import com.google.gson.JsonElement;

import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.Server;
import spielwitz.biDiServer.ServerClientBuildCheckResult;
import spielwitz.biDiServer.ServerConfiguration;
import spielwitz.biDiServer.ServerException;
import spielwitz.biDiServer.Tuple;

class LoadTestServer extends Server
{
	static final File ServerHomeDir = 
			Paths.get(
					System.getProperty("user.dir"),
					"LoadTestServerData").toFile();
	
	protected LoadTestServer(ServerConfiguration config) throws ServerException
	{
		super(config, ServerHomeDir.toString());
	}

	@Override
	protected Tuple<ResponseInfo, Object> onCustomRequestMessageReceived(String userId, Object payloadRequest)
	{
		if (payloadRequest.getClass() == CustomPayloadRequest.class)
		{
			CustomPayloadResponse payloadResponse = new CustomPayloadResponse(((CustomPayloadRequest)payloadRequest).getaString());
			
			return new Tuple<ResponseInfo, Object>(
					new ResponseInfo(true),
					payloadResponse);
		}
		else
			return null;
	}

	@Override
	protected void onConfigurationUpdated(ServerConfiguration config)
	{
	}

	@Override
	protected Object setDataSetInfoPayloadObject(String dataId, HashSet<String> userIds, Object dataSetPayloadObject,
			Object currentDataSetInfoPayloadObject)
	{
		return dataSetPayloadObject;
	}

	@Override
	protected JsonElement migrateDataSet(String className, JsonElement jsonElementBeforeMigration)
	{
		return null;
	}

	@Override
	protected String getBuild()
	{
		return "1234";
	}

	@Override
	protected ServerClientBuildCheckResult checkServerClientBuild(String clientBuild)
	{
		return new ServerClientBuildCheckResult(true, this.getBuild());
	}

}
