package org.java_websocket;

import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.HandshakeImpl1Server;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

/**
 * This class default implements all methods of the WebSocketListener that can be overridden optionally when advances functionalities is needed.<br>
 **/
public abstract class WebSocketAdapter implements WebSocketListener {

	/**
	 * This default implementation does not do anything. Go ahead and overwrite it.
	 * 
	 * @see org.java_websocket.WebSocketListener#onWebsocketHandshakeReceivedAsServer(WebSocket, org.java_websocket.drafts.Draft, org.java_websocket.handshake.ClientHandshake)
	 */
	@Override
	public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer( WebSocket conn, Draft draft, ClientHandshake request ) throws InvalidDataException {
		return new HandshakeImpl1Server();
	}

	@Override
	public void onWebsocketHandshakeReceivedAsClient( WebSocket conn, ClientHandshake request, ServerHandshake response ) throws InvalidDataException {
	}

	/**
	 * This default implementation does not do anything which will cause the connections to always progress.
	 * 
	 * @see org.java_websocket.WebSocketListener#onWebsocketHandshakeSentAsClient(WebSocket, org.java_websocket.handshake.ClientHandshake)
	 */
	@Override
	public void onWebsocketHandshakeSentAsClient( WebSocket conn, ClientHandshake request ) throws InvalidDataException {
	}

	/**
	 * This default implementation does not do anything. Go ahead and overwrite it
	 * 
	 * @see org.java_websocket.WebSocketListener#onWebsocketMessageFragment(WebSocket, org.java_websocket.framing.Framedata)
	 */
	@Override
	public void onWebsocketMessageFragment( WebSocket conn, Framedata frame ) {
	}

	/**
	 * This default implementation will send a pong in response to the received ping.
	 * The pong frame will have the same payload as the ping frame.
	 * 
	 * @see org.java_websocket.WebSocketListener#onWebsocketPing(WebSocket, org.java_websocket.framing.Framedata)
	 */
	@Override
	public void onWebsocketPing( WebSocket conn, Framedata f ) {
		FramedataImpl1 resp = new FramedataImpl1( f );
		resp.setOptcode( Opcode.PONG );
		conn.sendFrame( resp );
	}

	/**
	 * This default implementation does not do anything. Go ahead and overwrite it.
	 * 
	 * @see @see org.java_websocket.WebSocketListener#onWebsocketPong(WebSocket, Framedata)
	 */
	@Override
	public void onWebsocketPong( WebSocket conn, Framedata f ) {
	}

	/**
	 * Gets the XML string that should be returned if a client requests a Flash
	 * security policy.
	 * 
	 * The default implementation allows access from all remote domains, but
	 * only on the port that this WebSocketServer is listening on.
	 * 
	 * This is specifically implemented for gitime's WebSocket client for Flash:
	 * http://github.com/gimite/web-socket-js
	 * 
	 * @return An XML String that comforms to Flash's security policy. You MUST
	 *         not include the null char at the end, it is appended automatically.
	 */
	@Override
	public String getFlashPolicy( WebSocket conn ) {
		return "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"" + conn.getLocalSocketAddress().getPort() + "\" /></cross-domain-policy>\0";
	}

}
