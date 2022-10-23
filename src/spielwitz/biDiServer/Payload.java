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

import java.security.PrivateKey;
import java.security.PublicKey;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * A class allowing to serialize and deserialize objects to and from JSON in a type-safe manner.
 * @author spielwitz
 *
 */
public class Payload
{
	private static final Gson serializer = new Gson();
	
	private String className;
	private JsonElement jsonElement;
	private String jsonElementRsaEncrypted;
	
	/**
	 * Constructor.
	 * @param obj Payload pbject
	 */
	public Payload(Object obj)
	{
		if (obj != null)
		{
			this.className = obj.getClass().getName();
			this.jsonElement = serializer.toJsonTree(obj);
		}
	}
	
	/**
	 * Get the payload object.
	 * @return Payload object
	 */
	public Object getObject()
	{
		if (this.className == null || this.jsonElement == null)
		{
			return null;
		}
		
		try
		{
 			return serializer.fromJson(this.jsonElement, Class.forName(this.className));
		}
		catch (Exception x)
		{
			return null;
		}
	}
	
	/**
	 * Get the name of the class of the payload object.
	 * @return The name of the class of the payload object.
	 */
	public String getClassName()
	{
		return this.className;
	}
	
	/**
	 * Get a JSON string representation of the payload object.
	 */
	public String toString()
	{
		return serializer.toJson(this.jsonElement);
	}
	
	/**
	 * Get the JSON element representing the payload object.
	 * @return The payload object as a JSON element
	 */
	public JsonElement getJsonElement()
	{
		return jsonElement;
	}

	/**
	 * Set the JSON element representing the payload object.
	 * @param jsonElement The payload object as a JSON element
	 */
	public void setJsonElement(JsonElement jsonElement)
	{
		this.jsonElement = jsonElement;
	}

	void encryptRsa(PublicKey key)
	{
		this.jsonElementRsaEncrypted = 
				CryptoLib.base64Encode(
						CryptoLib.encryptRsa(
						serializer.toJson(jsonElement),
						key));
		this.jsonElement = null;
	}
	
	Object getObject(PrivateKey key)
	{
		try
		{
			this.jsonElement = 
					serializer.fromJson(
						CryptoLib.decryptRsa(
								CryptoLib.base64Decode(this.jsonElementRsaEncrypted),
							key),
						JsonElement.class);
			
			return this.getObject();
		}
		catch (Exception x)
		{
			return null;
		}
	}
}
