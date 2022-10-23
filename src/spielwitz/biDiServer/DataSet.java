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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;

/**
 * A data set stored at the server.
 * @author spielwitz
 *
 */
public class DataSet extends DataSetBase
{
	private static Gson serializer = new Gson();

	/**
	 * Create a data set object instance.
	 * @param id The data set ID
	 * @param userIds The IDs of the users who are authorized to read and write the data
	 * @param payloadObject The payload of the data set
	 */
	public DataSet(String id, HashSet<String> userIds, Object payloadObject)
	{
		super(id, userIds, payloadObject);
	}
	
	/**
	 * Set the IDs of the users who are authorized to read and write the data.
	 * @param userIds The IDs of the users who are authorized to read and write the data
	 */
	public void setUserIds(HashSet<String> userIds) {
		this.userIds = userIds;
	}
	
	/**
	 * Creates a JSON string representation of the data set.
	 * @return The JSON string
	 */
	public String serialize()
	{
		return serializer.toJson(this);
	}
	
	/**
	 * Creates a data set object instance from a JSON string.
	 * @param jsonString The JSON string
	 * @return The data set object instance
	 */
	public static DataSet deserialize(String jsonString)
	{
		try
		{
			return (DataSet)serializer.fromJson(jsonString, DataSet.class);
		}
		catch (Exception x)
		{
			return null;
		}
	}

	/**
	 * Creates a JSON string representation of the data set.
	 * @return The JSON string
	 */
	public String toString()
	{
		return this.serialize();
	}
	
	static DataSet readFromFile(String fileName)
	{
		DataSet data = null;
		
		try {
			FileInputStream fs = new FileInputStream(fileName);
			GZIPInputStream zipin = new GZIPInputStream (fs);
			ObjectInputStream is = new ObjectInputStream(zipin);
			String dataJson = (String)is.readObject();
			is.close();
			
			data = deserialize(dataJson);

		} catch (Exception e){}		
		
		return data;
	}
	
	String writeToFile(String fileName)
	{
		String dataJson = this.serialize();
		String errorText = null;
		
		try {
			FileOutputStream fs = new FileOutputStream(fileName);
			GZIPOutputStream zipout = new GZIPOutputStream(fs);
			ObjectOutputStream os = new ObjectOutputStream(zipout);
			os.writeObject(dataJson);
			os.close();
		} catch (Exception e) {
			errorText = e.toString();
		}
		
		return errorText;
	}
}
