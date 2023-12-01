package com.example.mvc.scala

/**
 * 给Object扩展常用方法
 */
object RichObject {
  /**
   * 生成json
   *
   */
  implicit class ObjectExtensions(obj: Any) {
    def toJson: String = JSONTools.toJson(obj)
  }


  /**
   * 执行函数调用，捕捉异常，包装返回
   *
   * @param f 实际执行函数
   * @tparam T 核心返回值
   */
  def invoke[T](f: () => T): InvokeRet[T] = {
    try {
      new InvokeRet(null, f())
    } catch {
      case ex: Throwable => {
        new InvokeRet(ex, null.asInstanceOf[T])
      }
    }

  }
}
