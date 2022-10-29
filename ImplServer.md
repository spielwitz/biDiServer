# Implementing the Server

Copy the latest [BiDiServer JAR file](https://github.com/spielwitz/biDiServer/releases) to your Java project and add it to the build path. Do the same with  [Gson 2.9.1, or later](https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson).

Create your server implementation class. The class extends class [`spielwitz.biDiServer.Server`](src/spielwitz/biDiServer/Server.java). Also implement the necessary constructor and methods:

```
package myPackage;

import java.util.HashSet;

import com.google.gson.JsonElement;

import spielwitz.biDiServer.ResponseInfo;
import spielwitz.biDiServer.Server;
import spielwitz.biDiServer.ServerClientBuildCheckResult;
import spielwitz.biDiServer.ServerConfiguration;
import spielwitz.biDiServer.ServerException;
import spielwitz.biDiServer.Tuple;

public class MyServer extends Server
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	protected MyServer(ServerConfiguration config, String homeDir) throws ServerException
	{
		super(config, homeDir);
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
	protected JsonElement migrateDataSet(String arg0, JsonElement arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onConfigurationUpdated(ServerConfiguration arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Tuple<ResponseInfo, Object> onCustomRequestMessageReceived(String arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object setDataSetInfoPayloadObject(String arg0, HashSet<String> arg1, Object arg2, Object arg3)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

```

## `main(String[] args)`

Create the [`ServerConfiguration`](src/spielwitz/biDiServer/ServerConfiguration.java) object instance, or read it from a file. Then call the constructor of your server and start the server. For example:

```
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

	MyServer myServer = null;

	try
	{
		myServer = new MyServer(config, System.getProperty("user.dir"));
	}
	catch (Exception x)
	{
		System.out.println("Server initialization error: " + x.getMessage());
		return;
	}

	myServer.start();
}
```

## `Constructor`

`homeDir` is the folder where the server creates its *ServerData* folder.

```
protected MyServer(ServerConfiguration config, String homeDir) throws ServerException
{
	super(config, homeDir);
	//Implement additional logic here, if necessary
}
```

## `getBuild()` `checkServerClientBuild(String clientBuild)`

You may want to compare if the server and a client run on compatible builds. In method `getBuild()`, return the server build as a string. If you want to disable build comparisons, return null.

If the implementations of `getBuild()` of both the server and the client return a value which is not null, then method `checkServerClientBuild(String clientBuild)` is called at runtime when a request from a client comes in. You can compare the client build with the server build. As a result, you return an instance of class [`ServerClientBuildCheckResult​`](src/spielwitz/biDiServer/ServerClientBuildCheckResult.java). This return object contains the information whether the builds are compatible, and, if they are not compatible, pass the information about the minimum required build to the client. If the builds are not compatible, the client request is rejected.

If your client-server implementation does not require build checks, you can implement the two methods as follows:

```
@Override
protected ServerClientBuildCheckResult checkServerClientBuild(String clientBuild)
{
	return new ServerClientBuildCheckResult(true, null);
}

@Override
protected String getBuild()
{
	return null;
}
```
## `migrateDataSet(String className, com.google.gson.JsonElement jsonElementBeforeMigration)`

While you develop your application further, the class of your data set payloads may change from one release to the next. For example, if you implemented a server for a game, and you add new features to the game, the game class may change with new fields being added, changed, or deleted. Then, you want to adapt the stored data set to the new class definition.

Since the server stores data sets as JSON files, you can manipulate the JSON structure of a data set payload, so that it can be loaded with the new payload class definition.

The method `migrateDataSet(String className, JsonElement jsonElementBeforeMigration)` is called for all data sets when the server is started. `className` contains the name of your payload class. `jsonElementBeforeMigration` is the Gson JsonElement of your data set payload before migration.

If you want to migrate a data set, create a new JsonElement and return it. If you do not want to migrate a data set, return null.

## `onConfigurationUpdated(ServerConfiguration config)`

Class [`Client`](src/spielwitz/biDiServer/Client.java) offers the method `setLogLevel​(LogLevel newLogLevel)` to change the server log level. When this service is called, the server calls `onConfigurationUpdated(ServerConfiguration config)` with the changed server configuration. You can implement the update of the server configuration file here, for example:

```
@Override
protected void onConfigurationUpdated(ServerConfiguration config)
{
	config.writeToFile(fileServerConf.getAbsolutePath());
}
```
## `onCustomRequestMessageReceived(String userId, Object payloadRequest`

In this method, you implement all your use case-specific request services. See document [Implementing Custom Requests](ImplCustomRequests.md) for details.

## `setDataSetInfoPayloadObject(String dataId, HashSet<String> userIds, Object dataSetPayloadObject,currentDataSetInfoPayloadObject)`

The server keeps [`DataSetInfo`](src/spielwitz/biDiServer/DataSetInfo.java) objects of all data sets in RAM for faster access. While a data set can have megabytes of data, a data set info should only contain the most relevant information for querying data sets. By default, the data set info contains the data set ID and the IDs of the users who are allowed to read, change, or delete the data set.

In addition, you can add a custom-defined payload that is used in your use case-specific implementation, for example, the information about which users have already submitted their moves. You may want to keep such information in RAM, instead of reading all data sets from the file system of the server to find the requested information.

In this method, you can set or manipulate the payload of a data set info object.  The current payload is `currentDataSetInfoPayloadObject`. You can return the same object, if you don't need to manipulate the payload, or you return a changed object of the same class.

You must not change the list of authorized users `userIds` of a data set. Such a change is not reflected in the data sets, and can cause inconsistencies. Use the client method `updateDataSet​(DataSet dataSet)` instead.

[Back to overview](README.md)

