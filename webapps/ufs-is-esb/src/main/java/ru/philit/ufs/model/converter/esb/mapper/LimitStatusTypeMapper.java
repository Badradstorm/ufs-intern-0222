package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;

@Mapper
public interface LimitStatusTypeMapper {

  /**
   * Конвертирует LimitStatusType в Boolean.
   */
  default Boolean map(LimitStatusType value) {
    switch (value) {
      case LIMIT_PASSED:
        return true;
      case LIMIT_ERROR:
      default:
        return false;
    }
  }
}
