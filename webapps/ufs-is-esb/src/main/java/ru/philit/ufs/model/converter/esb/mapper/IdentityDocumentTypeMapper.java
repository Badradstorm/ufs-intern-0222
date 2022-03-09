package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.esb.asfs.IDDtype;

@Mapper
public interface IdentityDocumentTypeMapper {

  @ValueMapping(source = "INTERNAL_PASSPORT", target = "INTERNPASSPORT")
  IDDtype toDto(IdentityDocumentType identityDocumentType);

  @ValueMapping(source = "INTERNPASSPORT", target = "INTERNAL_PASSPORT")
  IdentityDocumentType toDto(IDDtype idDtype);
}
