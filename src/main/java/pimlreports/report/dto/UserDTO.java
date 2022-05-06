package pimlreports.report.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDTO {
    private Long id;
    private String name;
    private String cpf;
    private String username;
    private String email;
    private String role;
    private Long warehouseId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", warehouseId=" + warehouseId +
                '}';
    }
}
