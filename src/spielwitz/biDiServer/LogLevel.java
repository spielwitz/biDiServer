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
 * Server log level.
 * @author spielwitz
 *
 */
public enum LogLevel
{
	/**
	 * Log level "Verbose". If this is set as the server log level, messages of all log levels are written to the log.
	 */
	Verbose,
	
	/**
	 * Log level "Information". If this is set as the server log level, messages of all log levels, except "Verbose", are written to the log.
	 */
	Information,
	
	/**
	 * Log level "Warning". If this is set as the server log level, messages of all log levels, except "Verbose" and "Information", are written to the log.
	 */
	Warning,
	
	/**
	 * Log level "Error". If this is set as the server log level, messages of log levels "Error", "Critical" and "General" are written to the log.
	 */
	Error,
	
	/**
	 * Log level "Critical". If this is set as the server log level, messages of log levels "Critical" and "General" are written to the log.
	 */
	Critical,
	
	/**
	 * Log level "General". Message of this log level are written to the server log regardless of the server log level.
	 */
	General
}
