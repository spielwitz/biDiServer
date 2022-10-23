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

import java.util.UUID;

public class PayloadTestClass2
{
	double aDoubleValue = 3.14;
	boolean aBooleanValue = true;
	private String aPrivateString = "Hello";
	public UUID aPublicId = UUID.randomUUID();
	
	public String getPrivateString()
	{
		return this.aPrivateString;
	}
	
	public boolean equals(Object obj) 
	{
		PayloadTestClass2 otherObj = (PayloadTestClass2)obj;
		
		return this.aDoubleValue == otherObj.aDoubleValue &&
			   this.aBooleanValue == otherObj.aBooleanValue &&
			   this.aPrivateString.equals(otherObj.aPrivateString) &&
			   this.aPublicId.equals(otherObj.aPublicId);
	}
}
