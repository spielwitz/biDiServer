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

import spielwitz.biDiServer.Client;
import spielwitz.biDiServer.ClientConfiguration;
import spielwitz.biDiServer.Notification;
import spielwitz.biDiServer.Response;
import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.ServerClientBuildCheckResult;

public class TestClient extends Client
{
	private IClientTesterCallback callback;
	
	public TestClient(ClientConfiguration conf, boolean establishNotificationSocket, IClientTesterCallback callback)
	{
		super(conf, establishNotificationSocket, TestData.clientLocale);
		this.callback = callback;
	}
	
	@Override
	public void onNotificationReceived(Notification notification)
	{
		this.callback.onNotificationReceived(this.getUserId(), notification);
	}

	@Override
	protected String getBuild()
	{
		return TestData.clientBuild;
	}

	@Override
	protected void onConnectionStatusChanged(boolean connected)
	{
		this.callback.onConnectionStatusChanged(this.getUserId(), connected);
	}

	public ResponseInfo increaseGameCounter(String gameId)
	{
		Response<?> response = this.sendCustomRequestMessage(new PayloadRequestIncreaseGameCounter(gameId));
		
		return response.getResponseInfo();
	}

	@Override
	protected ServerClientBuildCheckResult checkServerClientBuild(String serverBuild)
	{
		String buildClient = this.getBuild();
		String minimumCompatibleBuild = TestData.clientComptabileBuild;
		
		if (buildClient != null && 
			serverBuild != null &&
			serverBuild.compareTo(minimumCompatibleBuild) < 0)
				
		{
			return new ServerClientBuildCheckResult(false, minimumCompatibleBuild);
		}
		else
		{
			return new ServerClientBuildCheckResult(true, minimumCompatibleBuild);
		}
	}
}
