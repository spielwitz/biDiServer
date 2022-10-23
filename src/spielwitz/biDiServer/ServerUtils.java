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

import java.util.Arrays;

/**
 * Utility class for server-related tasks.
 * @author spielwitz
 *
 */
public class ServerUtils
{
	static final int FILENAME_MIN_LEN = 1;
	static final int FILENAME_MAX_LEN = 255;
	
	private static final Character[] INVALID_CHARACTERS = new Character[] {'.', '\\', '/', '"', '\'', '*', '<', '>', '?', '|'};

	/**
	 * Check if a file name, for example, for a user ID or a data set ID, is valid.
	 * @param filename The file name
	 * @return Information about the file name validity
	 */
	public static FileNameCheck checkFileName(String filename)
	{
		if (filename == null || filename.length() < FILENAME_MIN_LEN)
			return FileNameCheck.TooShort;
		else if (filename.length() > FILENAME_MAX_LEN)
			return FileNameCheck.TooLong;
		else if (Arrays.stream(INVALID_CHARACTERS).anyMatch(ch -> filename.contains(ch.toString())))
			return FileNameCheck.InvalidCharacters;
		else
			return FileNameCheck.Ok;
	}
	
	static String getInvalidCharactersAsString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < INVALID_CHARACTERS.length; i++)
		{
			if (i > 0)
				sb.append(" ");
			
			sb.append(INVALID_CHARACTERS[i]);
		}
		
		return sb.toString();
	}

}
