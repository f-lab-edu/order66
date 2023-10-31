package com.herryboro.order66.mapper;

import com.herryboro.order66.dto.ClientMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMemberMapper {
    void insertMember(ClientMemberDTO user); // 회원 가입

    ClientMemberDTO getUserById(Long id);

    ClientMemberDTO getUserByClientId(String clientId);
}
