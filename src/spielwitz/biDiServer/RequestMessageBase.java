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
 * The most simple request message, just to initiate connection between client and server.
 * @author spielwitz
 *
 */
class RequestMessageBase extends SerializableMessage
{
	private String sessionId;
	
	/**
	 * Constructor.
	 * @param sessionId Session ID
	 * @param payload Message payload
	 */
	RequestMessageBase(String sessionId, Payload payload)
	{
		super(payload);
		this.sessionId = sessionId;
	}

	/**
	 * Get the session ID.
	 * @return The session ID
	 */
	String getSessionId()
	{
		return sessionId;
	}
}
