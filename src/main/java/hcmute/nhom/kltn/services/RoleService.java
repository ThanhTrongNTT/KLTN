package hcmute.nhom.kltn.services;

import hcmute.nhom.kltn.dto.RoleDTO;
import hcmute.nhom.kltn.model.Role;

/**
 * Class RoleService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface RoleService extends AbstractService<RoleDTO, Role> {
    RoleDTO findByRoleName(String roleName);

    Role findByName(String roleName);
}
