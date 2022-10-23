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

import com.google.gson.Gson;

/**
 * A short summary of a data set. The data set info is kept in the RAM of the server. Therefore, it should only keep important information about a data set.
 * @author spielwitz
 *
 */
public class DataSetInfo extends DataSetBase
{
	DataSetInfo(String id, HashSet<String> userIds, Object payloadObject)
	{
		super(id, userIds, payloadObject);
	}
	
	/**
	 * Creates a JSON string representation of the data set info.
	 * @return The JSON string
	 */
	public String toString()
	{
		return new Gson().toJson(this);
	}
	
	void setUserIds(HashSet<String> userIds) {
		this.userIds = userIds;
	}
}
