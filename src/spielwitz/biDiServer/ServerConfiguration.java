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

import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * The configuration of a server.
 * @author spielwitz
 *
 */
public class ServerConfiguration extends FileBasedSerializableEntity
{
	/**
	 * The default log level of a server (Information)
	 */
	public static final LogLevel SERVER_DEFAULT_LOGLEVEL = LogLevel.Information;
	/**
	 * The default server port (56084)
	 */
	public static final int SERVER_PORT = 56084;
	/**
	 * Read a server configuration from a file.
	 * @param fileName File name
	 * @return The server configuration
	 */
	public static ServerConfiguration readFromFile(String fileName)
	{
		ServerConfiguration serverConfiguration = (ServerConfiguration) readFromFileInternal(fileName, ServerConfiguration.class);
		
		if (serverConfiguration != null)
		{
			serverConfiguration.serverPrivateKeyObject = CryptoLib.decodePrivateKeyFromBase64(serverConfiguration.serverPrivateKey);
		}
		
		return serverConfiguration;
	}
	private String url;
	
	private int port; 
	private String adminEmail;	
	
	private LogLevel logLevel;
	
	private String serverPrivateKey;
	
	private String serverPublicKey;
	
	private String locale;
	
	private transient PrivateKey serverPrivateKeyObject;

	/**
	 * Create a server configuration object.
	 * @param url The server URL
	 * @param port The server port
	 * @param adminEmail The e-mail address of the server administrator
	 * @param logLevel The minimum log level
	 * @param locale Language into which the server converts log messages and console outputs. "de-DE" or "en-US"
	 */
	public ServerConfiguration(
			String url, 
			int port, 
			String adminEmail, 
			LogLevel logLevel,
			String locale)
	{
		super();
		this.url = url;
		this.port = port;
		this.adminEmail = adminEmail;
		this.logLevel = logLevel;
		this.locale = locale;
		
		KeyPair serverKeyPair = CryptoLib.getNewKeyPair();
		
		this.serverPrivateKey = CryptoLib.encodePrivateKeyToBase64(serverKeyPair.getPrivate());
		this.serverPublicKey = CryptoLib.encodePublicKeyToBase64(serverKeyPair.getPublic()); 
		
		this.serverPrivateKeyObject = CryptoLib.decodePrivateKeyFromBase64(this.serverPrivateKey);
	}

	/** 
	 * Get the e-mail address of the server administrator.
	 * @return The e-mail address of the server administrator.
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/**
	 * Get the language into which the server converts log messages and console outputs. "de-DE" or "en-US"
	 * @return The language into which the server converts log messages and console outputs. "de-DE" or "en-US"
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Get the minimum log level.
	 * @return The minimum log level
	 */
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	/**
	 * Get the server port.
	 * @return The server port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get the server URL.
	 * @return The server URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Get the private RSA key of the server as a string
	 * @return The private RSA key of the server as a string
	 */
	String getServerPrivateKey() {
		return serverPrivateKey;
	}

	/**
	 * Get the private RSA key of the server.
	 * @return The private RSA key of the server
	 */
	PrivateKey getServerPrivateKeyObject() {
		return serverPrivateKeyObject;
	}

	/**
	 * Get the public RSA key of the server as a string
	 * @return The public RSA key of the server as a string
	 */
	String getServerPublicKey() {
		return serverPublicKey;
	}

	/**
	 * Set or change the minimum log level. 
	 * @param logLevel The minimum log level.
	 */
	void setLogLevel(LogLevel logLevel)
	{
		this.logLevel = logLevel;
	}
}
