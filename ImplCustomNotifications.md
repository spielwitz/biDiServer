# Implementing Custom Notifications

You can push notifications to users - including yourself, but not to the administrator - either from within your server or client implementation. If you want to have different kinds of notifications, your should define notification type-specific payload classes.

From within the client, you call the method `pushNotification​(ArrayList<String> recipients, Object object)` to push the `object` to the users contained in the list `recipients`.

From within the server, call method `pushNotification​(String sender, PayloadRequestMessagePushNotification payload)`. In the `payload` object instance of class [`PayloadRequestMessagePushNotification`](src/spielwitz/biDiServer/PayloadRequestMessagePushNotification.java), you define the recipients and the object that is pushed to the recipients.

On client side, you have to implement method `onNotificationReceived(Notification notification)`. The method is called when a client receives a notification. The payload of the notification was  encrypted on the server with the public key of the user. To decrypt the payload, you have to use the private key of the user, which is contained in the client configuration. Then, you can tell by the class of the notification payload of which type the notification is, for example:

```
@Override
public void onNotificationReceived(Notification notification)
{
	Object payloadObject = notification.getPayloadObject(this.getConfig().getUserPrivateKeyObject());
		
	if (payloadObject.getClass() == PayloadNotificationNewEvaluation.class)
	{
		[...]
	}
	else if (payloadObject.getClass() == [...]
```
[Back to overview](README.md)

