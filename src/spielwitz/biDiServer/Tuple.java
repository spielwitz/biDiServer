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
 * Represents a 2-tuple, or pair.
 * @author spielwitz
 *
 * @param <E1> The type of the tuple's first component.
 * @param <E2> The type of the tuple's second component.
 */
public class Tuple<E1, E2>
{
    private final E1 e1;
    private final E2 e2;

    /**
     * Constructor.
     * @param e1 The first component 
     * @param e2 The second component
     */
    public Tuple(E1 e1, E2 e2)
    {
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * Get the first component.
     * @return The first component.
     */
    public E1 getE1()
    {
        return e1;
    }

    /**
     * Get the second component.
     * @return The second component.
     */
    public E2 getE2()
    {
        return e2;
    }

    /**
     * Get a string representation of the tuple
     */
    public String toString()
    {
        return String.format("(%s, %s)", e1.toString(), e2.toString());
    }
}