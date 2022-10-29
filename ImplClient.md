# Implementing the Client

Copy the latest [BiDiServer JAR file](https://github.com/spielwitz/biDiServer/releases) to your Java project and add it to the build path. Do the same with  [Gson 2.9.1, or later](https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson).

Create your client implementation class. The class extends class [`spielwitz.biDiServer.Client`](src/spielwitz/biDiServer/Client.java). Also implement the necessary constructor and methods:

```
import spielwitz.biDiServer.Client;
import spielwitz.biDiServer.ClientConfiguration;
import spielwitz.biDiServer.Notification;
import spielwitz.biDiServer.ServerClientBuildCheckResult;

public class MyClient extends Client
{
	public MyClient(ClientConfiguration config, boolean establishNotificationSocket, String locale)
	{
		super(config, establishNotificationSocket, locale);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ServerClientBuildCheckResult checkServerClientBuild(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getBuild()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onConnectionStatusChanged(boolean arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onNotificationReceived(Notification arg0)
	{
		// TODO Auto-generated method stub
		
	}
}
```
## `Constructor`

In the constructor, you pass a [`ClientConfiguration`](src/spielwitz/biDiServer/ClientConfiguration.java) object to the super constructor. The client configuration is created by activating a user. In most cases, you read the client configuration from a file when starting the client application.

With the parameter `establishNotificationSocket` set to true, you can establish a notification socket on the server, so that the server can push notifications to the client.

The client also requires a locale string, denoting the language in which messages from the server or the client are returned. Currently, the following locales are supported:

* en-US: English
* de-DE: German

In the following example, the client implementation contains a callback interface object `callback`, which can handle use case-specific responses to custom-requests in the application.

```
private IMyClientCallback callback;

public TestClient(ClientConfiguration conf, boolean establishNotificationSocket, IMyClientCallback callback)
{
	super(conf, establishNotificationSocket, "en-US");
	this.callback = callback;
}
```
## `getBuild()` `checkServerClientBuild(String serverBuild)`

You may want to compare if the server and a client run on compatible builds. In method `getBuild()`, return the client build as a string. If you want to disable build comparisons, return null.

If the implementations of `getBuild()` of both the server and the client return a value which is not null, then method `checkServerClientBuild(String clientBuild)` is called at runtime when a response from the server is received. You can compare the client build with the server build. As a result, you return an instance of class [`ServerClientBuildCheckResult​`](src/spielwitz/biDiServer/ServerClientBuildCheckResult.java). This return object contains the information whether the builds are compatible, and, if they are not compatible, pass the information about the minimum required build. If the builds are not compatible, the server response is marked as not successful.

If your client-server implementation does not require build checks, you can implement the two methods as follows:

```
@Override
protected ServerClientBuildCheckResult checkServerClientBuild(String serverBuild)
{
	return null;
}

@Override
protected String getBuild()
{
	return null;
}
```
## `onConnectionStatusChanged(boolean connected)`

This method is called by the client when the connection status of the notification socket changes from connected to not connected, or vice-versa. If you instantiated a client with the parameter `establishNotificationSocket` set to false, this method is never called.

You may implement use case-specific coding here, for example, updating the connection status indicator on your client application UI.

## `onNotificationReceived(Notification notification)`

The method is called when the client receives a push notification from the server. The parameter `notification` of class [`Notification`](src/spielwitz/biDiServer/Notification.java) contains the payload of the notification. The payload is always custom-defined. See document [Implementing Custom Notifications](ImplCustomNotifications) for details.

[Back to overview](README.md)
