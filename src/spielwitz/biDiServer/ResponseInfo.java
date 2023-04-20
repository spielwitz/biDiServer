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

import com.google.gson.Gson;

/**
 * The information if a request was successful or not, combined with other information.
 * @author spielwitz
 *
 */
public class ResponseInfo
{
	private boolean success;
	private String message;
	private String serverBuild;
	
	/**
	 * Constructor.
	 * @param success True, if the request was successful
	 */
	public ResponseInfo(boolean success)
	{
		this.success = success;
	}
	
	/**
	 * Constructor.
	 * @param success True, if the request was successful
	 * @param message Text message
	 */
	public ResponseInfo(boolean success, String message)
	{
		this.success = success;
		this.message = message;
	}

	/**
	 * Get the text message.
	 * @return Text message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get the server build.
	 * @return Server build
	 */
	public String getServerBuild() {
		return serverBuild;
	}
	
	/**
	 * True, if the request was successful.
	 * @return True, if the request was successful
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Set the text message.
	 * @param message Text message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Set the server build.
	 * @param build Server build
	 */
	public void setServerBuild(String build) {
		this.serverBuild = build;
	}
	
	/**
	 * Get a JSON string representation of the response message info.
	 */
	public String toString()
	{
		return new Gson().toJson(this);
	}
}
