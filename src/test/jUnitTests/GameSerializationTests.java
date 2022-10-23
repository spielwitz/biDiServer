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

import spielwitz.biDiServer.DataSet;
import spielwitz.biDiServer.DataSetBase;
import test.testServerAndClient.Game;
import test.testServerAndClient.TestData;

public class GameSerializationTests
{
	@Test
	void serializeDeserializeGame()
	{
		DataSet dataSet = TestData.getGame0();
		Game game = (Game) dataSet.getPayloadObject();
		
		String jsonString = dataSet.serialize();
		
		DataSetBase dataSetAfter = (DataSetBase) DataSet.deserialize(jsonString);
		Game gameAfter = (Game) dataSetAfter.getPayloadObject();
		
		assertTrue(dataSet.getId().equals(dataSetAfter.getId()));
		assertTrue(dataSet.getUserIds().size() == dataSetAfter.getUserIds().size());
		assertTrue(game.dateUpdate == gameAfter.dateUpdate);
		assertTrue(game.counter == gameAfter.counter);
	}
}
