package ru.philit.ufs.model.converter.esb.mapper;

import java.math.BigInteger;
import org.mapstruct.Mapper;
import ru.philit.ufs.model.entity.user.WorkplaceType;

@Mapper
public interface WorkplaceTypeMapper {

  /**
   * Конвертирует BigInteger в WorkplaceType.
   */
  default WorkplaceType map(BigInteger value) {
    if (value.intValue() == 0) {
      return WorkplaceType.CASHBOX;
    } else if (value.intValue() == 1) {
      return WorkplaceType.UWP;
    } else {
      return WorkplaceType.OTHER;
    }
  }
}
