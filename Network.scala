package kodaloss

object Network {
  import java.io.{DataInputStream, DataOutputStream}
  import java.io.{BufferedInputStream, BufferedOutputStream}
  import java.net.{Socket, ServerSocket}

  def streamsFromSocket(s: Socket): (DataInputStream, DataOutputStream) = (
    new DataInputStream(new BufferedInputStream(s.getInputStream)),
    new DataOutputStream(new BufferedOutputStream(s.getOutputStream)))

  def writeAndFlush(dos: DataOutputStream, msg: String): Unit = {
    dos.writeUTF(msg)
    dos.flush()
  }

  case class ServerPort(serverSocket: ServerSocket){
    def port = serverSocket.getLocalPort
  }
  def openServerPort(port: Int): ServerPort = new ServerPort(new ServerSocket(port))

  case class Connection(sock: Socket, dis: DataInputStream, dos: DataOutputStream){
    def read: String = dis.readUTF
    def isActive: Boolean = sock.isBound && !sock.isClosed && sock.isConnected && !sock.isInputShutdown && !sock.isOutputShutdown
    def write(msg: String): Unit = writeAndFlush(dos, msg)
    def close(): Unit = { sock.close; dis.close; dos.close }
  }
  object Connection {
    def fromSocket(socket: Socket): Connection = {
      val (dis, dos) = streamsFromSocket(socket)
      new Connection(socket, dis, dos)
    }

    def toClient(from: ServerPort): Connection = {
      val sock = from.serverSocket.accept()
      fromSocket(sock)
    }

    def toServer(host: String, port: Int): Connection = {
      val sock = new Socket(host, port)
      fromSocket(sock)
    }
  }



}
