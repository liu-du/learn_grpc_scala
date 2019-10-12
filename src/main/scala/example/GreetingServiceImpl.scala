package example

import greet.{GreetingServiceGrpc, GreetingRequest, GreetingResponse}
import scala.concurrent.Future

object GreetingServiceImpl extends GreetingServiceGrpc.GreetingService {
  def greet(request: GreetingRequest): Future[GreetingResponse] =
    Future.successful(
      GreetingResponse(
        s"Hello ${request.getGreeting.firstName} ${request.getGreeting.lastName}, I'm a response!"
      )
    )
}
