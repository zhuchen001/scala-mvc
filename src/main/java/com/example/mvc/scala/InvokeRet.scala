package com.example.mvc.scala

/**
 * 调用结果
 * @param ex
 * @param ret
 * @tparam T
 */
class InvokeRet[T](var err: Throwable, var result: T) {
    def isError() : Boolean = {
      err != null
    }

  def isSuccess() : Boolean = {
    !isError()
  }
}
