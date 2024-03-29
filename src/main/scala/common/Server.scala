package common

import scala.concurrent.ExecutionContext.global
import io.grpc.ServerBuilder
import greet.{GreetingServiceGrpc, GreetingServiceImpl}
import pnd.PrimeNumberDecompositionServiceGrpc
import pnd.PrimeNumberDecompositionServiceImpl
import average.AverageServiceGrpc
import average.AverageServiceImpl
import java.io.File

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
      .addService(
        AverageServiceGrpc
          .bindService(AverageServiceImpl, global)
      )
      .useTransportSecurity(
        new File("ssl/server.crt"),
        new File("ssl/server.pem")
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
