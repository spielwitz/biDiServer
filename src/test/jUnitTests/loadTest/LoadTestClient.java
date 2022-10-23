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

import java.util.UUID;

import spielwitz.biDiServer.Client;
import spielwitz.biDiServer.ClientConfiguration;
import spielwitz.biDiServer.Notification;
import spielwitz.biDiServer.Response;
import spielwitz.biDiServer.ServerClientBuildCheckResult;

public class LoadTestClient extends Client
{
	public LoadTest.ClientActionsThread clientActionThread;

	protected LoadTestClient(ClientConfiguration config, boolean establishNotificationSocket, String locale)
	{
		super(config, establishNotificationSocket, locale);
	}

	@Override
	protected String getBuild()
	{
		return "1234";
	}

	@Override
	protected void onConnectionStatusChanged(boolean connected)
	{
	}

	@Override
	protected ServerClientBuildCheckResult checkServerClientBuild(String serverBuild)
	{
		return new ServerClientBuildCheckResult(true, this.getBuild());
	}

	@Override
	protected void onNotificationReceived(Notification notificationMessage)
	{
		UUID message = (UUID) notificationMessage.getPayloadObject(this.getConfig().getUserPrivateKeyObject());
		
		this.clientActionThread.onNotificationReceived(message);
	}
	
	Response<CustomPayloadResponse> customRequest(CustomPayloadRequest payloadRequest)
	{
		return this.sendCustomRequestMessage(payloadRequest);
	}
}
