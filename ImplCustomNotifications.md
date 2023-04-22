# Implementing Custom Notifications

You can push notifications to users - including yourself, but not to the administrator - either from within your server or client implementation. If you want to have different kinds of notifications, your should define notification type-specific payload classes.

From within the client, you call the method `pushNotification​(ArrayList<String> recipients, Object object)` to push the `object` to the users contained in the list `recipients`.

From within the server, call method `pushNotification​(String sender, PayloadRequestMessagePushNotification payload)`. In the `payload` object instance of class [`PayloadRequestMessagePushNotification`](src/spielwitz/biDiServer/PayloadRequestMessagePushNotification.java), you define the recipients and the object that is pushed to the recipients.

On client side, you have to implement method `onNotificationReceived(String sender, ArrayList<String> recipients, long dateCreated, Object payload)`. The method is called when a client receives a notification. You can tell by the class of the notification payload of which type the notification is, for example:

```
@Override
public void onNotificationReceived(String sender, ArrayList<String> recipients, long dateCreated, Object payload)
{
	if (payload.getClass() == PayloadNotificationNewEvaluation.class)
	{
		[...]
	}
	else if (payload.getClass() == [...]
```
[Back to overview](README.md)

