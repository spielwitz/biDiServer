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
 * A text property.
 * @author spielwitz
 *
 */
class TextProperty
{
	private String key;
	private Object[] args;

	/**
	 * Constructor.
	 * @param key Key of a text resource
	 */
	TextProperty(String key)
	{
		this.key = key;
	}
	
	/**
	 * Constructor.
	 * @param key Key of a text resource
	 * @param args List of arguments
	 */
	TextProperty(String key, Object[] args)
	{
		this.key = key;
		this.args = args;
	}

	/**
	 * Get the list of arguments.
	 * @return The list of arguments
	 */
	Object[] getArgs() {
		return args;
	}

	/**
	 * Get the text resource key.
	 * @return The text resource key
	 */
	String getKey() {
		return key;
	}
}
