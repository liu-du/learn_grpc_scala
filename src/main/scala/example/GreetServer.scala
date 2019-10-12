package example
import scala.concurrent.ExecutionContext.global
import io.grpc.{ServerBuilder, Server}
import greet.GreetingServiceGrpc

object DummyServer {
  def main(args: Array[String]): Unit = {
    val server = ServerBuilder
      .forPort(50051)
      .addService(
        GreetingServiceGrpc.bindService(GreetingServiceImpl, global)
      )
      .build()
      .start()

    sys.addShutdownHook {
      println("*** shutting down grpc server since JVM is shutting down.")
      server.shutdown()
      println("*** grpc server shutdown")
    }

    server.awaitTermination()
  }
}
