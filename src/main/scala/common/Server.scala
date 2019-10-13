package common

import scala.concurrent.ExecutionContext.global
import io.grpc.ServerBuilder
import greet.{GreetingServiceGrpc, GreetingServiceImpl}
import pnd.PrimeNumberDecompositionServiceGrpc
import pnd.PrimeNumberDecompositionServiceImpl

object Server {
  def main(args: Array[String]): Unit = {
    val server = ServerBuilder
      .forPort(50051)
      .addService(
        GreetingServiceGrpc.bindService(GreetingServiceImpl, global)
      )
      .addService(
        PrimeNumberDecompositionServiceGrpc
          .bindService(PrimeNumberDecompositionServiceImpl, global)
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
