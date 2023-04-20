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
 * The result of a compatibility check between server and client.
 * @author spielwitz
 *
 */
public class ServerClientBuildCheckResult
{
	private boolean areBuildsCompatible;
	private String minimumComptabileBuild;
	
	/**
	 * Constructor.
	 * @param areBuildsCompatible True, if the builds are compatible
	 * @param minimumComptabileBuild The minimum required build, either of the server, or the client
	 */
	public ServerClientBuildCheckResult(boolean areBuildsCompatible, String minimumComptabileBuild)
	{
		super();
		this.areBuildsCompatible = areBuildsCompatible;
		this.minimumComptabileBuild = minimumComptabileBuild;
	}
	
	/**
	 * The minimum required build, either of the server, or the client.
	 * @return The minimum required build, either of the server, or the client
	 */
	public String getMinimumComptabileBuild()
	{
		return minimumComptabileBuild;
	}
	
	/**
	 * True, if the builds are compatible.
	 * @return True, if the builds are compatible
	 */
	boolean areBuildsCompatible()
	{
		return areBuildsCompatible;
	}
}
