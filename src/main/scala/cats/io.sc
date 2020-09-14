/*
 * A data type for encoding side effects as pure values
 * similar : Scalaz Task, Monix Task, cats-effect Sync
 */
import cats.effect.{ContextShift, IO, Sync, Timer}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Future vs IO
  * Future는 참조 투명성(Referential Transparency)을 제공하지 않음
  * Future는 실행을 설명하는 것이 아니라, 직접 실행하고 결과를 memorize 한다
  * https://medium.com/@sderosiaux/are-scala-futures-the-past-69bd62b9c001
  *
  * Future(Asynchronous, Eager) vs IO(Asynchronous, Lazy)
  * https://typelevel.org/cats-effect/datatypes/io.html
  */
val f = Future { println("future hello") }
val sideEffect = {
  for {
    _ <- f
    _ <- f
  } yield ()
}
// Await.result(sideEffect, 10 seconds) // future hello 한번만 실행됨
// Await.result(sideEffect, 10 seconds) // future hello 호출되지 않음

val ioa = IO { println("io hello") }
val program: IO[Unit] = {
  for {
    _ <- ioa
    _ <- ioa
  } yield ()
}

program.unsafeRunSync() // io hello 두번 실행됨

/**
  * Future는 Cancel 되지 않는다??
  */
def crashF: Future[Duration] = Future.failed(new Exception("boom"))
def needCancelF = Future {
  Thread.sleep(1000)
  println("call needCancel Future")
}

Await.result(Future.firstCompletedOf(List(crashF, needCancelF)), Duration.Inf)

implicit val timer = IO.timer(global)
implicit val sync = Sync[IO]
implicit val cs = IO.contextShift(ExecutionContext.global)
def crashCats(implicit F: Sync[IO]): IO[Duration] =
  F.raiseError(new Exception("boom"))

def needCancelCats(implicit timer: Timer[IO], sync: Sync[IO]) = {
  for {
    _ <- timer.sleep(1 seconds)
    x <- sync.delay { println("needCancel cats") }
  } yield x
}

IO.race(crashCats, needCancelCats).unsafeRunSync

/**
  * Performance
  * https://twitter.com/jdegoes/status/924992350849552384/photo/1
  */