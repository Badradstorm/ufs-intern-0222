package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData.IdentityDocumentType;

@Mapper(uses = {IdentityDocumentTypeMapper.class})
public interface IdentityDocumentMapper {

  @Mapping(source = "identityDocument.type", target = "value")
  IdentityDocumentType toDto(IdentityDocument identityDocument);
}
