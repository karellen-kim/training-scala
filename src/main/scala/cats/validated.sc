import cats.data._
import cats.data.Validated._
import cats.implicits._

case class DomainValidation(errorMessage: String)

object BasicValidator {
  def validateEmail(email: String): Either[DomainValidation, String] =
    Either.cond(
      email.matches("^.*@.*"),
      email,
      DomainValidation("email invalid")
    )

  def validateAge(age: Int) = Either.cond(
    age >= 18 && age <= 75,
    age,
    DomainValidation("age invalid")
  )

  def validateAll(email: String, age: Int) = {
    for {
      email <- validateEmail(email)
      age <- validateAge(age)
    } yield (email, age)
  }
}

BasicValidator.validateEmail("abc")
BasicValidator.validateEmail("abc@abc.com")

BasicValidator.validateAll("abc@abc.com", 30)
BasicValidator.validateAll("abc.com", 1) // Left(DomainValidation(email invalid))

/**
  * Either를 사용하면 누적된 오류를 표시하기 어렵다.
  */

object AdvancedValidator {
  def validateEmail(email: String): ValidatedNec[DomainValidation, String] =
    if (email.matches("^.*@.*")) email.validNec else DomainValidation("email invalid").invalidNec

  def validateAge(age: Int): ValidatedNec[DomainValidation, Int] =
    if (age >= 18 && age <= 75) age.validNec else DomainValidation("age invalid").invalidNec

  def validateAll(email: String, age: Int): ValidatedNec[DomainValidation, (String, Int)] = {
    (validateEmail(email), validateAge(age)).mapN((e, a) => (e, a))
  }
}

AdvancedValidator.validateAll("abc@abc.com", 30)
AdvancedValidator.validateAll("abc.com", 1) // Invalid(Chain(DomainValidation(email invalid), DomainValidation(age invalid)))
