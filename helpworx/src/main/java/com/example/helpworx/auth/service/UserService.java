package com.example.helpworx.auth.service;

import com.example.helpworx.auth.dto.UserDto;
import com.example.helpworx.auth.util.SecurityUtil;
import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.repository.CtmmnyRepository;
import com.example.helpworx.users.domain.Role;
import com.example.helpworx.users.domain.UserRoleMap;
import com.example.helpworx.users.domain.Users;
import com.example.helpworx.users.dto.RegistingUser;
import com.example.helpworx.users.dto.UserDetailResponseDTO;
import com.example.helpworx.users.dto.UserInfoResponseDTO;
import com.example.helpworx.users.dto.UsersDto;
import com.example.helpworx.users.mapper.UsersMapper;
import com.example.helpworx.users.repository.RoleRepository;
import com.example.helpworx.users.repository.UserRoleMapRepository;
import com.example.helpworx.users.repository.UsersRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CtmmnyRepository ctmmnyRepository;
    private final UserRoleMapRepository userRoleMapRepository;
    public UserService(UsersRepository userRepository, RoleRepository roleRepository,CtmmnyRepository ctmmnyRepository,UserRoleMapRepository userRoleMapRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMapRepository = userRoleMapRepository;
        this.ctmmnyRepository = ctmmnyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Users signup(UserDto userDto) {

        if (usersRepository.findOneWithRolesByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다 ");
        }

        Ctmmny ctmmny;
        if (userDto.getCtmmnyCd() != null){
            ctmmny = ctmmnyRepository.findById(userDto.getCtmmnyCd()).orElse(null);
        }else{
            ctmmny = null;
        }

        Users user = Users.builder()
                .userEmail(userDto.getUserEmail())
                .userNm(userDto.getUserNm())
                .userPwd(passwordEncoder.encode(userDto.getUserPwd()))
                .userTeleno(userDto.getUserTeleno())
                .ctmmny(ctmmny)
                .isUsed(true)
                .build();

        user = usersRepository.save(user);

        Role role = roleRepository.findByRoleNm("ROLE_USER")
                .orElse(null);
        userRoleMapRepository.save(new UserRoleMap(user,role));
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<Users> getUserWithAuthorities(String userEmail) {
        return usersRepository.findOneWithRolesByUserEmail(userEmail);
    }

    @Transactional(readOnly = true)
    public Optional<UsersDto> getMyUserWithAuthorities() {
        Users user = SecurityUtil.getCurrentUserEmail().flatMap(usersRepository::findOneWithRolesByUserEmail).get();
        return Optional.ofNullable(UsersMapper.INSTANCE.EntityToDto(user));
    }

    public List<UserInfoResponseDTO> getUserInfoList(Pageable pageable, Long userId, String userNm, String ctmmnyCd, Boolean isUsed) {
        return usersRepository.findUserInfoList(pageable, userId, userNm, ctmmnyCd, isUsed).stream()
                .map(UserInfoResponseDTO ::new)
                .collect(Collectors.toList());
    }
    public UserDetailResponseDTO getUserInfo(Long userId) {
        final Users user = usersRepository.findById(userId).get();
        List<UserRoleMap> roleMaps = userRoleMapRepository.findListByUserId(userId);
        List<Long> roles = new ArrayList<>();
        for(UserRoleMap role: roleMaps) {
            roles.add(role.getRole().getId());
        }


        return UserDetailResponseDTO.builder()
                .userId(user.getId())
                .ctmmnyCd(user.getCtmmny().getId())
                .isUsed(user.getIsUsed())
                .roles(roles)
                .userEmail(user.getUserEmail())
                .userNm(user.getUserNm())
                .userPwd(user.getUserPwd())
                .userTeleno(user.getUserTeleno())
                .build();
    }

    @Transactional
    public Long updateByUserId(Long userId) {
        Users user = usersRepository.findById(userId).get();
        user.setIsUsed(false);
        return usersRepository.save(user).getId();
    }

    @Transactional
    public RegistingUser.ResponseDTO saveUser(RegistingUser.RequestDTO requestDTO) {
        final List<Role> roles = roleRepository.findAllById(requestDTO.getRoles());
        final Ctmmny ctmmny = ctmmnyRepository.findById(requestDTO.getCtmmnyCd()).get();
        final Users newUser = usersRepository.save(requestDTO.toEntity(ctmmny));
        final List<Long> roleIds = saveRoleMap(roles, newUser);

        return new RegistingUser.ResponseDTO(newUser, roleIds);
    }

    @Transactional
    public List<Long> saveRoleMap(List<Role> roles, Users newUser) {
        List<Long> roleMapIds = new ArrayList<>();
        for(Role role : roles) {
            UserRoleMap userRoleMap = UserRoleMap.builder()
                                    .role(role)
                                    .user(newUser)
                                    .build();
            Long newRoleMapId = userRoleMapRepository.save(userRoleMap).getRole().getId();
            roleMapIds.add(newRoleMapId);
        }
        return roleMapIds;
    }
}