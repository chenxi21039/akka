/*
 * Copyright (C) 2009-2016 Typesafe Inc. <http://www.typesafe.com>
 */

package docs.http.scaladsl

import akka.stream.{ Materializer, ActorMaterializer }
import akka.stream.testkit.AkkaSpec

class UnmarshalSpec extends AkkaSpec {

  "use unmarshal" in {
    import akka.http.scaladsl.unmarshalling.Unmarshal
    import system.dispatcher // ExecutionContext
    implicit val materializer: Materializer = ActorMaterializer()

    import scala.concurrent.Await
    import scala.concurrent.duration._

    val intFuture = Unmarshal("42").to[Int]
    val int = Await.result(intFuture, 1.second) // don't block in non-test code!
    int shouldEqual 42

    val boolFuture = Unmarshal("off").to[Boolean]
    val bool = Await.result(boolFuture, 1.second) // don't block in non-test code!
    bool shouldBe false
  }

}
