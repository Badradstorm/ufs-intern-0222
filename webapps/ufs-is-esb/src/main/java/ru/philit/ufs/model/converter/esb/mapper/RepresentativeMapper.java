package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData;

@Mapper(uses = {IdentityDocumentMapper.class})
public interface RepresentativeMapper {

  @Mapping(source = "representative.id", target = "repID")
  @Mapping(source = "representative.birthDate", target = "dateOfBirth")
  @Mapping(source = "representative.identityDocuments", target = "identityDocumentType")
  @Mapping(source = "representative.inn", target = "INN")
  @Mapping(source = "representative.repFio", target = "repFIO")
  RepData toRepDataDto(Representative representative);

  @Mapping(source = "repData.repID", target = "id")
  @Mapping(source = "repData.dateOfBirth", target = "birthDate")
  @Mapping(source = "repData.identityDocumentType", target = "identityDocuments")
  @Mapping(source = "repData.INN", target = "inn")
  @Mapping(source = "repData.repFIO", target = "repFio")
  Representative toRepDataDto(RepData repData);
}
