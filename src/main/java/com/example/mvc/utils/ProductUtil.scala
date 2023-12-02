package com.example.mvc.utils

import com.example.mvc.domain.{ProductBean, ProductDTO}

object ProductUtil {
  /**
   * 隐式函数实现DTO到PO的转换
   * @param dto
   * @return
   */
  implicit def dtoToBean(dto: ProductDTO): ProductBean = {
    ProductBean.builder()
      .id(dto.getId)
      .name(dto.getName)
      .ptype(dto.getPtype)
      .stafferId(dto.getStafferId)
      .subBeanList(dto.getSubBeanList)
      .build()
  }
}
