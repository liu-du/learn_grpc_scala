package example

import org.scalatest._
import greet.GreetingRequest
import greet.Greeting
import scala.concurrent.Await
import greet.GreetingResponse
import scala.concurrent.Future

class GreetingServiceImplSpec extends AsyncFlatSpec with Matchers {
  it should "return correct response" in {
    GreetingServiceImpl.greet(GreetingRequest(Some(Greeting("x", "y")))) map {
      _.shouldBe(GreetingResponse("Hello x y, I'm a response!"))
    }
  }
}
