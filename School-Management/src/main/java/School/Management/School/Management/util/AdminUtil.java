package School.Management.School.Management.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class AdminUtil {


    // returns schoolId from JWT token
    public UUID getSchoolId() {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new RuntimeException("Not authenticated");

        Object details = auth.getDetails();

        if (details instanceof Map<?, ?> map) {
            return UUID.fromString(map.get("schoolId").toString());
        }

        throw new RuntimeException("Invalid authentication details");
    }

    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }
}
