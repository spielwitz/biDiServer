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

package test.testServerAndClient;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import spielwitz.biDiServer.DataSet;
import spielwitz.biDiServer.User;

public class TestData
{
	public static final String clientBuild = "1234";
	public static final String clientComptabileBuild = "1230";
	
	public static final String serverBuild = "1234";
	public static final String serverComptabileBuild = "1230";
	
	public static final String clientLocale = "en-US";
	public static final String serverLocale = "de-DE";
	
	public static String[] getUserIds()
	{
		return new String[] {
				User.ADMIN_USER_ID,
				"User0",
				"User1",
				"User2",
				"User3",
				"User4",
				"User5",
				"User6",
				"User7",
				"User8",
				"User9",
				"User*?"
		};
	}
	
	public static Hashtable<String,DataSet> getGames()
	{
		Hashtable<String,DataSet> games = new Hashtable<String,DataSet>();
		
		DataSet game0 = getGame0();
		DataSet game1 = getGame1();
		DataSet gameX = getGameX();
		
		games.put(game0.getId(), game0);
		games.put(game1.getId(), game1);
		games.put(gameX.getId(), gameX);
		
		return games;
	}
	
	public static String[] getGameIds()
	{
		Enumeration<String> stringEnumeration = getGames().keys();
		String[] gameIds = new String[getGames().size()];
		
		int i = 0;
		
		while (stringEnumeration.hasMoreElements())
		{
			gameIds[i] = stringEnumeration.nextElement();
			i++;
		}
		
		return gameIds;
	}
	
	public static DataSet getGame0()
	{
		String[] userIds = getUserIds();
		
		HashSet<String> users = new HashSet<String>();
		
		users.add(userIds[0]);
		users.add(userIds[1]);
		users.add(userIds[2]);
		users.add(userIds[3]);
		users.add(userIds[4]);
		
		DataSet game = new DataSet("Game0", users, new Game());
		
		return game;
	}
	
	public static DataSet getGame1()
	{
		String[] userIds = getUserIds();
		
		HashSet<String> users = new HashSet<String>();
		
		users.add(userIds[4]);
		users.add(userIds[5]);
		users.add(userIds[6]);
		users.add(userIds[7]);
		users.add(userIds[8]);
		
		DataSet game = new DataSet("Game1", users, new Game());
		
		return game;
	}
	
	public static DataSet getGameX()
	{
		String[] userIds = getUserIds();
		
		HashSet<String> users = new HashSet<String>();
		
		users.add(userIds[4]);
		users.add(userIds[5]);
		users.add(userIds[6]);
		users.add(userIds[7]);
		users.add(userIds[8]);
		
		DataSet game = new DataSet("GameWithInvalidCharacter_?", users, new Game());
		
		return game;
	}
}
