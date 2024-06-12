package org.example.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.system.domain.userInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private String token;
    private userInfo userInfo;
}
