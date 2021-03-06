// typeclass itself
trait Num[A] {
  def plus(x: A, y: A): A
  def minus(x: A, y: A): A
}

object Num {
  // default instances placed in companion object and available without import
  implicit val intNum: Num[Int] = new Num[Int] {
    override def plus(x: Int, y: Int): Int = x + y
    override def minus(x: Int, y: Int): Int = x - y
  }

  implicit val doubleNum: Num[Double] = new Num[Double] {
    override def plus(x: Double, y: Double): Double = x + y
    override def minus(x: Double, y: Double): Double = x - y
  }

  def plus[A](x: A, y: A)(implicit num: Num[A]): A =
    num.plus(x, y)

  def minus[A](x: A, y: A)(implicit num: Num[A]): A =
    num.minus(x, y)
}

// interface syntax
object NumSyntax {
  implicit class NumOps[A](x: A) {
    def plus(y: A)(implicit num: Num[A]): A = num.plus(x, y)
    def minus(y: A)(implicit num: Num[A]): A = num.minus(x, y)
  }
}

import NumSyntax._

// interface syntax
1 plus 2
3.5 minus 0.4

// interface objects
Num.plus(1, 2)
Num.minus(3.5, 0.4)

// insert new type class instance into implicit scope
implicit def optionIntNum[A](implicit n: Num[A]): Num[Option[A]] = new Num[Option[A]] {
  override def plus(x: Option[A], y: Option[A]): Option[A] =
    x flatMap { xv => y map (yv => Num.plus(xv, yv)) }

  override def minus(x: Option[A], y: Option[A]): Option[A] =
    x flatMap { xv => y map (yv => Num.minus(xv, yv)) }
}

// for ".some"
import cats.implicits._

// here we go again
3.some plus 4.some minus 5.some
Num.plus(1.some, 2.some)

// we can do it for every A in F[A], which have type class instance
3.4.some plus 2.1.some
3.4.some minus none[Double]
none[Double] plus 0.12.some
none[Int] plus none[Int]
