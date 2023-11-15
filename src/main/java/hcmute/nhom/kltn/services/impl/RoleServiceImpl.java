package hcmute.nhom.kltn.services.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.RoleDTO;
import hcmute.nhom.kltn.mapper.RoleMapper;
import hcmute.nhom.kltn.model.Role;
import hcmute.nhom.kltn.repository.RoleRepository;
import hcmute.nhom.kltn.services.RoleService;

/**
 * Class RoleServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends AbstractServiceImpl<RoleRepository, RoleMapper, RoleDTO, Role>
        implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private static final String SERVICE_NAME = "RoleServiceImpl";
    private final RoleRepository roleRepository;

    @Override
    public RoleDTO findByRoleName(String roleName) {
        logger.info(getMessageStart(SERVICE_NAME, "findByRoleName"));
        Role role = roleRepository.findByName(roleName).orElse(null);
        if (Objects.isNull(role)) {
            return null;
        }
        logger.info(getMessageEnd(SERVICE_NAME, "findByRoleName"));
        return (RoleDTO) getMapper().toDto(role, getCycleAvoidingMappingContext());
    }

    @Override
    public Role findByName(String roleName) {
        logger.info(getMessageStart(SERVICE_NAME, "findByName"));
        Role role = roleRepository.findByName(roleName).orElse(null);
        logger.info(getMessageEnd(SERVICE_NAME, "findByName"));
        return role;
    }

    @Override
    public RoleMapper getMapper() {
        return RoleMapper.INSTANCE;
    }

}
