package com.example.mvc.scala

/**
 * 调用结果
 * @param ex
 * @param ret
 * @tparam T
 */
class InvokeRet[T](var error: Throwable, var result: T) {
    def isException() : Boolean = {
      error != null
    }

  def isSuccess() : Boolean = {
    !isException()
  }
}
