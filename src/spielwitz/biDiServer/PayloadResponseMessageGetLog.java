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
 * Response message payload for getting the server log.
 * @author spielwitz
 *
 */
public class PayloadResponseMessageGetLog extends PayloadMessageBase
{
	private String fileName;
	private String logCsv;
	
	/**
	 * Constructor.
	 * @param fileName The file name of the log on the server.
	 * @param logCsv The log as a comma-separated string 
	 */
	PayloadResponseMessageGetLog(String fileName, String logCsv)
	{
		super();
		this.fileName = fileName;
		this.logCsv = logCsv;
	}

	/**
	 * Get the file name of the log on the server.
	 * @return The file name of the log on the server
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * Get the log as a comma-separated string.
	 * @return The log as a comma-separated string 
	 */
	public String getLogCsv()
	{
		return logCsv;
	}
}
