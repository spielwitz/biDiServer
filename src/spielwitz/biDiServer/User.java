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

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.UUID;

/**
 * A user who is authorized to access the server.
 * @author spielwitz
 *
 */
public class User extends FileBasedSerializableEntity
{
	/**
	 * The reserved user name for the activation user
	 */
	public static final String ACTIVATION_USER_ID = "_ACTIVATE";
	
	/**
	 * The reserved user name for the server administrator
	 */
	public static final String ADMIN_USER_ID = "_ADMIN";
	
	private static final String[] RESERVED_USER_NAMES = new String[] {ACTIVATION_USER_ID, ADMIN_USER_ID};
	
	/**
	 * Check if a user ID complies to the rules.
	 * @param userId The user ID
	 * @return True, if the user ID is between 1 and 255 characters long, is a valid file name, and is not a reserved ID
	 */
	public static boolean isUserIdValid(String userId)
	{
		return !isUserIdReserved(userId) &&
			   ServerUtils.checkFileName(userId) == FileNameCheck.Ok;
	}
	
	static boolean isUserIdReserved(String userId)
	{
		return Arrays.stream(RESERVED_USER_NAMES).anyMatch(n -> userId.contains(n));
	}
	
	static User readFromFile(String fileName)
	{
		User user = (User) readFromFileInternal(fileName, User.class);
		
		if (user != null)
		{
			user.userPublicKeyObject = CryptoLib.decodePublicKeyFromBase64(user.userPublicKey);
		}
		
		return user;
	}
	private boolean active;
	private transient PublicKey userPublicKeyObject;
	private String id;
	private String name;

	private Hashtable<String,String> customData;
	
	private String activationCode;
	
	private String userPublicKey;
	
	User(
			String id,
			String name,
			Hashtable<String,String> customData)
	{
		this(id, name, customData, false, null, null);
	}
	
	User(
			String id,
			String name,
			Hashtable<String,String> customData,
			boolean active,
			String activationCode,
			String userPublicKey)
			
	{
		this.id = id;
		this.name = name;
		this.customData = customData;
		this.active = active;
		this.activationCode = activationCode;
		this.userPublicKey = userPublicKey;
		
		if (this.userPublicKey != null)
		{
			this.userPublicKeyObject = CryptoLib.decodePublicKeyFromBase64(this.userPublicKey);
		}
	}

	/**
	 * Get the custom data of the user. This is a set of key-value-pairs that can be used by custom clients and servers to add additional data to the user record, for example, an e-mail address.
	 * @return Custom data as key-value pairs
	 */
	public Hashtable<String,String> getCustomData() {
		return customData;
	}

	/**
	 * Get the user ID.
	 * @return The user ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the user name.
	 * @return The user name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the public RSA key of the user.
	 * @return The public RSA key of the user
	 */
	public PublicKey getUserPublicKeyObject()
	{
		return userPublicKeyObject;
	}
	
	/** 
	 * Is the user active?
	 * @return True, if the user is active.
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Creates a JSON string representation of the user
	 */
	public String toString()
	{
		return this.serialize();
	}
	
	void clearCredentials()
	{
		this.userPublicKey = null;
		this.userPublicKeyObject = null;
		
		this.active = false;
		this.activationCode = UUID.randomUUID().toString();
	}

	String getActivationCode() {
		return activationCode;
	}
	
	void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	void setActive(boolean active)
	{
		this.active = active;
	}
	
	void setCustomData(Hashtable<String,String> customData) {
		this.customData = customData;
	}

	void setName(String name) {
		this.name = name;
	}
	
	void setUserPublicKey(String userPublicKey) 
	{
		this.userPublicKey = userPublicKey;
		this.userPublicKeyObject = CryptoLib.decodePublicKeyFromBase64(this.userPublicKey);
	}

}
