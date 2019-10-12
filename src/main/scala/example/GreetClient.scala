package example
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import greet.{GreetingRequest, Greeting, GreetingServiceGrpc}
import scala.concurrent.Await
import scala.concurrent.duration._

object DummyClient {
  implicit val ec: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.global

  def main(args: Array[String]): Unit = {
    val channel = ManagedChannelBuilder
      .forAddress("localhost", 50051)
      .usePlaintext()
      .build()
    val request = GreetingRequest(Some(Greeting("Dwyane", "Wade")))
    val stub = GreetingServiceGrpc.stub(channel)
    val f = stub.greet(request)
    println(Await.result(f, 100.second).result)
  }
}
