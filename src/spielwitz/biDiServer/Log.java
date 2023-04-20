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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

class Log
{
	private static String getLocalizedDateString()
	{
		Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
	    LocalDateTime date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	    
	    String dateBuildStyle = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	    
		String year = dateBuildStyle.substring(0, 4);
		String month = dateBuildStyle.substring(4, 6);
		String day = dateBuildStyle.substring(6, 8);
		
		String hour = dateBuildStyle.substring(8, 10);
		String minute = dateBuildStyle.substring(10, 12);
		String second = dateBuildStyle.substring(12, 14);
		
		return TextProperties.getMessageText(TextProperties.DateFormat(day, month, year, hour, minute, second));
	}
	private Path logPath;
	
	private Server server;
	
	Log(Server server, Path logPath)
	{
		this.server = server;
		
		this.logPath = Paths.get(
						logPath.toString(), 
						new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".csv");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(TextProperties.getMessageText(TextProperties.LogDate()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogLevel()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogEventId()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogIpAddress()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogUser()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogRequestMessageType()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogPayload()) + "\t");
		sb.append(TextProperties.getMessageText(TextProperties.LogMessage()) + "\n");
		
		try {
		    Files.write(this.logPath, sb.toString().getBytes("UTF-8"), StandardOpenOption.CREATE);
		}catch (IOException e) {
		}
	}
	
	PayloadResponseMessageGetLog getLog()
	{
		File file = new File(this.logPath.toString());
		PayloadResponseMessageGetLog payloadResponse = null; 
		
		if (file.exists())
		{
			synchronized(this.logPath)
			{
				try
		        {
					payloadResponse = new PayloadResponseMessageGetLog(
							file.getName(),
							new String (Files.readAllBytes(this.logPath)));
		        }
		        catch (Exception e) 
		        {
		        }
			}
		}
		
		return payloadResponse;
	}
	
	long getSize()
	{
		File file = new File(this.logPath.toString());
		
		if (file.exists())
		{
			return file.length();
		}
		else
		{
			return 0;
		}
	}
	
	void logMessage(LogEventId eventId, LogLevel severity, String msg)
	{
		this.logMessage(eventId, severity, null, null, null, null, msg);
	}
	
	void logMessage(
			LogEventId eventId, 
			LogLevel severity,
			String ipAddress,
			String userId,
			String requestMessageType,
			String payload,
			String msg)
	{
		if (severity.compareTo(this.server.getLogLevel()) < 0)
		{
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(getLocalizedDateString());
		sb.append("\t");
		
		sb.append(severity.toString());
		sb.append("\t");
		
		sb.append(eventId.toString());
		sb.append("\t");
		
		if (ipAddress != null)
			sb.append(ipAddress);
		sb.append("\t");
		
		if (userId != null)
			sb.append(userId);
		sb.append("\t");
		
		if (requestMessageType != null)
			sb.append(requestMessageType);
		sb.append("\t");
		
		if (payload != null)
			sb.append(payload);
		sb.append("\t");
		
		sb.append(msg);
		
		sb.append("\n");
		
		synchronized(this.logPath)
		{
			try {
			    Files.write(this.logPath, sb.toString().getBytes("UTF-8"), StandardOpenOption.APPEND);
			}catch (IOException e) {
			}
		}
	}
}
