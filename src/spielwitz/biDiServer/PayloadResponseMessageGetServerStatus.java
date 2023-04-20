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
 * Response message payload for getting the server status.
 * @author spielwitz
 *
 */
public class PayloadResponseMessageGetServerStatus extends PayloadMessageBase
{
	private long serverStartDate;
	private long logSizeBytes;
	private LogLevel logLevel;
	private String build;
	
	/**
	 * Constructor.
	 * @param serverStartDate Server start date
	 * @param logSizeBytes Size of the log in bytes
	 * @param logLevel Log level
	 * @param build Build of the server
	 */
	PayloadResponseMessageGetServerStatus(
			long serverStartDate, 
			long logSizeBytes, 
			LogLevel logLevel, 
			String build)
	{
		super();
		this.serverStartDate = serverStartDate;
		this.logSizeBytes = logSizeBytes;
		this.logLevel = logLevel;
		this.build = build;
	}

	/**
	 * Get the build of the server.
	 * @return Build of the server
	 */
	public String getBuild()
	{
		return build;
	}

	/**
	 * Get the log level.
	 * @return Log level
	 */
	public LogLevel getLogLevel()
	{
		return logLevel;
	}

	/**
	 * Get the size of the log in bytes.
	 * @return Size of the log in bytes
	 */
	public long getLogSizeBytes()
	{
		return logSizeBytes;
	}

	/**
	 * Get the server start date.
	 * @return Server start date
	 */
	public long getServerStartDate()
	{
		return serverStartDate;
	}
}
