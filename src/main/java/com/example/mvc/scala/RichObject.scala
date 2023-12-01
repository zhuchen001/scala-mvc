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

  /**
   * 执行函数调用，捕捉异常，包装返回
   *
   * @param f 实际执行函数
   * @tparam T 核心返回值
   */
  def ?[T](f: () => T): InvokeRet[T] = {
    invoke(f)
  }

  /**
   * 执行函数且获取执行异常，如果没有异常返回null
   * @param f
   * @tparam T
   * @return
   */
  def ??[T](f: () => T): Throwable = {
    ?(f).err
  }
}
