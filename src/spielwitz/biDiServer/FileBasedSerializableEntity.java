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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

/**
 * Base class for entities that can be written to and read from a file system.
 * @author spielwitz
 *
 */
abstract class FileBasedSerializableEntity
{
	private static Gson serializer = new Gson();
	
	/**
	 * Serialize an object.
	 * @return The JSON representation of the object
	 */
	protected String serialize()
	{
		return serializer.toJson(this);
	}
	
	/**
	 * Write an object to a file.
	 * @param fileName The name of the file
	 * @return True, of the write was successful
	 */
	public boolean writeToFile(String fileName)
	{
		boolean success = true;
		
		Path parentFolderPath = Paths.get(fileName).getParent();
		
		if (parentFolderPath != null)
		{
			File parentFolder = Paths.get(fileName).getParent().toFile();
			
			if (!parentFolder.exists())
			{
				parentFolder.mkdirs();
			}
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
		{
			String text = this.serialize();
			bw.write(text);			
		} catch (IOException e)
		{
			e.printStackTrace();
			success = false;
		}
		
		return success;
	}

	/**
	 * Read an object from a file.
	 * @param fileName The file name
	 * @param c The class of the object
	 * @return The object
	 */
	protected static Object readFromFileInternal(String fileName, Class<?> c)
	{
		Object obj = null;
		
		try (BufferedReader br = new BufferedReader(
				new FileReader(new File(fileName))))
		{
			String json = br.readLine();
			obj = deserialize(json, c);
		} catch (Exception e)
		{
		}
		
		return obj;
	}
	
	private static Object deserialize(String json, Class<?> c)
	{
		try
		{
			return serializer.fromJson(json, c);
		} catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Compares two entities for equality.
	 * @return True, if both entities have the same content
	 */
	@Override
	public boolean equals(Object otherEntity)
	{
		if (otherEntity == null)
		{
			return false;
		}
		else if (this.getClass() != otherEntity.getClass())
		{
			return false;
		}
		else
		{
			return this.serialize().equals(((FileBasedSerializableEntity)otherEntity).serialize());
		}
	}
}
