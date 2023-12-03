package com.example.mvc.scala

/**
 * 调用结果（scala编译器会自动为case class生成apply方法作为构造方法）
 * @param ex
 * @param ret
 * @tparam T
 */
case class InvokeRet[T](var err: Throwable, var result: T) {
    def isError() : Boolean = {
      err != null
    }

  def isSuccess() : Boolean = {
    !isError()
  }
}

/**
 * 伴生对象
 */
//object InvokeRet{
//  /**
//   * 这样InvokeRet可以不用new了
//   */
//  def apply[T](err:Throwable, result: T) = new InvokeRet(err, result)
//}