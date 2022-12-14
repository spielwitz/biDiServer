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

import javax.crypto.Cipher;

/**
 * AES ciphers.
 * @author spielwitz
 *
 */
class Ciphers 
{
	Cipher cipherEncrypt;
	Cipher cipherDecrypt;
	String sessionId;
	long created;
	long lastUsed;
	
	/**
	 * Constructor.
	 * @param cipherEncrypt Cipher for message encryption
	 * @param cipherDecrypt Cipher for message decryption
	 * @param sessionId Session ID
	 */
	Ciphers(Cipher cipherEncrypt, Cipher cipherDecrypt, String sessionId)
	{
		this.cipherEncrypt = cipherEncrypt;
		this.cipherDecrypt = cipherDecrypt;
		this.sessionId = sessionId;
		this.lastUsed = 0;
		this.created = 0;
	}
}
