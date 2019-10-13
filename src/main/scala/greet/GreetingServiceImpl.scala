package greet

import scala.concurrent.Future

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
}
