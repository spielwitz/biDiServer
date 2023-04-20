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
 * The client-internal response to a request to the server.
 * @author spielwitz
 *
 */
class ResponseMessage extends SerializableMessage
{
	private ResponseInfo info;
	private TextProperty textProperty;
	
	/**
	 * Constructor.
	 */
	ResponseMessage()
	{
		this(null);
	}
	
	/**
	 * Constructor.
	 * @param success True, if the request was successful
	 * @param payload Payload
	 * @param textProperty Text property
	 */
	ResponseMessage(boolean success, Payload payload, TextProperty textProperty)
	{
		super(payload);
		
		this.info = new ResponseInfo(success);
		this.textProperty = textProperty;
	}
	
	/**
	 * Constructor.
	 * @param success True, if the request was successful
	 * @param message Text message
	 * @param payload Payload
	 */
	ResponseMessage(boolean success, String message, Payload payload)
	{
		super(payload);
		
		this.info = new ResponseInfo(success, message);
	}

	/**
	 * Constructor.
	 * @param payload Payload
	 */
	ResponseMessage(Payload payload)
	{
		this(true, null, payload);
	}

	/**
	 * Constructor.
	 * @param payload Payload
	 * @param info Response info
	 */
	ResponseMessage(Payload payload, ResponseInfo info)
	{
		super(payload);
		this.info = info;
	}
	
	/**
	 * Get the response info.
	 * @return The response info
	 */
	ResponseInfo getInfo() {
		return info;
	}

	/**
	 * Get the text message.
	 * @return The text message
	 */
	String getMessage()
	{
		return this.info.getMessage();
	}
	
	/**
	 * Get the server build.
	 * @return The server build
	 */
	String getServerBuild()
	{
		return this.info.getServerBuild();
	}

	/**
	 * Get the text property.
	 * @return The text property.
	 */
	TextProperty getTextProperty() {
		return this.textProperty;
	}
	
	/**
	 * True, if the request was successful.
	 * @return True, if the request was successful.
	 */
	boolean isSuccess()
	{
		return this.info.isSuccess();
	}
	
	/**
	 * Set the text message.
	 * @param message The text message
	 */
	void setMessage(String message)
	{
		this.info.setMessage(message);
	}

	/**
	 * Set the server build.
	 * @param build The server build.
	 */
	void setServerBuild(String build)
	{
		this.info.setServerBuild(build);
	}
}
