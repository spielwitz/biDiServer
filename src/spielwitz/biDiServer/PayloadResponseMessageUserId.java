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
 * Response message payload for negotiating the token and session.
 * @author spielwitz
 *
 */
class PayloadResponseMessageUserId extends PayloadMessageBase
{
	private String token;
	private boolean sessionValid;
	private ServerClientBuildCheckResult serverClientBuildCheck;
	
	/**
	 * Constructor.
	 * @param token Token
	 * @param sessionValid True, if the existing session is valid
	 * @param ServerClientBuildCheckResult Result of the build compatibility check on server side
	 */
	PayloadResponseMessageUserId(
			String token, 
			boolean sessionValid, 
			ServerClientBuildCheckResult serverClientBuildCheck)
	{
		this.token = token;
		this.sessionValid = sessionValid;
		this.serverClientBuildCheck = serverClientBuildCheck;
	}

	/**
	 * Get the token.
	 * @return The token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * Is the existing session valid?
	 * @return True, if the existing session is valid
	 */
	public boolean isSessionValid()
	{
		return sessionValid;
	}

	/**
	 * Get the result of the build compatibility check on server side.
	 * @return Result of the build compatibility check on server side
	 */
	ServerClientBuildCheckResult getServerClientBuildCheck()
	{
		return serverClientBuildCheck;
	}
}
