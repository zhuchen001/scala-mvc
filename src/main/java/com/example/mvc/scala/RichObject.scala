package com.example.mvc.scala

/**
 * 给Object扩展常用方法
 */
object RichObject {
  /**
   * 生成json
   *
   */
  implicit class ObjectExtensions[T](obj: T) {
    def toJson: String = JSONTools.toJson(obj)

    def ? : Option[T] = if (obj == null) None else Some(obj)
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
  def >[T](f: () => T): InvokeRet[T] = {
    invoke(f)
  }

  /**
   * 执行函数，如果异常返回true
   *
   * @param f
   * @tparam T
   * @return
   */
  def >>[T](f: () => T): Boolean = {
    val ret = >(f)
    // 默认打印日志
    if (ret.isError()) POUtils.warn("invoke function exception", ret.err)
    ret.isError()
  }
}
