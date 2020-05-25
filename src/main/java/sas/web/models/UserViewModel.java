package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {
    private String id;
    private String username;
    private String email;
    private List<String> authorities;
}
