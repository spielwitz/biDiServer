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

import java.util.Hashtable;

/**
 * The message payload for creating or changing a user or renew the credentials.
 * @author spielwitz
 *
 */
public class PayloadRequestMessageChangeUser extends PayloadMessageBase
{
	private String userId;
	private Hashtable<String,String> customData;
	private String name;
	private boolean create;
	private boolean renewCredentials;
	
	/**
	 * Constructor.
	 * @param userId The user ID
	 * @param customData Additional custom user data
	 * @param name The user name
	 * @param create True, if the user shall be created. False, if it shall be updated
	 * @param renewCredentials True, if the user credentials (RSA private and public key shall be renewed.
	 */
	public PayloadRequestMessageChangeUser(
			String userId, 
			Hashtable<String,String> customData, 
			String name, 
			boolean create,
			boolean renewCredentials)
	{
		super();
		this.userId = userId;
		this.customData = customData;
		this.name = name;
		this.create = create;
		this.renewCredentials = renewCredentials;
	}
	
	/**
	 * Get additional custom user data.
	 * @return Custom user data as key-value-pairs
	 */
	public Hashtable<String,String> getCustomData() {
		return customData;
	}
	
	/**
	 * Get the user name.
	 * @return The user name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the user ID.
	 * @return The user ID
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * True, if the user shall be created. False, if it shall be updated.
	 * @return True, if the user shall be created. False, if it shall be updated
	 */
	public boolean isCreate() {
		return create;
	}
	
	/**
	 * True, if the user credentials (RSA private and public key shall be renewed.
	 * @return True, if the user credentials (RSA private and public key shall be renewed
	 */
	public boolean isRenewCredentials() {
		return renewCredentials;
	}
}
