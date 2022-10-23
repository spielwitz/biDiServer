# Implementing Custom Requests

Since the BiDiServer library only knows abstract entities like data sets, but not what they actually represent, you have to implement use-case specific logic in your implementations of the abstract classes [`Server`](src/spielwitz/biDiServer/Server.java) and [`Client`](src/spielwitz/biDiServer/Client.java).

To implement custom requests, proceed as follows:

## 1. Create a Payload Request Class

For **every** custom request type, you need a dedicated request class. For example, if you want to implement a service for getting the games that are waiting for your moves, and another service for posting your moves, you need two request classes, for example, `RequestGetGamesWaitingForInput` and `RequestPostMoves`.

## 2. Implement the Server Method `onCustomRequestMessageReceived(String userId, Object payloadRequest)`

Since there is only this single method on server side to handle custom requests, you need to check the payload class to find out which custom request type is actually coming in. For example:

```
protected Tuple<ResponseInfo, Object> onCustomRequestMessageReceived(
			String userId, 
			Object payloadRequest)
{
	if (payloadRequest.getClass() == RequestGetGamesWaitingForInput.class)
	{
		return this.getGamesWaitingForInput(userId, (RequestGetGamesWaitingForInput)payloadRequest);
	}
	else if (payloadRequest.getClass() == RequestPostMoves.class)
	{
		return this.postMoves(userId, (RequestPostMoves)payloadRequest);
	}
	[...]
```
## 3. Implement a Client Method

On client side, it is recommeneded to create a dedicated method for every request type. The client provides the method `sendCustomRequestMessage​(Object payload)` to which you pass an object instance of the request type-specific request class, for example:

```
public [...] postMoves(String gameId, Moves moves)
{
	Response<?> response = this.sendCustomRequestMessage(new RequestPostMoves(gameId, moves));
	
	[...]
}
```
The returned object instance of class [`Response`](src/spielwitz/biDiServer/Response.java) may contain, along with the common [`ResponseInfo`](src/spielwitz/biDiServer/ResponseInfo.java) object, a payload of a request-specfic or a generic class. 

[Back to overview](README.md)
