package greet

import scala.concurrent.Future
import io.grpc.stub.StreamObserver

object GreetingServiceImpl extends GreetingServiceGrpc.GreetingService {
  def greet(request: GreetingRequest): Future[GreetingResponse] = {
    Future.successful(
      GreetingResponse(
        request.greeting match {
          case None => "Hello!"
          case Some(Greeting(firstName, lastName)) =>
            s"Hello $firstName $lastName!"
        }
      )
    )
  }

  def longGreet(
      responseObserver: StreamObserver[GreetingResponse]
  ): StreamObserver[GreetingRequest] = {
    var result = "Hello "

    val requestObserver =
      new StreamObserver[GreetingRequest] {
        def onNext(value: GreetingRequest): Unit = {
          println("received a request")
          result += value.greeting
            .map(greeting => s"${greeting.firstName} ${greeting.lastName} ")
            .getOrElse("")
        }
        def onError(t: Throwable): Unit = {}
        def onCompleted(): Unit = {
          println("client notify us it's finished")
          responseObserver.onNext(GreetingResponse(result + "!"))
          responseObserver.onCompleted()
        }
      }

    requestObserver
  }

  def greetEveryOne(responseObserver: StreamObserver[GreetingResponse]): StreamObserver[GreetingRequest] = {
    val requestObserver = new StreamObserver[GreetingRequest] {
      def onNext(value: GreetingRequest): Unit = {
        println("receiving a streaming request")
        println("sending   a streaming response")
        value.greeting.map {
            case Greeting(firstName, lastName) => GreetingResponse(s"Hello $firstName $lastName")
          }.foreach {
            responseObserver.onNext(_)
          }
      }
      def onError(t: Throwable): Unit = {}
      def onCompleted(): Unit = {
        println("client notifies completion")
        println("sending server completion to client")
        responseObserver.onCompleted()
      }
    } 

    requestObserver
  }


}
