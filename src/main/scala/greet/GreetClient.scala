package greet

import io.grpc.ManagedChannelBuilder
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.global

object GreetClient {

  def main(args: Array[String]): Unit = {
    val channel = ManagedChannelBuilder
      .forAddress("localhost", 50051)
      .usePlaintext()
      .build()
    val client = GreetingServiceGrpc.stub(channel)

    val request = GreetingRequest(Some(Greeting("Dwyane", "Wade")))
    val f = client.greet(request)
    println(Await.result(f, 100 seconds).result)
  }
}
