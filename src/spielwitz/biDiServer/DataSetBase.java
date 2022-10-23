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

import java.util.HashSet;

/**
 * Base class of a data set.
 * @author spielwitz
 *
 */
public abstract class DataSetBase
{
	private String id;
	
	/**
	 * IDs of the users related to a data set
	 */
	protected HashSet<String> userIds;
	private Payload payload;
	
	DataSetBase(String id, HashSet<String> userIds, Object payloadObject)
	{
		this.id = id;
		this.userIds = userIds;
		this.setPayloadObject(payloadObject);
	}

	/**
	 * Get the ID of a data set or data set info.
	 * @return The ID
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Get the IDs of the users who are authorized to read and write the data set.
	 * @return The user IDs
	 */
	public HashSet<String> getUserIds()
	{
		return userIds;
	}
	
	/**
	 * Get the payload of a data set.
	 * @return The payload
	 */
	public Payload getPayload()
	{
		return payload;
	}

	/**
	 * Get the payload object of the data set.
	 * @return The payload object
	 */
	public Object getPayloadObject()
	{
		return this.payload.getObject();
	}
	
	/**
	 * Set the payload object a data set.
	 * @param payloadObject The payload object
	 */
	public void setPayloadObject(Object payloadObject)
	{
		this.payload = new Payload(payloadObject);
	}
}
