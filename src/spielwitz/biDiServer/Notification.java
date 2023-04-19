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

package spielwitz.biDiServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

/**
 * A notification sent from the server to the clients.
 * @author spielwitz
 *
 */
public class Notification
{
	private static int counter;
	private static Gson serializer = new Gson();
	private static Object counterLock = new Object();
	
	static void deleteFile(String pathToNotificationsFolder, String userId, String id)
	{
		File dir = Paths.get(pathToNotificationsFolder, userId).toFile();
		
		if (!dir.exists())
		{
			dir.mkdir();
		}
		
		File file = new File( 
				Paths.get(
						pathToNotificationsFolder, 
						userId,
						id
				).toString());
		
		if (file.exists())
		{
			try
			{
				file.delete();
			}
			catch (Exception x) {}
		}
	}
	/**
	 * Get a notification from a JSON string.
	 * @param jsonString The JSON string
	 * @return The notification
	 */
	static Notification deserialize(String jsonString)
	{
		try
		{
			return serializer.fromJson(jsonString, Notification.class);
		}
		catch (Exception x)
		{
			return null;
		}
	}

	/**
	 * Get all notifications of a user currently stored in files.
	 * @param pathToNotificationsFolder Folder path on the server where notifications are stored
	 * @param userId The user ID of the recipient of the message
	 * @return All stored notifications of a user
	 */
	static ArrayList<Notification> getAllNotificationsMessagesOfUserFromFile(String pathToNotificationsFolder, String userId)
	{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		
		File dir = Paths.get(pathToNotificationsFolder, userId).toFile();
		
		if (!dir.exists())
		{
			return notifications;
		}
		
		String[] fileNames = dir.list();
		Arrays.sort(fileNames);
		
		for (String fileName: fileNames)
		{
			File file = Paths.get(
					pathToNotificationsFolder, 
					userId,
					fileName).toFile();
			
			Notification notification = null;
			
			try (BufferedReader br = new BufferedReader(
					new FileReader(file)))
			{
				String json = br.readLine();
				notification = Notification.deserialize(json);
				
				if (notification != null)
					notifications.add(notification);
			} catch (Exception e)
			{
			}
		}
		
		return notifications;
	}
	private boolean ping;
	private String sender;
	
	private ArrayList<String> recipients;

	private String id;

	private long dateCreated;

	private Payload payloadEncrypted;

	/**
	 * Constructor for creating a ping notification without payload. The notification is not dispatched to any user.
	 */
	Notification()
	{
		this.ping = true;
	}
	
	/**
	 * Constructor for creating a notification with a payload object. The notification is encrypted with the public key of the recipient.
	 * @param sender User ID of the sender (for information purposes only)
	 * @param recipients The user IDs of the recipients (for information purposes only)
	 * @param payloadObject The notification payload object
	 * @param key The public RSA of the recipient
	 */
	Notification(
			String sender,
			ArrayList<String> recipients, 
			Object payloadObject,
			PublicKey key)
	{
		this.dateCreated = System.currentTimeMillis();
		
		synchronized(counterLock)
		{
			this.id = Long.toString(this.dateCreated) + String.format("%04d", counter);
			
			counter++;
			if (counter > 9999)
			{
				counter = 0;
			}
		}
		
		this.ping = false;
		this.sender = sender;
		this.recipients = recipients;
		
		this.payloadEncrypted = new Payload(payloadObject);
		this.payloadEncrypted.encryptRsa(key);
	}
	
	/**
	 * Get the date when the notification was created on the server.
	 * @return The date when the notification was created on the server.
	 */
	public long getDateCreated()
	{
		return dateCreated;
	}
	
	/**
	 * Decrypt the notification payload object and return it.
	 * @param key The private RSA key of the recipient
	 * @return The notification payload object. Or null, if the the notification payload was null or could not be decrypted with the private RSA key.
	 */
	public Object getPayloadObject(PrivateKey key)
	{
		if (this.payloadEncrypted != null)
		{
			return this.payloadEncrypted.getObject(key);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Get the user IDs of the recipients of the notification.
	 * @return The user IDs of the recipients of the notification
	 */
	public ArrayList<String> getRecipients()
	{
		return recipients;
	}
	
	/**
	 * Get the user ID of the sender of the notification.
	 * @return The user ID of the sender of the notification
	 */
	public String getSender()
	{
		return sender;
	}

	/**
	 * Get a JSON representation of the notification.
	 * @return JSON representation of the notification
	 */
	public String toString()
	{
		return this.serialize();
	}
	
	String getId()
	{
		return this.id;
	}
	
	/**
	 * True if the notification is a ping notification.
	 * @return True if the notification is a ping notification
	 */
	boolean isPing()
	{
		return ping;
	}
	
	/**
	 * Get a JSON representation of the notification.
	 * @return JSON notification of the message
	 */
	String serialize()
	{
		return serializer.toJson(this);
	}
	
	/**
	 * Write the notification to a file.
	 * @param pathToNotificationsFolder Folder path on the server where notification are stored
	 * @param userId The user ID of the recipient of the notification
	 */
	void writeToFile(String pathToNotificationsFolder, String userId)
	{
		File dir = Paths.get(pathToNotificationsFolder, userId).toFile();
		
		if (!dir.exists())
		{
			dir.mkdir();
		}
		
		String fileName = 
				Paths.get(
						pathToNotificationsFolder, 
						userId,
						this.id
				).toString();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
		{
			String text = this.serialize();
			bw.write(text);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
