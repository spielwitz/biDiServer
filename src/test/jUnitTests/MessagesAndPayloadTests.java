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

import spielwitz.biDiServer.Payload;

public class MessagesAndPayloadTests
{
	@Test
	void serializeDeserializePayload()
	{
		PayloadTestClass payloadClassObj = new PayloadTestClass();
		
		Payload payload = new Payload(payloadClassObj);
		
		PayloadTestClass payloadClassObj2 = (PayloadTestClass)payload.getObject();
		
		assertTrue(payloadClassObj.getClass() == payloadClassObj2.getClass());
		assertTrue(payloadClassObj.subObjects.size() == payloadClassObj2.subObjects.size());
		
		for (int i = 0; i < payloadClassObj.subObjects.size(); i++)
		{
			PayloadTestClass2 subObj = payloadClassObj.subObjects.get(i);
			PayloadTestClass2 subObj2 = payloadClassObj2.subObjects.get(i);
			
			assertTrue(subObj.equals(subObj2));
		}
	}
	
	@Test
	void serializeDeserializePayloadNull()
	{
		PayloadTestClass payloadClassObj = null;
		Payload payload = new Payload(payloadClassObj);
		
		PayloadTestClass payloadClassObj2 = (PayloadTestClass)payload.getObject();
		assertTrue(payloadClassObj2 == null);
	}
	
	@Test
	void serializeDeserializePayloadOtherObjects()
	{
		Payload payload = new Payload(true);
		
		boolean result = (boolean)payload.getObject();
		
		assertTrue(result);
	}
}
