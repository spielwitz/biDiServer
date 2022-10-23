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
 * The message payload for activating a user.
 */
class PayloadRequestMessageActivateUser extends PayloadMessageBase
{
	private String userId;
	private String activationCode;
	private String userPublicKey;
	
	/**
	 * Constructor.
	 * @param userId The user ID
	 * @param activationCode Activation code
	 * @param userPublicKey Public RSA key of the user as a string
	 */
	PayloadRequestMessageActivateUser(String userId, String activationCode, String userPublicKey)
	{
		this.userId = userId;
		this.activationCode = activationCode;
		this.userPublicKey = userPublicKey;
	}

	/**
	 * Get the user ID.
	 * @return The user ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Get the activation code.
	 * @return The activation code
	 */
	public String getActivationCode() {
		return activationCode;
	}

	/**
	 * Get the public RSA key of the user as a string. 
	 * @return The public RSA key of the user as a string
	 */
	public String getUserPublicKey() {
		return userPublicKey;
	}
}
