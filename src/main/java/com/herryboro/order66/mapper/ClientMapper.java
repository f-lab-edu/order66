package com.herryboro.order66.mapper;

import com.herryboro.order66.dto.ClientInfoDTO;
import com.herryboro.order66.dto.UpdateClientInfoDto;
import org.apache.ibatis.annotations.Mapper;

    @Mapper
    public interface ClientMapper {
        void insertMember(ClientInfoDTO user); // 회원 가입

        ClientInfoDTO getUserById(Long id);

        ClientInfoDTO getUserByClientId(String clientId);

        void updateClientInfo(UpdateClientInfoDto clientInfo);
    }
