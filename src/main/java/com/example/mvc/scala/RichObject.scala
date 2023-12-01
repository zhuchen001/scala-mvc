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
}
