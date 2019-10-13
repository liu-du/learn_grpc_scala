package greet

import io.grpc.ManagedChannelBuilder
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.global
import common.Channel
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

object GreetClient extends Channel {

  val client = GreetingServiceGrpc.stub(channel)

  def main(args: Array[String]): Unit = {
    val request = GreetingRequest(Some(Greeting("Dwyane", "Wade")))
    val f = client.greet(request)

    val latch = new CountDownLatch(1)
    println(Await.result(f, 100.seconds).result)

    val responseObserver = new StreamObserver[GreetingResponse] {
      def onNext(value: GreetingResponse): Unit = {
        println("server send us a response")
        println(value.result)
      }
      def onError(t: Throwable): Unit = {}
      def onCompleted(): Unit = {
        println("server notify us it's done")
        latch.countDown()
      }
    }

    val requestObserver = client.longGreet(responseObserver)

    Thread.sleep(1000L)
    println("streaming #1")
    requestObserver.onNext(GreetingRequest(Some(Greeting("Dwyane", "Wade"))))

    Thread.sleep(1000L)
    println("streaming #2")
    requestObserver.onNext(GreetingRequest(Some(Greeting("Lebron", "James"))))

    Thread.sleep(1000L)
    println("streaming #3")
    requestObserver.onNext(GreetingRequest(Some(Greeting("Kobe", "Bryant"))))

    println("I (client) am done streaming")
    requestObserver.onCompleted()

    latch.await()
  }
}
