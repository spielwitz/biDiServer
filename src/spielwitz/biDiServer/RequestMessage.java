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
 * A request message sent to the server.
 * @author spielwitz
 *
 */
class RequestMessage extends RequestMessageBase
{
	private RequestMessageType type;
	
	private String token;
	
	/**
	 * Constructor.
	 * @param type Type of the request message
	 * @param sessionId Session ID
	 * @param token Token
	 * @param clientBuild Client build
	 * @param payload Message payload
	 */
	RequestMessage(RequestMessageType type, String sessionId, String token, String clientBuild, Payload payload)
	{
		super(sessionId, clientBuild, payload);
		
		this.type = type;
		this.token = token;
	}
	
	/**
	 * Get the token.
	 * @return The token
	 */
	String getToken()
	{
		return token;
	}

	/**
	 * Get the type of the request message.
	 * @return The type of the request message.
	 */
	RequestMessageType getType()
	{
		return type;
	}
}
