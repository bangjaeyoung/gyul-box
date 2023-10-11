package jeju.oneroom.domain.user.service;

import jeju.oneroom.domain.area.entity.Area;
//import jeju.oneroom.auth.utils.CustomAuthorityUtils;
import jeju.oneroom.domain.user.dto.UserDto;
import jeju.oneroom.domain.user.entity.User;
import jeju.oneroom.domain.user.mapper.UserMapper;
import jeju.oneroom.domain.user.repository.UserRepository;
import jeju.oneroom.global.exception.BusinessLogicException;
import jeju.oneroom.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
//    private final CustomAuthorityUtils authorityUtils;

    // Oauth2를 통한 로그인 시 첫 로그인 유저만 save
    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
//            List<String> roles = authorityUtils.createRoles(user.getEmail());
//            user.setRoles(roles);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    // User의 nickname과 area에 대한 수정
    @Transactional
    public User updateUser(UserDto.Patch patchDto, Area area) {
        User findUser = verifyExistsUser(patchDto.getId());
        findUser.update(patchDto.getNickname(), area);
        return findUser;
    }

    // 단일 유저에 대한 조회
    public UserDto.Response findUser(Long userId) {
        return userMapper.userToResponseDto(verifyExistsUser(userId));
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // userId를 통한 유저 유효성 확인
    public User verifyExistsUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_USER));
    }

    // email을 통한 유저 유효성 확인
    public User verifyExistsUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_USER));
    }
}
