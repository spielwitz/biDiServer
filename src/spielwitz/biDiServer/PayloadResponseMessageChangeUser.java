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

/**
 * Response message payload for creating or updating a user.
 * @author spielwitz
 *
 */
public class PayloadResponseMessageChangeUser extends PayloadMessageBase
{
	private String userId;
	private String activationCode;
	
	private String serverUrl;
	private int    serverPort;
	private String adminEmail;
	
	private String serverPublicKey;

	/**
	 * Constructor.
	 * @param userId User ID
	 * @param activationCode Activation code
	 * @param serverUrl URL of the server
	 * @param serverPort Port of the server
	 * @param adminEmail E-mail address of the server administrator
	 * @param serverPublicKey Public RSA key of the server as a string
	 */
	public PayloadResponseMessageChangeUser(
			String userId, 
			String activationCode, 
			String serverUrl, 
			int serverPort,
			String adminEmail, 
			String serverPublicKey)
	{
		super();
		this.userId = userId;
		this.activationCode = activationCode;
		this.serverUrl = serverUrl;
		this.serverPort = serverPort;
		this.adminEmail = adminEmail;
		this.serverPublicKey = serverPublicKey;
	}

	/**
	 * Get the Activation code.
	 * @return The activation cod
	 */
	public String getActivationCode() {
		return activationCode;
	}

	/**
	 * Get the e-mail address of the server administrator.
	 * @return The e-mail address of the server administrator
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/** Get the server port.
	 * @return The server port
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Get the public RSA key of the server as a string.
	 * @return The public RSA key of the server as a string
	 */
	public String getServerPublicKey() {
		return serverPublicKey;
	}

	/**
	 * Get the server URL.
	 * @return Server URL
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * Get the user ID.
	 * @return The user ID
	 */
	public String getUserId() {
		return userId;
	}
}
