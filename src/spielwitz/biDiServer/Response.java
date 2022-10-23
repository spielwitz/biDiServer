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
 * Response of a request to the server.
 * @author spielwitz
 *
 * @param <T> The data type of the response payload
 */
public class Response<T>
{
	private T payload;
	private ResponseInfo responseInfo;
	
	/**
	 * Constructor.
	 * @param payload The message payload
	 * @param responseInfo The information about the response
	 */
	Response(T payload, ResponseInfo responseInfo)
	{
		this.payload = payload;
		this.responseInfo = responseInfo;
	}

	/**
	 * Get the message payload.
	 * @return The message payload
	 */
	public T getPayload() {
		return payload;
	}

	/**
	 * Get the information about the response.
	 * @return The information about the response
	 */
	public ResponseInfo getResponseInfo() {
		return responseInfo;
	}
	
	/**
	 * Get a string representation of the response message.
	 */
	public String toString() {
        return String.format("ResponseInfo: <%s>, Payload: <%s>)", responseInfo.toString(), payload != null ? payload.toString() : "null");
    }
}
