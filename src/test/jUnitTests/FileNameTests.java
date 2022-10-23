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

package test.jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import spielwitz.biDiServer.FileNameCheck;
import spielwitz.biDiServer.ServerUtils;
import spielwitz.biDiServer.User;

public class FileNameTests
{
	@Test
	void CheckFileNames()
	{
		assertTrue(ServerUtils.checkFileName("Test") == FileNameCheck.Ok);
		assertTrue(ServerUtils.checkFileName("Montag-Morgen") == FileNameCheck.Ok);
		assertTrue(ServerUtils.checkFileName("Blaue Lagune") == FileNameCheck.Ok);
		assertTrue(ServerUtils.checkFileName(" ") == FileNameCheck.Ok);
		assertTrue(ServerUtils.checkFileName("为什么选择") == FileNameCheck.Ok);
		
		assertTrue(ServerUtils.checkFileName(null) == FileNameCheck.TooShort);
		assertTrue(ServerUtils.checkFileName("") == FileNameCheck.TooShort);
		assertTrue(ServerUtils.checkFileName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890") == FileNameCheck.TooLong);
		assertTrue(ServerUtils.checkFileName(".") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a\\b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a/b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a\"b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a\'b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a*b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a<b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a>b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a?b") == FileNameCheck.InvalidCharacters);
		assertTrue(ServerUtils.checkFileName("a|b") == FileNameCheck.InvalidCharacters);
	}
	
	@Test
	void checkUserNames()
	{
		assertFalse(User.isUserIdValid(User.ACTIVATION_USER_ID));
		assertFalse(User.isUserIdValid(User.ADMIN_USER_ID));
		assertFalse(User.isUserIdValid("a\"b"));
		
		assertTrue(User.isUserIdValid("为什么选择"));
	}
}