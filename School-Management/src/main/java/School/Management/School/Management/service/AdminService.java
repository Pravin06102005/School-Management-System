package School.Management.School.Management.service;

import School.Management.School.Management.dto.AdminDto;
import School.Management.School.Management.dto.RegisterRequest;

public interface AdminService {

    AdminDto register(RegisterRequest req);
    String login (String username, String password);
    AdminDto getProfile();
    AdminDto updateProfile(AdminDto dto);
    void deleteAccount();
}
