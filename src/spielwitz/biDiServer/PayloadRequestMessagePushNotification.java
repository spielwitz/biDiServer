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

import java.util.ArrayList;

/**
 * Message payload for pushing notifictions to specified recipients.
 * @author spielwitz
 *
 */
public class PayloadRequestMessagePushNotification extends PayloadMessageBase
{
	private Payload payload;
	private ArrayList<String> recipients;

	/**
	 * Constructor.
	 * @param recipients Recipients (user IDs) of the notification
	 * @param payloadObject The notification payload object
	 */
	public PayloadRequestMessagePushNotification(ArrayList<String> recipients, Object payloadObject)
	{
		this.payload = new Payload(payloadObject);
		this.recipients = recipients;
	}
	
	/**
	 * Get the notification payload.
	 * @return The notification payload
	 */
	public Payload getPayload()
	{
		return this.payload;
	}
	
	/**
	 * Get the notification payload object.
	 * @return The notification payload object
	 */
	public Object getPayloadObject()
	{
		return this.payload.getObject();
	}
	
	/**
	 * Get the recipients (user IDs) of the notification.
	 * @return The recipients (user IDs) of the notification
	 */
	public ArrayList<String> getRecipients()
	{
		return this.recipients;
	}
}
