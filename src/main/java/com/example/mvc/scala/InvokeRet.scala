package com.example.mvc.scala

/**
 * 调用结果
 * @param ex
 * @param ret
 * @tparam T
 */
class InvokeRet[T](var err: Throwable, var ret: T) {
    def isException() : Boolean = {
      err != null
    }

  def isSuccess() : Boolean = {
    !isException()
  }
}
