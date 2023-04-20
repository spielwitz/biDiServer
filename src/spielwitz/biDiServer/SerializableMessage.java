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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The base class for request and response messages.
 * @author spielwitz
 *
 */
abstract class SerializableMessage
{
	private static final Gson serializer = new Gson();
	
	private static final String PROPERTY_OBJECT = "obj";
	private static final String PROPERTY_CLASS_NAME = "class";
	
	/**
	 * Deserialize a message from a JSON string.
	 * @param json The JSON string
	 * @return The message
	 */
	static SerializableMessage deserialize(String json)
	{
		JsonObject jsonObject = serializer.fromJson(json, JsonObject.class);
		
		String className = jsonObject.get(PROPERTY_CLASS_NAME).getAsString();
		JsonElement jsonElementObject = jsonObject.get(PROPERTY_OBJECT);
		
		try
		{
			return (SerializableMessage) serializer.fromJson(jsonElementObject, Class.forName(className));
		}
		catch (Exception x)
		{
			return null;
		}
	}
	
	private Payload payload;
	
	/**
	 * Constructor.
	 * @param payload Payload
	 */
	protected SerializableMessage(Payload payload)
	{
		this.payload = payload;
	}
	
	/**
	 * Get the payload.
	 * @return Payload
	 */
	Payload getPayload()
	{
		return payload;
	}
	
	/**
	 * Get the payload object.
	 * @return Payload object
	 */
	Object getPayloadObject()
	{
		if (this.payload != null)
		{
			return this.payload.getObject();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Serialize the message to a JSON string.
	 * @return
	 */
	String serialize()
	{
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.add(PROPERTY_OBJECT, serializer.toJsonTree(this));
		jsonObject.addProperty(PROPERTY_CLASS_NAME, this.getClass().getName());
		
		return serializer.toJson(jsonObject);
	}
}
