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
 * Type of a request message.
 * @author spielwitz
 *
 */
enum RequestMessageType
{
	ACTIVATE_USER,
	CHANGE_USER,
	SHUTDOWN,
	PING,
	GET_USER,
	GET_USERS,
	DELETE_USER,
	GET_SERVER_STATUS,
	GET_LOG,
	SET_LOG_LEVEL,
	ESTABLISH_NOTIFICATION_SOCKET,
	GET_DATA_SET_INFOS_OF_USER,
	CREATE_DATA_SET,
	UPDATE_DATA_SET,
	DELETE_DATA_SET,
	GET_DATA_SET,
	PUSH_NOTIFICATION,
	DISCONNECT,
	
	CUSTOM
}
