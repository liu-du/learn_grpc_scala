package greet

import io.grpc.ManagedChannelBuilder
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.global
import common.Channel

object GreetClient extends Channel {

  val client = GreetingServiceGrpc.stub(channel)

  def main(args: Array[String]): Unit = {
    val request = GreetingRequest(Some(Greeting("Dwyane", "Wade")))
    val f = client.greet(request)

    println(Await.result(f, 100.seconds).result)
  }
}
