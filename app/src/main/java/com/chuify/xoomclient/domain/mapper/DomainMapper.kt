package com.chuify.xoomclient.domain.mapper

interface DomainMapper<T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

   // fun mapFromDomainModel(domainModel: DomainModel): T
}