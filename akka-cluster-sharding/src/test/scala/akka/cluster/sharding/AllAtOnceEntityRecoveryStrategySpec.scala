package akka.cluster.sharding

import scala.concurrent.duration._
import scala.language.postfixOps

import akka.cluster.sharding.ShardRegion.EntityId
import akka.testkit.AkkaSpec

class AllAtOnceEntityRecoveryStrategySpec extends AkkaSpec {
  val strategy = EntityRecoveryStrategy.allStrategy()

  "AllAtOnceEntityRecoveryStrategy" must {
    "recover entities" in {
      val entities = Set[EntityId]("1", "2", "3", "4", "5")
      val startTime = System.nanoTime()
      val result = strategy.recoverEntities(entities)
      result.size should ===(1)
      // the Future is completed immediately for allStrategy
      result.head.value.get.get should ===(entities)
    }

    "not recover when no entities to recover" in {
      val result = strategy.recoverEntities(Set[EntityId]())
      result.size should ===(0)
    }
  }
}
